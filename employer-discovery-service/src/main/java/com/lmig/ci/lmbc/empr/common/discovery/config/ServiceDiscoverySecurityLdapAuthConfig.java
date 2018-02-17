package com.lmig.ci.lmbc.empr.common.discovery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

import com.lmig.ci.devdisc.ldap.BindIDMemberOfAuthoritiesPopulator;
import com.lmig.ci.lmbc.empr.common.discovery.ServiceDiscoveryEnvironment;

@Configuration
@EnableConfigurationProperties(ServiceDiscoverySecurityProperties.class)
@EnableGlobalAuthentication
@Profile({ ServiceDiscoveryEnvironment.NOT_JUNIT })
public class ServiceDiscoverySecurityLdapAuthConfig {

    @Autowired
    ServiceDiscoverySecurityProperties properties;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
            .ldapAuthentication()
            .ldapAuthoritiesPopulator(new BindIDMemberOfAuthoritiesPopulator())
	        .userDnPatterns(properties.getSsidSearchBase(),
	                properties.getUserSearchBase())
	        .contextSource().url(properties.getLdapUrl());
        // @formatter:on
    }

}
