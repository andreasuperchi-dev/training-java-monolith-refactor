# Time Entry Acceptance Tests

## Rule-Derived Scenarios

| Scenario | Input | Expected Outcome | Rule Links |
|----------|-------|------------------|------------|
| TE-001 | Missing required fields | Validation errors with required-field messages | BR-010 |
| TE-002 | Missing date | Date defaults to current date semantics | BR-011 |
| TE-003 | Non-positive hours | Validation error for hours positivity | BR-008 |
| TE-004 | Future date | Validation error for future-dated entry | BR-008 |
| TE-005 | Weekend date | Warning-equivalent semantics preserved | BR-009 |
| TE-006 | Valid references + valid values | Persisted entry + success path | BR-005 |
