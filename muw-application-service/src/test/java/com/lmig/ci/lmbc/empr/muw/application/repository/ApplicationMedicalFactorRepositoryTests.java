/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 30, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationMedicalFactor;

/**
 * @author n0282721
 *
 */
public class ApplicationMedicalFactorRepositoryTests extends MuwApplicationServiceTests {
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicationMedicalFactorRepository applicationMedicalFactorsRepository;
	
	@Test
	public void testApplicationMedicalFactorsRepository() {
		
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
		ApplicationMedicalFactor applicationMedicalFactors = new ApplicationMedicalFactor();
		
		applicationMedicalFactors.setApplication(application);
		applicationMedicalFactors.setApplicationRoleCode("EMPLY");
		applicationMedicalFactors.setMedicalFactorQuestionCode("ABSNCE");
		applicationMedicalFactors.setMedicalFactorIndicator(true);
		applicationMedicalFactors.setMedicalFactorText("In the past year, have you been absent from work for 5 or more consecutive days due to illness and/or medical treatment, been home-confined, or made a claim for or received benefits, compensation, or pension for any injury, sickness, disability, or impaired condition?");
		
		// Save (Create) Applicant
		long applicationMedicalFactorsCount = applicationMedicalFactorsRepository.count();
		System.out.println("initial applicant count is: " + applicationMedicalFactorsCount);
		applicationMedicalFactorsRepository.save(applicationMedicalFactors);
		System.out.println("after save applicant count is: " + applicationMedicalFactorsRepository.count());
		assertEquals(applicationMedicalFactorsRepository.count(), applicationMedicalFactorsCount + 1);
		ApplicationMedicalFactor savedApplicationMedicalFactors = applicationMedicalFactorsRepository.findOne(applicationMedicalFactors.getApplicationMedicalFactorId());
		assertEquals(applicationMedicalFactors.getMedicalFactorQuestionCode(), savedApplicationMedicalFactors.getMedicalFactorQuestionCode());
		
		// Update Applicant
		savedApplicationMedicalFactors.setMedicalFactorQuestionCode("HOSPAL");
		applicationMedicalFactorsRepository.save(savedApplicationMedicalFactors);
		ApplicationMedicalFactor updatedApplicationMedicalFactors = applicationMedicalFactorsRepository.findOne(savedApplicationMedicalFactors.getApplicationMedicalFactorId());
		assertNotEquals(applicationMedicalFactors.getMedicalFactorQuestionCode(), updatedApplicationMedicalFactors.getMedicalFactorQuestionCode());
		
		// Delete Applicant
		applicationMedicalFactorsRepository.delete(updatedApplicationMedicalFactors.getApplicationMedicalFactorId());
		assertEquals(applicationMedicalFactorsRepository.count(), applicationMedicalFactorsCount);
	}
}
