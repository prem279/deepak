/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 14, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.config;

import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author n0159479
 *
 */
@Configuration
public class MuwAccountDozerConfig {

	@Bean(name = "org.dozer.Mapper")
	public DozerBeanMapper dozerBean() {
		List<String> mappingFiles = Arrays.asList(
				"dozer-global-configuration.xml", 
				"dozer-bean-mappings.xml");

		DozerBeanMapper dozerBean = new DozerBeanMapper();
		dozerBean.setMappingFiles(mappingFiles);
		return dozerBean;
	}

}
