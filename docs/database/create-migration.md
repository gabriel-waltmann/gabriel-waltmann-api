### How to create a migration

Add a new migration file in the `src/main/resources/db/migration` directory. 
The filename should follow the format `V<version>__<description>.sql`, where `<version>` is a unique version number and `<description>` is a brief description of the migration.
For example, `V1__create_user_table.sql`.

The migration file should contain the SQL statements needed to modify the database schema or data.
Make sure to use the correct SQL syntax for PostgreSQL, as this is the database used by the application.

### Example Migration File

```sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(500) NOT NULL,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);
```

### Running Migrations
Migrations are automatically applied when the application starts.
Ensure that the migration files are in the correct directory and follow the naming convention.