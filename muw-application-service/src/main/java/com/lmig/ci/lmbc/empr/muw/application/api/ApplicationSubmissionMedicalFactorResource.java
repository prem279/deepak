/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 29, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author n0159479
 *
 */
@Data
@EqualsAndHashCode(callSuper = false, exclude = "application")
@ToString(exclude = "application")
@Relation(collectionRelation = "applicationMedicalFactors", value = "applicationMedicalFactor")
public class ApplicationSubmissionMedicalFactorResource extends ResourceSupport {

	private Integer applicationMedicalFactorId;

	@Pattern(regexp = "^[A-Za-z]{5}$")
	private String applicationRoleCode;

	@Pattern(regexp = "(^[A-Za-z]{4}$)|(^[A-Za-z ]{6}$)")
	private String medicalFactorQuestionCode;

	@Pattern(regexp = "^[A-Za-z]{6}$")
	private String alternativeMedicalFactorQuestionCode;

	@NotNull
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean medicalFactorIndicator;

	@Pattern(regexp = "(^[ -~]{1,1000}$)")
	private String medicalFactorText;

	@JsonBackReference
	private ApplicationSubmissionApplicationResource application;

	private Integer concurrencyQuantity;
}
