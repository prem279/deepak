/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;

/* Create Sequence for appln_id on Application table */
CREATE SEQUENCE MUW_APPLN_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;

    
/* Create Application table */
CREATE TABLE muw_appln_t  ( 
    appln_id ${NUMBER19} ${IDENTITY_APPLN_1_1} NOT NULL,
    emplr_id ${NUMBER19} NOT NULL,
    emplr_emp_id ${NUMBER19} NOT NULL,
    emp_ssn_num ${BIGSTRING}(9) NOT NULL,
    appln_recvd_dtm ${DATETIME} NULL,
    fam_stts_chng_evnt_dte DATE NULL,
    reas_cde CHAR(4) NOT NULL,
    sbmn_mthd_cde CHAR(4) NOT NULL,
    ccrcy_qty ${NUMBER5} NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL
);

/* Primary Key for Application table */
${APPLICATION_PK}


/* Create Sequence for appln_prod_id on APPLICATION PRODUCT table */
CREATE SEQUENCE MUW_APPLN_PROD_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
    
/* Create Application Product table */
CREATE TABLE muw_appln_prod_t  ( 

        appln_prod_id ${NUMBER19} ${IDENTITY_APPLN_PROD_1_1} NOT NULL,
        appln_id ${NUMBER19} NOT NULL,
        prod_cde CHAR(3) NOT NULL,
        stts_typ_cde CHAR(6) NULL,
        ccrcy_qty ${NUMBER5} NOT NULL,
        crt_dtm ${DATETIME} NOT NULL,
        crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
        last_updt_dtm ${DATETIME} NULL,
        last_updt_usr_id_num ${BIGSTRING}(15) NULL
        
       ); 

    /* Foreign Key for Application Product table */
	ALTER TABLE muw_appln_prod_t
	ADD CONSTRAINT fk_appln_prod_to_muw_appln
	FOREIGN KEY (appln_id)
	REFERENCES muw_appln_t(appln_id);
	
/* Primary Key for Application Product table */
${APPLICATION_PROD_PK}
	  
/* Create Sequence for appln_applnt_prod_id on APPLICATION APPLICANT PRODUCT table */
CREATE SEQUENCE MUW_APPLN_APPLNT_PROD_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
	
/* Create Application Applicant Product table */
CREATE TABLE muw_appln_applnt_prod_t  ( 

        appln_applnt_prod_id ${NUMBER19} ${IDENTITY_APPLN_APPLNT_PROD_1_1} NOT NULL,
        appln_applnt_id ${NUMBER19} NOT NULL,
        appln_prod_id ${NUMBER19} NOT NULL,
        appln_id ${NUMBER19} NOT NULL,
		curr_prod_amt_typ_cde CHAR(3) NULL,
        curr_prod_amt DECIMAL(11,2) NULL,
        curr_prod_pct DECIMAL(8,4) NULL,
        curr_prod_qty DECIMAL(7,2) NULL,
        reqd_prod_amt_typ_cde CHAR(3) NULL,
        reqd_prod_amt DECIMAL(11,2) NULL,
        reqd_prod_pct DECIMAL(8,4) NULL,
        reqd_prod_qty DECIMAL(7,2) NULL,
        reqd_prod_bgn_dte DATE NULL,
        stts_dtrmn_dte DATE NOT NULL,
        stts_typ_cde CHAR(6) NULL,
        prod_appd_eff_dte DATE NULL,
        ccrcy_qty ${NUMBER5} NOT NULL,
        crt_dtm ${DATETIME} NOT NULL,
        crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
        last_updt_dtm ${DATETIME} NULL,
        last_updt_usr_id_num ${BIGSTRING}(15) NULL
);

    /* Foreign Key for Application Applicant Product table to Application table */
	ALTER TABLE muw_appln_applnt_prod_t
	ADD CONSTRAINT fk_appln_applnt_prod_to_appln
	FOREIGN KEY (appln_id)
	REFERENCES muw_appln_t(appln_id);

	/* Foreign Key for Application Applicant Product table to application product table */
	ALTER TABLE muw_appln_applnt_prod_t
	ADD CONSTRAINT fk_applnt_prod_to_appln_prod
	FOREIGN KEY (appln_prod_id)
	REFERENCES muw_appln_prod_t(appln_prod_id);
	
	${APPLN_APPLNT_PROD_PK}