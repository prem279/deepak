package com.lmig.ci.lmbc.empr.muw.application.config;

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
import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;

@Configuration
@Order(2) // this comes before oAuth2
public class MuwApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MuwApplicationSecurityProperties muwApplicationSecurityProperties;

	@Value("${management.context-path}")
	private String actuatorBasePath;

	@Autowired
	MuwApplicationSecurityProperties properties;

	@Configuration
	@Profile({ EnvironmentConstants.NOT_JUNIT })
	protected static class ApplicationAuthnConfig extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		MuwApplicationSecurityProperties properties;

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
				.permitAll().antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/products/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationReadAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/condensed_applications/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getEmployerReadAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/application_submissions/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getEmployerReadAccessGroups()))
				.antMatchers(HttpMethod.POST, EnvironmentConstants.BASE_PATH + "/application_submissions/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationCreateAccessGroups()))
				.antMatchers(HttpMethod.PUT, EnvironmentConstants.BASE_PATH + "/application_submissions/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationCreateAccessGroups()))
				.antMatchers(HttpMethod.POST,
						EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationCreateAccessGroups()))
				.antMatchers(HttpMethod.PUT,
						EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationUpdateAccessGroups()))
				.antMatchers(HttpMethod.GET,
						EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationReadAccessGroups()))
				.antMatchers(HttpMethod.POST,
						EnvironmentConstants.BASE_PATH + "/application_submission/**/applicants/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicantCreateAccessGroups()))
				.antMatchers(HttpMethod.GET,
						EnvironmentConstants.BASE_PATH + "/application_submission/**/applicants/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicantReadAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/application_submission/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationReadAccessGroups()))
				.antMatchers(HttpMethod.PUT,
						EnvironmentConstants.BASE_PATH + "/application_submission/**/applicants/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicantUpdateAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/applicants/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicantReadAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/event/**")
				.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationEventAccessGroups()))
				.anyRequest().denyAll().and().csrf().disable();

	}

}
