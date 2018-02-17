/*
  * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 23, 2016
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
@Table(name = "muw_appln_applnt_prod_t")
@EqualsAndHashCode(callSuper = false, exclude = "applicant")
@ToString(exclude = "applicant")
public class ApplicantProduct extends AbstractAuditableTableEntity implements Serializable {

	/**
	 * @param applicationProduct
	 */
	public ApplicantProduct(Applicant applicant, ApplicationProduct applicationProduct) {
		this.setApplicant(applicant);
		this.setApplicationProductId(applicationProduct.getApplicationProductId());
		this.setStatusDeterminationDate(new Date(new java.util.Date().getTime()));
		this.setStatusTypeCode("MANREV");
	}

	public ApplicantProduct() {
	}

	private static final long serialVersionUID = 5669982079246156839L;

	@Id
	@Column(name = "appln_applnt_prod_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUW_APPLN_APPLNT_PROD_SEQ")
	@SequenceGenerator(name = "MUW_APPLN_APPLNT_PROD_SEQ", sequenceName = "MUW_APPLN_APPLNT_PROD_SEQ", allocationSize = 1)
	private Integer applicationApplicantProductId;

	@Pattern(regexp = "^[A-Za-z]{3}$")
	@Column(name = "curr_prod_amt_typ_cde")
	private String currentProductAmountTypeCode;

	@Min(1)
	@Max(1000000)
	@Column(name = "curr_prod_amt")
	private Double currentProductAmount;

	@Min(1)
	@Max(100)
	@Column(name = "curr_prod_pct")
	private Double currentProductPercent;

	@Min(1)
	@Max(1000000)
	@Column(name = "curr_prod_qty")
	private Double currentProductQuantity;

	@Pattern(regexp = "^[A-Za-z]{3}$")
	@Column(name = "reqd_prod_amt_typ_cde")
	private String requestedProductAmountTypeCode;

	@Min(1)
	@Max(1000000)
	@Column(name = "reqd_prod_amt")
	private Double requestedProductAmount;

	@Min(1)
	@Max(100)
	@Column(name = "reqd_prod_pct")
	private Double requestedProductPercent;

	@Min(1)
	@Max(1000000)
	@Column(name = "reqd_prod_qty")
	private Double requestedProductQuantity;

	@Column(name = "reqd_prod_bgn_dte")
	private Date requestedProductBeginDate;

	@NotNull
	@Column(name = "stts_dtrmn_dte")
	private Date statusDeterminationDate;

	@NotNull
	@Pattern(regexp = "^[A-Za-z]{6}$")
	@Column(name = "stts_typ_cde")
	private String statusTypeCode;

	@Column(name = "prod_appd_eff_dte")
	private Date productApproveEffectiveDate;

	@Column(name = "cov_elig_dte")
	private Date coverageEligibilityDate;

	@ManyToOne
	@JoinColumn(name = "appln_applnt_id")
	@JsonBackReference
	private Applicant applicant;

	@NotNull
	@Min(1)
	@Max(999999999)
	@Column(name = "appln_prod_id")
	private Integer applicationProductId;

}