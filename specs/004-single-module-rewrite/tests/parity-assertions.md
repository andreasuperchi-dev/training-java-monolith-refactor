# Reporting Parity Assertions (US2)

## Assertion Categories

### A1 Structural Assertions
- Required columns/sections are present and semantically equivalent.
- Totals rows/summary sections are present where legacy includes them.

### A2 Arithmetic Assertions
- Customer bill line and total arithmetic matches exactly.
- Monthly and revenue aggregates match legacy arithmetic behavior.

### A3 Ordering Assertions
- Customer bill rows ordered by date descending.
- Monthly rows ordered by total amount descending.
- Revenue tables ordered by total revenue descending.

### A4 Error-Path Assertions
- Invalid/missing required input behavior remains equivalent.
- No new user-visible error semantics introduced.

### A5 Contract Drift Assertions
- Inputs/outputs align with reporting query contract.
- No externally visible response drift during switch.

## Verdict Rule

- PASS: all assertions pass
- FAIL: any assertion mismatch
- BLOCKED: ambiguity unresolved or insufficient evidence
