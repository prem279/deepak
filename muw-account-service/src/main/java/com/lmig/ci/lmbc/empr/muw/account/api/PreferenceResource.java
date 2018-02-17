/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 11, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.util.CustomDateDeserializer;
import com.lmig.ci.lmbc.empr.common.util.CustomDateSerializer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author n0159479
 *
 */
@Data
@EqualsAndHashCode(exclude = "employer")
@ToString(exclude = { "employer" })
@Relation(collectionRelation = "preferences", value = "preference")
public class PreferenceResource extends AbstractAuditableTableResource {

	@ApiModelProperty(value = "Employer's Preference ID", example = "3", required = true)
	private Integer employerPreferencesId;

	@ApiModelProperty(value = "Code for Employer's Primary Means of EOI Submission", example = "ONLN", required = true)
	@Pattern(regexp = "(^$)|(^[A-Z]{4}$)")
	private String submissionMethodCode;

	@ApiModelProperty(value = "Boolean for Employer Allowing Initial Enrollment", example = "true", required = false)
	private Boolean initialEnrollmentCode;

	@Pattern(regexp = "(^$)|(^[A-Za-z0-9 ]+$)")
	private String waitingPeriodDaysLimitQuantity;

	@ApiModelProperty(value = "Boolean for Employer Allowing Late Enrollment", example = "false", required = false)
	private Boolean allowLateEnrollmentIndicator;

	@ApiModelProperty(value = "Boolean for Employer Allowing Family Status Change for Enrollment", example = "true", required = false)
	private Boolean allowFamilyStatusChangeIndicator;

	@ApiModelProperty(value = "Boolean for Employer Display of Product Amounts", example = "true", required = false)
	private Boolean displayProductAmountsIndicator;

	@ApiModelProperty(value = "Code for Employer's Effective Date Calculation Strategy", example = "MANUAL", required = true)
	@NotNull
	@Pattern(regexp = "^[A-Z]{6}$")
	private String approveEffectiveDateCalculationRuleCode;

	@ApiModelProperty(value = "Boolean for Employer Allowing Salary Increase for Enrollment", example = "false", required = false)
	private Boolean salaryIncreaseRequireEOIIndicator;

	@ApiModelProperty(value = "Code for Employer's Means of Applicant Identification", example = "SSN", required = true)
	@NotNull
	@Pattern(regexp = "^[A-Z]{3}$")
	private String applicantIdentificationMethodCode;

	@ApiModelProperty(value = "Boolean for Employer Allowing Annual Enrollment", example = "true", required = false)
	private Boolean annualEnrollmentIndicator;

	@ApiModelProperty(value = "Employer's Annual Enrollment Start Date", example = "2010-07-10", required = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date annualEnrollmentStartDate;

	@ApiModelProperty(value = "Timeframe for Employer's Annual Enrollment Period", example = "60", required = false)
	@Min(0)
	@Max(999)
	private Integer annualEnrollmentPeriodQuantity;

	@ApiModelProperty(value = "Employer's Allowed Time for Applicants with Family Status Change to Reapply", example = "30", required = false)
	@Min(0)
	@Max(365)
	private Integer familyStatusChangeDaysLimitQuantity;

	@ApiModelProperty(value = "Employer's Maximum Days for Application Turnaround", example = "45", required = true)
	@NotNull
	@Min(0)
	@Max(999)
	private Integer applicationReturnDaysLimitQuantity;

	@ApiModelProperty(value = "Full Timeframe Allowed By Employer for Returning Medical Info Before Auto Closure", example = "30", required = true)
	@NotNull
	@Min(15)
	@Max(999)
	private Integer medicalReturnDaysLimitQuantity;

	@ApiModelProperty(value = "Boolean for Employer With Performance Guarantee", example = "true", required = false)
	private Boolean performanceGuaranteeIndicator;

	@ApiModelProperty(value = "Employer's Maximum Days Allowed for Application Decision Turnaround", example = "15", required = false)
	@Min(1)
	@Max(999)
	private Integer performanceGuaranteeDurationQuantity;

	@ApiModelProperty(value = "Employer's Custom Label for Employees", example = "Team Member", required = false)
	private List<LabelResource> employeeLabels = new ArrayList<LabelResource>();

	@ApiModelProperty(value = "Employer's Custom Label for Spouses", example = "Domestic Partner", required = false)
	private List<LabelResource> spouseLabels = new ArrayList<LabelResource>();

	@ApiModelProperty(value = "Employer's Preferred Method for Payroll Synchronization", example = "WEEKLY", required = false)
	@Pattern(regexp = "^[A-Z]{6}$")
	private String payrollSyncMode;

	@ApiModelProperty(value = "Employer's Preferred Day for Payroll Synchronization", example = "MONDAY", required = true)
	@Pattern(regexp = "^[A-Z]{6}$")
	private String payrollSyncDay;

	@ApiModelProperty(value = "Boolean for Employer Requesting ER Approval Letter", example = "true", required = false)
	private Boolean sendEmployerApprovalLetterIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting ER Closure Letter", example = "true", required = false)
	private Boolean sendEmployerClosureLetterIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting ER Denial Letter", example = "true", required = false)
	private Boolean sendEmployerDenialLetterIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting EE Approval Letter", example = "true", required = false)
	private Boolean sendEmployeeApprovalLetterIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting EE Medical Letter", example = "true", required = false)
	private Boolean sendEmployeeMedicalLetterIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting EE Denial Letter", example = "true", required = false)
	private Boolean sendEmployeeDenialLetterIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting EE Closure Letter", example = "true", required = false)
	private Boolean sendEmployeeClosureLetterIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting EE Closure Letter", example = "true", required = false)
	private Boolean includeEffectiveDateApprovalEmployerIndicator;

	@ApiModelProperty(value = "Boolean for Employer Including Effective Date on Correspondence", example = "true", required = false)
	private Boolean includeEffectiveDateApprovalEmployeeIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting EE Incomplete Letter", example = "true", required = false)
	private Boolean sendEmployeeIncompleteLetterIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting ER Medical Followup Letter", example = "true", required = false)
	private Boolean sendEmployerMedicalFollowupLetterIndicator;

	@ApiModelProperty(value = "Boolean for Employer Requesting EE Medical Followup Letter", example = "true", required = false)
	private Boolean sendEmployeeMedicalFollowupLetterIndicator;

	@ApiModelProperty(value = "Initial Timeframe Allowed By Employer for Applicant to Complete Medical Request", example = "60", required = false)
	@NotNull
	@Min(15)
	@Max(999)
	private Integer medicalGracePeriod;

	@ApiModelProperty(value = "Full Timeframe Allowed By Employer for Fixing Incomplete Application", example = "60", required = false)
	@NotNull
	@Min(15)
	@Max(999)
	private Integer returnIncompleteDays;

	@ApiModelProperty(value = "Initial Timeframe Allowed By Employer for Applicant to Fix Incomplete Before Auto Closure", example = "60", required = false)
	@NotNull
	@Min(15)
	@Max(999)
	private Integer incompleteGracePeriod;

	@ApiModelProperty(value = "Boolean for Employer with Portability Services", example = "true", required = false)
	private Boolean portabilityIndicator;

	@ApiModelProperty(value = "Boolean for Employer with Portability Preferred Rates", example = "true", required = false)
	private Boolean portabilityPreferredRatesIndicator;

	@ApiModelProperty(value = "Boolean for Employer with Port/Convert Mailing Services", example = "true", required = false)
	private Boolean portabilityConversionMailingServicesIndicator;

	@ApiModelProperty(value = "Timeframe for Employer's Conversion Grace Period", example = "60", required = false)
	@Min(0)
	@Max(999)
	private Integer conversionGracePeriod;

	@ApiModelProperty(value = "Boolean for Mid-Market Employer ", example = "true", required = false)
	@NotNull
	private Boolean midMarketEmployerIndicator;

	@ApiModelProperty(value = "Boolean for Employer with File-Feed Services", example = "true", required = false)
	@NotNull
	private Boolean fileFeedIndicator;

	@ApiModelProperty(value = "Code for Employer's File-Feed Frequency", example = "WEEKLY", required = false)
	@Pattern(regexp = "^[A-Z]{6}$")
	private String fileFeedFrequencyCode;

	@ApiModelProperty(value = "Code for Employer's File-Feed Mailing Type", example = "EWLTR", required = false)
	@Pattern(regexp = "^[A-Z]{5}$")
	private String fileFeedMailingCode;

	@ApiModelProperty(value = "Employer's File-Feed Contact Information", example = "somePerson@LibertyMutual.com", required = false)
	@Pattern(regexp = "(^$)|(^([A-Za-z0-9@.#&!-][ ]?)*([A-Za-z0-9!]){1,255}+$)")
	private String fileFeedHRContact;

	@ApiModelProperty(value = "Boolean for Employer on an Exchange Service", example = "true", required = false)
	@NotNull
	private Boolean exchangeIndicator;

	@ApiModelProperty(value = "Code for Employer's Exchange Service Name", example = "EWLTR", required = false)
	private String exchangeNameCode;

	@ApiModelProperty(value = "Boolean for Employer with Single Sign-On", example = "true", required = false)
	@NotNull
	private Boolean singleSignOnIndicator;

	@ApiModelProperty(value = "Boolean for Employer Allowing Approvals for Disability for Pregnancy Applicants", example = "true", required = false)
	@NotNull
	private Boolean disabilityPregnancyApprovalIndicator;

	@ApiModelProperty(value = "Boolean for suppressing eligibility pre-population", example = "true", required = false)
	@NotNull
	private Boolean suppressEligibilityPrePopIndicator;

	@ApiModelProperty(value = "Boolean for smoking qualifying question indicator", example = "true", required = false)
	@NotNull
	private Boolean smokingQualifyingQuestionIndicator;

	@ApiModelProperty(value = "Boolean for pregnancy qualifying question indicator", example = "true", required = false)
	@NotNull
	private Boolean pregnancyQualifyingQuestionIndicator;

	@ApiModelProperty(value = "Boolean for absence qualifying question indicator", example = "true", required = false)
	@NotNull
	private Boolean absenceQualifyingQuestionIndicator;

	@ApiModelProperty(value = "Boolean for previous declined insurance qualifying question indicator", example = "true", required = false)
	@NotNull
	private Boolean insuranceDeclinedQualifyingQuestionIndicator;

	@ApiModelProperty(value = "Boolean for displaying location(s) indicator", example = "true", required = false)
	@NotNull
	private Boolean displayLocationIndicator;

	@ApiModelProperty(value = "Boolean for newly eligible employees indicator", example = "true", required = false)
	@NotNull
	private Boolean newlyEligibleEmployeesIndicator;

	@ApiModelProperty(value = "Employer's Custom Label ", example = "Employeelabel", required = false)
	private List<LabelResource> employeeIdLabels = new ArrayList<LabelResource>();

	@JsonBackReference
	EmployerResource employer;
}
