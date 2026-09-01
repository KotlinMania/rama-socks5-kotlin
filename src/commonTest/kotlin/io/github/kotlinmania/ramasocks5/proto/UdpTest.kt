// port-lint: tests proto/udp.rs
package io.github.kotlinmania.ramasocks5.proto

import kotlin.test.Test
import kotlin.test.assertEquals

class UdpTest {
    @Test
    fun testUdpPacketWriteReadEq() {
        val header =
            UdpHeader(
                fragmentNumber = 2,
                destination = HostWithPort.localIpv6(45),
            )
        val bytes = header.writeToBytes()
        val decoded = UdpHeader.readFromBytes(bytes)
        assertEquals(header, decoded)
    }
}
