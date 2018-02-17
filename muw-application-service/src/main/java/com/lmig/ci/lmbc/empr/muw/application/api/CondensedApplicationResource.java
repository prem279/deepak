/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Jan 10, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.api;

import java.sql.Date;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @author n0296170
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class CondensedApplicationResource extends ResourceSupport {
	
    private Integer applicationId;
	
	private String employeeFirstName;
	
	private String employeeLastName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	private Date latestStatusDate;
	
    private Integer employerEmployeeId;

	private Integer concurrencyQuantity;
}