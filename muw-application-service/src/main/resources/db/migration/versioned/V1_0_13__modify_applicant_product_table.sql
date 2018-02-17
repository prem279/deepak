/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;

/* If status type code == null, set to default status of "MANREV" */

UPDATE muw_appln_applnt_prod_t SET stts_typ_cde = 'MANREV' WHERE stts_typ_cde IS NULL;

/* Now alter the column to be not null */

${iforacle}ALTER TABLE muw_appln_applnt_prod_t MODIFY (stts_typ_cde NOT NULL);
${ifh2}ALTER TABLE muw_appln_applnt_prod_t ALTER COLUMN stts_typ_cde SET NOT NULL;