/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWACCT_DB;

alter table emplr_eoi_prod_t drop column prod_lbl_nme;

update emplr_prefcs_t set emp_lbl = 'EMP', emp_id_lbl = 'EMPID', spus_lbl = 'SPUS';