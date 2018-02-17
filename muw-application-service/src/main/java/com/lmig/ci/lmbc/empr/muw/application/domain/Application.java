/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 9, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;
import com.lmig.ci.lmbc.empr.common.util.CustomDateDeserializer;
import com.lmig.ci.lmbc.empr.common.util.CustomDateOnlySerializer;
import com.lmig.ci.lmbc.empr.muw.application.service.Eventable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author n0296170
 *
 */

@Entity
@Data
@Table(name = "muw_appln_t")
@NamedEntityGraph(name = "graph.Application", attributeNodes = { @NamedAttributeNode(value = "applicationProducts"),
		@NamedAttributeNode(value = "applicants"), @NamedAttributeNode(value = "applicationMedicalFactors") })
@EqualsAndHashCode(callSuper = true)
public class Application extends AbstractAuditableTableEntity implements Serializable, Eventable {

	private static final long serialVersionUID = 5669982079246156839L;

	@Id
	@Column(name = "appln_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "muw_appln_seq")
	@SequenceGenerator(name = "muw_appln_seq", sequenceName = "MUW_appln_SEQ", allocationSize = 1)
	private Integer applicationId;

	@Min(1)
	@Max(Integer.MAX_VALUE)
	@NotNull
	@Column(name = "emplr_id")
	private Integer employerId;

	@Min(1)
	@Max(Integer.MAX_VALUE)
	@NotNull
	@Column(name = "emplr_emp_id")
	private Integer employerEmployeeId;

	@JsonSerialize(using = CustomDateOnlySerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@Column(name = "appln_recvd_dtm")
	private Date applicationReceivedDate;

	@JsonSerialize(using = CustomDateOnlySerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@Column(name = "fam_stts_chng_evnt_dte")
	private Date familySttsChangeEventDate;

	@NotNull
	@Pattern(regexp = "^[A-Za-z]{4}$")
	@Column(name = "reas_cde")
	private String reasonCode;

	@NotNull
	@Pattern(regexp = "^[A-Za-z]{4}$")
	@Column(name = "sbmn_mthd_cde")
	private String submissionMethodCode;

	@NotNull
	@Column(name = "file_feed_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean fileFeedIndicator = false;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "application")
	private Set<ApplicationProduct> applicationProducts = new HashSet<ApplicationProduct>();

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "application")
	private Set<Applicant> applicants = new HashSet<Applicant>();

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "application")
	private Set<ApplicationMedicalFactor> applicationMedicalFactors = new HashSet<ApplicationMedicalFactor>();

	@Override
	public Integer getSubjectAreaId() {
		return applicationId;
	}
}