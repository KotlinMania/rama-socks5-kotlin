// port-lint: tests proto/client.rs
package io.github.kotlinmania.ramasocks5.proto

import kotlin.test.Test
import kotlin.test.assertEquals

class ClientTest {
    @Test
    fun testClientHeaderWriteReadEq() {
        val header =
            ClientHeader(
                version = ProtocolVersion.Socks5,
                methods = listOf(SocksMethod.NoAuthenticationRequired, SocksMethod.UsernamePassword),
            )
        val decoded = ClientHeader.readFromBytes(header.writeToBytes())
        assertEquals(header, decoded)
    }

    @Test
    fun testClientRequestWriteReadEq() {
        val req1 = ClientRequest.connect(HostWithPort.exampleDomainWithPort(8080))
        val decoded1 = ClientRequest.readFromBytes(req1.writeToBytes())
        assertEquals(req1, decoded1)

        val req2 = ClientRequest.bind(HostWithPort.localIpv4(1080))
        val decoded2 = ClientRequest.readFromBytes(req2.writeToBytes())
        assertEquals(req2, decoded2)

        val req3 = ClientRequest.udpAssociate(HostWithPort.localIpv6(53))
        val decoded3 = ClientRequest.readFromBytes(req3.writeToBytes())
        assertEquals(req3, decoded3)
    }

    @Test
    fun testUsernamePasswordRequestWriteReadEq() {
        val req1 =
            UsernamePasswordRequest(
                version = UsernamePasswordSubnegotiationVersion.One,
                username = "alice",
                password = "secretpassword",
            )
        val decoded1 = UsernamePasswordRequest.readFromBytes(req1.writeToBytes())
        assertEquals(req1, decoded1)

        val req2 =
            UsernamePasswordRequest(
                version = UsernamePasswordSubnegotiationVersion.One,
                username = "bob",
                password = null,
            )
        val decoded2 = UsernamePasswordRequest.readFromBytes(req2.writeToBytes())
        assertEquals(req2, decoded2)
    }
}
