package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.service.ApplicationEventService;
import com.lmig.ci.lmbc.empr.muw.application.service.FullApplicationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@BasePathAwareController
@RequestMapping(value = EnvironmentConstants.BASE_PATH)
public class ApplicationController {

	@Autowired
	private FullApplicationService service;

	@Autowired
	private BasicApplicationResourceAssembler basicApplicationAssembler;

	@Autowired
	private ApplicationEventService applicationEventService;

	@RequestMapping(value = "/application_submission/basic_application", method = { RequestMethod.POST })
	@Consumes(MediaType.APPLICATION_JSON)
	ResponseEntity<BasicApplicationResource> createBasicApplication(
			@RequestBody @Valid BasicApplicationResource resource) {
		Application application = basicApplicationAssembler.toDomainObject(resource);

		return service.createBasicApplication(application);
	}

	@RequestMapping(value = "/application_submission/basic_application/{applicationId}", method = { RequestMethod.PUT })
	ResponseEntity<BasicApplicationResource> updateBasicApplication(
			@RequestBody @Valid BasicApplicationResource resource) {
		Application application = basicApplicationAssembler.toDomainObject(resource);

		return service.updateBasicApplication(application);
	}

	@RequestMapping(value = "/application_submission/basic_application/{applicationId}", method = { RequestMethod.GET })
	ResponseEntity<BasicApplicationResource> getApplication(@PathVariable("applicationId") Integer applicationId) {
		return service.getBasicApplication(applicationId);
	}

	@RequestMapping(value = "/application_submission", method = { RequestMethod.GET })
	ResponseEntity<ApplicationSearchResourceResponse> applicationSearch(
			@RequestParam(value = "div", required = true) String div,
			@RequestParam(value = "serial", required = true) String serial,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "employeeSsn", required = false) String employeeSsn,
			@RequestParam(value = "employeeIdNumber", required = false) String employeeIdentificationNumber) {
		return service.getApplications(lastName, div, serial, employeeSsn, employeeIdentificationNumber);
	}

	@RequestMapping(value = "/event", method = { RequestMethod.GET })
	@ApiOperation(value = "Retrieves all Application events greater than a given application event eventId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Application event retrieved successfully."),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	ResponseEntity<List<ApplicationEventResource>> findEvents(@RequestParam("eventId") Integer eventId) {
		return applicationEventService.getApplicationEvents(eventId);
	}

}
