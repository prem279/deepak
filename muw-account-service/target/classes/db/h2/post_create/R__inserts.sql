/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWACCT_DB;

INSERT INTO MUW_EMPLR_T ( EMPLR_STKR_LDGR_NUM, EMPLR_STKR_SRL_NUM, EMPLR_NME, CO_ACCS_CDE,
                SITUS_STT_CDE, BEG_DTE, CANCN_DTE, ANNV_DTE, ALTNT_NME, CCRCY_QTY, 
                CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM, EMPLR_PREFCS_ID )
    VALUES
    (
        '85',
        '123456',
        'LIBERTY MUTUAL',
        'MYLIBCONNECT',
        'NH',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'LIBERTY',
        0,
        CURRENT_TIMESTAMP,
        'JUNIT',
        CURRENT_TIMESTAMP,
        null,
        1
    );
    
    
    INSERT INTO MUW_EMPLR_T ( EMPLR_STKR_LDGR_NUM, EMPLR_STKR_SRL_NUM, EMPLR_NME, CO_ACCS_CDE,
                SITUS_STT_CDE, BEG_DTE, CANCN_DTE, ANNV_DTE, ALTNT_NME, CCRCY_QTY, 
                CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM, EMPLR_PREFCS_ID )
    VALUES
    (
        '86',
        '654321',
        'LIBERTY MUTUAL',
        'MYLIBCONNECT',
        'NH',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'LIBERTY',
        0,
        CURRENT_TIMESTAMP,
        'JUNIT',
        CURRENT_TIMESTAMP,
        null,
        2
    );

    
INSERT INTO EMPLR_EOI_PROD_T ( EMPLR_ID, PROD_CDE, BEG_DTE,
								CANCN_DTE, CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM, CCRCY_QTY )
    VALUES
    (
    	1,
        'LTD',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'JUNIT',
        NULL,
        NULL,
        0
    );

    INSERT INTO EMPLR_EOI_PROD_T ( EMPLR_ID, PROD_CDE, BEG_DTE,
								CANCN_DTE, CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM, CCRCY_QTY )
    VALUES
    (
    	2,
        'OCL',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'JUNIT',
        NULL,
        NULL,
        0
    );

    INSERT INTO EMPLR_EOI_PROD_T ( EMPLR_ID, PROD_CDE, BEG_DTE,
								CANCN_DTE, CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM, CCRCY_QTY )
    VALUES
    (
    	1,
        'BSL',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'JUNIT',
        NULL,
        NULL,
        0
    );
    
    INSERT INTO EMPLR_EOI_PROD_T ( EMPLR_ID, PROD_CDE, BEG_DTE,
								CANCN_DTE, CRT_DTM, CRT_USR_ID_NUM, LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM, CCRCY_QTY )
    VALUES
    (
    	1,
        'STD',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'JUNIT',
        NULL,
        NULL,
        0
    );
    
    
INSERT INTO EMPLR_PREFCS_T ( SBMN_MTHD_CDE, INIT_ERLMT_I, WTG_PRD_DYS,  ALLW_LAT_ERLMT_I, ALLW_FAM_STTS_CHNG_I,
								DSPL_PROD_AMTS_I, APPROVE_EFF_DTE_CALCN_RULE_CDE, PRLL_SYNC_MDE, PRLL_SYNC_DY, SAL_INCS_REQR_EVDN_OF_INSBTY_I, APPL_IDN_MTHD_CDE,
								ANNL_ERLMT_I, ANNL_ERLMT_STRT_DTE, ANNL_ERLMT_PRD_QTY, FAM_STTS_CHNG_DAYS_LMT_QTY, APPLN_RTRN_DAYS_LMT_QTY,
								MEDL_RTRN_DAYS_LMT_QTY,	PRFMC_GUAR_I, PRFMC_GUAR_DUR_QTY, EMP_LBL, SPUS_LBL, SEND_EMPLR_APP_LTTR_I,
								SEND_EMPLR_CLSR_LTTR_I, SEND_EMPLR_DNL_LTTR_I, SEND_EMP_APP_LTTR_I, SEND_EMP_MEDL_LTTR_I, SEND_EMP_DNL_LTTR_I,
								SEND_EMP_CLSR_LTTR_I, INCL_EFF_DTE_APP_EMPLR_I, INCL_EFF_DTE_APP_EMP_I, SEND_EMP_INCP_LTTR_I, SEND_EMPLR_MEDL_FLUP_LTTR_I,
								SEND_EMP_MEDL_FLUP_LTTR_I, MEDL_GRC_PRD, RTRN_INCP_DAYS, INCP_GRC_PRD, PRTBLTY_I, PRTBLTY_PREF_RATES_I,
								PRTBLTY_CNVS_MAILG_SVC_I, CNVS_GRC_PRD, MID_MKT_EMPLR_I, FILE_FEED_I, FILE_FEED_FREQY_CDE, FILE_FEED_MAILG_CDE,
								FILE_FEED_HR_CNTC, EXCH_I, EXCH_NME_CDE, SSO_I, DSAB_PREG_APPR_I, CCRCY_QTY, CRT_DTM, CRT_USR_ID_NUM,
    							LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM, supr_eligk_pre_pltn_i, smkg_qualg_ques_i, preg_qualg_ques_i, absne_qualg_ques_i,
    							ins_dli_qualg_i, new_elig_i, dspl_loc_i, emp_id_lbl)
    VALUES
    (
    	'ONLN',
    	1,
    	30,
    	1,
    	0,
    	1,
    	'ASDFGH',
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
    	'EMP',
    	'SPUS',
    	
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
    	'ASDFGH',
    	'FDSAG',
    	'Please email Chris Jennings at Liberty Mutual dot com',
    	1,
    	'EXC',
    	1,
    	0,
    	0,
    	CURRENT_TIMESTAMP,
    	'n0159479',
    	CURRENT_TIMESTAMP,
    	'n0000185',
    	1,
    	1,
    	1,
    	0,
    	1,
    	
    	0,
    	1,
    	
    	
    	'EMPID'
    );
    
    
    INSERT INTO EMPLR_PREFCS_T ( SBMN_MTHD_CDE, INIT_ERLMT_I, WTG_PRD_DYS,  ALLW_LAT_ERLMT_I, ALLW_FAM_STTS_CHNG_I,
								DSPL_PROD_AMTS_I, APPROVE_EFF_DTE_CALCN_RULE_CDE, PRLL_SYNC_MDE, PRLL_SYNC_DY, SAL_INCS_REQR_EVDN_OF_INSBTY_I, APPL_IDN_MTHD_CDE,
								ANNL_ERLMT_I, ANNL_ERLMT_STRT_DTE, ANNL_ERLMT_PRD_QTY, FAM_STTS_CHNG_DAYS_LMT_QTY, APPLN_RTRN_DAYS_LMT_QTY,
								MEDL_RTRN_DAYS_LMT_QTY,	PRFMC_GUAR_I, PRFMC_GUAR_DUR_QTY, EMP_LBL, SPUS_LBL, SEND_EMPLR_APP_LTTR_I,
								SEND_EMPLR_CLSR_LTTR_I, SEND_EMPLR_DNL_LTTR_I, SEND_EMP_APP_LTTR_I, SEND_EMP_MEDL_LTTR_I, SEND_EMP_DNL_LTTR_I,
								SEND_EMP_CLSR_LTTR_I, INCL_EFF_DTE_APP_EMPLR_I, INCL_EFF_DTE_APP_EMP_I, SEND_EMP_INCP_LTTR_I, SEND_EMPLR_MEDL_FLUP_LTTR_I,
								SEND_EMP_MEDL_FLUP_LTTR_I, MEDL_GRC_PRD, RTRN_INCP_DAYS, INCP_GRC_PRD, PRTBLTY_I, PRTBLTY_PREF_RATES_I,
								PRTBLTY_CNVS_MAILG_SVC_I, CNVS_GRC_PRD, MID_MKT_EMPLR_I, FILE_FEED_I, FILE_FEED_FREQY_CDE, FILE_FEED_MAILG_CDE,
								FILE_FEED_HR_CNTC, EXCH_I, EXCH_NME_CDE, SSO_I, DSAB_PREG_APPR_I, CCRCY_QTY, CRT_DTM, CRT_USR_ID_NUM,
    							LAST_UPDT_DTM, LAST_UPDT_USR_ID_NUM, supr_eligk_pre_pltn_i, smkg_qualg_ques_i, preg_qualg_ques_i, absne_qualg_ques_i,
    							ins_dli_qualg_i, new_elig_i, dspl_loc_i, emp_id_lbl)
    VALUES
    (
    	'ONLN',
    	1,
    	30,
    	1,
    	0,
    	1,
    	'ASDFGH',
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
    	'EMP',
    	'SPUS',
    
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
    	'ASDFGH',
    	'FDSAG',
    	'Please email Chris Jennings at Liberty Mutual dot com',
    	1,
    	'EXC',
    	1,
    	0,
    	0,
    	CURRENT_TIMESTAMP,
    	'n0159479',
    	CURRENT_TIMESTAMP,
    	'n0000185',
    	1,
    	1,
    	1,
    	0,
    	1,
    	1,
    	1,
    	'EMPID'
    );
    
    insert into lbl_txt_t (lbl_id, lang_cde, lbl_txt, ccrcy_qty, crt_usr_id_num, crt_dtm) values ((select lbl_txt_seq.nextval from dual), 'ENG', 'Short Term Disability Plus', 0, 'n123456789', CURRENT_TIMESTAMP);
	insert into prod_lbl_t (select prod_id, (select lbl_id from lbl_txt_t where lang_cde = 'ENG' and lbl_txt = 'Short Term Disability Plus') from EMPLR_EOI_PROD_T where prod_cde = 'STD');
    insert into lbl_txt_t (lbl_id, lang_cde, lbl_txt, ccrcy_qty, crt_usr_id_num, crt_dtm) values ((select lbl_txt_seq.nextval from dual), 'SPA', 'Incapacidad de Corto Plazo', 0, 'n123456789', CURRENT_TIMESTAMP);
	insert into prod_lbl_t (select prod_id, (select lbl_id from lbl_txt_t where lang_cde = 'SPA' and lbl_txt = 'Incapacidad de Corto Plazo') from EMPLR_EOI_PROD_T where prod_cde = 'STD');
    
    insert into lbl_txt_t (lbl_id, lang_cde, lbl_txt, ccrcy_qty, crt_usr_id_num, crt_dtm) values ((select lbl_txt_seq.nextval from dual), 'ENG', 'Long Term Disability Plus', 0, 'n123456789', CURRENT_TIMESTAMP);
	insert into prod_lbl_t (select prod_id, (select lbl_id from lbl_txt_t where lang_cde = 'ENG' and lbl_txt = 'Long Term Disability Plus') from EMPLR_EOI_PROD_T where prod_cde = 'LTD');
    insert into lbl_txt_t (lbl_id, lang_cde, lbl_txt, ccrcy_qty, crt_usr_id_num, crt_dtm) values ((select lbl_txt_seq.nextval from dual), 'SPA', 'Discapacidad a Largo Plazo', 0, 'n123456789', CURRENT_TIMESTAMP);
	insert into prod_lbl_t (select prod_id, (select lbl_id from lbl_txt_t where lang_cde = 'SPA' and lbl_txt = 'Discapacidad a Largo Plazo') from EMPLR_EOI_PROD_T where prod_cde = 'LTD');
   
    insert into lbl_txt_t (lbl_id, lang_cde, lbl_txt, ccrcy_qty, crt_usr_id_num, crt_dtm) values ((select lbl_txt_seq.nextval from dual), 'ENG', 'Associate', 0, 'n123456789', CURRENT_TIMESTAMP);
    insert into lbl_txt_t (lbl_id, lang_cde, lbl_txt, ccrcy_qty, crt_usr_id_num, crt_dtm) values ((select lbl_txt_seq.nextval from dual), 'ENG', 'Associate Number', 0, 'n123456789', CURRENT_TIMESTAMP);
    insert into lbl_txt_t (lbl_id, lang_cde, lbl_txt, ccrcy_qty, crt_usr_id_num, crt_dtm) values ((select lbl_txt_seq.nextval from dual), 'ENG', 'Domestic Partner', 0, 'n123456789', CURRENT_TIMESTAMP);
  
    
    insert into pref_lbl_t (select emplr_prefcs_id, emp_lbl, (select lbl_id from lbl_txt_t where lang_cde = 'ENG' and lbl_txt = 'Associate') from EMPLR_PREFCS_T );
    insert into pref_lbl_t (select emplr_prefcs_id, emp_id_lbl, (select lbl_id from lbl_txt_t where lang_cde = 'ENG' and lbl_txt = 'Associate Number') from EMPLR_PREFCS_T );
    insert into pref_lbl_t (select emplr_prefcs_id, spus_lbl, (select lbl_id from lbl_txt_t where lang_cde = 'ENG' and lbl_txt = 'Domestic Partner') from EMPLR_PREFCS_T );