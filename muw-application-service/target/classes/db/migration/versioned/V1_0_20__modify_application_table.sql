${SETSCHEMA} MUWAPPLN_DB;

ALTER TABLE MUW_APPLN_T ADD (file_feed_i CHAR(1) NULL);

UPDATE MUW_APPLN_T SET file_feed_i = 0;

/* Now alter the column to be not null */

${iforacle}ALTER TABLE MUW_APPLN_T MODIFY (file_feed_i NOT NULL);
${ifh2}ALTER TABLE MUW_APPLN_T ALTER COLUMN file_feed_i SET NOT NULL;
