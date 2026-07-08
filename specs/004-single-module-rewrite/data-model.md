# Data Model: Reporting Module Rewrite (Phase 3b)

This artifact defines reporting-module data entities and parity-critical projections used to preserve legacy report behavior.

## Reporting Scope

In scope entities and projections are limited to report generation concerns for:
- Customer Bill report (`reportType=customer`)
- Monthly Summary report (`reportType=monthly`)
- Revenue Summary report (`reportType=revenue`)

Out of scope:
- write-path domain redesign
- non-reporting module entities beyond required read joins

## Core Entities (Read Model Inputs)

### 1) Customer
- Source table: `customers`
- Required fields for reporting: `id`, `name`, `email`
- Reporting roles:
  - customer identity in customer bill and monthly summary
  - grouping key in revenue-by-customer summary
- Traceability: `BR-001`, `BR-013`

### 2) BillableHour
- Source table: `billable_hours`
- Required fields for reporting: `id`, `customer_id`, `user_id`, `category_id`, `hours`, `date_logged`, `note`
- Reporting roles:
  - base fact rows for line totals and rollups
  - month/year filtering basis
- Traceability: `BR-002`, `BR-003`, `BR-013`

### 3) BillingCategory
- Source table: `billing_categories`
- Required fields for reporting: `id`, `name`, `hourly_rate`
- Reporting roles:
  - rate lookup for line-total arithmetic (`hours * hourly_rate`)
  - category grouping for revenue-by-category
- Traceability: `BR-002`, `BR-004`

### 4) User
- Source table: `users`
- Required fields for reporting: `id`, `name`
- Reporting roles:
  - displayed actor label on customer bill line items
- Traceability: `BR-013`

## Derived Projections (Parity Outputs)

### P-001 Customer Bill Projection
- Inputs: `customerId`
- Projection fields:
  - line rows: `date_logged`, `user_name`, `category_name`, `hours`, `hourly_rate`, `line_total`, `note`
  - totals: `total_hours`, `total_amount`
- Ordering: descending by `date_logged`
- Formula:
  - `line_total = hours * hourly_rate`
  - `total_amount = SUM(line_total)`
  - `total_hours = SUM(hours)`
- Traceability: `BR-001`, `BR-002`, `BR-013`

### P-002 Monthly Summary Projection
- Inputs: `year`, `month`
- Date window behavior: SQL-style range from `YYYY-MM-01` to `YYYY-MM-31`
- Projection fields (grouped by customer):
  - `customer_name`, `total_hours`, `total_amount`
  - monthly totals row for hours and revenue
- Ordering: descending by `total_amount`
- Traceability: `BR-003`, `BR-013`

### P-003 Revenue Summary Projection
- Inputs: none (full dataset)
- Sub-projection A (by customer):
  - `customer_name`, `total_hours`, `total_revenue`, `avg_rate`
- Sub-projection B (by category):
  - `category_name`, `hourly_rate`, `total_hours`, `total_revenue`
- Ordering: descending by `total_revenue`
- Traceability: `BR-002`, `BR-004`, `BR-013`

## Validation and Invariants Relevant to Reporting

- Referential links for billable facts must remain valid (`customer_id`, `user_id`, `category_id`).
- Numeric calculations must preserve decimal arithmetic semantics used in legacy reporting totals.
- Empty/left-joined result behavior remains contract-visible and must not be silently changed during rewrite.
- Traceability: `BR-005`, `BR-013`

## Parity Mapping Matrix

| Legacy Behavior Anchor | Reporting Projection | Parity Requirement |
|------------------------|----------------------|--------------------|
| `BR-001` customer existence gate | P-001 Customer Bill | Unknown customer request yields equivalent failure behavior and no fabricated fallback |
| `BR-002` line and total arithmetic | P-001, P-003 | Calculated amounts exactly match legacy formula results |
| `BR-003` month/year filtering | P-002 | Included rows match legacy month-window behavior |
| `BR-004` revenue by category | P-003 | Category totals and grouping remain equivalent |
| `BR-013` direct SQL report semantics | P-001, P-002, P-003 | Output columns/order/totals are contract-equivalent |

## Known Ambiguity Boundaries

- Canonical logic ownership between JSP SQL and service methods remains an open governance item (`OQ-007`).
- For this phase, parity baseline is the externally observed report interface behavior; unresolved ownership is tracked, not invented.
