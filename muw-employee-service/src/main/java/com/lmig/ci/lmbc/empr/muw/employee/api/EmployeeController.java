package com.lmig.ci.lmbc.empr.muw.employee.api;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmig.ci.lmbc.empr.muw.employee.service.EmployeeEmployerService;
import com.lmig.ci.lmbc.empr.muw.employee.service.EmployeeEventService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@BasePathAwareController
public class EmployeeController {

	@Autowired
	EmployeeEmployerService service;

	@Autowired
	EmployeeEventService employeeEventService;

	@RequestMapping(value = "/employees", method = { RequestMethod.GET })
	@ApiOperation(value = "Retrieves an Employer Configuration for a given employerId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Employer Cconfiguration retrieved successfully."),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })

	public HttpEntity<EmployeeResource> search(@RequestParam(value = "div", required = true) String div,
			@RequestParam(value = "serial", required = true) String serial,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "employeeSsn", required = false) String employeeSsn,
			@RequestParam(value = "employeeIdNumber", required = false) String employeeIdentificationNumber) {

		return service.getEmployeeResource(div, serial, lastName, employeeSsn, employeeIdentificationNumber);

	}

	@RequestMapping(value = "/employees/{employerEmployeeId}", method = { RequestMethod.GET })
	@ApiOperation(value = "Retrieves an Employee for a given employerEmployeeId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Employee retrieved successfully."),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })

	ResponseEntity<EmployeeResource> getEmployerEmployeeId(
			@PathVariable("employerEmployeeId") Integer employerEmployeeId) {
		ResponseEntity<EmployeeResource> response = service.getEmployee(employerEmployeeId);

		return response;

	}

	@RequestMapping(value = "/employees", method = { RequestMethod.POST })
	@ApiOperation(value = "Retrieves an Employer Configuration for a given employerId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Employer Cconfiguration retrieved successfully."),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	ResponseEntity<EmployeeResource> create(@RequestBody @Valid EmployeeResource resource) {

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<EmployeeResource>> errors = validator.validate(resource);

		if (null != errors && !errors.isEmpty()) {
			throw new ConstraintViolationException(errors);
		}
		return service.createEmployee(resource);
	}

	@RequestMapping(value = "/employees/{employerEmployeeId}", method = { RequestMethod.PUT })
	@ApiOperation(value = "Retrieves an Employer Configuration for a given employerId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Employer Cconfiguration retrieved successfully."),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	ResponseEntity<EmployeeResource> update(@RequestBody @Valid EmployeeResource resource,
			@PathVariable("employerEmployeeId") Integer employerEmployeeId) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<EmployeeResource>> errors = validator.validate(resource);

		if (null != errors && !errors.isEmpty()) {
			throw new ConstraintViolationException(errors);
		}
		return service.updateEmployee(resource, employerEmployeeId);
	}

	@RequestMapping(value = "/event", method = { RequestMethod.GET })
	@ApiOperation(value = "Retrieves all Employee events greater than a given employee event eventId")
	@ApiResponses({ @ApiResponse(code = 200, message = "Employee event retrieved successfully."),
			@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
			@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
			@ApiResponse(code = 404, message = "No records found."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	ResponseEntity<List<EmployeeEventResource>> findEvents(@RequestParam("eventId") Integer eventId) {
		return employeeEventService.getEmployeeEvents(eventId);
	}
}
