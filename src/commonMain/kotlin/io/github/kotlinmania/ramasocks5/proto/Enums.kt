// port-lint: source src/proto/enums.rs
package io.github.kotlinmania.ramasocks5.proto

/**
 * A SOCKS5 protocol byte value.
 */
public sealed interface SocksByteValue {
    public val code: Int
}

/**
 * Protocol version as defined by [RFC 1928](https://datatracker.ietf.org/doc/html/rfc1928).
 */
public sealed class ProtocolVersion(override val code: Int) : SocksByteValue {
    public data object Socks5 : ProtocolVersion(0x05)
    public data class Unknown(private val rawCode: Int) : ProtocolVersion(rawCode and 0xff)

    public companion object {
        public fun fromCode(code: Int): ProtocolVersion =
            when (code and 0xff) {
                Socks5.code -> Socks5
                else -> Unknown(code)
            }
    }
}

/**
 * Subnegotiation version as defined by [RFC 1929](https://datatracker.ietf.org/doc/html/rfc1929#section-2).
 */
public sealed class UsernamePasswordSubnegotiationVersion(override val code: Int) : SocksByteValue {
    public data object One : UsernamePasswordSubnegotiationVersion(0x01)
    public data class Unknown(private val rawCode: Int) : UsernamePasswordSubnegotiationVersion(rawCode and 0xff)

    public companion object {
        public fun fromCode(code: Int): UsernamePasswordSubnegotiationVersion =
            when (code and 0xff) {
                One.code -> One
                else -> Unknown(code)
            }
    }
}

/**
 * SOCKS5 method as defined by [IANA SOCKS Methods](https://www.iana.org/assignments/socks-methods/socks-methods.xhtml).
 */
public sealed class SocksMethod(override val code: Int) : SocksByteValue {
    /**
     * No authentication required.
     *
     * Reference: [RFC 1928](https://datatracker.ietf.org/doc/html/rfc1928).
     */
    public data object NoAuthenticationRequired : SocksMethod(0x00)

    /**
     * Generic Security Services Application Program Interface.
     *
     * Reference: [RFC 1928](https://datatracker.ietf.org/doc/html/rfc1928).
     */
    public data object GSSAPI : SocksMethod(0x01)

    /**
     * Username/password authentication for SOCKS V5.
     *
     * Reference: [RFC 1929](https://datatracker.ietf.org/doc/html/rfc1929).
     */
    public data object UsernamePassword : SocksMethod(0x02)

    /**
     * Challenge-Handshake Authentication Protocol.
     *
     * Reference: Marc VanHeyingen <mailto:marcvh@aventail.com>.
     */
    public data object ChallengeHandshakeAuthenticationProtocol : SocksMethod(0x03)

    /**
     * Challenge-Response Authentication Method.
     *
     * Reference: Marc VanHeyingen <mailto:marcvh@aventail.com>.
     */
    public data object ChallengeResponseAuthenticationMethod : SocksMethod(0x05)

    /**
     * Secure Sockets Layer.
     *
     * Reference: Marc VanHeyingen <mailto:marcvh@aventail.com>.
     */
    public data object SecureSocksLayer : SocksMethod(0x06)

    /**
     * NDS Authentication.
     *
     * Reference: Vijay Talati <mailto:VTalati@novell.com>.
     */
    public data object NDSAuthentication : SocksMethod(0x07)

    /**
     * Multi-Authentication Framework.
     *
     * Reference: Vijay Talati <mailto:VTalati@novell.com>.
     */
    public data object MultiAuthenticationFramework : SocksMethod(0x08)

    /**
     * JSON Parameter Block.
     *
     * Reference: Brandon Wiley <mailto:brandon@operatorfoundation.org>.
     */
    public data object JSONParameterBlock : SocksMethod(0x09)

    /**
     * No acceptable methods.
     *
     * If the method selected by the server is `0xff`, none of the methods listed by the client
     * are acceptable, and the client must close the connection.
     *
     * Reference: [RFC 1928](https://datatracker.ietf.org/doc/html/rfc1928).
     */
    public data object NoAcceptableMethods : SocksMethod(0xff)

    public data class Unknown(private val rawCode: Int) : SocksMethod(rawCode and 0xff)

    public companion object {
        public fun fromCode(code: Int): SocksMethod =
            when (code and 0xff) {
                NoAuthenticationRequired.code -> NoAuthenticationRequired
                GSSAPI.code -> GSSAPI
                UsernamePassword.code -> UsernamePassword
                ChallengeHandshakeAuthenticationProtocol.code -> ChallengeHandshakeAuthenticationProtocol
                ChallengeResponseAuthenticationMethod.code -> ChallengeResponseAuthenticationMethod
                SecureSocksLayer.code -> SecureSocksLayer
                NDSAuthentication.code -> NDSAuthentication
                MultiAuthenticationFramework.code -> MultiAuthenticationFramework
                JSONParameterBlock.code -> JSONParameterBlock
                NoAcceptableMethods.code -> NoAcceptableMethods
                else -> Unknown(code)
            }
    }
}

/**
 * Request command.
 *
 * Reference: <https://datatracker.ietf.org/doc/html/rfc1928#section-4>.
 */
public sealed class Command(override val code: Int) : SocksByteValue {
    /**
     * Request the server to establish a connection on behalf of the client with the destination address.
     *
     * Reference: [RFC 1928](https://datatracker.ietf.org/doc/html/rfc1928).
     */
    public data object Connect : Command(0x01)

    /**
     * Used in protocols which require the client to accept connections from the server.
     *
     * FTP is a well-known example, which uses the primary client-to-server connection for commands
     * and status reports, but may use a server-to-client connection for transferring data on demand.
     *
     * Reference: [RFC 1928](https://datatracker.ietf.org/doc/html/rfc1928).
     */
    public data object Bind : Command(0x02)

    /**
     * Used to establish an association within the UDP relay process to handle UDP datagrams.
     *
     * Reference: [RFC 1929](https://datatracker.ietf.org/doc/html/rfc1929).
     */
    public data object UdpAssociate : Command(0x03)

    public data class Unknown(private val rawCode: Int) : Command(rawCode and 0xff)

    public companion object {
        public fun fromCode(code: Int): Command =
            when (code and 0xff) {
                Connect.code -> Connect
                Bind.code -> Bind
                UdpAssociate.code -> UdpAssociate
                else -> Unknown(code)
            }
    }
}

/**
 * Type of the address following it.
 *
 * Only used during encoding and decoding, but no use for the in-memory representation.
 *
 * Reference: <https://datatracker.ietf.org/doc/html/rfc1928>.
 */
public sealed class AddressType(override val code: Int) : SocksByteValue {
    /**
     * The address is a version-4 IP address, with a length of 4 octets.
     */
    public data object IpV4 : AddressType(0x01)

    /**
     * The address is a length-prefixed, maximum 255-byte domain name.
     *
     * The address field contains a fully-qualified domain name. The first octet of the address
     * field contains the number of octets of name that follow; there is no terminating NUL octet.
     */
    public data object DomainName : AddressType(0x03)

    /**
     * The address is a version-6 IP address, with a length of 16 octets.
     */
    public data object IpV6 : AddressType(0x04)

    public data class Unknown(private val rawCode: Int) : AddressType(rawCode and 0xff)

    public companion object {
        public fun fromCode(code: Int): AddressType =
            when (code and 0xff) {
                IpV4.code -> IpV4
                DomainName.code -> DomainName
                IpV6.code -> IpV6
                else -> Unknown(code)
            }
    }
}

/**
 * Indicates success or failure as the reply to a client request.
 *
 * Reference: <https://datatracker.ietf.org/doc/html/rfc1928#section-6>.
 */
public sealed class ReplyKind(override val code: Int) : SocksByteValue {
    public data object Succeeded : ReplyKind(0x00)
    public data object GeneralServerFailure : ReplyKind(0x01)
    public data object ConnectionNotAllowed : ReplyKind(0x02)
    public data object NetworkUnreachable : ReplyKind(0x03)
    public data object HostUnreachable : ReplyKind(0x04)
    public data object ConnectionRefused : ReplyKind(0x05)
    public data object TtlExpired : ReplyKind(0x06)
    public data object CommandNotSupported : ReplyKind(0x07)
    public data object AddressTypeNotSupported : ReplyKind(0x08)
    public data class Unknown(private val rawCode: Int) : ReplyKind(rawCode and 0xff)

    public companion object {
        public fun fromCode(code: Int): ReplyKind =
            when (code and 0xff) {
                Succeeded.code -> Succeeded
                GeneralServerFailure.code -> GeneralServerFailure
                ConnectionNotAllowed.code -> ConnectionNotAllowed
                NetworkUnreachable.code -> NetworkUnreachable
                HostUnreachable.code -> HostUnreachable
                ConnectionRefused.code -> ConnectionRefused
                TtlExpired.code -> TtlExpired
                CommandNotSupported.code -> CommandNotSupported
                AddressTypeNotSupported.code -> AddressTypeNotSupported
                else -> Unknown(code)
            }

        public fun fromThrowable(error: Throwable): ReplyKind =
            when {
                error.message?.contains("permission", ignoreCase = true) == true -> ConnectionNotAllowed
                error.message?.contains("host unreachable", ignoreCase = true) == true -> HostUnreachable
                error.message?.contains("network unreachable", ignoreCase = true) == true -> NetworkUnreachable
                error.message?.contains("timed out", ignoreCase = true) == true -> TtlExpired
                error.message?.contains("unexpected end", ignoreCase = true) == true -> TtlExpired
                else -> ConnectionRefused
            }
    }
}
