/*
Label2 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 17, 2016
 */

/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 17, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.domain;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;
import com.lmig.ci.reuse.vv.validation.VVCheck;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author n0296170
 *
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = { "employer" })
@ToString(exclude = { "employer", "labels" })
@Table(name = "emplr_eoi_prod_t")
public class EoiProduct extends AbstractAuditableTableEntity implements Serializable {
	private static final long serialVersionUID = 696569167084289460L;

	@Id
	@Column(name = "prod_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "muw_eoiprod_seq")
	@SequenceGenerator(name = "muw_eoiprod_seq", sequenceName = "MUW_EOIPROD_SEQ", allocationSize = 1)
	private Integer productId;

	@NotNull
	@Pattern(regexp = "^[A-Z]{3}$")
	@Column(name = "prod_cde")
	@VVCheck(code = "MUW_PRODUCT_CODES")
	private String productCode;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "prod_lbl_t", joinColumns = @JoinColumn(name = "prod_id", referencedColumnName = "prod_id"), inverseJoinColumns = @JoinColumn(name = "lbl_id", referencedColumnName = "lbl_id"))
	private List<Label> labels = new ArrayList<Label>();

	@Column(name = "cancn_dte")
	private Date cancellationDate;

	@NotNull
	@Column(name = "beg_dte")
	private Date beginDate;

	@ManyToOne
	@JoinColumn(name = "emplr_id")
	@JsonBackReference
	private Employer employer;
}
