/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 19, 2016
 */

package com.lmig.ci.lmbc.empr.common.domain;

import java.util.List;

import lombok.Data;

/**
 * @author n0159479
 *
 */
@Data
public class ErrorResource {
	
	private List<ErrorMessage> errors;

}
