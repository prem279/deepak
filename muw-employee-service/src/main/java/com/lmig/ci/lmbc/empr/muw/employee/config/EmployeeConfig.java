package com.lmig.ci.lmbc.empr.muw.employee.config;

import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

import com.lmig.ci.lmbc.empr.common.util.BeanDefaulter;

@Configuration
@EnableHypermediaSupport(type = { HypermediaType.HAL })
public class EmployeeConfig {
	
	@Bean(name = "employeeDataDefaulter")
    public BeanDefaulter employeeDataDefaulter() throws ParseException {
    	return new BeanDefaulter();
    }

}
