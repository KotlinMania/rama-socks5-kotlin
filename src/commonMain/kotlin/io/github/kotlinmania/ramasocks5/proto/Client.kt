// port-lint: source proto/client.rs
package io.github.kotlinmania.ramasocks5.proto

/**
 * The client connects to the server, and sends a header which
 * contains the protocol version desired and SOCKS methods supported by the client.
 */
public data class ClientHeader(
    public val version: ProtocolVersion = ProtocolVersion.Socks5,
    public val methods: List<SocksMethod>,
) {
    init {
        require(methods.isNotEmpty()) { "Methods must not be empty" }
        require(methods.size <= 255) { "Methods count must not exceed 255" }
    }

    public fun writeToBuf(writer: ByteWriter) {
        writer.writeU8(version.code)
        writer.writeU8(methods.size)
        for (m in methods) {
            writer.writeU8(m.code)
        }
    }

    public fun writeToBytes(): ByteArray {
        val writer = ByteWriter(2 + methods.size)
        writeToBuf(writer)
        return writer.toByteArray()
    }

    public companion object {
        public fun readFrom(reader: ByteReader): ClientHeader {
            val verCode = reader.readU8()
            val version = ProtocolVersion.fromCode(verCode)
            if (version !is ProtocolVersion.Socks5) {
                throw ProtocolError.UnexpectedByte(0, verCode.toUByte())
            }

            val count = reader.readU8()
            if (count == 0) {
                throw ProtocolError.UnexpectedByte(1, count.toUByte())
            }

            val methods = ArrayList<SocksMethod>(count)
            for (i in 0 until count) {
                methods.add(SocksMethod.fromCode(reader.readU8()))
            }
            return ClientHeader(version, methods)
        }

        public fun readFromBytes(bytes: ByteArray): ClientHeader =
            readFrom(ByteReader(bytes))
    }
}

/**
 * The SOCKS request sent by the client.
 */
public data class ClientRequest(
    public val version: ProtocolVersion = ProtocolVersion.Socks5,
    public val command: Command,
    public val destination: HostWithPort,
) {
    public fun writeToBuf(writer: ByteWriter) {
        writer.writeU8(version.code)
        writer.writeU8(command.code)
        writer.writeU8(0) // RSV
        writeAuthorityToBuf(destination, writer)
    }

    public fun writeToBytes(): ByteArray {
        val writer = ByteWriter(4 + authorityLength(destination))
        writeToBuf(writer)
        return writer.toByteArray()
    }

    public companion object {
        public fun connect(destination: HostWithPort): ClientRequest =
            ClientRequest(ProtocolVersion.Socks5, Command.Connect, destination)

        public fun bind(destination: HostWithPort): ClientRequest =
            ClientRequest(ProtocolVersion.Socks5, Command.Bind, destination)

        public fun udpAssociate(destination: HostWithPort): ClientRequest =
            ClientRequest(ProtocolVersion.Socks5, Command.UdpAssociate, destination)

        public fun readFrom(reader: ByteReader): ClientRequest {
            val verCode = reader.readU8()
            val version = ProtocolVersion.fromCode(verCode)
            if (version !is ProtocolVersion.Socks5) {
                throw ProtocolError.UnexpectedByte(0, verCode.toUByte())
            }

            val cmdCode = reader.readU8()
            val command = Command.fromCode(cmdCode)

            val rsv = reader.readU8()
            if (rsv != 0) {
                throw ProtocolError.UnexpectedByte(2, rsv.toUByte())
            }

            val destination = readAuthority(reader)
            return ClientRequest(version, command, destination)
        }

        public fun readFromBytes(bytes: ByteArray): ClientRequest =
            readFrom(ByteReader(bytes))
    }
}

/**
 * Initial username-password negotiation request sent by the client.
 */
public data class UsernamePasswordRequest(
    public val version: UsernamePasswordSubnegotiationVersion = UsernamePasswordSubnegotiationVersion.One,
    public val username: String,
    public val password: String? = null,
) {
    public fun writeToBuf(writer: ByteWriter) {
        val unameBytes = username.encodeToByteArray()
        val pwdBytes = password?.encodeToByteArray() ?: byteArrayOf()
        require(unameBytes.isNotEmpty() && unameBytes.size <= 255) { "Username length must be between 1 and 255" }
        require(pwdBytes.size <= 255) { "Password length must not exceed 255" }

        writer.writeU8(version.code)
        writer.writeU8(unameBytes.size)
        writer.writeBytes(unameBytes)
        writer.writeU8(pwdBytes.size)
        if (pwdBytes.isNotEmpty()) {
            writer.writeBytes(pwdBytes)
        }
    }

    public fun writeToBytes(): ByteArray {
        val unameBytes = username.encodeToByteArray()
        val pwdBytes = password?.encodeToByteArray() ?: byteArrayOf()
        val writer = ByteWriter(3 + unameBytes.size + pwdBytes.size)
        writeToBuf(writer)
        return writer.toByteArray()
    }

    public companion object {
        public fun readFrom(reader: ByteReader): UsernamePasswordRequest {
            val verCode = reader.readU8()
            val version = UsernamePasswordSubnegotiationVersion.fromCode(verCode)
            if (version !is UsernamePasswordSubnegotiationVersion.One) {
                throw ProtocolError.UnexpectedByte(0, verCode.toUByte())
            }

            val ulen = reader.readU8()
            if (ulen == 0) {
                throw ProtocolError.UnexpectedByte(1, ulen.toUByte())
            }

            val unameBytes = ByteArray(ulen)
            reader.readExact(unameBytes)
            val username = unameBytes.decodeToString()

            val plen = reader.readU8()
            val password =
                if (plen == 0) {
                    null
                } else {
                    val pwdBytes = ByteArray(plen)
                    reader.readExact(pwdBytes)
                    pwdBytes.decodeToString()
                }

            return UsernamePasswordRequest(version, username, password)
        }

        public fun readFromBytes(bytes: ByteArray): UsernamePasswordRequest =
            readFrom(ByteReader(bytes))
    }
}
