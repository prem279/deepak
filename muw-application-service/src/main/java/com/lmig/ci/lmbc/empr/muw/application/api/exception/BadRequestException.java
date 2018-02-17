package com.lmig.ci.lmbc.empr.muw.application.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 5024222293148725055L;

	public BadRequestException(String message) {
		super(message);
	}
	
	public BadRequestException(String message, StackTraceElement[] stackTraceElement)
	{
		super(message);
		this.setStackTrace(stackTraceElement);
	}
}
