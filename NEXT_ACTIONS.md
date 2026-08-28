# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 8/27 (29.6%)
- **Function parity:** 23/240 matched (target 87) — 9.6%
- **Class/type parity:** 5/80 matched (target 64) — 6.2%
- **Combined symbol parity:** 28/320 matched (target 151) — 8.8%
- **Average inline-code cosine:** 0.18 (function body across 8 matched files)
- **Average documentation cosine:** 0.61 (doc text across 8 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 8 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. proto.error

- **Target:** `proto.Error`
- **Similarity:** 0.13
- **Dependents:** 4
- **Priority Score:** 4030508.8
- **Functions:** 1/4 matched (target 3)
- **Missing functions:** `fmt`, `source`, `from`
- **Types:** 1/1 matched (target 6)
- **Missing types:** _none_

### 2. proto.client

- **Target:** `proto.Client`
- **Similarity:** 0.13
- **Dependents:** 0
- **Priority Score:** 101408.7
- **Functions:** 3/9 matched (target 18)
- **Missing functions:** `new`, `write_to`, `serialized_len`, `eq`, `test_header_write_read_eq`, `test_request_write_read_eq`
- **Types:** 1/5 matched (target 4)
- **Missing types:** `Header`, `Request`, `RequestRef`, `UsernamePasswordRequestRef`
- **Tests:** 1/3 matched

### 3. proto.common

- **Target:** `proto.Common`
- **Similarity:** 0.23
- **Dependents:** 0
- **Priority Score:** 91407.7
- **Functions:** 5/12 matched (target 27)
- **Missing functions:** `from`, `read_authority_sync`, `read_from`, `write_to`, `test_authority_write_read_sync_eq`, `read_from_sync`, `write_to_sync`
- **Types:** 0/2 matched (target 8)
- **Missing types:** `ReadError`, `SocksAuthority`
- **Tests:** 2/7 matched

### 4. proto.server

- **Target:** `proto.Server`
- **Similarity:** 0.33
- **Dependents:** 0
- **Priority Score:** 61706.7
- **Functions:** 10/14 matched (target 21)
- **Missing functions:** `new`, `write_to`, `serialized_len`, `new_invalid_credentails`
- **Types:** 1/3 matched (target 4)
- **Missing types:** `Header`, `Reply`
- **Tests:** 3/3 matched

### 5. proto.udp

- **Target:** `proto.Udp`
- **Similarity:** 0.19
- **Dependents:** 0
- **Priority Score:** 50908.1
- **Functions:** 3/8 matched (target 5)
- **Missing functions:** `read_from_sync`, `serialized_len`, `write_to`, `write_to_sync`, `test_udp_packet_write_read_sync_eq`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/4 matched

### 6. auth

- **Target:** `ramasocks5.Auth`
- **Similarity:** 0.46
- **Dependents:** 0
- **Priority Score:** 10305.4
- **Functions:** 1/2 matched (target 3)
- **Missing functions:** `from`
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_

### 7. proto.enums

- **Target:** `proto.Enums`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/1 matched (target 10)
- **Missing functions:** `from`
- **Types:** 0/0 matched (target 36)
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

### Matched

| Source | Target | Path |
|--------|--------|------|
| `lib` | `ramasocks5.Lib` | `lib` |

