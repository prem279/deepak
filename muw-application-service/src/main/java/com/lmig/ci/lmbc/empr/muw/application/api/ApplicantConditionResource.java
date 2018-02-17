/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Jan 10, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author n0296170
 *
 */
@Data
@EqualsAndHashCode(callSuper = false, exclude = "applicant")
@ToString(exclude = "applicant")
@Relation(collectionRelation = "applicantConditions", value = "applicantCondition")
public class ApplicantConditionResource extends ResourceSupport {

	private Integer applicantConditionId;

	@NotNull
	@Pattern(regexp = "(^[A-Z0-9]{4}$)")
	private String conditionCode;

	@Pattern(regexp = "(^[ -~]{0,255}$)")
	private String otherConditionText;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date conditionRecoveryDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date conditionOnsetDate;

	@NotNull
	@Pattern(regexp = "(^[A-Za-z0-9 '\\-,\\.]{0,255}$)")
	private String treatmentHealthFullName;

	@Pattern(regexp = "(^[ -~]{1,1000}$)")
	private String treatmentReceivedText;

	private Integer concurrencyQuantity;

	@JsonBackReference
	private ApplicantResource applicant;

	@Valid
	@JsonManagedReference
	private List<ApplicantMedicationResource> medications = new ArrayList<ApplicantMedicationResource>();

	@Valid
	@JsonManagedReference
	private List<QuestionnaireResponseResource> questions = new ArrayList<QuestionnaireResponseResource>();

}