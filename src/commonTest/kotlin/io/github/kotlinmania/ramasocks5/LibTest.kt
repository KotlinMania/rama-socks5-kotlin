// port-lint: tests lib.rs
package io.github.kotlinmania.ramasocks5

import kotlin.test.Test
import kotlin.test.assertEquals

class LibTest {
    @Test
    fun testRamaSocks5Version() {
        assertEquals("0.2.0", RamaSocks5.VERSION)
    }
}
