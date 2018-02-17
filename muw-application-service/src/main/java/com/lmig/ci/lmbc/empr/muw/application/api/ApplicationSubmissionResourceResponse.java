/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Feb 10, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author n0159479
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApplicationSubmissionResourceResponse extends ResourceSupport {
	private Integer applicationId;
	private Integer triageOutcome;
	private Boolean applicationAccepted;
	private List<String> invalidReasons;
}
