/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 4, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.account.domain.StakeholderLedgerSerialNumberPkConverter;

/**
 * @author n0280705
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.rest")
public class MuwAccountRepoConfig extends RepositoryRestMvcConfiguration {

	@Override
	public RepositoryRestConfiguration config() {
		return super.config();
	}

	@Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.account.domain.Employer.class);
		config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.account.domain.EoiProduct.class);
		config.exposeIdsFor(com.lmig.ci.lmbc.empr.muw.account.domain.Preference.class);
		config.setBasePath(EnvironmentConstants.BASE_PATH);
	}

	@Bean
	public StakeholderLedgerSerialNumberPkConverter stakeholderLedgerSerialNumberPkConverter() {
		return new StakeholderLedgerSerialNumberPkConverter();
	}

	@Override
	protected void configureConversionService(ConfigurableConversionService conversionService) {
		conversionService.addConverter(stakeholderLedgerSerialNumberPkConverter());
	}

}