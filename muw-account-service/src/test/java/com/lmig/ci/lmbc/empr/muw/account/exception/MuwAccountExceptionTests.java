package com.lmig.ci.lmbc.empr.muw.account.exception;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.lmig.ci.lmbc.empr.muw.account.MuwAccountServiceTests;
import com.lmig.ci.lmbc.empr.muw.account.api.exception.RequestedResourceNotFoundException;
import com.lmig.ci.lmbc.empr.muw.account.service.route.exception.AccountServiceEventLogNotFoundException;

public class MuwAccountExceptionTests extends MuwAccountServiceTests{
	@Test
	public void testExceptions() {
	try {
		throw new RequestedResourceNotFoundException();
	} catch (RequestedResourceNotFoundException e) {
		assertNotNull(e);
	}
	
	try {
		throw new RequestedResourceNotFoundException("Request resource Error Test");
	} catch (RequestedResourceNotFoundException e) {
		assertNotNull(e);
	}
	
	try {
		throw new AccountServiceEventLogNotFoundException("Request resource Error Test");
	} catch (AccountServiceEventLogNotFoundException e) {
		assertNotNull(e);
	}
}
}