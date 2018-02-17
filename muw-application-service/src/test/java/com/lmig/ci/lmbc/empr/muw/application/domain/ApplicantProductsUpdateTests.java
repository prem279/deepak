package com.lmig.ci.lmbc.empr.muw.application.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicantProductRepository;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicantRepository;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicationProductRepository;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicationRepository;
import com.lmig.ci.lmbc.empr.muw.application.service.ApplicationProductService;

public class ApplicantProductsUpdateTests extends MuwApplicationServiceTests {

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
	
	@Autowired
	ApplicationProductService applicationProductService;
	
	
	@Test
	public void testContext() {
		assertNotNull(context);
	}
	
	@Transactional
	@Test
	public void testUpdateApplicationProducts() {
	
		
		
		// Get application 1 from h2
		// Application 1 has BSL, STD, LTD application products
		// Application 1 has emply, spuse applicants
		List<Application> applications = applicationRepository.findByEmployerEmployeeId(1);
		Application application = applications.get(0);
		
		// Instantiate new application product for application 1
		ApplicationProduct applicationProduct = new ApplicationProduct();
		applicationProduct.setApplication(application);
		applicationProduct.setProductCode("OFL");
		
		Set<ApplicationProduct> applicationProducts = application.getApplicationProducts();
		
		applicationProducts.add(applicationProduct);
		application.setApplicationProducts(applicationProducts);
		
		application = applicationRepository.saveAndFlush(application);
		applicationProduct = applicationProductRepository.findOneByApplicationApplicationIdAndProductCode(application.getApplicationId(), "OFL");
		
		// applicationProductRepository.save(applicationProduct);
		
		
		// Update applicant products to reflect addition of OFL application product 

		applicationProductService.updateApplicantsProducts(application);
		
		application = applicationRepository.saveAndFlush(application);

		Applicant spouseApplicant = applicantRepository.getOne(3);
		
		// Spouse applicant should have had OFL product added
		// Retrieve last product of spouse applicant for application
		// Get child product, compare applicationProductId on applicant to that from application 
		List<ApplicantProduct> spouseProducts = spouseApplicant.getApplicantProducts();
		ApplicantProduct testProduct = spouseProducts.get(spouseProducts.size() - 1);
		assertEquals(applicationProduct.getApplicationProductId(), testProduct.getApplicationProductId());
		
		
		// Add new child applicant
		Applicant childApplicant = new Applicant();
		childApplicant.setApplication(application);
		childApplicant.setFirstName("Johnny");
		childApplicant.setLastName("Villa");
		childApplicant.setBirthCityName("Boston");
		childApplicant.setBirthStateProvinceCode("MA");
		childApplicant.setBirthDate(new Date(new java.util.Date().getTime()));
		childApplicant.setGenderCode("M");
		childApplicant.setHeightInchQuantity(13);
		childApplicant.setWeightQuantity(9);
		childApplicant.setApplicationRoleCode("CHILD");
		
		childApplicant = applicantRepository.saveAndFlush(childApplicant);
		
		Set<Applicant> applicationApplicants = application.getApplicants();
		applicationApplicants.add(childApplicant);
		application = applicationRepository.saveAndFlush(application);
	
		// Update application applicant products should add OFL product to child applicant

		applicationProductService.updateApplicantsProducts(application);
		application = applicationRepository.saveAndFlush(application);
		Long applicantProductsCount = applicantProductRepository.count();
		
		// Get child product, compare applicationProductId on applicant to that from application 
		List<ApplicantProduct> childProducts = childApplicant.getApplicantProducts();
		ApplicantProduct testChildProduct = childProducts.get(childProducts.size() - 1);
		assertEquals(applicationProduct.getApplicationProductId(), testChildProduct.getApplicationProductId());
	
		// Remove child applicant from application applicants list
		applicationApplicants = application.getApplicants();
		applicationApplicants.remove(childApplicant);
		application = applicationRepository.saveAndFlush(application);
	
		// Compare count of applicant products before and after deleting applicant;
		assertEquals(applicantProductsCount - 1, applicantProductRepository.count());
	}
}
