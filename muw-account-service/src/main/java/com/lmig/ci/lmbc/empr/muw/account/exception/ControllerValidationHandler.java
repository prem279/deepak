/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 24, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lmig.ci.lmbc.empr.common.domain.ErrorMessage;
import com.lmig.ci.lmbc.empr.common.domain.ErrorMessageType;
import com.lmig.ci.lmbc.empr.common.domain.ErrorResource;
import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;

/**
 * @Class ControllerValidationHandler
 * @author n0169128
 *         <P>
 * @Description:
 *               <p>
 */
@ControllerAdvice
public class ControllerValidationHandler {
	
	private final String UNKNOWN_VALUE = "Unknown Value";
	
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
	    		String invalidValue = UNKNOWN_VALUE;
	    		try {
	    			invalidValue = (String) cv.getInvalidValue();
	    		// FYI, the following code will produce a null pointer exception if the constraint exception is due to a null field
	    		// This is why the cast is done above, bc it can be cast to a null String
	    		// String invalidValue = cv.getInvalidValue().toString();
	    		} catch (Exception exception) {
	    			// There was an exception converting the value to String, so leave the value unknown
	    		}
    		
	    		ErrorMessage errorMessage = new ErrorMessage(ErrorMessageType.VALIDATION_ERROR, entity, property, message, invalidValue);
	    		errorMessages.add(errorMessage);
	    	}
    	}
    	
    	er.setErrors(errorMessages);
    	
    	return er;
    }
    
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResource processValidationError(DataIntegrityViolationException ex) {
    	
    	ErrorResource er = new ErrorResource();
    	List<ErrorMessage> errorMessages = new ArrayList<>();
    	
    	if (ex.getMessage().contains(EnvironmentConstants.DIV_SERIAL_ERROR)) {
    		
    		String entity = ex.getClass().getSimpleName();
    		String property = "Div/Serial";
    		String message = "Div/Serial is not unique";
    		String invalidValue = "";
    		
    		ErrorMessage errorMessage = new ErrorMessage(ErrorMessageType.VALIDATION_ERROR, entity, property, message, invalidValue);
    		errorMessages.add(errorMessage);
    		
    	} else if (ex.getMessage().contains(EnvironmentConstants.NO_PRODUCTS_ERROR)) {
    		
    		String entity = ex.getClass().getSimpleName();
    		String property = "Products";
    		String message = "Products are required on the complete endpoint";
    		String invalidValue = "Products are missing";
    		
    		ErrorMessage errorMessage = new ErrorMessage(ErrorMessageType.VALIDATION_ERROR, entity, property, message, invalidValue);
    		errorMessages.add(errorMessage);
    		
    	} else {
    		
    		String entity = ex.getClass().getSimpleName();
    		String property = "Unknown";
    		String message = "Unknow Error has occurred";
    		String invalidValue = "";
    		
    		ErrorMessage errorMessage = new ErrorMessage(ErrorMessageType.ERROR, entity, property, message, invalidValue);
    		errorMessages.add(errorMessage);
    		
    	}
    	
    	er.setErrors(errorMessages);
    	
    	return er;
    }
    
    @ExceptionHandler(RepositoryConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResource processValidationError(RepositoryConstraintViolationException ex) {
    	
    	ErrorResource er = new ErrorResource();
    	List<ObjectError> errors = ex.getErrors().getAllErrors();
    	List<ErrorMessage> errorMessages = new ArrayList<>();
    	
    	if(errors!=null) {
	    	for(ObjectError e : errors) {
	    		
	    		if (e instanceof FieldError) {
		    		String entity = e.getObjectName();
		    		String property = ((FieldError) e).getField();
		    		String message = e.getDefaultMessage();
		    		String invalidValue = UNKNOWN_VALUE;
		    		try {
		    			invalidValue =(String) ((FieldError) e).getRejectedValue();
		    			// FYI, the following code will produce a null pointer exception if the constraint exception is due to a null field
			    		// This is why the cast is done above, bc it can be cast to a null String
			    		// String invalidValue = cv.getInvalidValue().toString();
		    		} catch (Exception exception) {
			    		// There was an exception converting the value to String, so leave the value unknown
		    		}
		    		ErrorMessage errorMessage = new ErrorMessage(ErrorMessageType.VALIDATION_ERROR, entity, property, message, invalidValue);
		    		
		    		errorMessages.add(errorMessage);
	    		} else {
	    			String entity = e.getObjectName();
		    		String property = e.getClass().getSimpleName();
		    		String message = e.getDefaultMessage();
	    		
		    		String invalidValue = UNKNOWN_VALUE;
		    		try {
		    			invalidValue =(String) ((FieldError) e).getRejectedValue();
		    			// FYI, the following code will produce a null pointer exception if the constraint exception is due to a null field
			    		// This is why the cast is done above, bc it can be cast to a null String
			    		// String invalidValue = cv.getInvalidValue().toString();
		    		} catch (Exception exception) {
			    		// There was an exception converting the value to String, so leave the value unknown
		    		}
	    		
		    		ErrorMessage errorMessage = new ErrorMessage(ErrorMessageType.ERROR, entity, property, message, invalidValue);
		    		errorMessages.add(errorMessage);
	    		}
	    	}
    	}
    	
    	er.setErrors(errorMessages);
    	
    	return er;
    }
}
