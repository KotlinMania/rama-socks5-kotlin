// port-lint: source proto/common.rs
package io.github.kotlinmania.ramasocks5.proto

/**
 * Low-level binary reader for SOCKS5 protocol elements.
 */
public class ByteReader(
    private val data: ByteArray,
    public var offset: Int = 0,
) {
    public val remaining: Int
        get() = data.size - offset

    public fun hasRemaining(): Boolean = remaining > 0

    public fun readU8(): Int {
        if (offset >= data.size) {
            throw ProtocolError.IO(IndexOutOfBoundsException("Unexpected end of buffer at offset $offset"))
        }
        return data[offset++].toInt() and 0xff
    }

    public fun readU16(): Int {
        val b0 = readU8()
        val b1 = readU8()
        return (b0 shl 8) or b1
    }

    public fun readExact(dest: ByteArray, destOffset: Int = 0, length: Int = dest.size) {
        if (remaining < length) {
            throw ProtocolError.IO(IndexOutOfBoundsException("Expected $length bytes, available $remaining"))
        }
        data.copyInto(dest, destOffset, offset, offset + length)
        offset += length
    }
}

/**
 * Low-level binary writer for SOCKS5 protocol elements.
 */
public class ByteWriter(
    initialCapacity: Int = 64,
) {
    private var buffer = ByteArray(initialCapacity)
    public var size: Int = 0
        private set

    public fun writeU8(value: Int) {
        ensureCapacity(size + 1)
        buffer[size++] = (value and 0xff).toByte()
    }

    public fun writeU16(value: Int) {
        ensureCapacity(size + 2)
        buffer[size++] = ((value ushr 8) and 0xff).toByte()
        buffer[size++] = (value and 0xff).toByte()
    }

    public fun writeBytes(bytes: ByteArray, offset: Int = 0, length: Int = bytes.size) {
        ensureCapacity(size + length)
        bytes.copyInto(buffer, size, offset, offset + length)
        size += length
    }

    public fun toByteArray(): ByteArray = buffer.copyOf(size)

    private fun ensureCapacity(required: Int) {
        if (required > buffer.size) {
            var newCap = buffer.size * 2
            if (newCap < required) newCap = required
            buffer = buffer.copyOf(newCap)
        }
    }
}

/**
 * Host address representation for SOCKS5 (IPv4, IPv6, or Domain Name).
 */
public sealed class Host {
    public data class Ipv4(
        public val octets: ByteArray,
    ) : Host() {
        init {
            require(octets.size == 4) { "IPv4 octets must have length 4" }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Ipv4) return false
            return octets.contentEquals(other.octets)
        }

        override fun hashCode(): Int = octets.contentHashCode()

        override fun toString(): String =
            "${octets[0].toInt() and 0xff}.${octets[1].toInt() and 0xff}.${octets[2].toInt() and 0xff}.${octets[3].toInt() and 0xff}"
    }

    public data class Ipv6(
        public val octets: ByteArray,
    ) : Host() {
        init {
            require(octets.size == 16) { "IPv6 octets must have length 16" }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Ipv6) return false
            return octets.contentEquals(other.octets)
        }

        override fun hashCode(): Int = octets.contentHashCode()

        override fun toString(): String =
            octets.joinToString(":") { (it.toInt() and 0xff).toString(16).padStart(2, '0') }
    }

    public data class Domain(
        public val name: String,
    ) : Host() {
        init {
            require(name.encodeToByteArray().size <= 255) { "Domain name length must not exceed 255 bytes" }
        }

        override fun toString(): String = name
    }

    public companion object {
        public const val EXAMPLE_NAME: String = "example.com"
    }
}

/**
 * Host authority paired with a port.
 */
public data class HostWithPort(
    public val host: Host,
    public val port: Int,
) {
    init {
        require(port in 0..65535) { "Port must be in range 0..65535, got $port" }
    }

    override fun toString(): String =
        when (host) {
            is Host.Ipv6 -> "[$host]:$port"
            else -> "$host:$port"
        }

    public companion object {
        public fun localIpv4(port: Int): HostWithPort =
            HostWithPort(Host.Ipv4(byteArrayOf(127, 0, 0, 1)), port)

        public fun defaultIpv4(port: Int): HostWithPort =
            HostWithPort(Host.Ipv4(byteArrayOf(0, 0, 0, 0)), port)

        public fun localIpv6(port: Int): HostWithPort =
            HostWithPort(
                Host.Ipv6(
                    ByteArray(16).apply {
                        this[15] = 1
                    },
                ),
                port,
            )

        public fun exampleDomainWithPort(port: Int): HostWithPort =
            HostWithPort(Host.Domain("example.com"), port)

        public fun fromDomain(domain: String, port: Int): HostWithPort =
            HostWithPort(Host.Domain(domain), port)
    }
}

/**
 * Compute the length of an authority in encoded bytes.
 */
public fun authorityLength(authority: HostWithPort): Int =
    2 + when (val h = authority.host) {
        is Host.Domain -> 1 + h.name.encodeToByteArray().size
        is Host.Ipv4 -> 4
        is Host.Ipv6 -> 16
    }

/**
 * Read the authority from a SOCKS5 protocol byte stream.
 */
public fun readAuthority(reader: ByteReader): HostWithPort {
    val addressTypeCode = reader.readU8()
    val host: Host =
        when (val addressType = AddressType.fromCode(addressTypeCode)) {
            AddressType.IpV4 -> {
                val array = ByteArray(4)
                reader.readExact(array)
                Host.Ipv4(array)
            }
            AddressType.DomainName -> {
                val n = reader.readU8()
                if (n == 0) {
                    throw ProtocolError.UnexpectedByte(4, n.toUByte())
                }
                val raw = ByteArray(n)
                reader.readExact(raw)
                Host.Domain(raw.decodeToString())
            }
            AddressType.IpV6 -> {
                val array = ByteArray(16)
                reader.readExact(array)
                Host.Ipv6(array)
            }
            is AddressType.Unknown -> {
                throw ProtocolError.UnexpectedByte(3, addressTypeCode.toUByte())
            }
        }
    val port = reader.readU16()
    return HostWithPort(host, port)
}

/**
 * Write the authority into a binary buffer.
 */
public fun writeAuthorityToBuf(authority: HostWithPort, writer: ByteWriter) {
    when (val h = authority.host) {
        is Host.Domain -> {
            writer.writeU8(AddressType.DomainName.code)
            val bytes = h.name.encodeToByteArray()
            writer.writeU8(bytes.size)
            writer.writeBytes(bytes)
        }
        is Host.Ipv4 -> {
            writer.writeU8(AddressType.IpV4.code)
            writer.writeBytes(h.octets)
        }
        is Host.Ipv6 -> {
            writer.writeU8(AddressType.IpV6.code)
            writer.writeBytes(h.octets)
        }
    }
    writer.writeU16(authority.port)
}
