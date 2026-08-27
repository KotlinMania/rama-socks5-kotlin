// port-lint: tests rama-socks5/src/proto/enums.rs
package io.github.kotlinmania.ramasocks5.proto

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class EnumsTest {
    @Test
    fun socksMethodCodesRoundTrip() {
        assertEquals(0x00, SocksMethod.NoAuthenticationRequired.code)
        assertEquals(0x02, SocksMethod.UsernamePassword.code)
        assertEquals(0xff, SocksMethod.NoAcceptableMethods.code)
        assertEquals(SocksMethod.UsernamePassword, SocksMethod.fromCode(0x02))
        assertIs<SocksMethod.Unknown>(SocksMethod.fromCode(0x04))
    }

    @Test
    fun commandAndAddressTypeCodesRoundTrip() {
        assertEquals(Command.Connect, Command.fromCode(0x01))
        assertEquals(Command.Bind, Command.fromCode(0x02))
        assertEquals(Command.UdpAssociate, Command.fromCode(0x03))
        assertEquals(AddressType.DomainName, AddressType.fromCode(0x03))
        assertIs<AddressType.Unknown>(AddressType.fromCode(0x7f))
    }

    @Test
    fun replyKindMapsCodesAndErrors() {
        assertEquals(ReplyKind.Succeeded, ReplyKind.fromCode(0x00))
        assertEquals(ReplyKind.AddressTypeNotSupported, ReplyKind.fromCode(0x08))
        assertIs<ReplyKind.Unknown>(ReplyKind.fromCode(0x09))
        assertEquals(ReplyKind.ConnectionNotAllowed, ReplyKind.fromThrowable(Throwable("permission denied")))
        assertEquals(ReplyKind.TtlExpired, ReplyKind.fromThrowable(Throwable("timed out")))
        assertEquals(ReplyKind.ConnectionRefused, ReplyKind.fromThrowable(Throwable("refused")))
    }
}
