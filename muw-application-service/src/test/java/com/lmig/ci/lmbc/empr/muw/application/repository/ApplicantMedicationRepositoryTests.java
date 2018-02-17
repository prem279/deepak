/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Jan 9, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantCondition;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantMedication;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

/**
 * @author n0296170
 *
 *Junit Test cases for Applicant_medication
 *
 */
public class ApplicantMedicationRepositoryTests extends MuwApplicationServiceTests {
	@Autowired
	public WebApplicationContext context;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicantConditionRepository applicantConditionRepository;
	
	@Autowired
	private ApplicantMedicationRepository applicantMedicationRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;

	@Test
	public void testContext() {
		assertNotNull(context);
	}
	
	@Test
	public void testApplicantConditionRepository() {
		
		// Instantiate Application
		Application application = new Application();
		application.setEmployerId(1);
		application.setEmployerEmployeeId(1);
		application.setApplicationReceivedDate(new Date(new java.util.Date().getTime()));
		application.setFamilySttsChangeEventDate(new Date(new java.util.Date().getTime()));
		application.setReasonCode("asdf");
		application.setSubmissionMethodCode("ONLN");
		
		// Save (Create) Application
		long applicationCount = applicationRepository.count();
		System.out.println("initial application count is: " + applicationCount);
		applicationRepository.save(application);
		System.out.println("after save application count is: " + applicationRepository.count());
		assertEquals(applicationRepository.count(), applicationCount + 1);
		Application savedApplication = applicationRepository.findOne(application.getApplicationId());
		assertEquals(application.getReasonCode(), savedApplication.getReasonCode());
		
		// Instantiate Applicant
		Applicant applicant = new Applicant();
//		applicant.setApplicationId(savedApplication.getApplicationId());
		applicant.setApplication(savedApplication);
		applicant.setFirstName("Parson");
		applicant.setLastName("James");
		applicant.setApplicantSSN("245678913");
		applicant.setBirthCountryCode("USA");
		
		// Save (Create) Applicant
		long applicantCount = applicantRepository.count();
		System.out.println("initial applicant count is: " + applicantCount);
		applicantRepository.save(applicant);
		System.out.println("after save applicant count is: " + applicantRepository.count());
		assertEquals(applicantRepository.count(), applicantCount + 1);
		Applicant savedApplicant = applicantRepository.findOne(applicant.getApplicationApplicantId());
		assertEquals(applicant.getApplicantSSN(), savedApplicant.getApplicantSSN());
		
		
		//Instantiate Applicant Condition
		
		ApplicantCondition condition = new ApplicantCondition();
		
		condition.setApplicant(applicant);
		condition.setConditionCode("ABC4");
		condition.setOtherConditionText("ha ha");
		condition.setConditionRecoveryDate(new Date(new java.util.Date().getTime()));
		condition.setConditionOnsetDate(new Date(new java.util.Date().getTime()));
		condition.setTreatmentHealthFullName("Deepak");
		condition.setTreatmentReceivedText("Ragam");
		
		// Save (Create) ApplicantCondition
		long conditionCount = applicantConditionRepository.count();
		System.out.println("initial applicantcondition count is: " + conditionCount);
		applicantConditionRepository.save(condition);
		System.out.println(condition.toString());
		System.out.println("after save applicantCondition count is: " + applicantConditionRepository.count());
		assertEquals(applicantConditionRepository.count(), conditionCount + 1);
		ApplicantCondition savedCondition = applicantConditionRepository.findOne(condition.getApplicantConditionId());
		assertEquals(condition.getTreatmentHealthFullName(), savedCondition.getTreatmentHealthFullName());
		
		
		//Instantiate Applicant Medication
		
		ApplicantMedication medication = new ApplicantMedication();
		
		medication.setApplicantCondition(condition);
		medication.setMedicationCode("ABC6");
		medication.setOtherMedicationText("he he");
		medication.setPrescriptionDate(new Date(new java.util.Date().getTime()));
		
		// Save (Create) ApplicantMedication
		long medicationCount = applicantMedicationRepository.count();
		System.out.println("initial applicantmedication count is: " + medicationCount);
		applicantMedicationRepository.save(medication);
		System.out.println(medication.toString());
		System.out.println("after save applicantMedication count is: " + applicantMedicationRepository.count());
		assertEquals(applicantMedicationRepository.count(), medicationCount + 1);
		ApplicantMedication savedMedication = applicantMedicationRepository.findOne(medication.getApplicantMedicationId());
		assertEquals(medication.getMedicationCode(), savedMedication.getMedicationCode());
				
		
		// Update ApplicantMedication
		savedMedication.setOtherMedicationText("ha ha");
		applicantMedicationRepository.save(savedMedication);
		ApplicantMedication updatedMedication = applicantMedicationRepository.findOne(savedMedication.getApplicantMedicationId());
		assertNotEquals(medication.getOtherMedicationText(),updatedMedication.getOtherMedicationText());
		
		// Delete Applicant
		System.out.println("Before delete count is: " + applicantMedicationRepository.count());
		applicantMedicationRepository.delete(updatedMedication.getApplicantMedicationId());
		System.out.println("After delete count is: " + applicantMedicationRepository.count());
		assertEquals(applicantMedicationRepository.count(), medicationCount);
	}
}
