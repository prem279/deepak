package com.lmig.ci.lmbc.empr.muw.application.api;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct;

@Component
public class ApplicationSearchApplicationResourceAssembler extends ResourceAssemblerSupport<Application, ApplicationSearchApplicationResource> {

	

	@Autowired
	private Mapper mapper;
	
	public ApplicationSearchApplicationResourceAssembler() {
		super(ApplicationController.class, ApplicationSearchApplicationResource.class);
	}

	@Override
	public ApplicationSearchApplicationResource toResource(Application application) {
		
		ApplicationSearchApplicationResource resource = new ApplicationSearchApplicationResource();

		resource = mapper.map(application, ApplicationSearchApplicationResource.class);		
		
		return resource;
	}
	
	public Application toDomainObject(ApplicationSearchApplicationResource resource) {	
		Application domainObject = new Application();
		
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.Application.class);
		
		return domainObject;
	}
	
	public ApplicationProduct toDomainObject(ApplicationSearchApplicationProductResource resource) {
		
		ApplicationProduct domainObject = new ApplicationProduct();
		
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct.class);
		
		return domainObject;
	}
	
	public Applicant toDomainObject(ApplicationSearchApplicantResource resource)
	{
		Applicant domainObject = new Applicant();
		
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.Applicant.class);
				
		return domainObject;
	}
	
	public ApplicantProduct toDomainObject(ApplicationSearchApplicantProductResource resource)
	{
		ApplicantProduct domainObject = new ApplicantProduct();
		
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct.class);
		
		return domainObject;
	}
}
