/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 29, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="muw_appln_medl_qualg_fc_t")
@EqualsAndHashCode(callSuper=false, exclude = "application")
@ToString(exclude="application")
public class ApplicationMedicalFactor  extends AbstractAuditableTableEntity implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 6528580361150267655L;
	
	@Id
	@Column(name="appln_medl_qualg_fc_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="muw_appln_medl_qualg_seq")
	@SequenceGenerator(name="muw_appln_medl_qualg_seq", sequenceName="muw_appln_medl_qualg_seq", allocationSize = 1)
	private Integer applicationMedicalFactorId;
	
	@Pattern(regexp = "^[A-Za-z]{5}$")
	@Column(name="appln_role_cde")
	private String applicationRoleCode;
	
	@Pattern(regexp = "(^[A-Za-z]{4}$)|(^[A-Za-z ]{6}$)")
	@Column(name="medl_fctr_ques_cde")
	private String medicalFactorQuestionCode;
	
	@Pattern(regexp = "^[A-Za-z]{6}$")
	@Column(name="altnt_medl_fctr_ques_cde")
	private String alternativeMedicalFactorQuestionCode;
	
	@NotNull
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column(name="medl_fctr_i")
	private Boolean medicalFactorIndicator;
	
	@Pattern(regexp = "(^[ -~]{1,1000}$)")
	@Column(name="medl_fctr_txt")
	private String medicalFactorText;
	
	@ManyToOne
	@JoinColumn(name="appln_id")
	@JsonBackReference
	private Application application;
	
}
