/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.config;

import java.util.ArrayList;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.config")
public class MuwAccountSecurityProperties {
	private ArrayList<String> readAccessGroups;
	private ArrayList<String> readWriteAccessGroups;
	private ArrayList<String> readAccessEventGroups;
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
