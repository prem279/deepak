/* 
 * Copyright (C) 2015, Liberty Mutual Group 
 * 
 * Created on April 04, 2017 
 */
package com.lmig.ci.lmbc.empr.muw.application.api;


import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author n0296170
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ApplicationEventResource extends ResourceSupport {

    private Integer eventId;
	
	private int subjectAreaId;
	
	private String subjectAreaCode;
    
    private String changeEventCode;
    
	private String businessEventCode;
	
	private String beforeEventData;
	
	private String changeSet;
	
	private String afterEventData;
	
	private String lockOwnerName;
	
	private Timestamp lockTime;
	
	private String processedIndicator;
	
	private Timestamp processedTime;

}
