/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Feb 4, 2016
 */

package com.lmig.ci.lmbc.empr.muw.zuul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;;


@Configuration  
@EnableWebSecurity  
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MuwZuulSecurityConfig extends WebSecurityConfigurerAdapter  {

	/*@Autowired  
	private MuwAccountSecurityProperties securityProperties;  */

	//private String basePath = EnvironmentConstants.BASE_PATH;  
	
	@Value("${management.context-path}")
	private String actuatorBasePath;  
	
	@Override
	public void configure(HttpSecurity http) throws Exception {		
		http.antMatcher("/**").authorizeRequests()
		.antMatchers( "/login**", "/app/**", "/css/**", "/js/**", "/node_modules/**", "/assets/**", "/fonts/**", "/browser-sync/**")
			.permitAll()
        .antMatchers(actuatorBasePath + "/health")
        	.permitAll()
        .antMatchers(actuatorBasePath + "/info")
        	.permitAll()
        .antMatchers("/api/auth/isauthenticated")
        	.permitAll()
        .antMatchers("/api/auth/userdetails")
        	.authenticated()
        .antMatchers("/muwui/**")
        	.authenticated()
        .anyRequest()
			.authenticated().and().logout().logoutSuccessUrl("/").permitAll().and().csrf()
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}  
}
