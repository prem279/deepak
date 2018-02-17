package com.lmig.ci.lmbc.empr.muw.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class MuwAccountResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Value("${management.context-path}")
	private String actuatorBasePath;

	@Autowired
	private MuwAccountSecurityProperties muwAccountSecurityProperties;

	@Autowired
	Environment env;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		if (env.acceptsProfiles(EnvironmentConstants.JUNIT)) {
			resources.stateless(false);
		}
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(actuatorBasePath + "/health").permitAll()
				.antMatchers(actuatorBasePath + "/info").permitAll()
				.antMatchers(HttpMethod.POST, EnvironmentConstants.BASE_PATH + "/employer_configurations/**")
					.hasAnyRole(String.join(",", muwAccountSecurityProperties.getReadWriteAccessGroups()))
				.antMatchers(HttpMethod.PUT, EnvironmentConstants.BASE_PATH + "/employer_configurations/**")
					.hasAnyRole(String.join(",", muwAccountSecurityProperties.getReadWriteAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/employer_configurations/**")
					.hasAnyRole(String.join(",", muwAccountSecurityProperties.getReadAccessGroups()))
				.antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/event/**")
					.hasAnyRole(String.join(",", muwAccountSecurityProperties.getReadAccessEventGroups()));

	}
}