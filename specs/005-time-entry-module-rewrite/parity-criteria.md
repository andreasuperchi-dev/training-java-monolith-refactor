# Time Entry Parity Criteria

## Assertions

1. Required input outcomes are equivalent for customer, user, category, hours.
2. Missing date behavior is equivalent and defaults to current date.
3. Invalid values handling is equivalent for non-positive hours and future date.
4. Weekend behavior remains warning-oriented and does not drift.
5. Valid entry persistence success semantics are equivalent.
6. Error message category and severity remain contract-equivalent.

## Evidence Requirements

- Each parity assertion references `BR-*` anchors.
- Each assertion is demonstrated in integration and/or contract tests.
- Any unresolved policy ambiguity is tied to blocker IDs.
