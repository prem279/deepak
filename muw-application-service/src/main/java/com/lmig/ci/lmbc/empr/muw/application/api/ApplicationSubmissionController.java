/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Feb 13, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.application.service.FullApplicationService;

/**
 * @author n0159479
 *
 */
@BasePathAwareController
@RequestMapping(value = EnvironmentConstants.BASE_PATH)
public class ApplicationSubmissionController {

	@Autowired
	private FullApplicationService service;

	@RequestMapping(value = "/application_submissions", method = { RequestMethod.POST })
	ResponseEntity<ApplicationSubmissionResourceResponse> createApplication(
			@RequestBody @Valid ApplicationSubmissionResource resource) {

		return service.createApplicationSubmission(resource);
	}

	@RequestMapping(value = "/application_submissions/{applicationId}", method = { RequestMethod.PUT })
	ResponseEntity<ApplicationSubmissionResourceResponse> updateApplication(
			@PathVariable("applicationId") Integer applicationId,
			@RequestBody @Valid ApplicationSubmissionResource resource) {

		return service.updateApplicationSubmission(applicationId, resource);
	}

	@RequestMapping(value = "/application_submissions/{applicationId}", method = { RequestMethod.GET })
	ResponseEntity<ApplicationSubmissionResource> getApplication(@PathVariable("applicationId") Integer applicationId) {

		return service.getApplication(applicationId);
	}

}
