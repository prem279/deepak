package com.lmig.ci.lmbc.empr.muw.employee.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -2999857862838699957L;

	public BadRequestException(String message) {
		super(message);
	}
}
