# POC: Spring Data JDBC Temporal Data

It demonstrates how to persist temporal data using the available Java types to represent date and time.

The goal is to implement three different aggregates that contain at least one attribute related to date and time. We
want to document the appropriate table column type required to store data represented using classes from `java.time`
package, mainly the modern ones. The types used in this experiment are `Instant`, `OffsetDateTime` and `ZonedDateTime`.

The target database chosen for this experiment is Postgres and all three Java types are stored in columns of
type `TIMESTAMP WITH TIME ZONE`, meaning we are telling the database to store the timestamp according to the Universal
Time Convention (UTC). Each Java type has at least one test case demonstrating how to persist "raw" UTC values, but
there are test cases demonstrating how to persist values according to a time zone as well. It is possible to represent
date and time using other classes such as those from Calendar API or `java.sql` package, but they're not present in this
experiment.

The experiment should not provide any production code, only automated tests verifying the implementation. The tests
should provision a Postgres database using Testcontainers and create the necessary tables via database migrations run by
Flyway.

## How to run

| Description | Command          |
|-------------|------------------|
| Run tests   | `./gradlew test` |

## Preview

Logs from tests persisting `Instant` type:

```text
Executing SQL update and returning generated keys
Executing prepared SQL statement [INSERT INTO "customer" ("customer_created_at", "customer_name") VALUES (?, ?)]
Setting SQL statement parameter value: column index 1, parameter value [2022-10-29 19:52:33.205183929], value class [java.sql.Timestamp], SQL type 93
Setting SQL statement parameter value: column index 2, parameter value [John Smith], value class [java.lang.String], SQL type 12
SQL update affected 1 rows and returned 1 keys
Executing prepared SQL query
Executing prepared SQL statement [SELECT "customer"."customer_id" AS "customer_id", "customer"."customer_name" AS "customer_name", "customer"."customer_created_at" AS "customer_created_at" FROM "customer" WHERE "customer"."customer_id" = ?]
Setting SQL statement parameter value: column index 1, parameter value [3915df78-9f36-403c-b4b9-e699681252b2], value class [java.util.UUID], SQL type unknown
```

Logs from tests persisting `OffsetDateTime` type:

```text
Executing SQL update and returning generated keys
Executing prepared SQL statement [INSERT INTO "account" ("account_balance", "account_created_at") VALUES (?, ?)]
Setting SQL statement parameter value: column index 1, parameter value [750.00], value class [java.math.BigDecimal], SQL type 3
Setting SQL statement parameter value: column index 2, parameter value [2022-10-30T00:03:50.785061364Z], value class [java.time.OffsetDateTime], SQL type 2014
SQL update affected 1 rows and returned 1 keys
Executing prepared SQL query
Executing prepared SQL statement [SELECT "account"."account_id" AS "account_id", "account"."account_balance" AS "account_balance", "account"."account_created_at" AS "account_created_at" FROM "account" WHERE "account"."account_id" = ?]
Setting SQL statement parameter value: column index 1, parameter value [0f99bbc5-e595-4518-a521-c23f4c33e84f], value class [java.util.UUID], SQL type unknown

Executing SQL update and returning generated keys
Executing prepared SQL statement [INSERT INTO "account" ("account_balance", "account_created_at") VALUES (?, ?)]
Setting SQL statement parameter value: column index 1, parameter value [750.00], value class [java.math.BigDecimal], SQL type 3
Setting SQL statement parameter value: column index 2, parameter value [2022-10-29T21:01:41.372759031-03:00], value class [java.time.OffsetDateTime], SQL type 2014
SQL update affected 1 rows and returned 1 keys
Executing prepared SQL query
Executing prepared SQL statement [SELECT "account"."account_id" AS "account_id", "account"."account_balance" AS "account_balance", "account"."account_created_at" AS "account_created_at" FROM "account" WHERE "account"."account_id" = ?]
Setting SQL statement parameter value: column index 1, parameter value [4cb6b49a-ecb7-4225-b328-93a59a6dd57b], value class [java.util.UUID], SQL type unknown
```

Logs from tests persisting `ZonedDateTime` type:

```text
Executing SQL update and returning generated keys
Executing prepared SQL statement [INSERT INTO "vehicle" ("vehicle_category", "vehicle_created_at") VALUES (?, ?)]
Setting SQL statement parameter value: column index 1, parameter value [Motorcycle], value class [java.lang.String], SQL type 12
Setting SQL statement parameter value: column index 2, parameter value [2022-10-30 00:05:36.286725037], value class [java.sql.Timestamp], SQL type 93
SQL update affected 1 rows and returned 1 keys
Executing prepared SQL query
Executing prepared SQL statement [SELECT "vehicle"."vehicle_id" AS "vehicle_id", "vehicle"."vehicle_category" AS "vehicle_category", "vehicle"."vehicle_created_at" AS "vehicle_created_at" FROM "vehicle" WHERE "vehicle"."vehicle_id" = ?]
Setting SQL statement parameter value: column index 1, parameter value [235b8260-ba24-447a-872d-163e2ccf038a], value class [java.util.UUID], SQL type unknown

Executing SQL update and returning generated keys
Executing prepared SQL statement [INSERT INTO "vehicle" ("vehicle_category", "vehicle_created_at") VALUES (?, ?)]
Setting SQL statement parameter value: column index 1, parameter value [Motorcycle], value class [java.lang.String], SQL type 12
Setting SQL statement parameter value: column index 2, parameter value [2022-10-29 23:55:07.375779966], value class [java.sql.Timestamp], SQL type 93
SQL update affected 1 rows and returned 1 keys
Executing prepared SQL query
Executing prepared SQL statement [SELECT "vehicle"."vehicle_id" AS "vehicle_id", "vehicle"."vehicle_category" AS "vehicle_category", "vehicle"."vehicle_created_at" AS "vehicle_created_at" FROM "vehicle" WHERE "vehicle"."vehicle_id" = ?]
Setting SQL statement parameter value: column index 1, parameter value [fb1d6e24-2d72-4a72-bcff-55022bb5cf99], value class [java.util.UUID], SQL type unknown
```
