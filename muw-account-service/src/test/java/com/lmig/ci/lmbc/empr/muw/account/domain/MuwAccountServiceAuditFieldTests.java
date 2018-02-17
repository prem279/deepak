package com.lmig.ci.lmbc.empr.muw.account.domain;

import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmig.ci.lmbc.empr.muw.account.MuwAccountServiceTests;
import com.lmig.ci.lmbc.empr.muw.account.repository.EmployerRepository;

public class MuwAccountServiceAuditFieldTests extends MuwAccountServiceTests {
	@Autowired
	private EmployerRepository empRepository;

	@Test
	public void testAuditFields() throws Exception {
		StakeholderLedgerSerialNumberPk spk = new StakeholderLedgerSerialNumberPk();
		spk.setEmployerStakeholderLedgerNumber("02");
		spk.setEmployerStakeholderSerialNumber("10X123");
		Employer empl = new Employer();
		empl.setStakeholderLedgerSerialNumberPk(spk);

		Date beforeSaveTime = new Date(new java.util.Date().getTime());
		empl.setBeginDate(beforeSaveTime);
		empl.setCancellationDate(beforeSaveTime);
		empl.setCompanyAccessCode("Test");
		empl.setAnnvDate(beforeSaveTime);
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
		pre.setAnnualEnrollmentStartDate(beforeSaveTime);
		pre.setAnnualEnrollmentPeriodQuantity(30);
		pre.setFamilyStatusChangeDaysLimitQuantity(3);
		pre.setApplicationReturnDaysLimitQuantity(11);
		pre.setMedicalReturnDaysLimitQuantity(22);
		pre.setPerformanceGuaranteeIndicator(true);
		pre.setPerformanceGuaranteeDurationQuantity(100);
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
		pre.setFileFeedMailingCode("PAPER");
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
		pre.setNewlyEligibleEmployeesIndicator(true);

		pre.setEmployeeLabels(createLabelList());
		pre.setSpouseLabels(createLabelList());
		pre.setEmployeeIdLabels(createLabelList());

		EoiProduct pro = new EoiProduct();
		pro.setEmployer(empl);

		empl.setProducts(Arrays.asList(pro));

		pro.setProductCode("BCL");
		pro.setLabels(createLabelList());
		pro.setBeginDate(beforeSaveTime);
		pro.setCancellationDate(beforeSaveTime);

		// wrap in sleep to ensure time delay for compare
		TimeUnit.SECONDS.sleep(1);
		Employer savedEmp = empRepository.saveAndFlush(empl);
		TimeUnit.SECONDS.sleep(1);

		List<EoiProduct> prods = savedEmp.getProducts();

		Date afterSaveTime = new Date(new java.util.Date().getTime());

		// test created fields
		assertTrue(savedEmp.getConcurrencyQuantity().equals(0));
		assertTrue(savedEmp.getCreateUserIdNum().equals("JUNIT"));
		assertTrue(savedEmp.getCreateDatetime().compareTo(beforeSaveTime) > 0);
		assertTrue(savedEmp.getCreateDatetime().compareTo(afterSaveTime) < 0);

		assertTrue(savedEmp.getPreference().getConcurrencyQuantity().equals(0));
		assertTrue(savedEmp.getPreference().getCreateUserIdNum().equals("JUNIT"));
		assertTrue(savedEmp.getPreference().getCreateDatetime().compareTo(beforeSaveTime) > 0);
		assertTrue(savedEmp.getPreference().getCreateDatetime().compareTo(afterSaveTime) < 0);

		for (EoiProduct eoiProduct : prods) {
			assertTrue(eoiProduct.getConcurrencyQuantity().equals(0));
			assertTrue(eoiProduct.getCreateUserIdNum().equals("JUNIT"));
			assertTrue(eoiProduct.getCreateDatetime().compareTo(beforeSaveTime) > 0);
			assertTrue(eoiProduct.getCreateDatetime().compareTo(afterSaveTime) < 0);
			for (Label label : eoiProduct.getLabels()) {
				assertTrue(label.getConcurrencyQuantity().equals(0));
				assertTrue(label.getCreateUserIdNum().equals("JUNIT"));
				assertTrue(label.getCreateDatetime().compareTo(beforeSaveTime) > 0);
				assertTrue(label.getCreateDatetime().compareTo(afterSaveTime) < 0);
			}
		}

		// test updated fields
		savedEmp.setAlternateName("New Alt Name");
		// savedEmp.getPreference().setEmployeeLabel("XXX");
		for (EoiProduct eoiProduct : prods) {
			eoiProduct.setProductCode("OCL");
		}
		Date beforeUpdateTime = new Date(new java.util.Date().getTime());

		// wrap in sleep to ensure time delay for compare
		TimeUnit.SECONDS.sleep(1);
		savedEmp = empRepository.saveAndFlush(savedEmp);
		TimeUnit.SECONDS.sleep(1);

		Date afterUpdateTime = new Date(new java.util.Date().getTime());

		assertTrue(savedEmp.getConcurrencyQuantity().equals(1));
		assertTrue(savedEmp.getLastUpdateUserIdNum().equals("JUNIT"));
		assertTrue(savedEmp.getLastUpdateDatetime().compareTo(beforeUpdateTime) > 0);
		assertTrue(savedEmp.getLastUpdateDatetime().compareTo(afterUpdateTime) < 0);

		assertTrue(savedEmp.getPreference().getConcurrencyQuantity().equals(1));
		assertTrue(savedEmp.getPreference().getLastUpdateUserIdNum().equals("JUNIT"));
		assertTrue(savedEmp.getPreference().getLastUpdateDatetime().compareTo(beforeUpdateTime) > 0);
		assertTrue(savedEmp.getPreference().getLastUpdateDatetime().compareTo(afterUpdateTime) < 0);

		for (Label label : savedEmp.getPreference().getEmployeeIdLabels()) {
			assertTrue(label.getConcurrencyQuantity().equals(1));
			assertTrue(label.getLastUpdateUserIdNum().equals("JUNIT"));
			assertTrue(label.getLastUpdateDatetime().compareTo(beforeUpdateTime) > 0);
			assertTrue(label.getLastUpdateDatetime().compareTo(afterUpdateTime) < 0);
		}

		for (Label label : savedEmp.getPreference().getEmployeeLabels()) {
			assertTrue(label.getConcurrencyQuantity().equals(1));
			assertTrue(label.getLastUpdateUserIdNum().equals("JUNIT"));
			assertTrue(label.getLastUpdateDatetime().compareTo(beforeUpdateTime) > 0);
			assertTrue(label.getLastUpdateDatetime().compareTo(afterUpdateTime) < 0);
		}

		for (Label label : savedEmp.getPreference().getSpouseLabels()) {
			assertTrue(label.getConcurrencyQuantity().equals(1));
			assertTrue(label.getLastUpdateUserIdNum().equals("JUNIT"));
			assertTrue(label.getLastUpdateDatetime().compareTo(beforeUpdateTime) > 0);
			assertTrue(label.getLastUpdateDatetime().compareTo(afterUpdateTime) < 0);
		}

		prods = savedEmp.getProducts();
		for (EoiProduct eoiProduct : prods) {
			assertTrue(eoiProduct.getConcurrencyQuantity().equals(1));
			assertTrue(eoiProduct.getLastUpdateUserIdNum().equals("JUNIT"));
			assertTrue(eoiProduct.getLastUpdateDatetime().compareTo(beforeUpdateTime) > 0);
			assertTrue(eoiProduct.getLastUpdateDatetime().compareTo(afterUpdateTime) < 0);
			for (Label label : eoiProduct.getLabels()) {
				assertTrue(label.getConcurrencyQuantity().equals(1));
				assertTrue(label.getLastUpdateUserIdNum().equals("JUNIT"));
				assertTrue(label.getLastUpdateDatetime().compareTo(beforeUpdateTime) > 0);
				assertTrue(label.getLastUpdateDatetime().compareTo(afterUpdateTime) < 0);
			}
		}
	}

	private ArrayList<Label> createLabelList() {
		ArrayList<Label> listLabels = new ArrayList<>();
		Label label1 = new Label();
		label1.setLanguageCode("ENG");
		label1.setLabelText("Label Text");

		Label label2 = new Label();
		label2.setLanguageCode("SPA");
		label2.setLabelText("Lábel Text in Spánish");

		listLabels.add(label1);
		listLabels.add(label2);
		return listLabels;
	}
}
