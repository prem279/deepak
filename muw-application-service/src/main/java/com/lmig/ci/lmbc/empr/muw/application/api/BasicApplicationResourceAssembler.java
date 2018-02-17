package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.Set;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

@Component
public class BasicApplicationResourceAssembler extends ResourceAssemblerSupport<Application, BasicApplicationResource> {

	@Autowired
	private Mapper mapper;

	public BasicApplicationResourceAssembler() {
		super(ApplicationController.class, BasicApplicationResource.class);
	}

	@Override
	public BasicApplicationResource toResource(Application application) {

		BasicApplicationResource BasicApplicationResource = new BasicApplicationResource();

		BasicApplicationResource = mapper.map(application, BasicApplicationResource.class);

		return BasicApplicationResource;
	}

	public Application toDomainObject(BasicApplicationResource resource) {
		Application domainObject = new Application();

		// Dozer can do a deep copy of the bean ...
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.application.domain.Application.class);

		// employerDataDefaulter.setDefaultValues(domainObject);
		return domainObject;
	}

	public void setProductStatusBasedOnApplicant(BasicApplicationProductResource basicApplicationProductResource,
			Set<Applicant> applicants) {
		for (Applicant applicant : applicants) {
			for (ApplicantProduct applicantProduct : applicant.getApplicantProducts()) {
				if (applicantProduct.getApplicationProductId()
						.equals(basicApplicationProductResource.getApplicationProductId())) {
					String productStatus = evaluateProductStatus(basicApplicationProductResource.getStatusTypeCode(),
							applicantProduct.getStatusTypeCode());
					basicApplicationProductResource.setStatusTypeCode(productStatus);
				}
			}
		}
	}

	private String evaluateProductStatus(String status1, String status2) {
		String returnVal = "UNKNWN";
		status1 = null == status1 ? "" : status1;
		status2 = null == status2 ? "" : status2;
		if (status1.equalsIgnoreCase("APPEAL") || status2.equalsIgnoreCase("APPEAL")) {
			return "APPEAL";
		}
		if (status1.equalsIgnoreCase("CLOSED") || status2.equalsIgnoreCase("CLOSED")) {
			return "CLOSED";
		}
		if (status1.equalsIgnoreCase("DENIED") || status2.equalsIgnoreCase("DENIED")) {
			return "DENIED";
		}
		if (status1.equalsIgnoreCase("APPRVE") || status2.equalsIgnoreCase("APPRVE")) {
			return "APPRVE";
		}
		if (status1.equalsIgnoreCase("ATOCLD") || status2.equalsIgnoreCase("ATOCLD")) {
			return "ATOCLD";
		}
		if (status1.equalsIgnoreCase("REQINF") || status2.equalsIgnoreCase("REQINF")) {
			return "REQINF";
		}
		if (status1.equalsIgnoreCase("MANREV") || status2.equalsIgnoreCase("MANREV")) {
			return "MANREV";
		}
		if (status1.equalsIgnoreCase("ATODEN") || status2.equalsIgnoreCase("ATODEN")) {
			return "ATODEN";
		}
		if (status1.equalsIgnoreCase("ATOAPP") || status2.equalsIgnoreCase("ATOAPP")) {
			return "ATOAPP";
		}

		return returnVal;
	}

}
