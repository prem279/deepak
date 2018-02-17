/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Feb 27, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author n0189985
 *
 */
@Configuration
public class SwaggerConfig {                                    
	ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("LMB Medical Underwriting Application Service API")
            .description("Manages and provides access to employer EOI Application information")
            .license("Liberty Mutual Insurance")
            .licenseUrl("http://www.libertymutual.com")
            .build();
    }
 
    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.lmig.ci.lmbc.empr.muw.application.api"))
                    .build()
                .apiInfo(apiInfo());
    }

}