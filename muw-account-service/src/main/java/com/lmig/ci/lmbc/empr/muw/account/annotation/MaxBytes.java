package com.lmig.ci.lmbc.empr.muw.account.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Constraint(validatedBy = MaxBytesValidator.class)
public @interface MaxBytes {

	String message() default "Field must be less than specified bytes";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int max();
}
