// port-lint: tests proto/server.rs
package io.github.kotlinmania.ramasocks5.proto

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ServerTest {
    @Test
    fun testHeaderWriteReadEq() {
        val header = ServerHeader(ProtocolVersion.Socks5, SocksMethod.JSONParameterBlock)
        val bytes = header.writeToBytes()
        val decoded = ServerHeader.readFromBytes(bytes)
        assertEquals(header, decoded)
    }

    @Test
    fun testReplyWriteReadEq() {
        val reply1 = ServerReply(
            version = ProtocolVersion.Socks5,
            reply = ReplyKind.Succeeded,
            bindAddress = HostWithPort.defaultIpv4(4128),
        )
        val decoded1 = ServerReply.readFromBytes(reply1.writeToBytes())
        assertEquals(reply1, decoded1)

        val reply2 = ServerReply.errorReply(ReplyKind.ConnectionNotAllowed)
        val decoded2 = ServerReply.readFromBytes(reply2.writeToBytes())
        assertEquals(reply2, decoded2)
    }

    @Test
    fun testUsernamePasswordResponseWriteReadEq() {
        val resp1 = UsernamePasswordResponse.newSuccess()
        val decoded1 = UsernamePasswordResponse.readFromBytes(resp1.writeToBytes())
        assertEquals(resp1, decoded1)
        assertTrue(decoded1.isSuccess)

        val resp2 = UsernamePasswordResponse.newInvalidCredentials()
        val decoded2 = UsernamePasswordResponse.readFromBytes(resp2.writeToBytes())
        assertEquals(resp2, decoded2)
        assertTrue(!decoded2.isSuccess)
    }
}
