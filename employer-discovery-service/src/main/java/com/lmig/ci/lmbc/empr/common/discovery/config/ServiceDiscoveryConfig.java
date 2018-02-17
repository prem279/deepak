/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 10, 2016
 */

package com.lmig.ci.lmbc.empr.common.discovery.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.MutableDiscoveryClientOptionalArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.discovery.DiscoveryClient.DiscoveryClientOptionalArgs;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * @author n0169128
 *
 */
@Configuration
public class ServiceDiscoveryConfig {

    private Logger logger = LoggerFactory.getLogger(ServiceDiscoveryConfig.class);

    @Bean
    public DiscoveryClientOptionalArgs eurekaClientArgs(@Value("${discovery.client.username}") String username,
            @Value("${discovery.client.password}") String password) {
        logger.debug("Instantiating discovery client optional arguments");
        MutableDiscoveryClientOptionalArgs options = new MutableDiscoveryClientOptionalArgs();
        
        logger.debug("Creating new http basic authentication requester");
        ClientFilter filter = new HTTPBasicAuthFilter(username, password); // obviously encrypt these
        
        logger.debug("Adding the filter to optional arguments");
        options.setAdditionalFilters(Arrays.asList(filter));
        
        return options;
    }

}
