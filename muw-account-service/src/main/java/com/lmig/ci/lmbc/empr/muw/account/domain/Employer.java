/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 12, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author n0159479
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = "preference")
@Table(name = "muw_emplr_t")
public class Employer extends AbstractAuditableTableEntity implements Serializable {
	private static final long serialVersionUID = -8732330204929109692L;

	@Id
	@Column(name = "emplr_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "muw_emplr_seq")
	@SequenceGenerator(name = "muw_emplr_seq", sequenceName = "MUW_EMPLR_SEQ", allocationSize = 1)
	private Integer employerId;

	@Embedded
	@Valid
	@NotNull
	StakeholderLedgerSerialNumberPk stakeholderLedgerSerialNumberPk;

	@NotNull
	@Size(max = 132)
	@Pattern(regexp = "^([A-Za-z0-9@\'.,#&!-][ ]?)*([A-Za-z0-9\'&@.,!-])+$", message = "{NotNull.employerName}")
	@Column(name = "emplr_nme")
	private String employerName;

	@Pattern(regexp = "(^$)|(^[A-Za-z0-9@\'.,#&!-]{1,20}$)")
	@Column(name = "co_accs_cde")
	private String companyAccessCode;

	@NotNull
	@Pattern(regexp = "^[A-Za-z]{2}$")
	@Column(name = "situs_stt_cde")
	private String situsStateCode;

	@NotNull
	@Column(name = "beg_dte")
	private Date beginDate;

	@Column(name = "cancn_dte")
	private Date cancellationDate;

	@NotNull
	@Column(name = "ANNV_DTE")
	private Date annvDate;

	@Size(max = 132)
	@Pattern(regexp = "(^$)|(^([A-Za-z0-9@\'.,#&!-][ ]?)*([A-Za-z0-9\'&@.,!-])+$)")
	@Column(name = "altnt_nme")
	private String alternateName;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "employer")
	@JsonManagedReference
	private List<EoiProduct> products = new ArrayList<EoiProduct>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "emplr_prefcs_id", unique = true)
	private Preference preference;

}
