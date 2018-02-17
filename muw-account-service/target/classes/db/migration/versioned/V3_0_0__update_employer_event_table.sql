/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWACCT_DB;

/* Drop Employer Event table to make a few corrections */
DROP TABLE muw_emplr_evnt_t;

/* Create Employer Event table */
CREATE TABLE muw_emplr_evnt_t  ( 
    evnt_id ${NUMBER19} ${IDENTITY_EMPLR_EVNT_1_1} NOT NULL,
    sjct_area_id ${NUMBER19} NOT NULL,
    sjct_area_cde ${BIGSTRING}(70) NULL,
    chng_evnt_cde ${BIGSTRING}(10) NOT NULL,
    busn_evnt_cde ${BIGSTRING}(10) NULL,
    bfr_evnt_data_txt CLOB NULL,
    chng_set_txt CLOB NOT NULL,
    aftr_evnt_data_txt CLOB NOT NULL,
    prcsd_i CHAR(1) NOT NULL,
    prcsd_dtm ${DATETIME} NULL,
    row_eff_dtm ${DATETIME} NOT NULL,
    row_exp_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL    
);
   
/* Primary Key for Employer Event table */
${EMPLOYER_EVENT_PK}