/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jul 19, 2016
 */

package com.lmig.ci.lmbc.empr.common.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import com.lmig.reuse.properties.encryption.spring.annotation.EnableEncryptableProperties;
import com.lmig.reuse.properties.encryption.spring.annotation.ResourceBasedKey;

/**
 * @author n0169128
 *
 */
@EnableEncryptableProperties
@EnableEurekaServer
@ResourceBasedKey(profile = {ServiceDiscoveryEnvironment.LOCAL, ServiceDiscoveryEnvironment.JUNIT, ServiceDiscoveryEnvironment.SANDBOX}, location = "classpath:key/emprdiscservice-local-keys.key")
@SpringBootApplication
public class ServiceDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryApplication.class, args);
    }
}
