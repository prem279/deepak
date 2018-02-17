/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Feb 15, 2017
 */

package com.lmig.ci.lmbc.empr.muw.account.config;

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
	private String contactServiceUrl;
	private String contactServiceId;
	private String contactServicePassword;
	private String validValuesUrl;
}
