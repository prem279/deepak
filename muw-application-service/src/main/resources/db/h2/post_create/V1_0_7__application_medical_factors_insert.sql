INSERT INTO MUWAPPLN_DB.MUW_APPLN_MEDL_QUALG_FC_T
(
	APPLN_ID, 
	APPLN_ROLE_CDE, 
	MEDL_FCTR_QUES_CDE, 
	ALTNT_MEDL_FCTR_QUES_CDE, 
	MEDL_FCTR_I, 
	MEDL_FCTR_TXT, 
	CCRCY_QTY, 
	CRT_DTM, 
	CRT_USR_ID_NUM, 
	LAST_UPDT_DTM, 
	LAST_UPDT_USR_ID_NUM
)
VALUES
(
	1, 
	'EMPLY', 
	'ABSNCE', 
	NULL,
	1,
	'In the past year, have you been absent from work for 5 or more consecutive days due to illness and/or medical treatment, been home-confined, or made a claim for or received benefits, compensation, or pension for any injury, sickness, disability, or impaired condition?',
	1, 
	CURRENT_TIMESTAMP, 
	'JUNIT', 
	NULL, 
	NULL
);
