${SETSCHEMA} MUWAPPLN_DB;

drop table appln_event_t;

CREATE TABLE appln_event_t
(
	event_id ${NUMBER19} ${IDENTITY_APPLN_EVENT_1_1} NOT NULL,
	sjct_area_id ${NUMBER19} NULL,
	sjct_area_cde ${BIGSTRING}(10) NULL,
	chng_event_cde ${BIGSTRING}(10) NULL,
	busn_event_cde ${BIGSTRING}(10) NULL,
	bfr_evnt_data_txt CLOB NULL,
	chng_set_txt CLOB NOT NULL,
	aftr_evnt_data_txt CLOB NULL,
	lock_owner_name ${BIGSTRING}(70) NULL,
	lock_dtm ${DATETIME} NULL,
	prcsd_i CHAR(1) NULL,
	prcsd_dtm ${DATETIME} NULL,
	crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
	crt_dtm ${DATETIME} NOT NULL,
	last_updt_dtm ${DATETIME} NULL,
	last_updt_usr_id_num ${BIGSTRING}(15) NULL,
	ccrcy_qty ${NUMBER19} NULL
);

${APPLN_EVENT_PK}