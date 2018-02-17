package com.lmig.ci.lmbc.empr.muw.account.repository;
/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 29, 2016
 */

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmig.ci.lmbc.empr.muw.account.MuwAccountServiceTests;
import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;
import com.lmig.ci.lmbc.empr.muw.account.domain.Preference;
import com.lmig.ci.lmbc.empr.muw.account.domain.StakeholderLedgerSerialNumberPk;

/**
 * @author n0296170
 *
 */

public class PreferencesRepositoryTests extends MuwAccountServiceTests {

	@Autowired
	private EmployerRepository empRepository;

	@Autowired
	private PreferencesRepository preRepository;

	@Test
	@Transactional
	public void testRepository() {

		StakeholderLedgerSerialNumberPk spk = new StakeholderLedgerSerialNumberPk();
		spk.setEmployerStakeholderLedgerNumber("22");
		spk.setEmployerStakeholderSerialNumber("10X123");
		Employer empl = new Employer();
		empl.setStakeholderLedgerSerialNumberPk(spk);

		Date dd = new Date(new java.util.Date().getTime());
		empl.setBeginDate(dd);
		empl.setCancellationDate(dd);
		empl.setCompanyAccessCode("Test");
		empl.setAnnvDate(dd);
		empl.setEmployerName("Test");
		empl.setSitusStateCode("NY");

		Preference pre = new Preference();
		empl.setPreference(pre);

		pre.setSubmissionMethodCode("ONLN");
		pre.setAllowLateEnrollmentIndicator(true);
		pre.setAllowFamilyStatusChangeIndicator(false);
		pre.setDisplayProductAmountsIndicator(true);
		pre.setApproveEffectiveDateCalculationRuleCode("MANUAL");
		pre.setSalaryIncreaseRequireEOIIndicator(false);
		pre.setApplicantIdentificationMethodCode("EID");
		pre.setAnnualEnrollmentIndicator(true);
		pre.setAnnualEnrollmentStartDate(dd);
		pre.setAnnualEnrollmentPeriodQuantity(30);
		pre.setFamilyStatusChangeDaysLimitQuantity(3);
		pre.setApplicationReturnDaysLimitQuantity(11);
		pre.setMedicalReturnDaysLimitQuantity(22);
		pre.setPerformanceGuaranteeIndicator(true);
		pre.setPerformanceGuaranteeDurationQuantity(100);
		// pre.setEmployeeLabel("Employee");
		// pre.setSpouseLabel("Domestic partner");

		pre.setSendEmployerApprovalLetterIndicator(true);
		pre.setSendEmployerClosureLetterIndicator(true);
		pre.setSendEmployerDenialLetterIndicator(false);
		pre.setSendEmployeeApprovalLetterIndicator(true);
		pre.setSendEmployeeMedicalLetterIndicator(false);
		pre.setSendEmployeeDenialLetterIndicator(true);
		pre.setSendEmployeeClosureLetterIndicator(false);
		pre.setIncludeEffectiveDateApprovalEmployerIndicator(false);
		pre.setIncludeEffectiveDateApprovalEmployeeIndicator(false);
		pre.setSendEmployeeIncompleteLetterIndicator(false);
		pre.setSendEmployerMedicalFollowupLetterIndicator(true);
		pre.setSendEmployeeMedicalFollowupLetterIndicator(true);
		pre.setMedicalGracePeriod(30);
		pre.setReturnIncompleteDays(120);
		pre.setIncompleteGracePeriod(60);
		pre.setPortabilityIndicator(true);
		pre.setPortabilityPreferredRatesIndicator(false);
		pre.setPortabilityConversionMailingServicesIndicator(false);
		pre.setConversionGracePeriod(30);
		pre.setMidMarketEmployerIndicator(false);
		pre.setFileFeedIndicator(true);
		pre.setFileFeedFrequencyCode("WEEKLY");
		pre.setFileFeedMailingCode("EMAIL");
		pre.setFileFeedHRContact("Please email chris jennings at liberty mutual dot com");
		pre.setExchangeIndicator(true);
		pre.setExchangeNameCode("MMX");
		pre.setSingleSignOnIndicator(true);
		pre.setInitialEnrollmentCode(false);
		pre.setPayrollSyncMode("WEEKLY");
		pre.setPayrollSyncDay("FRIDAY");
		pre.setDisabilityPregnancyApprovalIndicator(true);
		pre.setAbsenceQualifyingQuestionIndicator(true);
		pre.setSuppressEligibilityPrePopIndicator(true);
		pre.setSmokingQualifyingQuestionIndicator(false);
		pre.setPregnancyQualifyingQuestionIndicator(true);
		pre.setInsuranceDeclinedQualifyingQuestionIndicator(false);
		pre.setDisplayLocationIndicator(true);
		// pre.setEmployeeIdLabel("No296170");
		pre.setNewlyEligibleEmployeesIndicator(true);

		long count = preRepository.count();

		// add
		empRepository.saveAndFlush(empl);

		System.out.println(empl.getEmployerId());

		preRepository.saveAndFlush(pre);

		assertEquals(preRepository.count(), count + 1);

		List<Preference> lst = preRepository.findAll();

		Iterator<Preference> itr = lst.iterator();
		Preference temp = null;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getEmployerPreferencesId() == pre.getEmployerPreferencesId()) {
				break;
			}
		}

		// update
		temp.setApplicationReturnDaysLimitQuantity(4);
		preRepository.save(temp);

		Preference ep = preRepository.findOne(temp.getEmployerPreferencesId());
		assertEquals(ep.getApplicationReturnDaysLimitQuantity().intValue(), 4);
		preRepository.delete(ep);
		empRepository.delete(empl);

		assertEquals(preRepository.count(), count);
	}

}
