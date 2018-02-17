/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWEMP_DB;


${iforacle} ALTER TABLE emplr_emp_t DROP (row_exp_dtm, row_eff_dtm);

${ifjunit} ALTER TABLE emplr_emp_t DROP COLUMN ROW_EFF_DTM;
${ifjunit} ALTER TABLE emplr_emp_t DROP COLUMN ROW_EXP_DTM;