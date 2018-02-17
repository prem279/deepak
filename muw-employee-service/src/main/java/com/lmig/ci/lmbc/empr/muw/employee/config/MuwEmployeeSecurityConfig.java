/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Feb 4, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.lmig.ci.devdisc.ldap.BindIDMemberOfAuthoritiesPopulator;
import com.lmig.ci.lmbc.empr.muw.employee.EnvironmentConstants;

@Configuration
@Order(2)
public class MuwEmployeeSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MuwEmployeeSecurityProperties muwEmployeeSecurityProperties;

	@Autowired
	MuwEmployeeSecurityProperties properties;

	@Value("${management.context-path}")
	private String actuatorBasePath;

	@Configuration
	@Profile({ EnvironmentConstants.NOT_JUNIT })
	protected static class EmployeeAuthnConfig extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		MuwEmployeeSecurityProperties properties;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {

			auth.ldapAuthentication().ldapAuthoritiesPopulator(new BindIDMemberOfAuthoritiesPopulator())
					.userDnPatterns(properties.getSsidSearchBase(), properties.getUserSearchBase()).contextSource()
					.url(properties.getLdapUrl());

		}
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.requestMatcher(new BasicRequestMatcher()).httpBasic().and().authorizeRequests()
				.antMatchers(actuatorBasePath + "/health").permitAll().antMatchers(actuatorBasePath + "/info")
				.permitAll()

				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/employees/**")
				.hasAnyRole(String.join(",", muwEmployeeSecurityProperties.getEmployeeReadAccessGroups()))
				.antMatchers(HttpMethod.POST, EnvironmentConstants.BASE_PATH + "/employees/**")
				.hasAnyRole(String.join(",", muwEmployeeSecurityProperties.getEmployeeCreateAccessGroups()))
				.antMatchers(HttpMethod.PUT, EnvironmentConstants.BASE_PATH + "/employees/**")
				.hasAnyRole(String.join(",", muwEmployeeSecurityProperties.getEmployeeUpdateAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/event/**")
				.hasAnyRole(String.join(",", muwEmployeeSecurityProperties.getEmployeeEventAccessGroups())).anyRequest()
				.authenticated().and().csrf().disable();
	}

}
