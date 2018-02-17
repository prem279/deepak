/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWACCT_DB;

ALTER TABLE emplr_prefcs_t ADD (
    supr_eligk_pre_pltn_i CHAR(1) NULL, 
    smkg_qualg_ques_i CHAR(1) NULL, 
    preg_qualg_ques_i CHAR(1) NULL, 
    absne_qualg_ques_i CHAR(1) NULL, 
    ins_dli_qualg_i CHAR(1) NULL, 
    new_elig_i CHAR(1) NULL, 
    dspl_loc_i CHAR(1) NULL, 
    exis_emp_new_elig_covr_i CHAR(1) NULL, 
    emp_id_lbl ${BIGSTRING}(40) NULL);

UPDATE emplr_prefcs_t
  SET supr_eligk_pre_pltn_i  = '0',
    smkg_qualg_ques_i        = '0',
    preg_qualg_ques_i        = '0',
    absne_qualg_ques_i       = '0',
    ins_dli_qualg_i          = '0',
    new_elig_i               = '0',
    dspl_loc_i               = '0',
    exis_emp_new_elig_covr_i = '0',
    emp_id_lbl               = '0';
    
${H2IGNORELINE} ALTER TABLE emplr_prefcs_t MODIFY (
${H2IGNORELINE}    supr_eligk_pre_pltn_i NOT NULL, 
${H2IGNORELINE}    smkg_qualg_ques_i NOT NULL, 
${H2IGNORELINE}    preg_qualg_ques_i NOT NULL, 
${H2IGNORELINE}    absne_qualg_ques_i NOT NULL, 
${H2IGNORELINE}    ins_dli_qualg_i NOT NULL, 
${H2IGNORELINE}    new_elig_i NOT NULL, 
${H2IGNORELINE}    dspl_loc_i NOT NULL, 
${H2IGNORELINE}    exis_emp_new_elig_covr_i NOT NULL, 
${H2IGNORELINE}    emp_id_lbl NOT NULL
${H2IGNORELINE} );

${ORACLEIGNORELINE} ALTER TABLE emplr_prefcs_t ALTER COLUMN supr_eligk_pre_pltn_i SET NOT NULL;
${ORACLEIGNORELINE} ALTER TABLE emplr_prefcs_t ALTER COLUMN smkg_qualg_ques_i SET NOT NULL; 
${ORACLEIGNORELINE} ALTER TABLE emplr_prefcs_t ALTER COLUMN preg_qualg_ques_i SET NOT NULL;
${ORACLEIGNORELINE} ALTER TABLE emplr_prefcs_t ALTER COLUMN absne_qualg_ques_i SET NOT NULL; 
${ORACLEIGNORELINE} ALTER TABLE emplr_prefcs_t ALTER COLUMN ins_dli_qualg_i SET NOT NULL; 
${ORACLEIGNORELINE} ALTER TABLE emplr_prefcs_t ALTER COLUMN new_elig_i SET NOT NULL; 
${ORACLEIGNORELINE} ALTER TABLE emplr_prefcs_t ALTER COLUMN dspl_loc_i SET NOT NULL; 
${ORACLEIGNORELINE} ALTER TABLE emplr_prefcs_t ALTER COLUMN exis_emp_new_elig_covr_i SET NOT NULL; 
${ORACLEIGNORELINE} ALTER TABLE emplr_prefcs_t ALTER COLUMN emp_id_lbl SET NOT NULL;
					
