/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Jan 5, 2017
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author n0296170
 *
 */

@Entity
@Data
@Table(name="muw_applnt_cond_t")
@EqualsAndHashCode(callSuper = false, exclude = "applicant")
@ToString(exclude = "applicant")
public class ApplicantCondition extends AbstractAuditableTableEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -3483874050001616339L;

	@Id
	@Column(name="applnt_cond_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="muw_applnt_cond_seq")
	@SequenceGenerator(name="muw_applnt_cond_seq", sequenceName="MUW_APPLNT_COND_SEQ", allocationSize = 1)
	private Integer applicantConditionId;
	
	@NotNull
	@Pattern(regexp = "(^[A-Z0-9]{4}$)")
	@Column(name="cond_cde")
	private String conditionCode;
	
	@Pattern(regexp = "(^[ -~]{0,255}$)")
	@Column(name="othr_cond_txt")
	private String otherConditionText;
	
	@Column(name="cond_rcov_dte")
	private Date conditionRecoveryDate;
	
	@Column(name="cond_onst_dte")
	private Date conditionOnsetDate;
	
	@NotNull
	@Pattern(regexp = "(^[A-Za-z0-9 '\\-,\\.]{1,255}$)")
	@Column(name="trtm_hlth_prof_fullnme")
	private String treatmentHealthFullName;	
	
	@Pattern(regexp = "(^$)|(^[ -~]{1,1000}$)")
	@Column(name="trtm_recvd_txt")
	private String treatmentReceivedText;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY, mappedBy = "applicantCondition")
	@JsonManagedReference
	private List<ApplicantMedication> medications = new ArrayList<ApplicantMedication>();
		
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY, mappedBy = "applicantCondition")
	@JsonManagedReference
	private List<QuestionnaireResponse> questionnaireResponses = new ArrayList<QuestionnaireResponse>();
	
	
	@ManyToOne
	@JoinColumn(name="appln_applnt_id")
	@JsonBackReference
	private Applicant applicant;
}
