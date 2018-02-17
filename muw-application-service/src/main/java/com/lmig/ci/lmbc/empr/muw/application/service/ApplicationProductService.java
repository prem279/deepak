/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Feb 22, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmig.ci.lmbc.empr.muw.application.config.MuwApplicationProductConfig;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct;

/**
 * @author n0043003
 *
 */
@Service
public class ApplicationProductService {
	
	@Autowired
	MuwApplicationProductConfig muwApplicationProductConfig;

	public List<String> getEligibleApplicantRoles(ApplicationProduct applicationProduct) {
	//	return MuwApplicationProductConfig.eligibleApplicantRoleMap.get(applicationProduct.getProductCode());
		return muwApplicationProductConfig.productConfiguration.get(applicationProduct.getProductCode());
	}

	/* (non-Javadoc)
	 * @see com.lmig.ci.lmbc.empr.muw.application.service.ApplicationProductService#getEligibleApplicantRoles(com.lmig.ci.lmbc.empr.muw.application.domain.Application)
	 */

	
	/* (non-Javadoc)
	 * @see com.lmig.ci.lmbc.empr.muw.application.service.ApplicationProductService#updateApplicantsProducts(com.lmig.ci.lmbc.empr.muw.application.domain.Application)
	 */

	public boolean updateApplicantsProducts(Application application) {
		boolean changeMade = false;
		//add new products
		if(application.getApplicationProducts() != null && application.getApplicants() != null){
			for (ApplicationProduct applicationProduct : application.getApplicationProducts()) {
				for (Applicant applicant : application.getApplicants()) {
					if(isEligibleForProduct(applicationProduct, applicant) && !hasProduct(applicationProduct, applicant)){
						if(addProduct(applicationProduct, applicant)){
							changeMade = true;
						}
					}
				}
			}
		}

		return changeMade;
	}
	/* (non-Javadoc)
	 * @see com.lmig.ci.lmbc.empr.muw.application.service.ApplicationProductService#isEligibleForProduct(com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct)
	 */
	/**
	 * @param applicationProduct - the product to determine eligibility for
	 * @param applicant - the applicant to determine eligibility for
	 * @return whether or not the applicant can apply for the given product
	 */

	public boolean isEligibleForProduct(ApplicationProduct applicationProduct, Applicant applicant) {
		List <String> eligibleApplicantRoles = getEligibleApplicantRoles(applicationProduct);

		//product code not found in list
		if(eligibleApplicantRoles == null){
			return false;
		}
		else{
			return eligibleApplicantRoles.contains(applicant.getApplicationRoleCode());
		}
	}
	/**
	 * @param applicationProduct - The product to check for
	 * @param applicant - The applicant to check to see if has product
	 * @return true if applicant already has given product
	 */
	public boolean hasProduct(ApplicationProduct applicationProduct, Applicant applicant) {
		boolean hasProduct = false;

		if(applicationProduct == null){
			return false;
		}

		if(applicant.getApplicantProducts() !=null){
			for (ApplicantProduct applicantProduct : applicant.getApplicantProducts()) {
				if(applicationProduct.getApplicationProductId().equals(applicantProduct.getApplicationProductId())){
					hasProduct = true;
				}
			}
		}

		return hasProduct;
	}

	/**
	 * @param applicationProduct - the product to add
	 * @param applicant - the applicant to add the product to
	 * @return true if product successfully added
	 * A new ApplicantProduct will be created from the given applicationProduct and added to the applicant products collection
	 */
	public boolean addProduct(ApplicationProduct applicationProduct, Applicant applicant) {
		if(applicationProduct == null){
			return false;
		}

		ApplicantProduct applicantProduct = new ApplicantProduct(applicant, applicationProduct);

		if(applicant.getApplicantProducts() == null){
			applicant.setApplicantProducts(new ArrayList<ApplicantProduct>());
		}
		
		return applicant.getApplicantProducts().add(applicantProduct);
	}
}


