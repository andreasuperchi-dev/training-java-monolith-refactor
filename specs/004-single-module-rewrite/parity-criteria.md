# Reporting Parity Criteria

Defines comparison rules for determining legacy-vs-rewrite equivalence.

## Core Equivalence Rules

- Inputs: same request parameters and context for both implementations.
- Output semantics: same business-significant values and grouping behavior.
- Ordering: same row order where legacy defines explicit ordering.
- Error behavior: equivalent failure path for invalid/missing required inputs.

## Numeric Comparison Rules

- Monetary and hours values must match exact arithmetic semantics of legacy outputs.
- No hidden normalization that changes displayed totals.
- Formatting differences are allowed only if business value is unchanged and representation remains contract-compatible.

## Structural Comparison Rules

- Required columns/fields must be present with equivalent meaning.
- Totals rows and summary sections must appear with equivalent semantics.
- Empty/sparse-data behavior must match legacy visibility rules.

## Report-Type Specific Checks

### Customer Bill
- Customer identity present.
- Line-level details include date/user/category/hours/rate/amount/note.
- Totals row exactness.

### Monthly Summary
- Date-window inclusion parity.
- Group-by customer parity.
- Monthly totals parity.

### Revenue Summary
- By-customer summary parity.
- By-category summary parity.
- Ordering by total revenue parity.

## Verdict Levels

- PASS: No business-significant divergence.
- FAIL: Any divergence in semantics, totals, ordering, or error behavior.
- BLOCKED: Ambiguous behavior without resolved rule reference.
