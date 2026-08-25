# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 3/27 (11.1%)
- **Function parity:** 1/235 matched (target 11) — 0.4%
- **Class/type parity:** 1/67 matched (target 41) — 1.5%
- **Combined symbol parity:** 2/302 matched (target 52) — 0.7%
- **Average inline-code cosine:** 0.23 (function body across 2 matched files)
- **Average documentation cosine:** 0.91 (doc text across 2 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 3 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. auth

- **Target:** `ramasocks5.Auth [PROVENANCE-FALLBACK]`
- **Similarity:** 0.46
- **Dependents:** 0
- **Priority Score:** 10305.4
- **Functions:** 1/2 matched (target 3)
- **Missing functions:** `from`
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/auth.rs` vs expected `auth.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:src/auth.rs` vs expected `auth.rs`
- **Proposed provenance header:** `// port-lint: source auth.rs` (current: `// port-lint: source src/auth.rs`)
- **Proposed provenance header:** `// port-lint: tests auth.rs` (current: `// port-lint: tests src/auth.rs`)
- **Lint issues:** 2

### 2. proto.enums

- **Target:** `proto.Enums [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/1 matched (target 7)
- **Missing functions:** `from`
- **Types:** 0/0 matched (target 35)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/proto/enums.rs` vs expected `proto/enums.rs`
- **Proposed provenance header:** `// port-lint: source proto/enums.rs` (current: `// port-lint: source src/proto/enums.rs`)
- **Lint issues:** 1

### 3. lib

- **Target:** `ramasocks5.Lib [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 1)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:src/lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source src/lib.rs`)
- **Proposed provenance header:** `// port-lint: tests lib.rs` (current: `// port-lint: tests src/lib.rs`)
- **Lint issues:** 2

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `client.mod` | `client.Mod` | 0 | `client/mod.rs` | `client/Mod.kt` |
| `proto.mod` | `proto.Mod` | 0 | `proto/mod.rs` | `proto/Mod.kt` |
| `server.mod` | `server.Mod` | 0 | `server/mod.rs` | `server/Mod.kt` |
| `test.mod` | `server.test.Mod` | 0 | `server/test/mod.rs` | `server/test/Mod.kt` |
| `udp.mod` | `server.udp.Mod` | 0 | `server/udp/mod.rs` | `server/udp/Mod.kt` |

