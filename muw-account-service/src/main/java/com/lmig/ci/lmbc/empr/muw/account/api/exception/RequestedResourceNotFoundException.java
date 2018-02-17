package com.lmig.ci.lmbc.empr.muw.account.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Could not locate requested resource")
public class RequestedResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2999857862838699957L;

	public RequestedResourceNotFoundException(String message) {
		super(message);
	}
	public RequestedResourceNotFoundException() {
		super();
	}
}
