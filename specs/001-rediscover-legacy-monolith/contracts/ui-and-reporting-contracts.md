# Interface Contracts: UI and Reporting Surfaces

This document captures externally visible interfaces exposed by the current monolith as discovered from repository artifacts.

## 1) Inbound Web Interface Contract

### Context root and transport
- Base context root: `/big-bad-monolith`
- Expected transports: HTTP (`:9080`) and HTTPS (`:9443`) in default Liberty config

### Evidence
- `src/main/liberty/config/server.xml` (`webApplication` context root and endpoint ports)
- `README.md` access points

### Pages and behaviors
- `GET /big-bad-monolith/` -> dashboard (`index.jsp`) showing aggregate customer/user/revenue counts.
- `GET|POST /big-bad-monolith/customers.jsp` -> customer create/list/delete workflow.
- `GET|POST /big-bad-monolith/users.jsp` -> user create/list workflow.
- `GET|POST /big-bad-monolith/categories.jsp` -> category create/rate-update workflow.
- `GET|POST /big-bad-monolith/hours.jsp` -> billable hour logging and recent entries.
- `GET /big-bad-monolith/reports.jsp` with query parameters -> customer, monthly, and revenue reports.

### Request parameter contracts

#### `customers.jsp`
- `action=add`: requires `name`, `email`; optional `address`.
- `action=delete`: requires numeric `id`.

#### `users.jsp`
- `action=add`: expects `name`, `email`.
- Note: parameter-to-constructor mapping appears reversed (`new User(name, email)` while constructor is `(email, name)`). Behavior should be validated during rediscovery documentation.

#### `categories.jsp`
- `action=add`: requires `name`, `hourlyRate`; optional `description`.
- `action=update`: requires numeric `id`, decimal `newRate`.

#### `hours.jsp`
- `action=log`: expects `customerId`, `userId`, `categoryId`, `hours`; optional `note`, `date` (defaults to current date).

#### `reports.jsp`
- `reportType=customer`: requires `customerId`.
- `reportType=monthly`: requires `year`, `month`.
- `reportType=revenue`: no additional parameters.

## 2) Data Access Contract (Persistence Integration)

### Primary contract
- Contract name: `jdbc/DefaultDataSource` JNDI datasource lookup.
- Fallback contract: embedded Derby JDBC URL `jdbc:derby:./data/bigbadmonolith;create=true`.

### Evidence
- `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/LibertyConnectionManager.java`
- `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/ConnectionManager.java`
- `src/main/liberty/config/server.xml`

### Behavioral notes
- On startup, code attempts Liberty datasource lookup; on failure, logs to stderr and falls back to embedded Derby.
- Startup listener initializes schema and sample data.

## 3) Reporting Computation Contract

### Customer bill report (`reportType=customer`)
- Input: `customerId`
- Output columns: date, user, category, hours, hourly rate, amount, description; totals row for hours and amount.
- Calculation contract: line total = `hours * hourly_rate`; total bill = sum(line total).

### Monthly report (`reportType=monthly`)
- Inputs: `year`, `month`
- Date filter contract: SQL range `YYYY-MM-01` to `YYYY-MM-31`.
- Output: per-customer total hours and total revenue, plus monthly totals.

### Revenue summary (`reportType=revenue`)
- Output A: revenue by customer (sum hours, sum revenue, average rate).
- Output B: revenue by category (hourly rate, total hours, total revenue).

### Evidence
- `src/main/webapp/reports.jsp`
- `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java`

## 4) Security and Access Contract (Current Observed)

- Liberty app binding maps role `users` to basic registry user `user1` in server config.
- App security is enabled with failover to basic auth.
- `web.xml` does not define additional URL-pattern constraints.

### Evidence
- `src/main/liberty/config/server.xml`
- `src/main/webapp/WEB-INF/web.xml`

## 5) Out-of-Scope or Unconfirmed Interfaces

- README mentions REST API endpoints but repository evidence in this scan did not identify servlet/JAX-RS resource classes exposing them.
- Mark as open question until code evidence is found.

## 6) Cross-Links

- Integration inventory reference: `contracts/integration-inventory.md`
- Open question references:
	- API endpoint uncertainty: `open-questions.md` (`OQ-005`)
	- Runtime auth-scope uncertainty: `open-questions.md` (`OQ-006`)
	- Canonical reporting-path uncertainty: `open-questions.md` (`OQ-007`)
