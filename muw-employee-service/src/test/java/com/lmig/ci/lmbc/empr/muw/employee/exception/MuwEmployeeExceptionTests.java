package com.lmig.ci.lmbc.empr.muw.employee.exception;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.lmig.ci.lmbc.empr.muw.employee.MuwEmployeeServiceTests;
import com.lmig.ci.lmbc.empr.muw.employee.api.exception.BadRequestException;
import com.lmig.ci.lmbc.empr.muw.employee.api.exception.RequestedResourceNotFoundException;


public class MuwEmployeeExceptionTests extends MuwEmployeeServiceTests {
	@Test
	public void testExceptions() {
		
		try {
			throw new BadRequestException("Bad Request Error Test");
		} catch (BadRequestException e) {
			assertNotNull(e);
		}
		
		try {
			throw new RequestedResourceNotFoundException("Request resource Error Test");
		} catch (RequestedResourceNotFoundException e) {
			assertNotNull(e);
		}
	}
}
