package com.lmig.ci.lmbc.empr.muw.application.api;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

@Component
public class ApplicationResourceAssembler extends ResourceAssemblerSupport<Application, ApplicationResource> {

	@Autowired
	private Mapper mapper;
	
	public ApplicationResourceAssembler() {
		super(ApplicationController.class, ApplicationResource.class);
	}

	@Override
	public ApplicationResource toResource(Application application) {
		
		ApplicationResource applicationResource = new ApplicationResource();

		applicationResource = mapper.map(application, com.lmig.ci.lmbc.empr.muw.application.api.ApplicationResource.class);		
		
		return applicationResource;
	}
	
	public Application toDomainObject(ApplicationResource resource) {	
		Application domainObject = new Application();
		
		// This is only a shallow copy (i.e. it will not copy the properties of the Preferences object within the Employer object
		// BeanUtils.copyProperties(resource, domainObject);
		
		// Dozer can do a deep copy of the bean ...
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.Application.class);
		
		//employerDataDefaulter.setDefaultValues(domainObject);
		return domainObject;
	}
	
}
