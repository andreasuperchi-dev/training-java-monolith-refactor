# Acceptance Tests: Customer Bill Reporting (US2)

## Rule Anchors
- BR-001, BR-002, BR-013

## Test Cases

### AC-CUST-001 Valid Customer Bill
- Given: valid customer with billable entries
- When: requesting customer bill report
- Then: line items and totals match legacy outputs exactly

### AC-CUST-002 Empty Customer Bill
- Given: valid customer with no billable entries
- When: requesting customer bill report
- Then: empty/zero-equivalent behavior matches legacy

### AC-CUST-003 Invalid Customer
- Given: missing or invalid customer id
- When: requesting customer bill report
- Then: failure behavior matches legacy semantics

## Expected Parity Artifacts
- Row ordering parity (date descending)
- Arithmetic parity (`hours * hourly_rate`)
- Totals parity
- Error-path parity
