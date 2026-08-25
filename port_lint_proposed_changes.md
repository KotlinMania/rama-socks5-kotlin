# port-lint Proposed Changes

**Generated:** 2026-08-25
**Source:** tmp/rama-socks5/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/ramasocks5

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/ramasocks5/Auth.kt` | `// port-lint: source src/auth.rs` | `// port-lint: source auth.rs` | `auth.rs` | `port-lint provenance header matched only after fallback normalization: 'src/auth.rs' vs expected 'auth.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/ramasocks5/AuthTest.kt` | `// port-lint: tests src/auth.rs` | `// port-lint: tests auth.rs` | `auth.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:src/auth.rs' vs expected 'auth.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/ramasocks5/proto/Enums.kt` | `// port-lint: source src/proto/enums.rs` | `// port-lint: source proto/enums.rs` | `proto/enums.rs` | `port-lint provenance header matched only after fallback normalization: 'src/proto/enums.rs' vs expected 'proto/enums.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/ramasocks5/Lib.kt` | `// port-lint: source src/lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'src/lib.rs' vs expected 'lib.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/ramasocks5/LibTest.kt` | `// port-lint: tests src/lib.rs` | `// port-lint: tests lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:src/lib.rs' vs expected 'lib.rs'` |
