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
public class ApplicationResource extends ResourceSupport {

	private Integer applicationId;

	@Min(1)
	@Max(999999999)
	@NotNull
	private Integer employerId;

	@Min(1)
	@Max(999999999)
	@NotNull
	private Integer employerEmployeeId;

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

	private Integer concurrencyQuantity;

	@Valid
	@JsonManagedReference
	private List<ApplicationProductResource> applicationProducts = new ArrayList<ApplicationProductResource>();

	@Valid
	@JsonManagedReference
	private List<ApplicantResource> applicants = new ArrayList<ApplicantResource>();

	@Valid
	@JsonManagedReference
	private List<ApplicationMedicalFactorResource> applicationMedicalFactors = new ArrayList<ApplicationMedicalFactorResource>();
}
