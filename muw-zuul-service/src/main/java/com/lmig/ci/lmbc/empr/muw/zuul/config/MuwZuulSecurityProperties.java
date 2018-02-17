/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 30, 2016
 */

package com.lmig.ci.lmbc.empr.muw.zuul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
/**
 * @author N0189985
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix="security.config")
public class MuwZuulSecurityProperties {
	private String serviceId;
	private String servicePassword;
}