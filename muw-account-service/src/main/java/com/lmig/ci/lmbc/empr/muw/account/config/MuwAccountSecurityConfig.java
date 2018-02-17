package com.lmig.ci.lmbc.empr.muw.account.config;

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
import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;

@Configuration
@Order(2) // this comes before oAuth2
// @EnableConfigurationProperties(MuwAccountSecurityProperties.class)
public class MuwAccountSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${management.context-path}")
	private String actuatorBasePath;

	@Autowired
	MuwAccountSecurityProperties properties;

	@Configuration
	@Profile({ EnvironmentConstants.NOT_JUNIT })
	protected static class ApplicationAuthnConfig extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		MuwAccountSecurityProperties properties;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {

			auth.ldapAuthentication().ldapAuthoritiesPopulator(new BindIDMemberOfAuthoritiesPopulator())
					.userDnPatterns(properties.getSsidSearchBase(), properties.getUserSearchBase())
					// .groupSearchBase(properties.getGroupSearchBase())
					.contextSource().url(properties.getLdapUrl());

		}

	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.requestMatcher(new BasicRequestMatcher()).httpBasic().and().authorizeRequests()
				.antMatchers(actuatorBasePath + "/health").permitAll()
				.antMatchers(HttpMethod.POST, EnvironmentConstants.BASE_PATH + "/employer_configurations/**")
				.hasAnyRole(String.join(",", properties.getReadWriteAccessGroups()))
				.antMatchers(HttpMethod.PUT, EnvironmentConstants.BASE_PATH + "/employer_configurations/**")
				.hasAnyRole(String.join(",", properties.getReadWriteAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/employer_configurations/**")
				.hasAnyRole(String.join(",", properties.getReadAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/event/**")
				.hasAnyRole(String.join(",", properties.getReadAccessEventGroups())).anyRequest().denyAll().and().csrf().disable();

	}

}
