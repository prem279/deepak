/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 11, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.api;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;
import com.lmig.ci.lmbc.empr.muw.account.service.MuwEmployerConfigurationContactService;
import com.lmig.ci.lmbc.empr.muw.account.service.MuwEmployerConfigurationService;
import com.lmig.ci.lmbc.empr.muw.account.service.MuwEmployerConfigurationServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author n0159479
 *
 */
@BasePathAwareController
public class EmployerController {

	@Autowired
	private MuwEmployerConfigurationContactService contactService;

	@Autowired
	private final MuwEmployerConfigurationService service;

	@Autowired
	private EmployerResourceAssembler employerAssembler;

	@Autowired
	public EmployerController(MuwEmployerConfigurationServiceImpl service) {
		this.service = service;
	}

	@Transactional
	@RequestMapping(value = "/employer_configurations", method = {
			RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Creates an Employer Configuration")
	@ApiResponses({ @ApiResponse(code = 201, message = "Employer Created successfully."),
			@ApiResponse(code = 400, message = "Invalid Employer"),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	ResponseEntity<EmployerResource> create(@RequestBody @Valid EmployerResource resource) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<EmployerResource>> errors = validator.validate(resource);

		if (null != errors && !errors.isEmpty()) {
			throw new ConstraintViolationException(errors);
		}

		resource.getEmployerStakeholderLedgerNumber();
		resource.getEmployerStakeholderSerialNumber();
		ResponseEntity<EmployerResource> finalResponse = new ResponseEntity<EmployerResource>(HttpStatus.BAD_REQUEST);

		Employer employerDataIn = employerAssembler.toDomainObject(resource);
		ResponseEntity<EmployerResource> response = service.create(employerDataIn, resource);
		EmployerResource employerResource = response.getBody();

		finalResponse = new ResponseEntity<EmployerResource>(employerResource, response.getStatusCode());

		return (finalResponse);
	}

	@Transactional
	@RequestMapping(value = "/employer_configurations/{employerId}", method = { RequestMethod.PUT })
	@ApiOperation(value = "Creates an Employer Configuration")
	@ApiResponses({ @ApiResponse(code = 201, message = "Employer Created successfully."),
			@ApiResponse(code = 400, message = "Invalid Employer"),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	ResponseEntity<EmployerResource> update(@RequestBody @Valid EmployerResource resource,
			@PathVariable("employerId") Integer employerId) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<EmployerResource>> errors = validator.validate(resource);

		if (null != errors && !errors.isEmpty()) {
			throw new ConstraintViolationException(errors);
		}

		Employer employerDataIn = employerAssembler.toDomainObject(resource);
		ResponseEntity<EmployerResource> response = service.update(employerId, employerDataIn, resource);
		EmployerResource employerResource = response.getBody();

		ResponseEntity<EmployerResource> finalResponse = new ResponseEntity<EmployerResource>(employerResource,
				response.getStatusCode());
		return (finalResponse);
	}

	@RequestMapping(value = "/employer_configurations/{employerId}", method = { RequestMethod.GET })
	@ApiOperation(value = "Retrieves an Employer Configuration for a given employerId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Employer Cconfiguration retrieved successfully."),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })

	ResponseEntity<EmployerResource> getEmployer(@PathVariable("employerId") Integer employerId) {
		ResponseEntity<EmployerResource> response = service.getEmployerConfiguration(employerId);
		EmployerResource employerResource = response.getBody();
		// code check for null value from the service need to be updated
		employerResource.setAddresses(
				contactService.getPrimaryAddress("MUWEMP", employerResource.getEmployerId().toString()).getBody());

		return response;

	}

	@RequestMapping(value = "/employer_configurations", method = { RequestMethod.GET })
	@ApiOperation(value = "Retrieves an Employer Configuration for a given employerId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Employer Cconfiguration retrieved successfully."),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })

	public HttpEntity<EmployerResource> search(@RequestParam("div") String div, @RequestParam("serial") String serial) {
		ResponseEntity<EmployerResource> response = (ResponseEntity<EmployerResource>) service.findByDivAndSerial(div,
				serial);
		EmployerResource employerResource = response.getBody();
		// code check for null value from the service need to be updated
		employerResource.setAddresses(
				contactService.getPrimaryAddress("MUWEMP", employerResource.getEmployerId().toString()).getBody());

		return response;

	}

	@RequestMapping(value = "/event", method = { RequestMethod.GET })
	@ApiOperation(value = "Retrieves all Account events greater than a given account event eventId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Employee event retrieved successfully."),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	ResponseEntity<List<AccountServiceEventLogResource>> findEvents(@RequestParam("eventId") Integer eventId) {
		return service.getAccountEvents(eventId);
	}

}
