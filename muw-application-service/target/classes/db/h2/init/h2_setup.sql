CREATE SCHEMA IF NOT EXISTS MUWAPPLN_DB;

SET SCHEMA MUWAPPLN_DB;

CREATE ROLE IF NOT EXISTS MUW01W;

CREATE ROLE IF NOT EXISTS MUW_MANAGE;

GRANT MUW01W TO sa;