/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Jan 10, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lmig.ci.lmbc.empr.muw.application.service.Eventable;

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
public class ApplicationSubmissionResource extends ResourceSupport implements Eventable {

	@NotNull
	@Valid
	private ApplicationSubmissionApplicationResource application;

	@NotNull
	@Valid
	private ApplicationSubmissionEmployeeResource employee;

	private Integer concurrencyQuantity;

	@JsonIgnore
	@Override
	public Integer getSubjectAreaId() {
		if (application != null) {
			return application.getApplicationId();
		}

		return null;
	}
}
