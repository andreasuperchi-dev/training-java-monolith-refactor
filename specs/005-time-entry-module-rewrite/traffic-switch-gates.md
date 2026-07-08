# Time Entry Traffic Switch Gates

## Gate Definition

| Gate | Description | Pass Condition |
|------|-------------|----------------|
| G1 | Unit parity baseline | US1 unit suite passes |
| G2 | Integration parity baseline | US2 integration parity suite passes |
| G3 | Interface contract parity | US3 contract suite passes |
| G4 | Blocker hygiene | No unresolved blocker marked as required-to-launch |
| G5 | Traceability completeness | FR to BR to tests mapping complete |

## Rollout Sequence

1. Keep legacy path default.
2. Enable rewritten path for limited validation traffic.
3. Compare parity signals and error categories.
4. Increase traffic only if no contract/parity drift.
