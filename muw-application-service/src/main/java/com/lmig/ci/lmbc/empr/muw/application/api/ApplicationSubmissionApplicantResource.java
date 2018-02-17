package com.lmig.ci.lmbc.empr.muw.application.api;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "application")
@ToString(exclude = "application")
@Relation(collectionRelation = "applicants", value = "applicant")
public class ApplicationSubmissionApplicantResource extends ResourceSupport {

	private Integer applicationApplicantId;

	@Pattern(regexp = "^[A-Za-z]{5}$")
	@Column(name = "appln_role_cde")
	private String applicationRoleCode;

	@Pattern(regexp = "(^$)|(^\\d{9}$)")
	@Column(name = "applnt_ssn")
	private String applicantSSN;

	@Column(name = "brth_dte")
	private Date birthDate;

	@Pattern(regexp = "^[A-Za-z '-]{0,35}$")
	@Column(name = "frst_nme")
	private String firstName;

	@Pattern(regexp = "^[A-Za-z]{0,1}$")
	@Column(name = "midin_nme")
	private String middleInitial;

	@Pattern(regexp = "^[A-Za-z '-]{0,70}$")
	@Column(name = "last_nme")
	private String lastName;

	@Pattern(regexp = "^[A-Za-z '-]{0,70}$")
	@Column(name = "prev_last_nme")
	private String priorLastName;

	@Pattern(regexp = "^[A-Za-z '-.]{0,70}$")
	@Column(name = "brth_city_nme")
	private String birthCityName;

	@Pattern(regexp = "^[A-Za-z]{0,2}$")
	@Column(name = "brth_stpr_cde")
	private String birthStateProvinceCode;

	@Pattern(regexp = "^[A-Za-z]{0,3}$")
	@Column(name = "brth_ctry_cde")
	private String birthCountryCode;

	@Pattern(regexp = "^[mMfF]{0,1}$")
	@Column(name = "gndr_cde")
	private String genderCode;

	@Min(0)
	@Max(999)
	@Column(name = "hght_inch_qty")
	private Integer heightInchQuantity;

	@Min(0)
	@Max(999)
	@Column(name = "wgt_qty")
	private Integer weightQuantity;

	@JsonBackReference
	private ApplicationSubmissionApplicationResource application;

	@JsonManagedReference
	@Valid
	private List<ApplicationSubmissionApplicantConditionResource> conditions = new ArrayList<ApplicationSubmissionApplicantConditionResource>();

	@JsonManagedReference
	@Valid
	private List<ApplicationSubmissionApplicantProductResource> applicantProducts = new ArrayList<ApplicationSubmissionApplicantProductResource>();

	private Integer concurrencyQuantity;
}
