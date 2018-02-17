/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 24, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lmig.ci.lmbc.empr.common.domain.ErrorMessage;
import com.lmig.ci.lmbc.empr.common.domain.ErrorMessageType;
import com.lmig.ci.lmbc.empr.common.domain.ErrorResource;

/**
 * @Class ControllerValidationHandler
 * @author n0169128
 *         <P>
 * @Description:
 *               <p>
 */
@ControllerAdvice
public class ControllerValidationHandler {
    @Autowired
    private MessageSource msgSource;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResource processValidationError(ConstraintViolationException ex) {
    	
    	ErrorResource er = new ErrorResource();
    	Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    	List<ErrorMessage> errorMessages = new ArrayList<>();
    	
    	if(violations!=null) {
	    	for(ConstraintViolation<?> cv : violations) {
	    		
	    		String entity = cv.getRootBeanClass().getSimpleName();
	    		String property = cv.getPropertyPath().toString();
	    		String message = cv.getMessage();
	    		
	    		String invalidValue = (String) cv.getInvalidValue();
	    		// FYI, the following code will produce a null pointer exception if the constraint exception is due to a null field
	    		// This is why the cast is done above, bc it can be cast to a null String
	    		// String invalidValue = cv.getInvalidValue().toString();
    		
	    		ErrorMessage errorMessage = new ErrorMessage(ErrorMessageType.VALIDATION_ERROR, entity, property, message, invalidValue);
	    		
	    		errorMessages.add(errorMessage);
	    	}
    	}
    	
    	er.setErrors(errorMessages);
    	
    	return er;
    }
}
