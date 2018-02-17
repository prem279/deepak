/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 4, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;

import lombok.Data;

/**
 * @author n0296170
 *
 */
@SuppressWarnings("serial")
@Data
@Entity
@Table(name="emplr_emp_t")
public class Employee extends AbstractAuditableTableEntity implements Serializable{

	@Id
    @Column(name="emplr_emp_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="muw_emplr_emp_seq")
	@SequenceGenerator(name="muw_emplr_emp_seq", sequenceName="MUW_EMPLR_EMP_SEQ", initialValue=1, allocationSize = 1)
	private Integer employerEmployeeId;	
	
	@NotNull
	@Min(1)
	@Max(99999999)
	@Column(name="emplr_id")
	private Integer employerId;
	
	@NotNull
	@Pattern(regexp = "^\\d{9}$")
	@Column(name="emp_ssn_num")
	private String employeeSsn;
	
	@Pattern(regexp="^[A-Za-z'-]{0,70}$")
	@Column(name="prev_last_nme")
	private String previousLastName;
	
	@Min(0)
	@Max(99999999)
	@Column(name="annl_erngs_amt")
	// TODO should this be an integer?
	private Integer annualEarningsAmount;
	
	@Pattern(regexp = "^[A-Za-z0-9]{0,70}$")
	@Column(name="emp_clas_cde")
	private String employeeClassCode;
	
	@Pattern(regexp = "^[A-Za-z0-9]{0,20}$")
	@Column(name="emp_idn_num")
	private String employeeIdentificationNumber;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	@Column(name="hire_dte")
	private Date hireDate;
	
	@Pattern(regexp="^[A-Z a-z0-9'#&-]{0,100}$")
	@Column(name="job_titl_nme")
	private String jobTitleName;
	
	@NotNull
	@Pattern(regexp="^[A-Za-z]{0,6}$")
	@Column(name="cmunn_mthd_cde")
	private String communicationMethodCode;
	
	@Pattern(regexp = "^[A-Za-z0-9]{0,8}$")
	@Column(name="rptg_locn_num")
	private String reportingLocationNumber;
}
	
