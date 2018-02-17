${SETSCHEMA} MUWEMP_DB;


${iforacle} ALTER TABLE emplr_emp_t MODIFY (crt_usr_id_num ${BIGSTRING}(50));
${ifjunit} ALTER TABLE emplr_emp_t ALTER COLUMN crt_usr_id_num ${BIGSTRING}(50);

${iforacle} ALTER TABLE emplr_emp_t MODIFY (last_updt_usr_id_num ${BIGSTRING}(50));
${ifjunit} ALTER TABLE emplr_emp_t ALTER COLUMN last_updt_usr_id_num ${BIGSTRING}(50);


${iforacle} ALTER TABLE emplr_emp_event_t MODIFY (crt_usr_id_num ${BIGSTRING}(50));
${ifjunit} ALTER TABLE emplr_emp_event_t ALTER COLUMN crt_usr_id_num ${BIGSTRING}(50);

${iforacle} ALTER TABLE emplr_emp_event_t MODIFY (last_updt_usr_id_num ${BIGSTRING}(50));
${ifjunit} ALTER TABLE emplr_emp_event_t ALTER COLUMN last_updt_usr_id_num ${BIGSTRING}(50);
