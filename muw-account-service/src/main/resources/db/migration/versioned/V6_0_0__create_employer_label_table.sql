
    /* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWACCT_DB;

CREATE SEQUENCE LBL_TXT_SEQ
    START WITH     1
    INCREMENT BY   1
    NOCACHE
    NOCYCLE;
 
/* Create label table */
CREATE TABLE lbl_txt_t  (
    lbl_id		${NUMBER19}  NOT NULL,
	lang_cde		VARCHAR(3) NOT NULL,
	lbl_txt			${BIGSTRING}(500) NOT NULL,
	ccrcy_qty ${NUMBER5} NOT NULL,
    crt_dtm ${DATETIME} NOT NULL,
    crt_usr_id_num ${BIGSTRING}(15) NOT NULL,
    last_updt_dtm ${DATETIME} NULL,
    last_updt_usr_id_num ${BIGSTRING}(15) NULL
);
   
/* Primary Key for label table */
${LABEL_TXT_PK}

/* Create product/label join table */
CREATE TABLE prod_lbl_t  (
	prod_id		${NUMBER19} NOT NULL,
	lbl_id		${NUMBER19} NOT NULL
);

${PROD_LBL_PK}

CREATE TABLE pref_lbl_t  (
	emplr_prefcs_id	${NUMBER19} NOT NULL,
	pref_type		VARCHAR(6) NOT NULL,
	lbl_id			${NUMBER19} NOT NULL
);
   
${PREF_LBL_PK}