/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Jan 10, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author n0296170
 *
 */
@Data
@EqualsAndHashCode(callSuper = false, exclude = "applicantCondition")
@ToString(exclude = "applicantCondition")
@Relation(collectionRelation = "applicantMedications", value = "applicantMedication")
public class ApplicationSubmissionApplicantMedicationResource extends ResourceSupport {

	private Integer applicantMedicationId;

	@NotNull
	@Pattern(regexp = "(^[A-Z0-9]{4}$)")
	private String medicationCode;

	@Pattern(regexp = "(^[A-Za-z0-9 '-,]{0,255}$)")
	private String otherMedicationText;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date PrescriptionDate;

	@JsonBackReference
	private ApplicationSubmissionApplicantConditionResource applicantCondition;

	private Integer concurrencyQuantity;
}
