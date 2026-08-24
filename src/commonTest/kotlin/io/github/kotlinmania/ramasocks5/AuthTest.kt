// port-lint: tests src/auth.rs
package io.github.kotlinmania.ramasocks5

import io.github.kotlinmania.ramasocks5.proto.SocksMethod
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthTest {
    @Test
    fun testUsernamePasswordAuthMethod() {
        val auth = Socks5Auth.fromCredentials("alice", "secret123")
        assertEquals(SocksMethod.UsernamePassword, auth.socks5Method())
        if (auth is Socks5Auth.UsernamePassword) {
            assertEquals("alice", auth.credentials.username)
            assertEquals("secret123", auth.credentials.password)
        }
    }
}
