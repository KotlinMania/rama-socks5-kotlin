// port-lint: source rama-socks5/src/proto/udp.rs
package io.github.kotlinmania.ramasocks5.proto

/**
 * Layout for a header sent by a UDP Client (as request) and UDP server (as response),
 * for any datagram (to be) relayed by the proxy.
 */
public data class UdpHeader(
    public val fragmentNumber: Int = 0,
    public val destination: HostWithPort,
) {
    init {
        require(fragmentNumber in 0..255) { "Fragment number must be in range 0..255, got $fragmentNumber" }
    }

    public fun writeToBuf(writer: ByteWriter) {
        writer.writeU16(0) // RSV
        writer.writeU8(fragmentNumber)
        writeAuthorityToBuf(destination, writer)
    }

    public fun writeToBytes(): ByteArray {
        val writer = ByteWriter(5 + authorityLength(destination))
        writeToBuf(writer)
        return writer.toByteArray()
    }

    public companion object {
        public fun readFrom(reader: ByteReader): UdpHeader {
            val rsv = reader.readU16()
            if (rsv != 0) {
                // SOCKS5 RFC allows non-zero reserved in some contexts, but typical parser passes it
            }

            val fragmentNumber = reader.readU8()
            val destination = readAuthority(reader)

            return UdpHeader(fragmentNumber, destination)
        }

        public fun readFromBytes(bytes: ByteArray): UdpHeader =
            readFrom(ByteReader(bytes))
    }
}
