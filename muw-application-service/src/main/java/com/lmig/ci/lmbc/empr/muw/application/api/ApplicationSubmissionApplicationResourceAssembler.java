package com.lmig.ci.lmbc.empr.muw.application.api;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

@Component
public class ApplicationSubmissionApplicationResourceAssembler extends ResourceAssemblerSupport<Application, ApplicationSubmissionApplicationResource> {

	

	@Autowired
	private Mapper mapper;
	
	public ApplicationSubmissionApplicationResourceAssembler() {
		super(ApplicationController.class, ApplicationSubmissionApplicationResource.class);
	}

	@Override
	public ApplicationSubmissionApplicationResource toResource(Application application) {
		
		ApplicationSubmissionApplicationResource resource = new ApplicationSubmissionApplicationResource();

		resource = mapper.map(application, ApplicationSubmissionApplicationResource.class);		
		
		return resource;
	}
	
	public Application toDomainObject(ApplicationSubmissionApplicationResource resource) {	
		Application domainObject = new Application();
		
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.Application.class);
		
		return domainObject;
	}
	
	public ApplicationSubmissionResourceResponse toApplicationSubmissionResponseResource(Application application)
	{
		ApplicationSubmissionResourceResponse responseResource = new ApplicationSubmissionResourceResponse();
		if(application != null && application.getApplicationId() != null)
		{
			responseResource.setApplicationId(application.getApplicationId());
		}
		else
		{
			//TODO data integrity violation
		}
		
		return responseResource;
	}
	
	public Applicant toDomainObject(ApplicationSubmissionApplicantResource resource)
	{
		Applicant domainObject = new Applicant();
		
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.Applicant.class);
				
		return domainObject;
	}
	
	public ApplicantProduct toDomainObject(ApplicationSubmissionApplicantProductResource resource)
	{
		ApplicantProduct domainObject = new ApplicantProduct();
		
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct.class);
		
		return domainObject;
	}
}
