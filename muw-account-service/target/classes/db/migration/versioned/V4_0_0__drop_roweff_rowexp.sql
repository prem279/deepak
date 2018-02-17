/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWACCT_DB;

ALTER TABLE MUWACCT_DB.EMPLR_EOI_PROD_T DROP COLUMN ROW_EFF_DTM; 
ALTER TABLE MUWACCT_DB.EMPLR_EOI_PROD_T DROP COLUMN ROW_EXP_DTM;

ALTER TABLE MUWACCT_DB.EMPLR_PREFCS_T DROP COLUMN ROW_EFF_DTM;
ALTER TABLE MUWACCT_DB.EMPLR_PREFCS_T DROP COLUMN ROW_EXP_DTM; 

ALTER TABLE MUWACCT_DB.MUW_EMPLR_EVNT_T DROP COLUMN ROW_EFF_DTM;  
ALTER TABLE MUWACCT_DB.MUW_EMPLR_EVNT_T DROP COLUMN ROW_EXP_DTM; 

ALTER TABLE MUWACCT_DB.MUW_EMPLR_T DROP COLUMN ROW_EFF_DTM;
ALTER TABLE MUWACCT_DB.MUW_EMPLR_T DROP COLUMN ROW_EXP_DTM;