package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.service.FullApplicationService;

@BasePathAwareController
@RequestMapping(value = EnvironmentConstants.BASE_PATH)
public class ApplicantController {

	@Autowired
	private FullApplicationService service;

	@Autowired
	private ApplicantResourceAssembler applicantAssembler;

	@RequestMapping(value = "/application_submission/{applicationId}/applicants", method = { RequestMethod.POST })
	ResponseEntity<ApplicantResource> createApplicant(@PathVariable("applicationId") Integer applicationId,
			@RequestBody @Valid ApplicantResource resource) {
		Applicant applicant = applicantAssembler.toDomainObject(resource);

		return service.createApplicant(applicationId, applicant, false);
	}

	@RequestMapping(value = "/application_submission/{applicationId}/applicants", method = { RequestMethod.GET })
	ResponseEntity<List<ApplicantResource>> getApplicants(@PathVariable("applicationId") Integer applicationId) {
		return service.getApplicants(applicationId);
	}

	@RequestMapping(value = "/application_submission/{applicationId}/applicants/{applicantId}", method = {
			RequestMethod.PUT })
	ResponseEntity<ApplicantResource> updateApplicant(@PathVariable("applicationId") Integer applicationId,
			@PathVariable("applicantId") Integer applicantId, @RequestBody @Valid ApplicantResource resource) {
		Applicant applicant = applicantAssembler.toDomainObject(resource);

		return service.updateApplicant(applicationId, applicantId, applicant);
	}

	@RequestMapping(value = "/application_submission/{applicationId}/applicants/{applicantId}", method = {
			RequestMethod.GET })
	ResponseEntity<ApplicantResource> getApplicant(@PathVariable("applicationId") Integer applicationId,
			@PathVariable("applicantId") Integer applicantId) {
		return service.getApplicant(applicationId, applicantId);
	}

	@RequestMapping(value = "/applicants", method = { RequestMethod.GET })
	ResponseEntity<List<ApplicantExtendedResource>> create(
			@RequestParam(value = "ssnMatchByApplicantId", required = true) Integer applicationApplicantId) {

		return service.getPreviousApplicants(applicationApplicantId);
	}

}
