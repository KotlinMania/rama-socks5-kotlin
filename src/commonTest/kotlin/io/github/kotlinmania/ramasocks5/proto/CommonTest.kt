// port-lint: tests proto/common.rs
package io.github.kotlinmania.ramasocks5.proto

import kotlin.test.Test
import kotlin.test.assertEquals

class CommonTest {
    @Test
    fun testAuthorityLength() {
        val testCases =
            listOf(
                HostWithPort.localIpv4(1248) to (4 + 2),
                HostWithPort.localIpv6(42) to (16 + 2),
                HostWithPort(Host.Domain(Host.EXAMPLE_NAME), 1) to (1 + 11 + 2),
            )

        for ((authority, expectedLength) in testCases) {
            val length = authorityLength(authority)
            assertEquals(expectedLength, length, "authority: $authority")
        }
    }

    @Test
    fun testAuthorityWriteReadEq() {
        val authorities =
            listOf(
                HostWithPort.localIpv4(1),
                HostWithPort.localIpv6(42),
                HostWithPort.exampleDomainWithPort(1450),
            )

        for (auth in authorities) {
            val writer = ByteWriter()
            writeAuthorityToBuf(auth, writer)
            val bytes = writer.toByteArray()
            val reader = ByteReader(bytes)
            val decoded = readAuthority(reader)
            assertEquals(auth, decoded)
        }
    }
}
