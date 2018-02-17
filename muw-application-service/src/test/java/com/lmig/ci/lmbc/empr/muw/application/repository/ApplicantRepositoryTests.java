/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 30, 2016
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
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

/**
 * @author n0159479
 *
 */
public class ApplicantRepositoryTests extends MuwApplicationServiceTests {
	@Autowired
	public WebApplicationContext context;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;

	@Test
	public void testContext() {
		assertNotNull(context);
	}
	
	@Test
	public void testApplicationRepository() {
		
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
		applicant.setApplication(savedApplication);
		applicant.setFirstName("Parson");
		applicant.setLastName("James");
		applicant.setApplicantSSN("245678913");
		applicant.setBirthCountryCode("USA");
		
		// Save (Create) Applicant
		long applicantCount = applicantRepository.count();
		System.out.println("initial applicant count is: " + applicantCount);
		applicantRepository.saveAndFlush(applicant);
		System.out.println("after save applicant count is: " + applicantRepository.count());
		assertEquals(applicantRepository.count(), applicantCount + 1);
		Applicant savedApplicant = applicantRepository.findOne(applicant.getApplicationApplicantId());
		assertEquals(applicant.getApplicantSSN(), savedApplicant.getApplicantSSN());
		
		// Update Applicant
		savedApplicant.setFirstName("NotParson");
		applicantRepository.saveAndFlush(savedApplicant);
		Applicant updatedApplicant = applicantRepository.findOne(savedApplicant.getApplicationApplicantId());
		assertNotEquals(applicant.getFirstName(), updatedApplicant.getFirstName());
		
		// Delete Applicant
		applicantRepository.delete(updatedApplicant.getApplicationApplicantId());
		assertEquals(applicantRepository.count(), applicantCount);
	}
}
