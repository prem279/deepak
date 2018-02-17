package com.lmig.ci.lmbc.empr.muw.employee.api;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.util.CustomDateDeserializer;
import com.lmig.ci.lmbc.empr.common.util.CustomDateSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "employee", value = "employee")
public class EmployeeResource extends ResourceSupport implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6543494150499180966L;

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

	@Pattern(regexp = "^[A-Za-z]{0,3}$")
	private String communicationMethodCode;

	private Integer concurrencyQuantity;

	private String firstName;

	private String lastName;

	private String middleInitial;

	@Pattern(regexp = "(^[A-Za-z0-9_]{1,50}+$)")
	private String createUserIdNumber;

	@Pattern(regexp = "(^[A-Za-z0-9_]{1,50}+$)")
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
