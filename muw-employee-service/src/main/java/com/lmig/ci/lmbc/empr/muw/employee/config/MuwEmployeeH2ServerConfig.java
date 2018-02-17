/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 17, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lmig.ci.lmbc.empr.muw.employee.EnvironmentConstants;


/**
 * @author n0159479
 *
 */

@Configuration
@Profile({EnvironmentConstants.LOCAL, EnvironmentConstants.JUNIT})
public class MuwEmployeeH2ServerConfig {

	// Web port
	@Value("${h2.web.port:10011}")
	private String h2WebPort;

	/**
	 * Web console for the embedded h2 database.
	 *
	 * Go to http://localhost:10011 and connect to the database
	 * "jdbc:h2:mem:employee_lcl", username "sa", password empty.
	 */
	@Bean
	@ConditionalOnExpression("${h2.web.enabled:false}")
	public Server h2WebServer() throws SQLException {
		try {
			return Server.createWebServer("-web", "-webAllowOthers", "-webPort", h2WebPort).start();
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		return null;
	}
}
