# Data Model Summary: Rediscover Legacy Monolith

This summary documents entities, relationships, and invariants discoverable from repository code/configuration only.

## Enforcement-Source Taxonomy

- **Application Validation**: Logic intended to accept/reject business inputs before persistence.
- **Defensive Code**: Safeguards for resilience/defaulting/fallback behavior, not always policy-defining.
- **Database Constraint**: Engine-enforced schema integrity guarantees.
- **Mixed Enforcement**: Same invariant represented in more than one source with primary + secondary enforcement.

## Entity Completeness Checks

- Checked entities in scope: `User`, `Customer`, `BillingCategory`, `BillableHour`.
- Completeness criteria: field coverage, persistence mapping presence, constraint/enforcement attribution, and relationship linkage.
- Result: all four entities are represented with model + persistence + constraint evidence; remaining uncertainty is policy intent (tracked in `open-questions.md`).

## Entity: User

### Fields
- `id: Long` (database identity primary key)
- `email: String`
- `name: String`

### Evidence
- Model: `src/main/java/com/sourcegraph/demo/bigbadmonolith/entity/User.java`
- Persistence: `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/UserDAO.java`
- Table schema: `users` in `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/ConnectionManager.java`

### Invariants
- `email` is unique at DB level (`UNIQUE` on `users.email`) - Enforcement: Database Constraint.
- `email` and `name` are required in schema (`NOT NULL`) - Enforcement: Database Constraint.

## Entity: Customer

### Fields
- `id: Long` (database identity primary key)
- `name: String`
- `email: String`
- `address: String`
- `createdAt: DateTime`

### Evidence
- Model: `src/main/java/com/sourcegraph/demo/bigbadmonolith/entity/Customer.java`
- Persistence + defensive validation: `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/CustomerDAO.java`
- Table schema: `customers` in `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/ConnectionManager.java`

### Invariants
- `name` and `email` must be non-empty before save/update - Enforcement: Application Validation (`IllegalArgumentException` in `CustomerDAO`).
- `name`, `email`, and `created_at` required by schema (`NOT NULL`) - Enforcement: Database Constraint.
- `createdAt` defaults to `DateTime.now()` if not provided on constructor/save path - Enforcement: Defensive Code + Application Behavior.

## Entity: BillingCategory

### Fields
- `id: Long` (database identity primary key)
- `name: String`
- `description: String`
- `hourlyRate: BigDecimal`

### Evidence
- Model: `src/main/java/com/sourcegraph/demo/bigbadmonolith/entity/BillingCategory.java`
- Persistence: `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/BillingCategoryDAO.java`
- Table schema: `billing_categories` in `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/ConnectionManager.java`

### Invariants
- `hourly_rate` must be present (`NOT NULL`) - Enforcement: Database Constraint.
- No explicit backend validation enforces non-negative `hourlyRate`; UI form uses `min="0"` in `categories.jsp` - Enforcement: UI Constraint (soft), not hard DB/service guarantee.

## Entity: BillableHour

### Fields
- `id: Long` (database identity primary key)
- `customerId: Long` (FK to `customers.id`)
- `userId: Long` (FK to `users.id`)
- `categoryId: Long` (FK to `billing_categories.id`)
- `hours: BigDecimal`
- `note: String`
- `dateLogged: LocalDate`
- `createdAt: DateTime`

### Evidence
- Model: `src/main/java/com/sourcegraph/demo/bigbadmonolith/entity/BillableHour.java`
- Persistence: `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/BillableHourDAO.java`
- Service validation rules: `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java`
- Table schema: `billable_hours` in `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/ConnectionManager.java`

### Invariants
- `customer_id`, `user_id`, `category_id`, `hours`, `date_logged`, `created_at` are required (`NOT NULL`) - Enforcement: Database Constraint.
- FK references to customer/user/category must exist - Enforcement: Database Constraint.
- `hours > 0` is checked in service validation method - Enforcement: Application Validation (service), but `hours.jsp` save path does not call this service validator.
- `dateLogged` cannot be future date in service validation method - Enforcement: Application Validation (service), not universally enforced.
- Weekend logging is flagged as warning in service validation (`DateTimeUtils.isWorkingDay`) - Enforcement: Defensive/Advisory Code, not rejection.
- `createdAt` defaults to now if missing on save - Enforcement: Defensive Code in DAO.

## Relationships

- `Customer 1..* BillableHour` via `billable_hours.customer_id`.
- `User 1..* BillableHour` via `billable_hours.user_id`.
- `BillingCategory 1..* BillableHour` via `billable_hours.category_id`.

### Evidence
- FK definitions: `ConnectionManager` / `LibertyConnectionManager` table DDL for `billable_hours`.
- Join usage in reporting SQL: `src/main/webapp/reports.jsp`.

### Relationship Cardinality and Join-Evidence Cross-Checks

- `Customer 1..* BillableHour`: validated by FK plus customer-joined reporting queries.
- `User 1..* BillableHour`: validated by FK plus user-joined reporting queries.
- `BillingCategory 1..* BillableHour`: validated by FK plus category-joined reporting queries and service computations.
- Cross-check result: cardinalities are consistent between schema and observed join paths; no conflicting many-to-many evidence observed.

## Derived Domain Concepts

- Revenue line item = `billable_hours.hours * billing_categories.hourly_rate`.
- Customer total bill = sum of line items for all customer billable hours.
- Monthly revenue summary = grouped sums by date window and customer/category.

### Evidence
- `BillingService.generateCustomerBill` and `generateMonthlyReport`.
- SQL aggregates and joins in `reports.jsp`.

## State Transitions

- `User`: created -> updated -> deleted (DAO operations).
- `Customer`: created -> updated -> deleted (DAO operations; delete may fail under FK constraints).
- `BillingCategory`: created -> updated (rate changes) -> deleted (DAO operations; delete may fail under FK constraints).
- `BillableHour`: created -> updated -> deleted (DAO operations).

## Invariants Enforced Only by Defensive Code or DB Constraints

- Defensive only:
  - `createdAt` auto-population for `Customer` and `BillableHour` when missing.
  - Runtime DB mode fallback from Liberty datasource to embedded Derby.
- DB constraints only:
  - `users.email` uniqueness.
  - Referential integrity for all billable hour links.
  - Required fields marked `NOT NULL` in all four tables.

## Dedicated Defensive-Only Invariants

- Customer and billable-hour `createdAt` timestamps are defaulted when absent.
- Runtime persistence path falls back from Liberty datasource to embedded Derby on datasource unavailability.

## Dedicated DB-Constraint-Only Invariants

- `users.email` uniqueness is enforced only by schema constraint.
- Billable-hour foreign-key linkage (`customer_id`, `user_id`, `category_id`) is enforced only by schema constraints.
- Mandatory not-null fields are enforced by schema constraints across all core tables.

## Mixed-Enforcement Invariants and Precedence

- Hours positivity:
  - Primary: application validation in service (`hours > 0`).
  - Secondary: UI input constraints (`min` attributes where present).
  - DB note: schema requires non-null hours but does not encode positivity threshold.
- Date policy:
  - Primary: service validation (`not future-dated`) and advisory weekend warning.
  - Secondary: UI date field defaults/current date behavior.
  - DB note: schema requires non-null date but does not encode future-date policy.

Precedence note:

- Where application validation and UI checks diverge, canonical policy source is unresolved and tracked in `open-questions.md`.

## Cross-Artifact Terminology Consistency Notes

- Use `BillableHour` for entity name and `billable hour` for prose references.
- Use `invariant` for rules expected to always hold; use `validation` for path-specific checks.
- Use `defensive code` only for fallback/defaulting/resilience semantics, not for direct business rule assertions.

## Open Data-Model Questions (Code Alone Cannot Answer)

- Is duplicate `customers.email` acceptable by business policy (schema allows duplicates)?
- Should `billing_categories.hourly_rate` permit zero values in policy terms (UI blocks negative, but DB allows)?
- Are weekend billable hours valid-but-flagged or policy-violating in formal billing rules?
- Should direct JSP writes bypassing `BillingService.validateBillableHour` be considered intended behavior or legacy drift?
