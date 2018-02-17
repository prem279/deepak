package com.lmig.ci.lmbc.empr.muw.application.api;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

@Component
public class CondensedApplicationResourceAssembler extends ResourceAssemblerSupport<Application, CondensedApplicationResource> {

	

	@Autowired
	private Mapper mapper;
	/*	
	@Autowired
    private BeanDefaulter beanDefaulter;
	
	@PostConstruct
    public void setupBeanDefaulter() {
        beanDefaulter.getIgnoredFields().add("applicationId");
	}
      */  
	public CondensedApplicationResourceAssembler() {
		super(CondensedApplicationController.class, CondensedApplicationResource.class);
	}

	@Override
	public CondensedApplicationResource toResource(Application application) {
		
		CondensedApplicationResource CondensedApplicationResource = new CondensedApplicationResource();

		CondensedApplicationResource = mapper.map(application, CondensedApplicationResource.class);		
		
		return CondensedApplicationResource;
	}
	
	public Application toDomainObject(CondensedApplicationResource resource) {	
		Application domainObject = new Application();
		
		// Dozer can do a deep copy of the bean ...
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.Application.class);
		
		//employerDataDefaulter.setDefaultValues(domainObject);
		return domainObject;
	}
}
