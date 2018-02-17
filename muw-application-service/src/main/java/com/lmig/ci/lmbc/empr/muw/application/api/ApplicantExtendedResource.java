package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.util.CustomDateDeserializer;
import com.lmig.ci.lmbc.empr.common.util.CustomDateSerializer;
import com.lmig.ci.lmbc.empr.common.util.JsonToHtmlDateSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "application")
@ToString(exclude = "application")
@Relation(collectionRelation = "applicants", value = "applicant")
public class ApplicantExtendedResource extends ResourceSupport {

	private Integer applicationApplicantId;
	
	@JsonBackReference
	private ApplicationResource application;
	
	private Integer applicationId;
	
	//TODO pattern
	@Column(name="appln_role_cde")
	private String applicationRoleCode;
	
	//TODO pattern
	@Column(name="applnt_ssn")
	private String applicantSSN;
	
	@JsonSerialize(using=JsonToHtmlDateSerializer.class)
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	@Column(name="brth_dte")
	private Date birthDate;
	
	//TODO pattern
	@Column(name="frst_nme")
	private String firstName;
	
	//TODO pattern
	@Column(name="midin_nme")
	private String middleInitial;
	
	//TODO pattern
	@Column(name="last_nme")
	private String lastName;
	
	//TODO pattern
	@Column(name="pr_last_nme")
	private String priorLastName;
	
	//TODO pattern
	@Column(name="brth_city_nme")
	private String birthCityName;
	
	//TODO pattern
	@Column(name="brth_stpr_cde")
	private String birthStateProvinceCode;

	//TODO pattern
	@Column(name="brth_ctry_cde")
	private String birthCountryCode;
	
	//TODO pattern
	@Column(name="gndr_cde")
	private String genderCode;
	
	//TODO pattern
	@Column(name="hght_inch_qty")
	private Integer heightInchQuantity;
	
	//TODO pattern
	@Column(name="wgt_qty")
	//TODO should this name indicate units? e.g. pounds or kilograms, like the "heightInchQuantity" field above
	private Integer weightQuantity;
	
	@JsonManagedReference
	private List<ApplicantConditionResource> conditions;
	
	@JsonManagedReference
	private List<ApplicantProductExtendedResource> applicantProducts;
	
	private String communicationMethodCode;

	private Integer concurrencyQuantity;
}
