/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 30, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author N0189985
 *
 */
@Data
@Configuration
public class EurekaClientProperties {

	@Value("${eureka.client.serviceUrl.defaultZone}")
	private String defaultZone;

}