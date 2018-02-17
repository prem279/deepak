package com.lmig.ci.lmbc.empr.muw.application.api;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationEvent;

@Component
public class ApplicationEventResourceAssembler extends ResourceAssemblerSupport<ApplicationEvent, ApplicationEventResource> {

	@Autowired
	private Mapper mapper;
	
	public ApplicationEventResourceAssembler() {
		super(ApplicationController.class, ApplicationEventResource.class);
	}

	@Override
	public ApplicationEventResource toResource(ApplicationEvent applicationEvent) {
		
		ApplicationEventResource applicationEventResource = new ApplicationEventResource();

		applicationEventResource = mapper.map(applicationEvent, com.lmig.ci.lmbc.empr.muw.application.api.ApplicationEventResource.class);		
		
		return applicationEventResource;
	}
	
	public ApplicationEvent toDomainObject(ApplicationResource resource) {	
		ApplicationEvent domainObject = new ApplicationEvent();
		
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationEvent.class);
		
		return domainObject;
	}
	
}
