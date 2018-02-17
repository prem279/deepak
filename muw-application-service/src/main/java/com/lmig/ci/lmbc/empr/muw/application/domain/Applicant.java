/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 29, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author n0159479
 *
 */
@Entity
@Data
@Table(name="muw_appln_applnt_t")
@EqualsAndHashCode(callSuper=true, exclude = "application")
@ToString(exclude="application")
public class Applicant  extends AbstractAuditableTableEntity implements Serializable {/**
 * 
 */
	private static final long serialVersionUID = 6528580361150267655L;

	@Id
	@Column(name="appln_applnt_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="muw_appln_applnt_seq")
	@SequenceGenerator(name="muw_appln_applnt_seq", sequenceName="MUW_appln_applnt_SEQ", allocationSize = 1)
	private Integer applicationApplicantId;

	@Pattern(regexp = "^[A-Za-z]{5}$")
	@Column(name="appln_role_cde")
	private String applicationRoleCode;

	@Pattern(regexp = "(^$)|(^\\d{9}$)")
	@Column(name="applnt_ssn")
	private String applicantSSN;

	//@CreatedDate
	//@JsonSerialize(using=JsonToHtmlDateSerializer.class)
	//@JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	//@JsonDeserialize(using=JsonToHtmlDateDeserializer.class)	
	@Column(name="brth_dte")
	private Date birthDate;

	@Pattern(regexp="^[A-Za-z '-]{0,35}$")
	@Column(name="frst_nme")
	private String firstName;

	@Pattern(regexp="^[A-Za-z]{0,1}$")
	@Column(name="midin_nme")
	private String middleInitial;

	@Pattern(regexp="^[A-Za-z '-]{0,70}$")
	@Column(name="last_nme")
	private String lastName;

	@Pattern(regexp="^[A-Za-z '-]{0,70}$")
	@Column(name="prev_last_nme")
	private String priorLastName;

	@Pattern(regexp="^[A-Za-z '-.]{0,70}$")
	@Column(name="brth_city_nme")
	private String birthCityName;

	@Pattern(regexp="^[A-Za-z]{0,2}$")
	@Column(name="brth_stpr_cde")
	private String birthStateProvinceCode;

	@Pattern(regexp="^[A-Za-z]{0,3}$")
	@Column(name="brth_ctry_cde")
	private String birthCountryCode;

	@Pattern(regexp="^[mMfF]{0,1}$")
	@Column(name="gndr_cde")
	private String genderCode;

	@Min(0)
	@Max(999)
	@Column(name="hght_inch_qty")
	private Integer heightInchQuantity;

	@Min(0)
	@Max(999)
	@Column(name="wgt_qty")
	private Integer weightQuantity;

	@ManyToOne
	@JoinColumn(name="appln_id")
	@JsonBackReference
	private Application application;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY, mappedBy = "applicant")
	@JsonManagedReference
	private List<ApplicantCondition> conditions = new ArrayList<ApplicantCondition>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY, mappedBy = "applicant")
	@JsonManagedReference
	private List<ApplicantProduct> applicantProducts = new ArrayList<ApplicantProduct>();


}
