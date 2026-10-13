// port-lint: tests proto/common.rs
package io.github.kotlinmania.ramasocks5.proto

import io.github.kotlinmania.bytes.BytesMut
import io.github.kotlinmania.prettyassertions.assertEq
import kotlin.test.Test

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
            assertEq(expectedLength, length)
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
            assertEq(auth, decoded)

            val bufMut = BytesMut.withCapacity(64)
            writeAuthorityToBuf(auth, bufMut)
            val buf = bufMut.freeze()
            val decodedFromBuf = readAuthority(buf)
            assertEq(auth, decodedFromBuf)
        }
    }
}
