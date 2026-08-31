// port-lint: source rama-socks5/src/proto/server.rs
package io.github.kotlinmania.ramasocks5.proto

/**
 * The server selects from one of the methods given in METHODS, and
 * sends a header back containing the selected METHOD and same Protocol version.
 */
public data class ServerHeader(
    public val version: ProtocolVersion = ProtocolVersion.Socks5,
    public val method: SocksMethod,
) {
    public fun writeToBuf(writer: ByteWriter) {
        writer.writeU8(version.code)
        writer.writeU8(method.code)
    }

    public fun writeToBytes(): ByteArray {
        val writer = ByteWriter(2)
        writeToBuf(writer)
        return writer.toByteArray()
    }

    public companion object {
        public fun readFrom(reader: ByteReader): ServerHeader {
            val verCode = reader.readU8()
            val version = ProtocolVersion.fromCode(verCode)
            if (version !is ProtocolVersion.Socks5) {
                throw ProtocolError.UnexpectedByte(0, verCode.toUByte())
            }
            val methodCode = reader.readU8()
            val method = SocksMethod.fromCode(methodCode)
            return ServerHeader(version, method)
        }

        public fun readFromBytes(bytes: ByteArray): ServerHeader =
            readFrom(ByteReader(bytes))
    }
}

/**
 * Sent by the server as a reply to an earlier client request.
 */
public data class ServerReply(
    public val version: ProtocolVersion = ProtocolVersion.Socks5,
    public val reply: ReplyKind,
    public val bindAddress: HostWithPort,
) {
    public fun writeToBuf(writer: ByteWriter) {
        writer.writeU8(version.code)
        writer.writeU8(reply.code)
        writer.writeU8(0) // RSV
        writeAuthorityToBuf(bindAddress, writer)
    }

    public fun writeToBytes(): ByteArray {
        val writer = ByteWriter(4 + authorityLength(bindAddress))
        writeToBuf(writer)
        return writer.toByteArray()
    }

    public companion object {
        public fun success(addr: HostWithPort): ServerReply =
            ServerReply(ProtocolVersion.Socks5, ReplyKind.Succeeded, addr)

        public fun errorReply(kind: ReplyKind): ServerReply =
            ServerReply(ProtocolVersion.Socks5, kind, HostWithPort.defaultIpv4(0))

        public fun readFrom(reader: ByteReader): ServerReply {
            val verCode = reader.readU8()
            val version = ProtocolVersion.fromCode(verCode)
            if (version !is ProtocolVersion.Socks5) {
                throw ProtocolError.UnexpectedByte(0, verCode.toUByte())
            }

            val replyCode = reader.readU8()
            val reply = ReplyKind.fromCode(replyCode)

            val rsv = reader.readU8()
            if (rsv != 0) {
                throw ProtocolError.UnexpectedByte(2, rsv.toUByte())
            }

            val bindAddress = readAuthority(reader)
            return ServerReply(version, reply, bindAddress)
        }

        public fun readFromBytes(bytes: ByteArray): ServerReply =
            readFrom(ByteReader(bytes))
    }
}

/**
 * Response to the username-password request sent by the client.
 */
public data class UsernamePasswordResponse(
    public val version: UsernamePasswordSubnegotiationVersion = UsernamePasswordSubnegotiationVersion.One,
    public val status: Int,
) {
    public val isSuccess: Boolean
        get() = status == 0

    public fun writeToBuf(writer: ByteWriter) {
        writer.writeU8(version.code)
        writer.writeU8(status)
    }

    public fun writeToBytes(): ByteArray {
        val writer = ByteWriter(2)
        writeToBuf(writer)
        return writer.toByteArray()
    }

    public companion object {
        public fun newSuccess(): UsernamePasswordResponse =
            UsernamePasswordResponse(UsernamePasswordSubnegotiationVersion.One, 0)

        public fun newInvalidCredentials(): UsernamePasswordResponse =
            UsernamePasswordResponse(UsernamePasswordSubnegotiationVersion.One, 1)

        public fun newUserNotFound(): UsernamePasswordResponse =
            UsernamePasswordResponse(UsernamePasswordSubnegotiationVersion.One, 2)

        public fun newAuthSystemUnavailable(): UsernamePasswordResponse =
            UsernamePasswordResponse(UsernamePasswordSubnegotiationVersion.One, 4)

        public fun readFrom(reader: ByteReader): UsernamePasswordResponse {
            val verCode = reader.readU8()
            val version = UsernamePasswordSubnegotiationVersion.fromCode(verCode)
            if (version !is UsernamePasswordSubnegotiationVersion.One) {
                throw ProtocolError.UnexpectedByte(0, verCode.toUByte())
            }
            val status = reader.readU8()
            return UsernamePasswordResponse(version, status)
        }

        public fun readFromBytes(bytes: ByteArray): UsernamePasswordResponse =
            readFrom(ByteReader(bytes))
    }
}
