# Time Entry Traceability Matrix

## Requirement to Rule Coverage

| Requirement | Rule Links | Notes |
|-------------|------------|-------|
| FR-001 | BR-010 | Time Entry boundary selected |
| FR-002 | BR-010 | Scope constrained to Time Entry behavior |
| FR-003 | BR-008, BR-009, BR-010, BR-011 | Core Time Entry rule anchors |
| FR-004 | BR-008, BR-009, BR-010, BR-011 | Acceptance tests derive from rule set |
| FR-005 | BR-010 | Required input semantics preserved |
| FR-006 | BR-011 | Date defaulting preserved |
| FR-007 | BR-008 | Positivity and future-date checks preserved |
| FR-008 | BR-009 | Weekend warning parity preserved |
| FR-009 | BR-010, BR-011 | Interface semantics preserved |
| FR-010 | OQ-002, OQ-003 | Ambiguity blocking policy enforced |
| FR-011 | SA-008 | Switch-gate compatibility for migration |
| FR-012 | SA-008 | Substitution and architecture trace constraints preserved |

## Acceptance Test Mapping

| Test ID | Rule Links | Parity Assertion |
|---------|------------|------------------|
| TE-001 | BR-010 | Required fields produce equivalent validation outcomes |
| TE-002 | BR-011 | Missing date defaults equivalently |
| TE-003 | BR-008 | Invalid hours/future date handling equivalent |
| TE-004 | BR-009 | Weekend warning behavior equivalent |
| TE-005 | BR-005 | Valid references persist equivalently |
