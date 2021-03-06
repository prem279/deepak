/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWACCT_DB;
/* Adding disability pregnancy approvals allowed indicator */
${H2IGNORELINE}DECLARE COL_COUNT INTEGER;

${H2IGNORELINE}BEGIN
	
	${H2IGNORELINE}SELECT COUNT(*) INTO COL_COUNT FROM USER_TAB_COLUMNS
	${H2IGNORELINE}WHERE TABLE_NAME = 'EMPLR_PREFCS_T' 
	${H2IGNORELINE}AND COLUMN_NAME = 'DSAB_PREG_APPR_I';

	${H2IGNORELINE}IF(COL_COUNT = 1)
	${H2IGNORELINE}THEN
	${H2IGNORELINE}	EXECUTE IMMEDIATE 'ALTER TABLE EMPLR_PREFCS_T DROP COLUMN DSAB_PREG_APPR_I';
	${H2IGNORELINE}END IF;
	
	${H2IGNORELINE}EXECUTE IMMEDIATE 'ALTER TABLE EMPLR_PREFCS_T ADD DSAB_PREG_APPR_I CHAR(1) DEFAULT ''0'' NULL';

${H2IGNORELINE}END;

${ORACLEIGNORELINE}ALTER TABLE MUWACCT_DB.EMPLR_PREFCS_T DROP COLUMN IF EXISTS DSAB_PREG_APPR_I;
${ORACLEIGNORELINE}ALTER TABLE EMPLR_PREFCS_T ADD DSAB_PREG_APPR_I CHAR(1) DEFAULT '0' NULL;
