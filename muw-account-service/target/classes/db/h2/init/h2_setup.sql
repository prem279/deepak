CREATE SCHEMA IF NOT EXISTS MUWACCT_DB;

SET SCHEMA MUWACCT_DB;

CREATE ROLE IF NOT EXISTS MUW01W;

CREATE ROLE IF NOT EXISTS MUW_MANAGE;

GRANT MUW01W TO sa;