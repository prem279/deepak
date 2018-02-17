package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.util.CustomDateOnlyDeserializer;
import com.lmig.ci.lmbc.empr.common.util.CustomDateOnlySerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "applications", value = "application")
public class ApplicationSubmissionApplicationResource extends ResourceSupport {

	@Min(1)
	@Max(Integer.MAX_VALUE)
	private Integer applicationId;

	@Min(1)
	@Max(Integer.MAX_VALUE)
	private Integer employerId;

	@Pattern(regexp = "^\\d{2}$")
	private String div;

	@Pattern(regexp = "^[A-z0-9]{6}$")
	private String serial;

	@JsonSerialize(using = CustomDateOnlySerializer.class)
	@JsonDeserialize(using = CustomDateOnlyDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date applicationReceivedDate;

	@JsonSerialize(using = CustomDateOnlySerializer.class)
	@JsonDeserialize(using = CustomDateOnlyDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date familySttsChangeEventDate;

	@NotNull
	@Pattern(regexp = "^[A-Za-z]{4}$")
	private String reasonCode;

	@NotNull
	@Pattern(regexp = "^[A-Za-z]{4}$")
	private String submissionMethodCode;

	@Valid
	@JsonManagedReference
	private List<ApplicationSubmissionApplicantResource> applicants = new ArrayList<ApplicationSubmissionApplicantResource>();

	@Valid
	@JsonManagedReference
	private List<ApplicationSubmissionMedicalFactorResource> applicationMedicalFactors = new ArrayList<ApplicationSubmissionMedicalFactorResource>();

	private Integer concurrencyQuantity;
}
