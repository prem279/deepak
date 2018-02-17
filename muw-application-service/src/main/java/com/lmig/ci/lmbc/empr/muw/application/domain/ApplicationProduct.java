/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 23, 2016
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
@Table(name = "muw_appln_prod_t")
@EqualsAndHashCode(callSuper = false, exclude = "application")
@ToString(exclude = "application")
public class ApplicationProduct extends AbstractAuditableTableEntity implements Serializable {

	private static final long serialVersionUID = 5669982079246156839L;

	@Id
	@Column(name = "appln_prod_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUW_APPLN_PROD_SEQ")
	@SequenceGenerator(name = "MUW_APPLN_PROD_SEQ", sequenceName = "MUW_APPLN_PROD_SEQ", allocationSize = 1)
	private Integer applicationProductId;

	@NotNull
	@Pattern(regexp = "^[A-Za-z]{3}$")
	@Column(name = "prod_cde")
	private String productCode;

	@ManyToOne
	@JoinColumn(name = "appln_id")
	@JsonBackReference
	private Application application;

	/**
	 * eligibleApplicantRoleMap
	 *
	 * @return list of applicant roles that can apply for this product
	 */
	/*
	 * public List<String> getEligibleApplicantRoles() { return
	 * MuwApplicationProductConfig.eligibleApplicantRoleMap.get(this.productCode
	 * ); }
	 */

	// Removing link to applicant products from here. It is linked from
	// Applicant. Remove this code block after regression testing is complete
	// @OneToMany(cascade = CascadeType.REMOVE, fetch=FetchType.LAZY)
	// @JoinColumn(name = "appln_prod_id", referencedColumnName =
	// "appln_prod_id", insertable = false, updatable = false)
	// @JsonManagedReference
	// private List<ApplicantProduct> applicantProduct;

}