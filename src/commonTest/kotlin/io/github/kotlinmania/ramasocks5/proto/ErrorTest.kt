// port-lint: tests rama-socks5/src/proto/error.rs
package io.github.kotlinmania.ramasocks5.proto

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ErrorTest {
    @Test
    fun testUnexpectedByteDisplay() {
        val err = ProtocolError.unexpectedByte(4, 0x05u)
        assertTrue(err.message!!.contains("unexpected byte"))
        assertTrue(err.message!!.contains("at position 4"))
    }

    @Test
    fun testIoAndUtf8Errors() {
        val ioErr = ProtocolError.IO(IllegalStateException("connection reset"))
        assertTrue(ioErr.message!!.contains("I/O: connection reset"))
        assertEquals("connection reset", ioErr.cause?.message)

        val utf8Err = ProtocolError.Utf8(IllegalArgumentException("invalid utf8"))
        assertTrue(utf8Err.message!!.contains("utf-8 conversion: invalid utf8"))
    }
}
