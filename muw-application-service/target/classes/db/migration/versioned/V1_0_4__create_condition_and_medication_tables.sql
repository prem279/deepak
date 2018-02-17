/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;

/* Create Sequence for cond_id on ApplicantCondition table */
CREATE SEQUENCE MUW_APPLNT_COND_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
    
    /*Creating Applicant condition table*/
    
    CREATE TABLE muw_applnt_cond_t  ( 
    
    applnt_cond_id ${NUMBER19} ${IDENTITY_APPLNT_COND_1_1} NOT NULL,
    appln_applnt_id ${NUMBER19} NOT NULL,
    cond_cde CHAR(4) NOT NULL,
    othr_cond_txt ${BIGSTRING}(255) NULL,
    cond_onst_dte DATE NULL,
    cond_rcov_dte DATE NULL,
    trtm_hlth_prof_fullnme ${BIGSTRING}(255) NOT NULL,
    trtm_recvd_txt ${BIGSTRING}(1000) NOT NULL,
    ccrcy_qty ${NUMBER5} NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL
);

 /* Primary Key for Application Product table */
${APPLICANT_COND_PK} 

/* Foreign Key for Applicant condition table */
ALTER TABLE muw_applnt_cond_t
ADD CONSTRAINT fk_applnt_cond_to_appln_applnt
  FOREIGN KEY (appln_applnt_id)
  REFERENCES muw_appln_applnt_t(appln_applnt_id);
  

  
/* Create Sequence for mdct_id on ApplicantMedication table */
CREATE SEQUENCE MUW_APPLNT_MDCT_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
    
    /*Creating Applicant Medication table*/
    
    CREATE TABLE muw_applnt_mdct_t  ( 
    
    applnt_mdct_id ${NUMBER19} ${IDENTITY_APPLNT_MDCT_1_1} NOT NULL,
    applnt_cond_id ${NUMBER19} NOT NULL,
    appln_applnt_id ${NUMBER19} NOT NULL,
    mdct_cde CHAR(4) NOT NULL,
    othr_mdct_txt ${BIGSTRING}(255) NULL,
    prsc_dte DATE NULL,
    ccrcy_qty ${NUMBER5} NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL
);
    
/* Primary Key for Application Product table */
${APPLICANT_MDCT_PK} 

/* Foreign Key for Applicant Medication table */
ALTER TABLE muw_applnt_mdct_t
ADD CONSTRAINT fk_applnt_mdct_to_applnt_cond
  FOREIGN KEY (applnt_cond_id)
  REFERENCES muw_applnt_cond_t(applnt_cond_id);
