/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Jan 10, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.util.CustomDateDeserializer;
import com.lmig.ci.lmbc.empr.common.util.CustomDateSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author n0296170
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "employees", value = "employee")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationSubmissionEmployeeResource extends ResourceSupport {

	private Integer employerEmployeeId;

	@Min(1)
	@Max(99999999)
	private Integer employerId;

	@Pattern(regexp = "^\\d{9}$")
	private String employeeSsn;

	@Pattern(regexp = "^[A-Za-z'-]{0,70}$")
	private String previousLastName;

	@Min(0)
	@Max(99999999)
	private Double annualEarningsAmount;

	@Pattern(regexp = "^[A-Za-z0-9]{0,70}$")
	private String employeeClassCode;

	@Pattern(regexp = "^[A-Za-z0-9]{0,20}$")
	private String employeeIdentificationNumber;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date hireDate;

	@Pattern(regexp = "^[A-Z a-z0-9'#&-]{0,100}$")
	private String jobTitleName;

	@Pattern(regexp = "^[A-Za-z]{0,6}$")
	private String communicationMethodCode;

	private Integer concurrencyQuantity;

	private String firstName;

	private String lastName;

	private String middleInitial;

	@Pattern(regexp = "(^[A-Za-z0-9]{1,50}+$)")
	private String createUserIdNumber;

	@Pattern(regexp = "(^[A-Za-z0-9]{1,50}+$)")
	private String lastUpdatedUserIdNumber;

	@Pattern(regexp = "^[A-Za-z0-9]{0,8}$")
	private String reportingLocationNumber;

	private boolean mailingAddressSame;

	@Valid
	private PhysicalAddressResource employeeHomeAddress;

	@Valid
	private PhysicalAddressResource employeeMailingAddress;

	@Valid
	private EmailAddressResource employeeEmailAddress;

	@Valid
	private PhoneNumberResource employeeTelephone;

	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", timezone = "Etc/UTC")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date createDatetime;

	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", timezone = "Etc/UTC")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date lastUpdateDatetime;

}
