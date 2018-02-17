/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWEMP_DB;

INSERT INTO emplr_emp_t ( 
    emplr_id,
    emp_ssn_num, 
   emp_idn_num,
    cmunn_mthd_cde,
  
    ccrcy_qty,
    crt_dtm,
    crt_usr_id_num )
    VALUES
    (
        1,
        '123456789',
       'N0296170',
        'EML',
        
        3,
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
    
    INSERT INTO emplr_emp_t ( 
    emplr_id,
    emp_ssn_num, 
   emp_idn_num,
    cmunn_mthd_cde,
   
    ccrcy_qty,
    crt_dtm,
    crt_usr_id_num )
    VALUES
    (
        2,
        '123986789',
       'N0296171',
        'FAX',
        
        3,
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
    
  
