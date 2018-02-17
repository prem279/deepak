/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWACCT_DB;

/* Create Sequence for emplr_id on Employer table */
CREATE SEQUENCE MUW_EMPLR_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
 
/* Create Employer table */
CREATE TABLE muw_emplr_t  ( 
    emplr_id ${NUMBER19} ${IDENTITY_EMPLR_1_1} NOT NULL,
    emplr_stkr_ldgr_num CHAR(2) NOT NULL,
    emplr_stkr_srl_num CHAR(6) NOT NULL,
    emplr_nme ${BIGSTRING}(132) NOT NULL,    
    co_accs_cde ${BIGSTRING}(20) NULL,
    situs_stt_cde CHAR(2) NOT NULL,
    beg_dte DATE NOT NULL,
    cancn_dte DATE NULL,
    annv_dte DATE NOT NULL, -- look into how to store this as just MM/DD
    altnt_nme ${BIGSTRING}(132) NULL,
    row_eff_dtm ${DATETIME} NOT NULL,
    row_exp_dtm ${DATETIME} NOT NULL,
    ccrcy_qty ${NUMBER5} NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL,
    emplr_prefcs_id ${NUMBER19} NULL
);

/* Primary Key for Employer table */
${EMPLOYER_PK}

/* Unique Index on Div/Serial */    
CREATE UNIQUE INDEX div_serial_unique_index
ON muw_emplr_t (emplr_stkr_ldgr_num, emplr_stkr_srl_num);

/* Create Sequence for emplr_prefcs_id on Preferences table */
CREATE SEQUENCE MUW_PREF_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
    
/* Create Employer Preferences table */ 
CREATE TABLE emplr_prefcs_t  (
    emplr_prefcs_id ${NUMBER19} ${IDENTITY_PREF_1_1} NOT NULL,
    sbmn_mthd_cde CHAR(4) NULL,
    init_erlmt_i CHAR(1) NOT NULL,
    wtg_prd_dys ${BIGSTRING}(30) NULL, 
    allw_lat_erlmt_i CHAR(1) NOT NULL,
    allw_fam_stts_chng_i CHAR(1) NOT NULL,
    dspl_prod_amts_i CHAR(1) NOT NULL,
    approve_eff_dte_calcn_rule_cde CHAR(6) NOT NULL,
    prll_sync_mde CHAR(6) NULL,
    prll_sync_dy CHAR(6) NULL,
    sal_incs_reqr_evdn_of_insbty_i CHAR(1) NOT NULL, -- Had to shorten this for oracle
    appl_idn_mthd_cde CHAR(3) NOT NULL,
    annl_erlmt_i CHAR(1) NOT NULL,
    annl_erlmt_strt_dte DATE NULL,  -- look into how to store this as just MM/DD
    annl_erlmt_prd_qty  ${NUMBER5} NULL,
    fam_stts_chng_days_lmt_qty ${NUMBER5} NULL,
    appln_rtrn_days_lmt_qty ${NUMBER5}  NULL,
    medl_rtrn_days_lmt_qty ${NUMBER5} NOT NULL,
    prfmc_guar_i CHAR(1) NOT NULL,
    prfmc_guar_dur_qty ${NUMBER5} NULL,
    emp_lbl ${BIGSTRING}(30) NULL, 
    spus_lbl ${BIGSTRING}(40) NULL,
    send_emplr_app_lttr_i CHAR(1) NOT NULL,
    send_emplr_clsr_lttr_i CHAR(1) NOT NULL,
    send_emplr_dnl_lttr_i CHAR(1) NOT NULL,
    send_emp_app_lttr_i CHAR(1) NOT NULL,
    send_emp_medl_lttr_i CHAR(1) NOT NULL,
    send_emp_dnl_lttr_i CHAR(1) NOT NULL,
    send_emp_clsr_lttr_i CHAR(1) NOT NULL,
    incl_eff_dte_app_emplr_i CHAR(1) NOT NULL,
    incl_eff_dte_app_emp_i CHAR(1) NOT NULL,
    send_emp_incp_lttr_i CHAR(1) NOT NULL,
    send_emplr_medl_flup_lttr_i CHAR(1) NOT NULL,
    send_emp_medl_flup_lttr_i CHAR(1) NOT NULL,
    medl_grc_prd ${NUMBER5} NOT NULL,
    rtrn_incp_days ${NUMBER5} NOT NULL,
    incp_grc_prd ${NUMBER5} NOT NULL,
    prtblty_i CHAR(1) NOT NULL,
    prtblty_pref_rates_i CHAR(1) NOT NULL,
    prtblty_cnvs_mailg_svc_i CHAR(1) NOT NULL,
    cnvs_grc_prd ${NUMBER5} NULL,
    mid_mkt_emplr_i CHAR(1) NOT NULL,
    sso_i CHAR(1) NOT NULL, 
    file_feed_i CHAR(1) NOT NULL,
    file_feed_freqy_cde CHAR(6) NULL,
    file_feed_mailg_cde CHAR(5) NULL,
    file_feed_hr_cntc ${BIGSTRING}(255) NULL,
    exch_i CHAR(1) NOT NULL,
    exch_nme_cde CHAR(3) NULL,
    row_eff_dtm ${DATETIME} NOT NULL,
    row_exp_dtm ${DATETIME} NOT NULL,
    ccrcy_qty ${NUMBER5} NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL 
);

/* Create Sequence for prod_id on Product table */
CREATE SEQUENCE MUW_EOIPROD_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
 
/* Create Product table */
CREATE TABLE emplr_eoi_prod_t  (
    prod_id ${NUMBER19} ${IDENTITY_EOIPROD_1_1} NOT NULL,
    emplr_id ${NUMBER19} NOT NULL,
    prod_cde CHAR(3) NOT NULL,
    prod_lbl_nme ${BIGSTRING}(100) NULL, -- this didn't have a size in the model
    beg_dte DATE NOT NULL,
    cancn_dte DATE NULL,
    row_eff_dtm ${DATETIME} NOT NULL,
    row_exp_dtm ${DATETIME} NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL
);

/* Foreign Key for Product table */
ALTER TABLE emplr_eoi_prod_t
ADD CONSTRAINT fk_eoi_prod_to_muw_emplr
  FOREIGN KEY (emplr_id)
  REFERENCES muw_emplr_t(emplr_id);
  