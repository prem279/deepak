package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lmig.ci.lmbc.empr.muw.application.api.CondensedApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.exception.RequestedResourceNotFoundException;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicationRepository;

@Service
public class ApplicationServiceImpl implements ApplicationService{
	
	@Autowired
	private ApplicationRepository applications;

	
	@Override
	public HttpEntity<List<CondensedApplicationResource>> findByEmployerIdAndApplicationStatus(int employerId ,String applicationStatus) {
		List<Application> found = applications.findByEmployerIdAndApplicationStatus(employerId, applicationStatus);
		
		if (found != null && found.size()>0) {
			
			return ResponseEntity.ok(getApplicationServiceResourcesFromApplications(found));			
			
		}
	
		throw new RequestedResourceNotFoundException("Unable to find requested applications");

	
	}

	private List<CondensedApplicationResource> getApplicationServiceResourcesFromApplications(List<Application> appln) {
		Iterator<Application> applicationIterator = appln.iterator();
		Iterator<Applicant> applicantIterator = null;
		Iterator<ApplicantProduct> applicantProductIterator = null;

		List<CondensedApplicationResource> results = new ArrayList<CondensedApplicationResource>();
		int applicationId = 0;
		Application app = null;
		Applicant applnt = null;
		ApplicantProduct applntprod = null;
		Set<Applicant> applicantList = null;
		List<ApplicantProduct> applicantProductList = null;
		CondensedApplicationResource car = null;
		CondensedApplicationResource compr = null;
		while (applicationIterator.hasNext()) {
			app = applicationIterator.next();
			applicationId = app.getApplicationId();
			applicantList = app.getApplicants();
			compr = null;
			car = null;
			if (applicantList != null && applicantList.size() > 0) {
				applicantIterator = applicantList.iterator();
				while (applicantIterator.hasNext()) {
					applnt = applicantIterator.next();
					applicantProductList = applnt.getApplicantProducts();
					if (applicantProductList != null && applicantProductList.size() > 0) {
						applicantProductIterator = applicantProductList.iterator();
						while (applicantProductIterator.hasNext()) {
							applntprod = applicantProductIterator.next();
							car = new CondensedApplicationResource();
							car.setApplicationId(applicationId);
							car.setEmployerEmployeeId(app.getEmployerEmployeeId());
							car.setEmployeeFirstName("First");
							car.setEmployeeLastName("Last" + UUID.randomUUID().toString().substring(0, 7));
							// asr.setStatusTypeCode(applntprod.getStatusTypeCode());
							car.setLatestStatusDate(applntprod.getStatusDeterminationDate());

							if (compr == null)
								compr = car;
							else if (compr.getLatestStatusDate().before(car.getLatestStatusDate()))
								compr = car;

							// results.add(asr);

						}

					}

				}
			}
			if (compr != null)
				results.add(compr);

		}
		return results;

	}
}
