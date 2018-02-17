/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Mar 1, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author n0159479
 *
 */
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

	private static final long serialVersionUID = 3449356177628106899L;
	
	public InternalServerErrorException(String message)
	{
		super(message);
	}
	
	public InternalServerErrorException(String message, StackTraceElement[] stackTraceElement)
	{
		super(message);
		this.setStackTrace(stackTraceElement);
	}
	
}
