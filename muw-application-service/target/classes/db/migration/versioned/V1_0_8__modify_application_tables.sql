/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;

ALTER TABLE muw_appln_applnt_prod_t
DROP COLUMN appln_id;

ALTER TABLE muw_appln_t
DROP COLUMN  emp_ssn_num;

ALTER TABLE muw_applnt_mdct_t
DROP COLUMN appln_applnt_id;

-- This isn't getting created in H2, is it needed? if so, need to ensure Oracle gets it and H2 does not
--ALTER TABLE muw_appln_applnt_prod_t
--DROP CONSTRAINT fk_appln_applnt_prod_to_appln;