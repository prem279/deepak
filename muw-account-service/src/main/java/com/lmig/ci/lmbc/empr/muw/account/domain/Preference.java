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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@EqualsAndHashCode(callSuper = true, exclude = { "employeeLabelCode", "spouseLabelCode", "employeeIdLabelCode" })
@Table(name = "emplr_prefcs_t")
@ToString(exclude = { "employeeLabels", "spouseLabels", "employeeIdLabels" })
public class Preference extends AbstractAuditableTableEntity implements Serializable {
	private static final long serialVersionUID = 8501404001471304837L;

	@Id
	@Column(name = "emplr_prefcs_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "muw_pref_seq")
	@SequenceGenerator(name = "muw_pref_seq", sequenceName = "MUW_PREF_SEQ", allocationSize = 1)
	private Integer employerPreferencesId;

	@Pattern(regexp = "(^$)|(^[A-Z]{4}$)")
	@Column(name = "sbmn_mthd_cde")
	@VVCheck(code = "MUW_SUBMISSION_METHOD_CODES", nullValid = true)
	private String submissionMethodCode;

	@NotNull
	@Column(name = "init_erlmt_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean initialEnrollmentCode;

	@Pattern(regexp = "(^$)|(^[A-Za-z0-9 ]+$)")
	@Column(name = "wtg_prd_dys")
	private String waitingPeriodDaysLimitQuantity;

	@NotNull
	@Column(name = "allw_lat_erlmt_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean allowLateEnrollmentIndicator;

	@NotNull
	@Column(name = "allw_fam_stts_chng_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean allowFamilyStatusChangeIndicator;

	@NotNull
	@Column(name = "dspl_prod_amts_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean displayProductAmountsIndicator;

	@NotNull
	@Pattern(regexp = "^[A-Z]{6}$")
	@Column(name = "approve_eff_dte_calcn_rule_cde")
	@VVCheck(code = "MUW_EFFECTIVE_DATE_STRATEGY_CODES")
	private String approveEffectiveDateCalculationRuleCode;

	@NotNull
	@Column(name = "sal_incs_reqr_evdn_of_insbty_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean salaryIncreaseRequireEOIIndicator;

	@NotNull
	@Pattern(regexp = "^[A-Z]{3}$")
	@Column(name = "appl_idn_mthd_cde")
	@VVCheck(code = "MUW_UNIQUE_SUBMISSION_ID_CODES")
	private String applicantIdentificationMethodCode;

	@NotNull
	@Column(name = "annl_erlmt_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean annualEnrollmentIndicator;

	@Column(name = "annl_erlmt_strt_dte")
	private Date annualEnrollmentStartDate;

	@Min(0)
	@Max(999)
	@Column(name = "annl_erlmt_prd_qty")
	private Integer annualEnrollmentPeriodQuantity;

	@Min(0)
	@Max(365)
	@Column(name = "fam_stts_chng_days_lmt_qty")
	private Integer familyStatusChangeDaysLimitQuantity;

	@Min(0)
	@Max(999)
	@Column(name = "appln_rtrn_days_lmt_qty")
	private Integer applicationReturnDaysLimitQuantity;

	@NotNull
	@Min(15)
	@Max(999)
	@Column(name = "medl_rtrn_days_lmt_qty")
	private Integer medicalReturnDaysLimitQuantity;

	@NotNull
	@Column(name = "prfmc_guar_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean performanceGuaranteeIndicator;

	@Min(1)
	@Max(999)
	@Column(name = "prfmc_guar_dur_qty")
	private Integer performanceGuaranteeDurationQuantity;

	@Column(name = "emp_lbl")
	private final String employeeLabelCode = "EMP";

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "pref_lbl_t", joinColumns = {
			@JoinColumn(name = "emplr_prefcs_id", referencedColumnName = "emplr_prefcs_id"),
			@JoinColumn(name = "pref_type", referencedColumnName = "emp_lbl", insertable = true) }, inverseJoinColumns = @JoinColumn(name = "lbl_id", referencedColumnName = "lbl_id"))
	@JsonManagedReference
	private List<Label> employeeLabels = new ArrayList<Label>();

	@Column(name = "spus_lbl")
	private final String spouseLabelCode = "SPUS";

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "pref_lbl_t", joinColumns = {
			@JoinColumn(name = "emplr_prefcs_id", referencedColumnName = "emplr_prefcs_id"),
			@JoinColumn(name = "pref_type", referencedColumnName = "spus_lbl", insertable = true) }, inverseJoinColumns = @JoinColumn(name = "lbl_id", referencedColumnName = "lbl_id"))
	@JsonManagedReference
	private List<Label> spouseLabels = new ArrayList<Label>();

	@Pattern(regexp = "^[A-Z]{6}$")
	@Column(name = "prll_sync_mde")
	@VVCheck(code = "MUW_PAYROLL_SYNCH_MODE_CODES", nullValid = true)
	private String payrollSyncMode;

	@Pattern(regexp = "^[A-Z]{6}$")
	@Column(name = "prll_sync_dy")
	@VVCheck(code = "MUW_PAYROLL_SYNCH_DAY_CODES", nullValid = true)
	private String payrollSyncDay;

	@NotNull
	@Column(name = "send_emplr_app_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployerApprovalLetterIndicator;

	@NotNull
	@Column(name = "send_emplr_clsr_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployerClosureLetterIndicator;

	@NotNull
	@Column(name = "send_emplr_dnl_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployerDenialLetterIndicator;

	@NotNull
	@Column(name = "send_emp_app_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployeeApprovalLetterIndicator;

	@NotNull
	@Column(name = "send_emp_medl_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployeeMedicalLetterIndicator;

	@NotNull
	@Column(name = "send_emp_dnl_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployeeDenialLetterIndicator;

	@NotNull
	@Column(name = "send_emp_clsr_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployeeClosureLetterIndicator;

	@NotNull
	@Column(name = "incl_eff_dte_app_emplr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean includeEffectiveDateApprovalEmployerIndicator;

	@NotNull
	@Column(name = "incl_eff_dte_app_emp_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean includeEffectiveDateApprovalEmployeeIndicator;

	@NotNull
	@Column(name = "send_emp_incp_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployeeIncompleteLetterIndicator;

	@NotNull
	@Column(name = "send_emplr_medl_flup_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployerMedicalFollowupLetterIndicator;

	@NotNull
	@Column(name = "send_emp_medl_flup_lttr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sendEmployeeMedicalFollowupLetterIndicator;

	@NotNull
	@Min(15)
	@Max(999)
	@Column(name = "medl_grc_prd")
	private Integer medicalGracePeriod;

	@NotNull
	@Min(15)
	@Max(999)
	@Column(name = "rtrn_incp_days")
	private Integer returnIncompleteDays;

	@NotNull
	@Min(15)
	@Max(999)
	@Column(name = "incp_grc_prd")
	private Integer incompleteGracePeriod;

	@NotNull
	@Column(name = "prtblty_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean portabilityIndicator;

	@NotNull
	@Column(name = "prtblty_pref_rates_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean portabilityPreferredRatesIndicator;

	@NotNull
	@Column(name = "prtblty_cnvs_mailg_svc_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean portabilityConversionMailingServicesIndicator;

	@Min(0)
	@Max(999)
	@Column(name = "cnvs_grc_prd")
	private Integer conversionGracePeriod;

	@NotNull
	@Column(name = "mid_mkt_emplr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean midMarketEmployerIndicator;

	@NotNull
	@Column(name = "file_feed_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean fileFeedIndicator;

	@Pattern(regexp = "^[A-Z]{6}$")
	@Column(name = "file_feed_freqy_cde")
	@VVCheck(code = "MUW_FILE_FEED_FREQUENCY_CODES", nullValid = true)
	private String fileFeedFrequencyCode;

	@Pattern(regexp = "^[A-Z]{5}$")
	@Column(name = "file_feed_mailg_cde")
	@VVCheck(code = "MUW_FILE_FEED_MAILING_CODES", nullValid = true)
	private String fileFeedMailingCode;

	@Length(max = 255)
	@Pattern(regexp = "(^$)|(^([A-Za-z0-9@.#&!-][ ]?)*([A-Za-z0-9!])+$)")
	@Column(name = "file_feed_hr_cntc")
	private String fileFeedHRContact;

	@NotNull
	@Column(name = "exch_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean exchangeIndicator;

	@Pattern(regexp = "^[A-Z]{3}$")
	@Column(name = "exch_nme_cde")
	@VVCheck(code = "MUW_EXCHANGE_NAME_CODES", nullValid = true)
	private String exchangeNameCode;

	@NotNull
	@Column(name = "sso_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean singleSignOnIndicator;

	@NotNull
	@Column(name = "dsab_preg_appr_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean disabilityPregnancyApprovalIndicator;

	@NotNull
	@Column(name = "supr_eligk_pre_pltn_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean suppressEligibilityPrePopIndicator;

	@NotNull
	@Column(name = "smkg_qualg_ques_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean smokingQualifyingQuestionIndicator;

	@NotNull
	@Column(name = "preg_qualg_ques_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean pregnancyQualifyingQuestionIndicator;

	@NotNull
	@Column(name = "absne_qualg_ques_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean absenceQualifyingQuestionIndicator;

	@NotNull
	@Column(name = "ins_dli_qualg_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean insuranceDeclinedQualifyingQuestionIndicator;

	@NotNull
	@Column(name = "new_elig_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean newlyEligibleEmployeesIndicator;

	@NotNull
	@Column(name = "dspl_loc_i")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean displayLocationIndicator;

	@Column(name = "emp_id_lbl")
	private final String employeeIdLabelCode = "EMPID";

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "pref_lbl_t", joinColumns = {
			@JoinColumn(name = "emplr_prefcs_id", referencedColumnName = "emplr_prefcs_id"),
			@JoinColumn(name = "pref_type", referencedColumnName = "emp_id_lbl", insertable = true) }, inverseJoinColumns = @JoinColumn(name = "lbl_id", referencedColumnName = "lbl_id"))
	@JsonManagedReference
	private List<Label> employeeIdLabels = new ArrayList<Label>();

}
