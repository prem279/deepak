/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 4, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.lmig.ci.lmbc.empr.muw.employee.EnvironmentConstants;


/**
 * @author n0280705
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.rest")
public class RepoConfig extends RepositoryRestMvcConfiguration {
 
	@Override
	public RepositoryRestConfiguration config() {
		RepositoryRestConfiguration config = super.config();
		config.setBasePath(EnvironmentConstants.BASE_PATH);
	    return config;
	}
}