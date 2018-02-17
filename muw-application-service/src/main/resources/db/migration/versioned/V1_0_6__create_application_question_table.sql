/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;

/* Create Sequence for muw_appln_medl_qualg_fc_t on Application Question table */
CREATE SEQUENCE MUW_APPLN_MEDL_QUALG_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
    
    /* Create Application Question table */
CREATE TABLE muw_appln_medl_qualg_fc_t
(
	appln_medl_qualg_fc_id ${NUMBER19} ${IDENTITY_APPLN_MEDL_QUALG_FC_1_1} NOT NULL,
	appln_id ${NUMBER19} NOT NULL,
	appln_role_cde CHAR(5) NOT NULL,
	medl_fctr_ques_cde CHAR(6) NOT NULL,
	altnt_medl_fctr_ques_cde CHAR(6) NULL,
	medl_fctr_i CHAR(1) NOT NULL,
	medl_fctr_txt ${BIGSTRING}(500) NULL,
	ccrcy_qty ${NUMBER5} NOT NULL,
	crt_dtm ${DATETIME} NOT NULL,
	crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
	last_updt_dtm ${DATETIME} NULL,
	last_updt_usr_id_num ${BIGSTRING}(15) NULL
); 
    
/* Primary Key for Application Question table */
${APPLN_MEDL_QUALG_FC_PK}

