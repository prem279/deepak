/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.config;

import java.util.ArrayList;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.config")
public class MuwApplicationSecurityProperties {
	private ArrayList<String> applicationReadAccessGroups;
	private ArrayList<String> applicationCreateAccessGroups;
	private ArrayList<String> applicationUpdateAccessGroups;
	private ArrayList<String> applicantReadAccessGroups;
	private ArrayList<String> applicantCreateAccessGroups;
	private ArrayList<String> applicantUpdateAccessGroups;
	private ArrayList<String> employerReadAccessGroups;
	private ArrayList<String> applicationEventAccessGroups;
	private ArrayList<String> fileFeedUserIds;
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
