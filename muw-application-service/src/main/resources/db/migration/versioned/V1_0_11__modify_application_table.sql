/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;

${iforacle}ALTER TABLE muw_appln_t MODIFY (appln_recvd_dtm DATE);
${ifh2}ALTER TABLE muw_appln_t ALTER COLUMN appln_recvd_dtm DATE;