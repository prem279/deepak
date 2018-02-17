package com.lmig.ci.lmbc.empr.common.discovery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.lmig.ci.lmbc.empr.common.discovery.ServiceDiscoveryConstants;

@Configuration
@EnableConfigurationProperties(ServiceDiscoverySecurityProperties.class)
@EnableWebSecurity
public class ServiceDiscoverySecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String DEFAULT_NESTED_WILCARD = "/**";

    @Autowired
    ServiceDiscoverySecurityProperties properties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	    .and().httpBasic()
    	    .and().authorizeRequests()
    	        .antMatchers(ServiceDiscoveryConstants.ACTUATOR_ROOT + "/health")
    	            .permitAll()
    	            /*
                .antMatchers(HttpMethod.GET, ServiceDiscoveryConstants.SWAGGER_ROOT + DEFAULT_NESTED_WILCARD)
                    .hasRole(properties.getGuestRole())
  	            .antMatchers(HttpMethod.GET, ServiceDiscoveryConstants.V1_ROOT + DEFAULT_NESTED_WILCARD)
    	            .hasAnyRole(properties.getUserRole(), properties.getAdminRole())
    	        .antMatchers(HttpMethod.POST, ServiceDiscoveryConstants.V1_ROOT + DEFAULT_NESTED_WILCARD)
                    .hasAnyRole(properties.getUserRole())
                .antMatchers(HttpMethod.PUT, ServiceDiscoveryConstants.V1_ROOT + DEFAULT_NESTED_WILCARD)
                    .hasAnyRole(properties.getUserRole())
                .antMatchers(HttpMethod.DELETE, ServiceDiscoveryConstants.V1_ROOT + DEFAULT_NESTED_WILCARD)
                    .hasAnyRole(properties.getUserRole())
                .antMatchers(HttpMethod.GET, ServiceDiscoveryConstants.ACTUATOR_ROOT + DEFAULT_NESTED_WILCARD)
                    .hasAnyRole(properties.getUserRole(), properties.getAdminRole())
                .antMatchers(HttpMethod.POST, ServiceDiscoveryConstants.ACTUATOR_ROOT + DEFAULT_NESTED_WILCARD)
                    .hasRole(properties.getAdminRole())
                .antMatchers(HttpMethod.PUT, ServiceDiscoveryConstants.ACTUATOR_ROOT + DEFAULT_NESTED_WILCARD)
                    .hasRole(properties.getAdminRole())
                .antMatchers(HttpMethod.DELETE, ServiceDiscoveryConstants.ACTUATOR_ROOT + DEFAULT_NESTED_WILCARD)
                    .hasRole(properties.getAdminRole())
                    */
     	    .and().csrf().disable();
	    // @formatter:on
    }

}
