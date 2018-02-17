/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWEMP_DB;

/* Create Sequence for emplr_emp_id on Employee table */
CREATE SEQUENCE MUW_EMPLR_EMP_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
    
    /* Create Employer Employee table */
CREATE TABLE emplr_emp_t  ( 
    emplr_emp_id ${NUMBER19} ${IDENTITY_EMPLR_EMP_1_1} NOT NULL,
    emplr_id ${NUMBER19} NOT NULL,
    emp_ssn_num ${BIGSTRING}(9) NOT NULL,  -- need data type   
    prev_last_nme ${BIGSTRING}(70) NULL, -- not sure of element name
    annl_erngs_amt ${NUMBER8} NULL,
    emp_clas_cde ${BIGSTRING}(70) NULL,
    emp_idn_num ${BIGSTRING}(20) NULL,
    hire_dte DATE NULL,
    job_titl_nme ${BIGSTRING}(100) NULL,
    cmunn_mthd_cde CHAR(6) NOT NULL,
    rptg_locn_num ${BIGSTRING}(8) NULL,
    row_eff_dtm ${DATETIME} NOT NULL,
    row_exp_dtm ${DATETIME} NOT NULL,
    ccrcy_qty ${NUMBER5} NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL
);
    
/* Primary Key for Employee table */
${EMPLOYEE_PK}

