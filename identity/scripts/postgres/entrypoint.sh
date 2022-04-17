#!/usr/bin/env bash

echo "================================================"
echo "Postgres post installation configuration started"
echo "================================================"

psql -v ON_ERROR_STOP=1 --username "${POSTGRES_USER}" --dbname "${POSTGRES_DB}" <<-EOSQL
    CREATE USER "${DB_USER}" WITH PASSWORD '${DB_SECRET}';

    CREATE DATABASE "${DB_NAME}";

    GRANT ALL PRIVILEGES ON DATABASE "${DB_NAME}" TO "${DB_USER}";

    \connect "${DB_NAME}";

    REVOKE CONNECT ON DATABASE "${DB_NAME}" FROM PUBLIC;
    GRANT CONNECT ON DATABASE "${DB_NAME}" TO "${DB_USER}";

    REVOKE ALL ON SCHEMA public FROM PUBLIC;
    GRANT CREATE, USAGE ON SCHEMA public TO "${DB_USER}";

    REVOKE ALL ON ALL TABLES IN SCHEMA public FROM PUBLIC;
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO "${DB_USER}";
    GRANT ALL ON ALL TABLES IN SCHEMA public TO "${DB_USER}";

    CREATE SCHEMA "${DB_SCHEMA}";

    REVOKE ALL ON SCHEMA "${DB_SCHEMA}" FROM PUBLIC;
    GRANT CREATE, USAGE ON SCHEMA "${DB_SCHEMA}" TO "${DB_USER}";

    REVOKE ALL ON ALL TABLES IN SCHEMA "${DB_SCHEMA}" FROM PUBLIC;
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA "${DB_SCHEMA}" TO "${DB_USER}";
    GRANT ALL ON ALL TABLES IN SCHEMA "${DB_SCHEMA}" TO "${DB_USER}";

    CREATE SCHEMA liquibase;

    REVOKE ALL ON SCHEMA liquibase FROM PUBLIC;
    GRANT CREATE, USAGE ON SCHEMA liquibase TO "${DB_USER}";

    REVOKE ALL ON ALL TABLES IN SCHEMA liquibase FROM PUBLIC;
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA liquibase TO "${DB_USER}";
    GRANT ALL ON ALL TABLES IN SCHEMA liquibase TO "${DB_USER}";
EOSQL

echo "================================================="
echo "Postgres post installation configuration finished"
echo "================================================="
