/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;

/* Create Sequence for appln_id on Application table */
CREATE SEQUENCE MUW_APPLN_APPLNT_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;

 /* Create Application Applicant table */
CREATE TABLE muw_appln_applnt_t  (      
	appln_applnt_id ${NUMBER19} ${IDENTITY_APPLN_APPLNT} NOT NULL,
	appln_id ${NUMBER19} NOT NULL,
	appln_role_cde CHAR(5) NULL,
	appln_recvd_dtm ${DATETIME} NULL,
	applnt_ssn CHAR(9) NULL,
	brth_dte DATE NULL,
	frst_nme ${BIGSTRING}(35) NULL,
	midin_nme CHAR(1) NULL,
	last_nme ${BIGSTRING}(35) NULL,
	prev_last_nme ${BIGSTRING}(35) NULL,
	brth_city_nme ${BIGSTRING}(70) NULL,
	brth_stpr_cde CHAR(2) NULL,
	brth_ctry_cde CHAR(3) NULL,
	gndr_cde CHAR(1) NULL,
	hght_inch_qty ${NUMBER3} NULL,
	wgt_qty ${NUMBER3} NULL,
	ccrcy_qty ${NUMBER5} NULL,
	crt_dtm ${DATETIME} NULL,
	crt_usr_id_num ${BIGSTRING}(15) NULL,
	last_updt_dtm ${DATETIME} NULL,
	last_updt_usr_id_num ${BIGSTRING}(15) NULL
);

 /* Primary Key for Application Applicant table */
${APPLN_APPLNT_PK} 


/* Foreign Key for Application Applicant table */
ALTER TABLE muw_appln_applnt_t
ADD CONSTRAINT fk_applnt_to_appln
  FOREIGN KEY (appln_id)
  REFERENCES muw_appln_t(appln_id);

