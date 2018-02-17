/* SET SCHEMA FOR SESSION */
${SETSCHEMA} MUWAPPLN_DB;


INSERT INTO muw_appln_t ( 
    emplr_id,
    emplr_emp_id,
    emp_ssn_num, 
    appln_recvd_dtm,
    fam_stts_chng_evnt_dte,
    reas_cde,
    sbmn_mthd_cde,
    ccrcy_qty,
    crt_dtm,
    crt_usr_id_num )
    VALUES
    (
        1,
        1,
        '123456789',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'asdf',
        'ONLN',
        3,
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
   
    
    INSERT INTO muw_appln_prod_t ( 
    
        appln_id,
        prod_cde,
        ccrcy_qty,
        crt_dtm,
        crt_usr_id_num,
        last_updt_dtm,
        last_updt_usr_id_num   )
        
         VALUES
    (
        1,
        'BSL',
        5,
        CURRENT_TIMESTAMP,
        'N0296170',
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
    
    INSERT INTO muw_appln_prod_t ( 
    
        appln_id,
        prod_cde,
        ccrcy_qty,
        crt_dtm,
        crt_usr_id_num,
        last_updt_dtm,
        last_updt_usr_id_num   )
        
         VALUES
    (
        1,
        'STD',
        5,
        CURRENT_TIMESTAMP,
        'N0296170',
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
    
    INSERT INTO muw_appln_prod_t ( 
    
        appln_id,
        prod_cde,
        ccrcy_qty,
        crt_dtm,
        crt_usr_id_num,
        last_updt_dtm,
        last_updt_usr_id_num   )
        
         VALUES
    (
        1,
        'LTD',
        5,
        CURRENT_TIMESTAMP,
        'N0296170',
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
    
    
    INSERT INTO muw_appln_t ( 
    emplr_id,
    emplr_emp_id,
    emp_ssn_num, 
    appln_recvd_dtm,
    fam_stts_chng_evnt_dte,
    reas_cde,
    sbmn_mthd_cde,
    ccrcy_qty,
    crt_dtm,
    crt_usr_id_num 
    )
    
    VALUES
    (
        2,
        2,
        '234567891',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'abcd',
        'ONLN',
        0,
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
    
    INSERT INTO muw_appln_prod_t ( 
    
        appln_id,
        prod_cde,
        ccrcy_qty,
        crt_dtm,
        crt_usr_id_num,
        last_updt_dtm,
        last_updt_usr_id_num   )
        
         VALUES
    (
        2,
        'BFL',
        5,
        CURRENT_TIMESTAMP,
        'N0296170',
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
    
    INSERT INTO muw_appln_prod_t ( 
    
        appln_id,
        prod_cde,
        ccrcy_qty,
        crt_dtm,
        crt_usr_id_num,
        last_updt_dtm,
        last_updt_usr_id_num   )
        
         VALUES
    (
        2,
        'LTD',
        5,
        CURRENT_TIMESTAMP,
        'N0296170',
        CURRENT_TIMESTAMP,
        'JUNIT',
    );
   
    
	INSERT INTO muw_appln_applnt_prod_t  ( 
	
	        appln_applnt_id,
	        appln_prod_id,
	        appln_id,
			curr_prod_amt_typ_cde,
	        curr_prod_amt,
	        curr_prod_pct,
	        curr_prod_qty,
	        reqd_prod_amt_typ_cde,
	        reqd_prod_amt,
	        reqd_prod_pct,
	        reqd_prod_qty,
	        reqd_prod_bgn_dte,
	        stts_dtrmn_dte,
	        stts_typ_cde,
	        prod_appd_eff_dte,
	        ccrcy_qty,
	        crt_dtm,
	        crt_usr_id_num,
	        last_updt_dtm,
	        last_updt_usr_id_num
	        
	)
	VALUES (
	
			1,
			1,
			1,
			'DOL',
			10000.0,
			80.0,
			666.0,
			'DOL',
			12500.0,
			100.0,
			666.0,
			CURRENT_TIMESTAMP,
			CURRENT_TIMESTAMP,
			'MANREV',
			CURRENT_TIMESTAMP,
			5,
			CURRENT_TIMESTAMP,
			'JUNIT',
			CURRENT_TIMESTAMP,
			'JUNIT'
			
	);
	
	INSERT INTO muw_appln_applnt_prod_t  ( 
	
	        appln_applnt_id,
	        appln_prod_id,
	        appln_id,
			curr_prod_amt_typ_cde,
	        curr_prod_amt,
	        curr_prod_pct,
	        curr_prod_qty,
	        reqd_prod_amt_typ_cde,
	        reqd_prod_amt,
	        reqd_prod_pct,
	        reqd_prod_qty,
	        reqd_prod_bgn_dte,
	        stts_dtrmn_dte,
	        stts_typ_cde,
	        prod_appd_eff_dte,
	        ccrcy_qty,
	        crt_dtm,
	        crt_usr_id_num,
	        last_updt_dtm,
	        last_updt_usr_id_num
	        
	)
	VALUES (
	
			1,
			2,
			1,
			'SAL',
			15000.0,
			75.0,
			666.0,
			'PER',
			20000.0,
			100.0,
			666.0,
			CURRENT_TIMESTAMP,
			CURRENT_TIMESTAMP,
			'ATOAPP',
			CURRENT_TIMESTAMP,
			5,
			CURRENT_TIMESTAMP,
			'JUNIT',
			CURRENT_TIMESTAMP,
			'JUNIT'
			
	);
	
	INSERT INTO muw_appln_applnt_prod_t  ( 
	
	        appln_applnt_id,
	        appln_prod_id,
	        appln_id,
			curr_prod_amt_typ_cde,
	        curr_prod_amt,
	        curr_prod_pct,
	        curr_prod_qty,
	        reqd_prod_amt_typ_cde,
	        reqd_prod_amt,
	        reqd_prod_pct,
	        reqd_prod_qty,
	        reqd_prod_bgn_dte,
	        stts_dtrmn_dte,
	        stts_typ_cde,
	        prod_appd_eff_dte,
	        ccrcy_qty,
	        crt_dtm,
	        crt_usr_id_num,
	        last_updt_dtm,
	        last_updt_usr_id_num
	        
	)
	VALUES (
	
			1,
			1,
			2,
			'DOL',
			10000.0,
			80.0,
			666.0,
			'SAL',
			12500.0,
			100.0,
			666.0,
			CURRENT_TIMESTAMP,
			CURRENT_TIMESTAMP,
			'ATOAPP',
			CURRENT_TIMESTAMP,
			5,
			CURRENT_TIMESTAMP,
			'JUNIT',
			CURRENT_TIMESTAMP,
			'JUNIT'
			
	);
	
	INSERT INTO muw_appln_applnt_prod_t  ( 
	
	        appln_applnt_id,
	        appln_prod_id,
	        appln_id,
			curr_prod_amt_typ_cde,
	        curr_prod_amt,
	        curr_prod_pct,
	        curr_prod_qty,
	        reqd_prod_amt_typ_cde,
	        reqd_prod_amt,
	        reqd_prod_pct,
	        reqd_prod_qty,
	        reqd_prod_bgn_dte,
	        stts_dtrmn_dte,
	        stts_typ_cde,
	        prod_appd_eff_dte,
	        ccrcy_qty,
	        crt_dtm,
	        crt_usr_id_num,
	        last_updt_dtm,
	        last_updt_usr_id_num
	        
	)
	VALUES (
	
			1,
			2,
			2,
			'DOL',
			15000.0,
			75.0,
			666.0,
			'PER',
			20000.0,
			100.0,
			666.0,
			CURRENT_TIMESTAMP,
			CURRENT_TIMESTAMP,
			'DENIED',
			CURRENT_TIMESTAMP,
			5,
			CURRENT_TIMESTAMP,
			'JUNIT',
			CURRENT_TIMESTAMP,
			'JUNIT'
			
	);
	
	INSERT INTO muw_appln_applnt_prod_t  ( 
	
	        appln_applnt_id,
	        appln_prod_id,
	        appln_id,
			curr_prod_amt_typ_cde,
	        curr_prod_amt,
	        curr_prod_pct,
	        curr_prod_qty,
	        reqd_prod_amt_typ_cde,
	        reqd_prod_amt,
	        reqd_prod_pct,
	        reqd_prod_qty,
	        reqd_prod_bgn_dte,
	        stts_dtrmn_dte,
	        stts_typ_cde,
	        prod_appd_eff_dte,
	        ccrcy_qty,
	        crt_dtm,
	        crt_usr_id_num,
	        last_updt_dtm,
	        last_updt_usr_id_num
	        
	)
	VALUES (
	
			3,
			1,
			1,
			'DOL',
			10000.0,
			80.0,
			666.0,
			'DOL',
			12500.0,
			100.0,
			666.0,
			CURRENT_TIMESTAMP,
			CURRENT_TIMESTAMP,
			'DENIED',
			CURRENT_TIMESTAMP,
			5,
			CURRENT_TIMESTAMP,
			'JUNIT',
			CURRENT_TIMESTAMP,
			'JUNIT'
			
	);
	
	INSERT INTO muw_appln_applnt_prod_t  ( 
	
	        appln_applnt_id,
	        appln_prod_id,
	        appln_id,
			curr_prod_amt_typ_cde,
	        curr_prod_amt,
	        curr_prod_pct,
	        curr_prod_qty,
	        reqd_prod_amt_typ_cde,
	        reqd_prod_amt,
	        reqd_prod_pct,
	        reqd_prod_qty,
	        reqd_prod_bgn_dte,
	        stts_dtrmn_dte,
	        stts_typ_cde,
	        prod_appd_eff_dte,
	        ccrcy_qty,
	        crt_dtm,
	        crt_usr_id_num,
	        last_updt_dtm,
	        last_updt_usr_id_num
	        
	)
	VALUES (
	
			3,
			2,
			1,
			'DOL',
			15000.0,
			75.0,
			666.0,
			'DOL',
			20000.0,
			100.0,
			666.0,
			CURRENT_TIMESTAMP,
			CURRENT_TIMESTAMP,
			'ATOAPP',
			CURRENT_TIMESTAMP,
			5,
			CURRENT_TIMESTAMP,
			'JUNIT',
			CURRENT_TIMESTAMP,
			'JUNIT'
			
	);
    
    