# Acceptance Tests: Revenue Summary Reporting (US2)

## Rule Anchors
- BR-002, BR-004, BR-013

## Test Cases

### AC-REV-001 Revenue by Customer
- Given: billable entries across customers
- When: requesting revenue summary report
- Then: customer totals and average-rate semantics match legacy outputs

### AC-REV-002 Revenue by Category
- Given: billable entries across categories
- When: requesting revenue summary report
- Then: category totals match legacy arithmetic and grouping behavior

### AC-REV-003 Sparse Data Left-Join Visibility
- Given: sparse billable data
- When: requesting revenue summary report
- Then: output visibility and aggregate defaults match legacy behavior

## Expected Parity Artifacts
- Customer summary parity
- Category summary parity
- Ordering by total revenue parity
- Sparse data behavior parity
