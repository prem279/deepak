/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWEMP_DB;

${iforacle} ALTER TABLE emplr_emp_t MODIFY (cmunn_mthd_cde VARCHAR(6));
${ifjunit} ALTER TABLE emplr_emp_t ALTER COLUMN cmunn_mthd_cde VARCHAR(6);

update emplr_emp_t set cmunn_mthd_cde = substr(cmunn_mthd_cde, 1,3);


${iforacle} ALTER TABLE emplr_emp_t MODIFY (cmunn_mthd_cde VARCHAR(3));
${ifjunit} ALTER TABLE emplr_emp_t ALTER COLUMN cmunn_mthd_cde VARCHAR(3);
