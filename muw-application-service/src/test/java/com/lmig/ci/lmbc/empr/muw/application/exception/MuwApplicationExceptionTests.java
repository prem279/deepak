/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.exception;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.api.exception.BadRequestException;
import com.lmig.ci.lmbc.empr.muw.application.api.exception.InternalServerErrorException;


public class MuwApplicationExceptionTests extends MuwApplicationServiceTests {
	@Test
	public void testConfigExceptions() {
		try {
			throw new InternalServerErrorException("Error Test");
		} catch (InternalServerErrorException e) {
			assertNotNull(e);
		}
		
		try {
			throw new BadRequestException("Bad Request Error Test");
		} catch (BadRequestException e) {
			assertNotNull(e);
		}

		try {
			throw new InternalServerErrorException("Error Test", new Throwable().getStackTrace());
		} catch (InternalServerErrorException e) {
			assertNotNull(e);
		}
		
		try {
			throw new BadRequestException("Bad Request Error Test", new Throwable().getStackTrace());
		} catch (BadRequestException e) {
			assertNotNull(e);
		}
	}
}

