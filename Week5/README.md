## Implementation of a database migration using Flyway

This is a project to show a demo implementation of database migration. The project is created with maven and is configured with the Flyway maven plugin, and the H2 dependency in the pom file.

### Creating the first migration
We first create a migration sql script `V1__Create_user_table.sql` in the migration directory `src/main/resources/db/migration` (this script sets up the schema and the users table) and execute it using `mvn clean flyway:migrate`, which should show the following output:
```
[INFO] Database: jdbc:h2:file:./databases/foobar (H2 2.3)
[INFO] Creating schema "app-db" ...
[INFO] Creating Schema History table "app-db"."flyway_schema_history" ...
[INFO] Migrating schema "app-db" to version "1 - Create user table"
[INFO] Successfully applied 1 migration to schema "app-db", now at version v1 (execution time 00:00.012s)
```

### Adding a second migration
If we now add a second migration script called `src/main/resources/db/migration/V2__Add_users.sql` and execute using `mvn flyway:migrate`, we will then see:
```
[INFO] Database: jdbc:h2:file:./databases/foobar (H2 2.3)
[INFO] Successfully validated 3 migrations (execution time 00:00.027s)
[INFO] Current version of schema "app-db": 1
[INFO] Migrating schema "app-db" to version "2 - Add users"
[INFO] Successfully applied 1 migration to schema "app-db", now at version v2 (execution time 00:00.009s)
```