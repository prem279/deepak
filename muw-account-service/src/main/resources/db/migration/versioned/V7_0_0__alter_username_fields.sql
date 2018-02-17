${SETSCHEMA} MUWACCT_DB;


${iforacle} ALTER TABLE muw_emplr_t MODIFY (crt_usr_id_num ${BIGSTRING}(50));
${ifjunit} ALTER TABLE muw_emplr_t ALTER COLUMN crt_usr_id_num ${BIGSTRING}(50);

${iforacle} ALTER TABLE emplr_prefcs_t MODIFY (last_updt_usr_id_num ${BIGSTRING}(50));
${ifjunit} ALTER TABLE emplr_prefcs_t ALTER COLUMN last_updt_usr_id_num ${BIGSTRING}(50);


${iforacle} ALTER TABLE emplr_eoi_prod_t MODIFY (crt_usr_id_num ${BIGSTRING}(50));
${ifjunit} ALTER TABLE emplr_eoi_prod_t ALTER COLUMN crt_usr_id_num ${BIGSTRING}(50);

${iforacle} ALTER TABLE muw_emplr_evnt_t MODIFY (last_updt_usr_id_num ${BIGSTRING}(50));
${ifjunit} ALTER TABLE muw_emplr_evnt_t ALTER COLUMN last_updt_usr_id_num ${BIGSTRING}(50);
