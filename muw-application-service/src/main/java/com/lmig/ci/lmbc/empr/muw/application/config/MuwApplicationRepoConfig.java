/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 4, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;


/**
 * @author n0280705
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.rest")
public class MuwApplicationRepoConfig extends RepositoryRestMvcConfiguration {
	
	@Override
	public RepositoryRestConfiguration config() {
		RepositoryRestConfiguration config = super.config();

        config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.application.domain.Application.class);
        config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct.class);
        config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct.class);
        config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.application.domain.Applicant.class);
        config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantCondition.class);
        config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantMedication.class);
        config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationMedicalFactor.class);
        
        config.setBasePath(EnvironmentConstants.BASE_PATH);
	    return config;
	}
}