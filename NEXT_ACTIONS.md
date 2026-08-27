# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 4/27 (14.8%)
- **Function parity:** 2/266 matched (target 17) — 0.8%
- **Class/type parity:** 2/80 matched (target 48) — 2.5%
- **Combined symbol parity:** 4/346 matched (target 65) — 1.2%
- **Average inline-code cosine:** 0.20 (function body across 3 matched files)
- **Average documentation cosine:** 0.91 (doc text across 3 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 4 files with <0.60 function similarity

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

### 2. rama-socks5.auth

- **Target:** `ramasocks5.Auth`
- **Similarity:** 0.46
- **Dependents:** 0
- **Priority Score:** 10305.4
- **Functions:** 1/2 matched (target 3)
- **Missing functions:** `from`
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_

### 3. proto.enums

- **Target:** `proto.Enums`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/1 matched (target 10)
- **Missing functions:** `from`
- **Types:** 0/0 matched (target 36)
- **Missing types:** _none_

### 4. rama-socks5.lib

- **Target:** `ramasocks5.Lib [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 1)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

