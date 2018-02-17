package com.lmig.ci.lmbc.empr.muw.application.config;

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

import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class MuwApplicationResourceServerConfig extends ResourceServerConfigurerAdapter{
	@Value("${management.context-path}")
	private String actuatorBasePath;  

	@Autowired
	private MuwApplicationSecurityProperties muwApplicationSecurityProperties;
	
	 @Autowired
	 Environment env;
	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		if(env.acceptsProfiles(EnvironmentConstants.JUNIT)){
			resources.stateless(false);
		}
    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
        .antMatchers(actuatorBasePath + "/health")
    		.permitAll()
    	.antMatchers(actuatorBasePath + "/info")
    		.permitAll()
        .antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/products/**")
        	.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationReadAccessGroups()))
        .antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/condensed_applications/**")
        	.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getEmployerReadAccessGroups()))
        .antMatchers(HttpMethod.POST, EnvironmentConstants.BASE_PATH + "/application_submissions/**")
        	.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationCreateAccessGroups()))
        .antMatchers(HttpMethod.POST, EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/**")
    		.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationCreateAccessGroups()))
        .antMatchers(HttpMethod.PUT, EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/**")
        	.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationUpdateAccessGroups()))
        .antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/**")
        	.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationReadAccessGroups()))
        .antMatchers(HttpMethod.POST, EnvironmentConstants.BASE_PATH + "/application_submission/**/applicants/**")
        	.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicantCreateAccessGroups()))
        .antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/application_submission/**/applicants/**")
        	.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicantReadAccessGroups()))
        .antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/application_submission/**")
        	.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicationReadAccessGroups()))
        .antMatchers(HttpMethod.PUT, EnvironmentConstants.BASE_PATH + "/application_submission/**/applicants/**")
        	.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicantUpdateAccessGroups()))
        .antMatchers(HttpMethod.GET, EnvironmentConstants.BASE_PATH + "/applicants/**")
    		.hasAnyRole(String.join(",", muwApplicationSecurityProperties.getApplicantReadAccessGroups()));
	}
}