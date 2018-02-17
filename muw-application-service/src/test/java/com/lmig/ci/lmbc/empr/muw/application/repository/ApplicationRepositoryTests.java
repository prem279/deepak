/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 9, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

/**
 * @author n0296170
 *
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct;

public class ApplicationRepositoryTests extends MuwApplicationServiceTests{
	
	@Autowired
	public WebApplicationContext context;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicationProductRepository applicationProductRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;
	
	@Autowired
	private ApplicantProductRepository applicantProductRepository;

	@Test
	public void testContext() {
		assertNotNull(context);
	}
	
	@Test
	public void testApplicationRepository() {
		
		Application app = new Application();
		app.setEmployerId(1);
		app.setEmployerEmployeeId(1);
		app.setApplicationReceivedDate(new Date(new java.util.Date().getTime()));
		app.setFamilySttsChangeEventDate(new Date(new java.util.Date().getTime()));
		app.setReasonCode("asdf");
		app.setSubmissionMethodCode("ONLN");
		
		// Save Application
		long appCount = applicationRepository.count();
		System.out.println("count is"+appCount);
		applicationRepository.save(app);
		System.out.println("count is"+applicationRepository.count());
		assertEquals(applicationRepository.count(), appCount + 1);
		Application savedApplication = applicationRepository.findOne(app.getApplicationId());
		assertEquals(app.getReasonCode(), savedApplication.getReasonCode());
		
		// Update Application
		savedApplication.setReasonCode("fdso");
		applicationRepository.save(savedApplication);
		Application updatedApplication = applicationRepository.findOne(savedApplication.getApplicationId());
		assertNotEquals(app.getReasonCode(), updatedApplication.getReasonCode());
		
		// Delete Application
		applicationRepository.delete(updatedApplication);
		assertEquals(applicationRepository.count(), appCount);
		
	}
	
	@Test
	public void testApplicationProductRepository() {
		
		ApplicationProduct applicationProduct = new ApplicationProduct();
		List<Application> applicationList = applicationRepository.findAll();
		if (null == applicationList || applicationList.isEmpty()) {
			Application app = new Application();
			app.setEmployerId(1);
			app.setEmployerEmployeeId(1);
			app.setApplicationReceivedDate(new Date(new java.util.Date().getTime()));
			app.setFamilySttsChangeEventDate(new Date(new java.util.Date().getTime()));
			app.setReasonCode("asdf");
			app.setSubmissionMethodCode("ONLN");
			applicationRepository.save(app);
			applicationList = applicationRepository.findAll();
		}
		Application application = applicationList.get(0);
		
		applicationProduct.setApplication(application);
		applicationProduct.setProductCode("STD");
		
		// Save Application Product
		long appCount = applicationProductRepository.count();
		System.out.println("count is"+appCount);
		applicationProductRepository.save(applicationProduct);
		System.out.println("count is"+applicationProductRepository.count());
		assertEquals(applicationProductRepository.count(), appCount + 1);
		ApplicationProduct savedApplicationProduct = applicationProductRepository.findOne(applicationProduct.getApplicationProductId());
		assertEquals(applicationProduct.getProductCode(), savedApplicationProduct.getProductCode());
		
		// Update Employee
		savedApplicationProduct.setProductCode("XXX");
		applicationProductRepository.save(savedApplicationProduct);
		ApplicationProduct updatedApplicationProduct = applicationProductRepository.findOne(savedApplicationProduct.getApplicationProductId());
		assertNotEquals(applicationProduct.getProductCode(), updatedApplicationProduct.getProductCode());
		
		// Delete Employee
		applicationProductRepository.delete(updatedApplicationProduct.getApplicationProductId());
		assertEquals(applicationProductRepository.count(), appCount);
		
	}
	
	@Test
	public void testApplicantProductRepository() {
		
		List<Application> applicationList = applicationRepository.findAll();
		if (null == applicationList || applicationList.isEmpty()) {
			Application app = new Application();
			app.setEmployerId(1);
			app.setEmployerEmployeeId(1);
			app.setApplicationReceivedDate(new Date(new java.util.Date().getTime()));
			app.setFamilySttsChangeEventDate(new Date(new java.util.Date().getTime()));
			app.setReasonCode("asdf");
			app.setSubmissionMethodCode("ONLN");
			applicationRepository.save(app);
			applicationList = applicationRepository.findAll();
		}
		Application application = applicationList.get(0);
		
		List<ApplicationProduct> applicationProductList = applicationProductRepository.findAll();
		if (null == applicationProductList || applicationProductList.isEmpty()) {
			ApplicationProduct applicationProduct = new ApplicationProduct();
			applicationProduct.setApplication(application);
			applicationProduct.setProductCode("STD");
			applicationProductRepository.save(applicationProduct);
			applicationProductList = applicationProductRepository.findAll();
		}
		ApplicationProduct applicationProduct = applicationProductList.get(0);
		
		List<Applicant> applicantList = applicantRepository.findAll();
		if(null == applicantList || applicantList.isEmpty())
		{
			Applicant applicant = new Applicant();
			applicant.setApplicantSSN("111223333");
			applicant.setFirstName("Robert");
			applicant.setMiddleInitial("J");
			applicant.setLastName("Vila");
			applicantRepository.save(applicant);
			applicantList = applicantRepository.findAll();
		}
		Applicant applicant = applicantList.get(0);
		
		ApplicantProduct originalApplicantProduct = new ApplicantProduct();
		originalApplicantProduct.setApplicant(applicant);
		//originalApplicantProduct.setApplicationProduct(applicationProduct);
		originalApplicantProduct.setApplicationProductId(applicationProduct.getApplicationProductId());
		originalApplicantProduct.setStatusTypeCode("APPROV");
		originalApplicantProduct.setProductApproveEffectiveDate(new Date(new java.util.Date().getTime()));
		originalApplicantProduct.setStatusDeterminationDate(new Date(new java.util.Date().getTime()));
		originalApplicantProduct.setCurrentProductAmountTypeCode("ASD");

		// Save Application Applicant Product
		long appCount = applicantProductRepository.count();
		System.out.println("count is"+appCount);
		ApplicantProduct savedApplicantProduct = applicantProductRepository.save(originalApplicantProduct);
		System.out.println("count is"+applicantProductRepository.count());
		assertEquals(applicantProductRepository.count(), appCount + 1);
		ApplicantProduct retrievedSavedApplicantProduct = applicantProductRepository.findOne(savedApplicantProduct.getApplicationApplicantProductId());
		assertEquals(originalApplicantProduct.getCurrentProductAmountTypeCode(), savedApplicantProduct.getCurrentProductAmountTypeCode());
		
		// Update Application Applicant Product
		savedApplicantProduct.setCurrentProductAmountTypeCode("ZZZ");
		ApplicantProduct updatedApplicantProduct = applicantProductRepository.save(savedApplicantProduct);
		ApplicantProduct retrievedUpdatedApplicantProduct = applicantProductRepository.findOne(savedApplicantProduct.getApplicationApplicantProductId());
		assertNotEquals(retrievedSavedApplicantProduct.getCurrentProductAmountTypeCode(), retrievedUpdatedApplicantProduct.getCurrentProductAmountTypeCode());
		
		applicantProductRepository.delete(updatedApplicantProduct.getApplicationApplicantProductId());
		
		assertEquals(applicantProductRepository.count(), appCount);
		
	}
}
