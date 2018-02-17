/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 17, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;

/**
 * @author n0159479
 *
 */

@Configuration
@Profile({EnvironmentConstants.LOCAL})
public class MuwApplicationH2ServerConfig {

	// Web port
	@Value("${h2.web.port:10002}")
	private String h2WebPort;

	/**
	 * Web console for the embedded h2 database.
	 *
	 * Go to http://localhost:10002 and connect to the database
	 * "jdbc:h2:mem:application_lcl", username "sa", password empty.
	 */
	@Bean(destroyMethod = "stop")
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
