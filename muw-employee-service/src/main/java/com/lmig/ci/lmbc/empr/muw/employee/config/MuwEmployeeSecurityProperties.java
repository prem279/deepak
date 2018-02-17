/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.config;

import java.util.ArrayList;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.config")
public class MuwEmployeeSecurityProperties {
	private ArrayList<String> employeeReadAccessGroups;
	private ArrayList<String> employeeCreateAccessGroups;
	private ArrayList<String> employeeUpdateAccessGroups;
	private ArrayList<String> employeeEventAccessGroups;

	private String adminRole;
	private String guestRole;
	private String groupSearchBase;
	private String userRole;
	private String userSearchBase;
	private String ssidSearchBase;
	private String ldapUrl;

	private String serviceId;
	private String servicePassword;
	
}
