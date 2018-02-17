package com.lmig.ci.lmbc.empr.muw.account.annotation;

import java.nio.charset.Charset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxBytesValidator implements ConstraintValidator<MaxBytes, String> {

	private int max;

	@Override
	public void initialize(MaxBytes constraintAnnotation) {
		this.max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value.getBytes(Charset.forName("UTF-8")).length <= max;
	}

}
