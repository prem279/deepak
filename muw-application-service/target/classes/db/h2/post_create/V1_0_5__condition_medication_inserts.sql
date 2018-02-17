INSERT INTO MUWAPPLN_DB.MUW_APPLNT_COND_T
(
   
    appln_applnt_id,
    cond_cde,
    othr_cond_txt,
    cond_onst_dte,
    cond_rcov_dte,
    trtm_hlth_prof_fullnme,
    trtm_recvd_txt,
    ccrcy_qty,
    crt_dtm,
    crt_usr_id_num,
    last_updt_dtm,
    last_updt_usr_id_num
)
VALUES
(
	1,
	'ABCD', 
	NULL,
	'2016-12-12',
	'2012-12-12',
	'DEEPAK',
	'haha',
	5, 
	CURRENT_TIMESTAMP, 
	'JUNIT', 
	NULL, 
	'N0296170'
);


INSERT INTO MUWAPPLN_DB.MUW_APPLNT_MDCT_T
(
     
    applnt_cond_id,
    appln_applnt_id,
    mdct_cde,
    othr_mdct_txt,
    prsc_dte,
    ccrcy_qty,
    crt_dtm,
    crt_usr_id_num,
    last_updt_dtm,
    last_updt_usr_id_num
)
VALUES
(
	1,
	1,
	'ABCD', 
	NULL,
    '2016-12-12',
	5, 
	CURRENT_TIMESTAMP, 
	'JUNIT', 
	NULL, 
	NULL
);