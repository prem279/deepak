${SETSCHEMA} MUWAPPLN_DB;

/* Create Sequence for appln_id on Application table */
CREATE SEQUENCE applnt_quesn_rspn_seq
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;

CREATE TABLE APPLN_APPLNT_QUESN_RSPN_T (
	applnt_quesn_rspn_id ${NUMBER19} ${IDENTITY_QUES_RES_1_1} NOT NULL,	
	applnt_cond_id ${NUMBER19} NOT NULL,
	quesn_typ_cde CHAR(6) NOT NULL,
	ques_cde CHAR(6) NOT NULL,
	addl_ques_cde CHAR(6) NULL,
	rspn_amt ${NUMBER5_2} NULL,
	rspn_dte ${DATETIME} NULL,
	rspn_i CHAR(1) NULL,
	rspn_num ${BIGSTRING}(35) NULL,
	rspn_qty ${NUMBER5_2} NULL,
	RSPN_TXT ${BIGSTRING}(1000) NULL,
	ccrcy_qty ${NUMBER5} NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL
); 

/* Primary Key for Applicant Question Response table */
${QUESTION_RESPONSE_PK}

 /* Foreign Key for Applicant Question Response table to Applicant Condition table */
	ALTER TABLE APPLN_APPLNT_QUESN_RSPN_T
	ADD CONSTRAINT fk_QUESN_RSPN_to_applnt_cond
  FOREIGN KEY (applnt_cond_id)
  REFERENCES muw_applnt_cond_t(applnt_cond_id);