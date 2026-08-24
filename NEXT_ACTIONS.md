# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 1/27 (3.7%)
- **Function parity:** 0/235 matched (target 7) — 0.0%
- **Class/type parity:** 0/67 matched (target 35) — 0.0%
- **Combined symbol parity:** 0/302 matched (target 42) — 0.0%
- **Average inline-code cosine:** 0.00 (function body across 1 matched files)
- **Average documentation cosine:** 0.99 (doc text across 1 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 1 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. proto.enums

- **Target:** `proto.Enums`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/1 matched (target 7)
- **Missing functions:** `from`
- **Types:** 0/0 matched (target 35)
- **Missing types:** _none_

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
| `client.mod` | `client.Mod` | 0 | `src/client/mod.rs` | `client/Mod.kt` |
| `lib` | `Lib` | 0 | `src/lib.rs` | `Lib.kt` |
| `proto.mod` | `proto.Mod` | 0 | `src/proto/mod.rs` | `proto/Mod.kt` |
| `server.mod` | `server.Mod` | 0 | `src/server/mod.rs` | `server/Mod.kt` |
| `test.mod` | `server.test.Mod` | 0 | `src/server/test/mod.rs` | `server/test/Mod.kt` |
| `udp.mod` | `server.udp.Mod` | 0 | `src/server/udp/mod.rs` | `server/udp/Mod.kt` |

