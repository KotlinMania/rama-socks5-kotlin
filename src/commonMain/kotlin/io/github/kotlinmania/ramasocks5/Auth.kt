// port-lint: source auth.rs
package io.github.kotlinmania.ramasocks5

import io.github.kotlinmania.ramasocks5.proto.SocksMethod

/**
 * Basic user credentials model.
 */
public data class BasicCredentials(
    public val username: String,
    public val password: String,
)

/**
 * Authentication modes supported for SOCKS V5.
 */
public sealed class Socks5Auth {
    /**
     * Username/Password Authentication for SOCKS V5 ([RFC 1929](https://datatracker.ietf.org/doc/html/rfc1929)).
     */
    public data class UsernamePassword(
        public val credentials: BasicCredentials,
    ) : Socks5Auth()

    /**
     * Return the [SocksMethod] linked to this authentication type.
     */
    public fun socks5Method(): SocksMethod =
        when (this) {
            is UsernamePassword -> SocksMethod.UsernamePassword
        }

    public companion object {
        public fun fromCredentials(username: String, password: String): Socks5Auth =
            UsernamePassword(BasicCredentials(username, password))
    }
}
