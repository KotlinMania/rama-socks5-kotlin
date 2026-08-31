// port-lint: source rama-socks5/src/proto/error.rs
package io.github.kotlinmania.ramasocks5.proto

/**
 * Protocol errors encountered during SOCKS5 operations.
 */
public sealed class ProtocolError(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {
    /**
     * An I/O Error during reading or writing of data.
     */
    public class IO(
        cause: Throwable,
    ) : ProtocolError("protocol error: I/O: ${cause.message}", cause)

    /**
     * Unexpected byte at the paired position.
     */
    public class UnexpectedByte(
        public val pos: Int,
        public val byte: UByte,
    ) : ProtocolError("protocol error: unexpected byte x'${byte.toString(16)}' at position $pos")

    /**
     * Unexpected error happened.
     */
    public class Unexpected(
        cause: Throwable,
    ) : ProtocolError("protocol error: unexpected: ${cause.message}", cause)

    /**
     * Utf-8 error in case something went wrong during bytes to utf-8 conversion.
     */
    public class Utf8(
        cause: Throwable,
    ) : ProtocolError("protocol error: utf-8 conversion: ${cause.message}", cause)

    public companion object {
        public fun unexpectedByte(pos: Int, byte: UByte): ProtocolError =
            UnexpectedByte(pos, byte)
    }
}
