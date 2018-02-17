/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Mar 8, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

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
@Relation(collectionRelation = "applicationSubmissions", value = "applicationSubmission")
public class ApplicationSearchResourceResponse extends ResourceSupport {

	private List<ApplicationSearchApplicationResource> applications;

	private ApplicationSearchEmployeeResource employee;
	
}
	