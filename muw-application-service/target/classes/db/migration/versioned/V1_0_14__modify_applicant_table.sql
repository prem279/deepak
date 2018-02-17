/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;

/* If status type code == null, set to default status of "MANREV" */

UPDATE muw_appln_applnt_t SET CCRCY_QTY = 0 WHERE CCRCY_QTY IS NULL;

/* Now alter the column to be not null */

${iforacle}ALTER TABLE muw_appln_applnt_t MODIFY (CCRCY_QTY NOT NULL);
${ifh2}ALTER TABLE muw_appln_applnt_t ALTER COLUMN CCRCY_QTY SET NOT NULL;