# Business Rules: Rediscover Legacy Monolith

This document captures business-relevant behavior as plain-English rules backed by concrete repository evidence.

## Confidence and Traceability

- `E1 Confirmed Code Path`: Rule is directly executed in application code paths and backed by concrete references.
- `E2 Deterministic Static Evidence`: Rule is clear from static logic/queries but may require runtime confirmation for full context.
- `E3 Config or Schema Evidence`: Rule comes from configuration/schema constraints without direct business-path confirmation.
- `E4 Inferred or Unverified`: Rule candidate exists but cannot be proven from code/config alone.

Confidence per rule is assigned using weakest-link logic across trigger, decision logic, and persisted/output effect.

## Rules Catalog

### BR-001 Bill generation requires an existing customer

- Statement: A customer bill is generated only if the customer exists; otherwise billing fails with a not-found runtime error.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java` (`generateCustomerBill`, customer null check)
- Example: Requesting a bill for a non-existent `customerId` throws `RuntimeException("Customer not found")` before totals are computed.
- Confidence: `E1`

### BR-002 Customer bill totals are computed from hours and category rate

- Statement: Each bill line amount is `hours * hourlyRate`; customer total amount is sum of line amounts; total hours is sum of hours.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java` (`generateCustomerBill` loop)
  - `src/main/webapp/reports.jsp` (customer bill SQL and rendering)
- Example: An 8.50 hour entry in a 150.00/hr category contributes 1275.00 to customer bill total.
- Confidence: `E1`

### BR-003 Monthly report includes only entries matching requested year and month

- Statement: Monthly reporting in service logic includes billable hours whose `dateLogged` year and month equal requested values.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java` (`generateMonthlyReport` date filter)
- Example: Entry dated 2026-06-30 is excluded from report for year=2026 month=7.
- Confidence: `E1`

### BR-004 Monthly report aggregates revenue by billing category

- Statement: Monthly reporting accumulates category-level revenue in a `Map<String, BigDecimal>` keyed by category name.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java` (`revenueByCategory` updates)
- Example: Development and Consulting entries are reported as separate category totals.
- Confidence: `E1`

### BR-005 Billable hour references must point to existing customer, user, and category

- Statement: Every billable hour record must reference existing customer, user, and category rows.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/ConnectionManager.java` (`billable_hours` FK constraints)
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/LibertyConnectionManager.java` (same schema for Liberty mode)
- Example: Inserting a billable hour with unknown `category_id` violates FK constraint and fails.
- Confidence: `E3`

### BR-006 User email identity must be unique

- Statement: Users are uniquely identified by email at DB constraint level.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/ConnectionManager.java` (`users.email VARCHAR(255) NOT NULL UNIQUE`)
- Example: Second insert with same email fails at DB level.
- Confidence: `E3`

### BR-007 Customer name and email are required for create/update in DAO path

- Statement: Customer create/update operations reject null/blank `name` and `email` in DAO validation.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/CustomerDAO.java` (`IllegalArgumentException` checks)
- Example: Calling `CustomerDAO.save` with blank name throws before SQL execution.
- Confidence: `E1`

### BR-008 Billable hours should be positive and not future-dated in service validation

- Statement: Service-level validation flags non-positive hours and future date values as invalid.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java` (`validateBillableHour`)
- Example: `hours = 0` or `dateLogged > now` returns validation errors.
- Confidence: `E2`

### BR-009 Weekend billable hours are warnings, not strict rejection, in service validation

- Statement: Weekend logging produces warning text in validation, not a hard block in that method.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java` (`DateTimeUtils.isWorkingDay` warning branch)
- Example: Saturday entry returns message containing "Warning: Hours logged on weekend."
- Confidence: `E2`

### BR-010 Hours logging UI requires customer/user/category/hours inputs

- Statement: Hours form enforces presence checks for customer, user, category, and hours before save attempt.
- Evidence:
  - `src/main/webapp/hours.jsp` (`action=log` validation block)
- Example: Empty `customerId` yields "Customer is required." in validation message.
- Confidence: `E1`

### BR-011 Hours logging defaults missing date to current day

- Statement: If hours form omits date, log date defaults to current date.
- Evidence:
  - `src/main/webapp/hours.jsp` (`if (dateStr ... else LocalDate.now())`)
- Example: Submitting without date stores entry for current local date.
- Confidence: `E1`

### BR-012 Dashboard revenue is derived from all hours and current category rates

- Statement: Dashboard revenue sums `hour.hours * category.hourlyRate` for all entries and categories loaded at view time.
- Evidence:
  - `src/main/webapp/index.jsp` (dashboard total calculation)
- Example: Updating a category rate affects displayed aggregate revenue for historical entries in this computation path.
- Confidence: `E2`

### BR-013 Customer report in JSP computes totals with SQL joins and arithmetic

- Statement: Customer and revenue reports rely on direct SQL joins in JSP with arithmetic totals.
- Evidence:
  - `src/main/webapp/reports.jsp` (customer/monthly/revenue query blocks)
- Example: Customer bill table total row is derived from SQL result iteration.
- Confidence: `E1`

### BR-014 Application startup seeds sample data only when no users exist

- Statement: Sample data initialization short-circuits when user table already contains data.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/DataInitializationService.java` (`if (!existingUsers.isEmpty()) return;`)
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/StartupListener.java`
- Example: Second startup after initial seeding does not duplicate sample rows.
- Confidence: `E1`

### BR-015 Database mode prioritizes Liberty datasource, then falls back to embedded Derby

- Statement: Runtime attempts JNDI datasource first; if unavailable, persistence falls back to embedded Derby connection manager.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/LibertyConnectionManager.java`
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/StartupListener.java`
- Example: Local run without Liberty JNDI still works using `jdbc:derby:./data/bigbadmonolith;create=true`.
- Confidence: `E1`

## Contested Findings

### CF-001 User creation field mapping may be inconsistent in users JSP path

- Observation: `users.jsp` builds `new User(name, email)` while `User` constructor expects `(email, name)`.
- Evidence:
  - `src/main/webapp/users.jsp`
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/entity/User.java`
- Impact: User name/email values may be swapped during this path.
- Confidence: `E2`
- Status: Open question tracked in `open-questions.md` (`OQ-001`).

### CF-002 Validation policy differs between service and JSP write paths

- Observation: Service has richer billable-hour validation, but JSP logging writes directly via DAO without calling `BillingService.validateBillableHour`.
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java`
  - `src/main/webapp/hours.jsp`
- Impact: Effective business validation may depend on entry path.
- Confidence: `E2`
- Status: Open question tracked in `open-questions.md` (`OQ-002`).

## Rule-to-Evidence Quick Index

- Billing and totals: BR-001, BR-002, BR-003, BR-004
- Constraints and identity: BR-005, BR-006, BR-007
- Time-entry validation behavior: BR-008, BR-009, BR-010, BR-011
- Reporting and dashboard behavior: BR-012, BR-013
- Startup/runtime platform behavior: BR-014, BR-015
