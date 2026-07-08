# Legacy Time Entry Scenarios

## Validation and Entry Scenarios

| Scenario ID | Input Shape | Legacy Expected Outcome | Rule Link |
|-------------|-------------|-------------------------|-----------|
| L-TE-001 | Missing customer | Error: customer required | BR-010 |
| L-TE-002 | Missing user | Error: user required | BR-010 |
| L-TE-003 | Missing category | Error: category required | BR-010 |
| L-TE-004 | Missing/empty hours | Error: hours required | BR-010 |
| L-TE-005 | Missing date | Defaults to current date | BR-011 |
| L-TE-006 | Hours <= 0 | Error: positive hours required | BR-008 |
| L-TE-007 | Date in future | Error: future date not allowed | BR-008 |
| L-TE-008 | Weekend date | Warning semantics, no hard reject by weekend alone | BR-009 |
| L-TE-009 | Unknown customer/user/category | Error surfaced as invalid reference | BR-005 |
| L-TE-010 | Valid references + valid hours/date | Entry persisted successfully | BR-005 |
