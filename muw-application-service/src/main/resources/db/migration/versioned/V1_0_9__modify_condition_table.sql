/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;

${iforacle} ALTER TABLE muw_applnt_cond_t MODIFY (trtm_recvd_txt NULL);
${ifh2} ALTER TABLE muw_applnt_cond_t ALTER COLUMN trtm_recvd_txt SET NULL;

