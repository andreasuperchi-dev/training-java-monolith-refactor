# Data Model: Time Entry Module Rewrite (Phase 3b)

This artifact defines Time Entry entities and parity-critical semantics for the rewrite slice.

## Scope

In scope:
- Time entry creation inputs and validation semantics
- Billable hour persistence fields used by Time Entry flow
- Relationships required for valid time entry references

Out of scope:
- reporting/query model redesign
- billing-calculation domain redesign
- non-Time Entry write flows

## Core Entities

### 1) BillableHour
- Source table/entity: `billable_hours`
- Required fields:
  - `customer_id`, `user_id`, `category_id`
  - `hours`, `note`, `date_logged`, `created_at`
- Parity anchors:
  - required relationship references (`BR-005`)
  - positivity/future-date validation semantics (`BR-008`)
  - weekend warning semantics (`BR-009`)
  - input/default behavior from hours logging flow (`BR-010`, `BR-011`)

### 2) Customer
- Source: `customers`
- Role: required reference for valid time entry logging (`customer_id`).
- Parity anchor: required-input and FK relationship semantics (`BR-005`, `BR-010`).

### 3) User
- Source: `users`
- Role: required reference for valid time entry logging (`user_id`).
- Parity anchor: required-input and FK relationship semantics (`BR-005`, `BR-010`).

### 4) BillingCategory
- Source: `billing_categories`
- Role: required reference and rate context for billable entry (`category_id`).
- Parity anchor: required-input semantics and downstream billing compatibility (`BR-005`, `BR-010`).

## Parity-Critical Validation Records

### V-001 Required Input Presence
- Required: customer, user, category, hours
- Expected behavior: missing inputs produce validation error path equivalent to legacy hours flow.
- Traceability: `BR-010`.

### V-002 Hours Value Constraints
- Hours must be positive (non-zero).
- Expected behavior: invalid value handling remains equivalent to legacy semantics.
- Traceability: `BR-008`.

### V-003 Date Handling
- Date is optional input.
- Missing date defaults to current date.
- Future-dated entry handling remains equivalent to legacy validation path.
- Traceability: `BR-008`, `BR-011`.

### V-004 Weekend Semantics
- Weekend entries preserve warning-equivalent behavior unless policy clarification supersedes.
- Traceability: `BR-009`, `OQ-003`.

## Relationship and Invariant Matrix

| Invariant | Source | Rewrite Parity Requirement |
|-----------|--------|----------------------------|
| `customer_id` references valid customer | BR-005 | Preserve reference validity requirement |
| `user_id` references valid user | BR-005 | Preserve reference validity requirement |
| `category_id` references valid category | BR-005 | Preserve reference validity requirement |
| required input presence | BR-010 | Preserve user-visible validation semantics |
| default date behavior | BR-011 | Preserve default-to-current-date behavior |

## Known Ambiguity Boundaries

- Authoritative validation source (service vs JSP path) remains tracked (`OQ-002` / `CF-002`).
- Weekend strictness policy remains unresolved (`OQ-003`), so warning-equivalent behavior is preserved as baseline.
