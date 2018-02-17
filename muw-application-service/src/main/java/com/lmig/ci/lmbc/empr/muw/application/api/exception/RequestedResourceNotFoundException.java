package com.lmig.ci.lmbc.empr.muw.application.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class RequestedResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2999857862838699957L;

	public RequestedResourceNotFoundException(String message) {
		super(message);
	}
}
