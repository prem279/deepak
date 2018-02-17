/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Jan 5, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="muw_applnt_mdct_t")
@EqualsAndHashCode(callSuper = false, exclude = "applicantCondition")
@ToString(exclude = "applicantCondition")
public class ApplicantMedication extends AbstractAuditableTableEntity implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 253820622409858983L;


	@Id
	@Column(name="applnt_mdct_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="muw_applnt_mdct_seq")
	@SequenceGenerator(name="muw_applnt_mdct_seq", sequenceName="MUW_APPLNT_MDCT_SEQ", allocationSize = 1)
	private Integer applicantMedicationId;
	
	
	@NotNull
	@Pattern(regexp = "(^[A-Z0-9]{4}$)")
	@Column(name="mdct_cde")
	private String medicationCode;
	
	@Pattern(regexp = "(^[A-Za-z0-9 '-,]{0,255}$)")
	@Column(name="othr_mdct_txt")
	private String otherMedicationText;
	
	@Column(name="prsc_dte")
	private Date PrescriptionDate;
	
	@ManyToOne
	@JoinColumn(name="applnt_cond_id")
	@JsonBackReference
	private ApplicantCondition applicantCondition;
}
