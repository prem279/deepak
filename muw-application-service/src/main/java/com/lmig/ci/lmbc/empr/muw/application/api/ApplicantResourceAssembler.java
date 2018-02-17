package com.lmig.ci.lmbc.empr.muw.application.api;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;

@Component
public class ApplicantResourceAssembler extends ResourceAssemblerSupport<Applicant, ApplicantResource> {

	@Autowired
	private Mapper mapper;
	
	public ApplicantResourceAssembler() {
		super(ApplicantController.class, ApplicantResource.class);
	}

	@Override
	public ApplicantResource toResource(Applicant applicant) {
		
		ApplicantResource applicantResource = new ApplicantResource();
		
		applicantResource = mapper.map(applicant, com.lmig.ci.lmbc.empr.muw.application.api.ApplicantResource.class);
		applicantResource.setDecisionStatus(evaluateProductStatus(applicant));
		
		return applicantResource;
	}
	
	public Applicant toDomainObject(ApplicantResource resource) {	
		Applicant domainObject = new Applicant();
		
		// This is only a shallow copy (i.e. it will not copy the properties of the Preferences object within the Employer object
		// BeanUtils.copyProperties(resource, domainObject);
		
		// Dozer can do a deep copy of the bean ...
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.Applicant.class);
		
		//employerDataDefaulter.setDefaultValues(domainObject);
		return domainObject;
	}
	
	public ApplicantExtendedResource toExtendedResource(Applicant applicant) {
		
		ApplicantExtendedResource applicantResource = new ApplicantExtendedResource();

		applicantResource = mapper.map(applicant, com.lmig.ci.lmbc.empr.muw.application.api.ApplicantExtendedResource.class);		
		
		return applicantResource;
	}
	
	public Applicant toDomainObject(ApplicantExtendedResource resource) {	
		Applicant domainObject = new Applicant();
		
		// This is only a shallow copy (i.e. it will not copy the properties of the Preferences object within the Employer object
		// BeanUtils.copyProperties(resource, domainObject);
		
		// Dozer can do a deep copy of the bean ...
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.Applicant.class);
		
		//employerDataDefaulter.setDefaultValues(domainObject);
		return domainObject;
	}
	private String evaluateProductStatus(Applicant applicant) {
		if ((applicant == null) || (applicant.getApplicantProducts() == null)) {
			return null;
		}
		String returnVal = "Complete";
		for (ApplicantProduct applicantProduct : applicant.getApplicantProducts()) {
			switch (applicantProduct.getStatusTypeCode()) {
				case("APPEAL"):
				case("REQINF"):
				case("MANREV"):
				case("REQMED"):
					returnVal="In Process";
				break;
			}
		}
		return returnVal;
	}
}
