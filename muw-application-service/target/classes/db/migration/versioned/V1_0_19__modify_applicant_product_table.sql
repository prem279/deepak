${SETSCHEMA} MUWAPPLN_DB;

ALTER TABLE muw_appln_applnt_prod_t ADD (cov_elig_dte ${DATETIME} NULL);
