package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.application.service.ApplicationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@BasePathAwareController
@RequestMapping(value = EnvironmentConstants.BASE_PATH)
public class CondensedApplicationController {

	@Autowired
	private ApplicationService service;
	
	@RequestMapping(path = "/condensed_applications", method = RequestMethod.GET)
	@ApiOperation(value = "Creates an Employer record and any associated Preferences or EOI Products")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid Request"),
					@ApiResponse(code = 401, message = "User is not authorized to perform this request."),
					@ApiResponse(code = 403, message = "User is not permitted to perform this request."),
					@ApiResponse(code = 404, message = "No records found."),
					@ApiResponse(code = 500, message = "Internal server error.") })
	public HttpEntity<List<CondensedApplicationResource>> search(@RequestParam("employerId") int employerId, 
			                                                   @RequestParam("applicationStatus") String applicationStatus) {
  
	   return service.findByEmployerIdAndApplicationStatus(employerId, applicationStatus);
	  
		
	}
	
}
