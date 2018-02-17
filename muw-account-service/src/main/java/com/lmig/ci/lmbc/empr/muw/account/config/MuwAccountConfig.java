/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 11, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.config;

import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

import com.lmig.ci.lmbc.empr.common.util.BeanDefaulter;

/**
 * @author n0159479
 *
 */

@Configuration
@EnableHypermediaSupport(type = { HypermediaType.HAL })
public class MuwAccountConfig {

    @Bean(name = "employerDataDefaulter")
    public BeanDefaulter employerDataDefaulter() throws ParseException {
    	return new BeanDefaulter();
    }
}
