# Acceptance Tests: Monthly Summary Reporting (US2)

## Rule Anchors
- BR-003, BR-013

## Test Cases

### AC-MONTH-001 Month With Data
- Given: reportable entries within selected year/month
- When: requesting monthly summary report
- Then: only matching-month entries are included and grouped by customer

### AC-MONTH-002 Month Without Data
- Given: no entries in selected year/month
- When: requesting monthly summary report
- Then: empty/zero-equivalent result matches legacy behavior

### AC-MONTH-003 Ordering and Totals
- Given: multiple customer totals in month
- When: monthly summary is generated
- Then: rows are ordered by total amount descending with monthly totals parity

## Expected Parity Artifacts
- Date-window inclusion parity
- Grouping parity
- Monthly totals parity
- Ordering parity
