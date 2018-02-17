package com.lmig.ci.lmbc.empr.muw.application.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicantRepository;


public class ApplicantProductsDecisionStatusTests extends MuwApplicationServiceTests {
	@Autowired
	ApplicantResourceAssembler applicantResourceAssembler;
	@Autowired
	private ApplicantRepository applicantRepository;
	
	@Transactional
	@Test
	public void testGetApplicantProductsDecisionStatusInProcess() {
	
		
		
		//Get Applicant decision details details for applicant 1
		Applicant applicant = applicantRepository.findOne(1);
		// Instantiate new application product for application 1

		ApplicantResource applicantResource = applicantResourceAssembler.toResource(applicant);
		assertEquals(applicantResource.getDecisionStatus(), "In Process");
		
				
	}
	@Transactional
	@Test
public void testGetApplicantProductsDecisionStatusComplete() {
	
		
		
		//Get Applicant decision details details for applicant 3
		Applicant applicant = applicantRepository.findOne(3);
		
		ApplicantResource applicantResource = applicantResourceAssembler.toResource(applicant);
		assertEquals(applicantResource.getDecisionStatus(), "Complete");
		
		
		
	}
	
	

}
