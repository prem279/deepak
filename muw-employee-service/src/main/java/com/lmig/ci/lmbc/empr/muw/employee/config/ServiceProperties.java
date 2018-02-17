/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Feb 15, 2017
 */

package com.lmig.ci.lmbc.empr.muw.employee.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author n0296170
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "service")
public class ServiceProperties {
	private String employerServiceUrl;
	private String employerServiceId;
	private String employerServicePassword;
	private String contactServiceUrl;
	
}
