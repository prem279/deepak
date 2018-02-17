/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWEMP_DB;

INSERT INTO emplr_emp_t ( 
    emplr_id,
    emp_ssn_num, 
   emp_idn_num,
    cmunn_mthd_cde,
    row_eff_dtm,
    row_exp_dtm,
    ccrcy_qty,
    crt_dtm,
    crt_usr_id_num )
    VALUES
    (
        1,
        '123456789',
       'N0296170',
        'EML',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        3,
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
    
    INSERT INTO emplr_emp_t ( 
    emplr_id,
    emp_ssn_num, 
   emp_idn_num,
    cmunn_mthd_cde,
    row_eff_dtm,
    row_exp_dtm,
    ccrcy_qty,
    crt_dtm,
    crt_usr_id_num )
    VALUES
    (
        2,
        '123986789',
       'N0296171',
        'FAX',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        3,
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
    
 /*   
INSERT INTO emp_phon_num_t (emplr_emp_id, PHON_TYP_CDE, PHON_CTRY_CDE_NUM, 
  PHON_AREA_CDE_NUM, PHON_EXCH_NUM, PHON_LOCL_NUM, PHON_EXTN_NUM, pref_phon_num_i, ROW_EFF_DTM,
   ROW_EXP_DTM, CCRCY_QTY, CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, 
LAST_UPDT_USR_ID_NUM)
    VALUES
    (  
    '1',
    '01',
    '91',
    '911',
    '3',
    '22',
    '321',
    '1',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP, 
    '4',
    CURRENT_TIMESTAMP,
    'N0296170',
    CURRENT_TIMESTAMP,
    NULL
    
   
  ); 

    
/*    
    INSERT INTO emp_addr_t ( EMPLR_STKR_LDGR_NUM, EMPLR_STKR_SRL_NUM, EMPLR_NME, CO_ACCS_CDE,
                SITUS_STT_CDE, BEG_DTE, CANCN_DTE, ANNV_DTE, ALTNT_NME, ROW_EFF_DTM, ROW_EXP_DTM, CCRCY_QTY,
                CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM )
    VALUES
    (
        'BB',
        '654321',
        'LIBERTY MUTUAL',
        'MYLIBCONNECT',
        'NH',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'LIBERTY',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        3,
        CURRENT_TIMESTAMP,
        'JUNIT',
        CURRENT_TIMESTAMP,
        NULL
    );

    
INSERT INTO EMPLR_EOI_PROD_T ( EMPLR_ID, PROD_CDE, PROD_LBL_NME, MAX_BEN_AMT, MAX_NON_MEDL_BEN_AMT, BEG_DTE, 
								CANCN_DTE, ROW_EFF_DTM, ROW_EXP_DTM,
                				CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM )
    VALUES
    (
    	1,
        'BCL',
        'LABEL',
        0,
        0,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'JUNIT',
        NULL,
        NULL
    );

    INSERT INTO EMPLR_EOI_PROD_T ( EMPLR_ID, PROD_CDE, PROD_LBL_NME, MAX_BEN_AMT, MAX_NON_MEDL_BEN_AMT, BEG_DTE, 
								CANCN_DTE, ROW_EFF_DTM, ROW_EXP_DTM,
                				CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM )
    VALUES
    (
    	2,
        'NBV',
        'LABEL',
        0,
        0,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'JUNIT',
        NULL,
        NULL
    );

    
INSERT INTO EMPLR_PREFCS_T ( SBMN_MTHD_CDE, INIT_ERLMT_I, WTG_PRD_DYS, VRS_I, ALLW_LAT_ERLMT_I, ALLW_FAM_STTS_CHNG_I,
								DSPL_PROD_AMTS_I, APPROVE_EFF_DTE_CALCN_RULE_CDE, PRLL_SYNC_MDE, PRLL_SYNC_DY, SAL_INCS_REQR_EVDN_OF_INSBTY_I, APPL_IDN_MTHD_CDE,
								ANNL_ERLMT_I, ANNL_ERLMT_STRT_DTE, ANNL_ERLMT_PRD_QTY, FAM_STTS_CHNG_DAYS_LMT_QTY, APPLN_RTRN_DAYS_LMT_QTY,
								MEDL_RTRN_DAYS_LMT_QTY,	PRFMC_GUAR_I, PRFMC_GUAR_DUR_QTY, EMP_LBL, SPUS_LBL, CMUNN_MTHD_CDE, SEND_EMPLR_APP_LTTR_I,
								SEND_EMPLR_CLSR_LTTR_I, SEND_EMPLR_DNL_LTTR_I, SEND_EMP_APP_LTTR_I, SEND_EMP_MEDL_LTTR_I, SEND_EMP_DNL_LTTR_I,
								SEND_EMP_CLSR_LTTR_I, INCL_EFF_DTE_APP_EMPLR_I, INCL_EFF_DTE_APP_EMP_I, SEND_EMP_INCP_LTTR_I, SEND_EMPLR_MEDL_FLUP_LTTR_I,
								SEND_EMP_MEDL_FLUP_LTTR_I, MEDL_GRC_PRD, RTRN_INCP_DAYS, INCP_GRC_PRD, PRTBLTY_I, PRTBLTY_PREF_RATES_I,
								PRTBLTY_CNVS_MAILG_SVC_I, CNVS_GRC_PRD, MID_MKT_EMPLR_I, FILE_FEED_I, FILE_FEED_FREQY_CDE, FILE_FEED_MAILG_CDE,
								FILE_FEED_HR_CNTC, EXCH_I, EXCH_NME_CDE, SSO_I, ROW_EFF_DTM, ROW_EXP_DTM, CCRCY_QTY, CRT_DTM, CRT_USR_ID_NUM,
    							LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM )
    VALUES
    (
    	'ONLN',
    	1,
    	30,
    	1,
    	1,
    	0,
    	1,
    	'717117',
    	'BIWKLY',
    	'THRDAY',
    	0,
    	'EID',
    	1,
    	CURRENT_TIMESTAMP,
    	30,
    	5,
    	30,
    	60,
    	1,
    	100,
    	'Employee',
    	'Domestic Partner',
    	'email',
    	1,
    	1,
    	0,
    	1,
    	0,
    	1,
    	0,
    	0,
    	0,
    	0,
    	1,
    	1,
    	30,
    	120,
    	60,
    	1,
    	0,
    	0,
    	30,
    	0,
    	1,
    	'1',
    	'016c',
    	'Please email Chris Jennings at Liberty Mutual dot com',
    	1,
    	'EXC',
    	1,
    	CURRENT_TIMESTAMP,
    	CURRENT_TIMESTAMP,
    	0,
    	CURRENT_TIMESTAMP,
    	'n0159479',
    	CURRENT_TIMESTAMP,
    	'n0000185'
    );
    
    
    INSERT INTO EMPLR_PREFCS_T ( SBMN_MTHD_CDE, INIT_ERLMT_I, WTG_PRD_DYS, VRS_I, ALLW_LAT_ERLMT_I, ALLW_FAM_STTS_CHNG_I,
								DSPL_PROD_AMTS_I, APPROVE_EFF_DTE_CALCN_RULE_CDE, PRLL_SYNC_MDE, PRLL_SYNC_DY, SAL_INCS_REQR_EVDN_OF_INSBTY_I, APPL_IDN_MTHD_CDE,
								ANNL_ERLMT_I, ANNL_ERLMT_STRT_DTE, ANNL_ERLMT_PRD_QTY, FAM_STTS_CHNG_DAYS_LMT_QTY, APPLN_RTRN_DAYS_LMT_QTY,
								MEDL_RTRN_DAYS_LMT_QTY,	PRFMC_GUAR_I, PRFMC_GUAR_DUR_QTY, EMP_LBL, SPUS_LBL, CMUNN_MTHD_CDE, SEND_EMPLR_APP_LTTR_I,
								SEND_EMPLR_CLSR_LTTR_I, SEND_EMPLR_DNL_LTTR_I, SEND_EMP_APP_LTTR_I, SEND_EMP_MEDL_LTTR_I, SEND_EMP_DNL_LTTR_I,
								SEND_EMP_CLSR_LTTR_I, INCL_EFF_DTE_APP_EMPLR_I, INCL_EFF_DTE_APP_EMP_I, SEND_EMP_INCP_LTTR_I, SEND_EMPLR_MEDL_FLUP_LTTR_I,
								SEND_EMP_MEDL_FLUP_LTTR_I, MEDL_GRC_PRD, RTRN_INCP_DAYS, INCP_GRC_PRD, PRTBLTY_I, PRTBLTY_PREF_RATES_I,
								PRTBLTY_CNVS_MAILG_SVC_I, CNVS_GRC_PRD, MID_MKT_EMPLR_I, FILE_FEED_I, FILE_FEED_FREQY_CDE, FILE_FEED_MAILG_CDE,
								FILE_FEED_HR_CNTC, EXCH_I, EXCH_NME_CDE, SSO_I, ROW_EFF_DTM, ROW_EXP_DTM, CCRCY_QTY, CRT_DTM, CRT_USR_ID_NUM,
    							LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM )
    VALUES
    (
    	'ONLN',
    	1,
    	30,
    	1,
    	1,
    	0,
    	1,
    	'717117',
    	'BIWKLY',
    	'THRDAY',
    	0,
    	'EID',
    	1,
    	CURRENT_TIMESTAMP,
    	30,
    	5,
    	30,
    	60,
    	1,
    	100,
    	'Employee',
    	'Domestic Partner',
    	'email',
    	1,
    	1,
    	0,
    	1,
    	0,
    	1,
    	0,
    	0,
    	0,
    	0,
    	1,
    	1,
    	30,
    	120,
    	60,
    	1,
    	0,
    	0,
    	30,
    	0,
    	1,
    	'1',
    	'016c',
    	'Please email Chris Jennings at Liberty Mutual dot com',
    	1,
    	'EXC',
    	1,
    	CURRENT_TIMESTAMP,
    	CURRENT_TIMESTAMP,
    	0,
    	CURRENT_TIMESTAMP,
    	'n0159479',
    	CURRENT_TIMESTAMP,
    	'n0000185'
    );*/