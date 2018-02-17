/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 24, 2016
 */

package com.lmig.ci.lmbc.empr.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Class ErrorMessage
 * @author n0169128
 * <P>
 * @Description:
 * <p>
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
public ErrorMessage(){
		
	}
    
   private ErrorMessageType type;
   private String entity;
   private String property;
   private String message;
   private String invalidValue;
   
}
