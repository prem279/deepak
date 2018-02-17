/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jan 22, 2016
 */

package com.lmig.ci.lmbc.empr.muw.zuul;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.lmig.ci.lmbc.empr.muw.zuul.filter.pre.BasicAuthorizationHeaderFilter;
import com.lmig.reuse.properties.encryption.spring.annotation.EnableEncryptableProperties;
import com.lmig.reuse.properties.encryption.spring.annotation.ResourceBasedKey;

/**
 * @Class UserApplication
 * @author Chris Jennings
 * <P>
 * @Description:
 * <p>
 */
//@EnableEncryptableProperties
//@ResourceBasedKey(profile = { EnvironmentConstants.JUNIT, EnvironmentConstants.LOCAL}, location = "classpath:key/muwzuul-local-keys.key")
@EnableDiscoveryClient
@EnableWebSecurity
@EnableZuulProxy
@ResourceBasedKey(profile = { "junit", "local" }, location = "classpath:key/muwzuul-local-keys.key")
@EnableEncryptableProperties
@ComponentScan(basePackages = {"com.lmig.ci.lmbc.empr.auth.util", "com.lmig.ci.lmbc.empr.muw.zuul"})
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class MuwZuulService {

	//private static final Logger logger = LoggerFactory.getLogger(MuwZuulService.class);
	
	//true will set the default fiddler port as a proxy so it can trace the java process
	//if you set this to true you must have fiddle open
	@Value("${fiddler.trace-java}")
	private Boolean traceJava;  
	
	
	public static void main(String[] args) {
		try {
			SpringApplication.run(MuwZuulService.class, args);
		} catch (Exception e) {
			e.printStackTrace();
			
		}


		System.out.println("Starting Spring Boot Application: Zuul Service");		
	}

	@PostConstruct
	protected void setupFiddler(){
		if(traceJava){
			System.setProperty("DproxySet", "true");
			System.setProperty("DproxyHost", "127.0.0.1");
			System.setProperty("DproxyPort", "8888");
			System.setProperty("https.proxyHost", "127.0.0.1");
			System.setProperty("https.proxyPort", "8888");
			System.setProperty("javax.net.ssl.trustStore", "src\\main\\resources\\FiddlerKeystore");
			System.setProperty("javax.net.ssl.trustStoreType", "JKS");
			System.setProperty("javax.net.ssl.trustStorePassword", "mypassword");
		}
	}
	
	@Bean
	public BasicAuthorizationHeaderFilter simpleFilter() {
		return new BasicAuthorizationHeaderFilter();
	}


}
