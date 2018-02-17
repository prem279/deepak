/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.common.discovery.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="security.config")
public class ServiceDiscoverySecurityProperties {

	private String adminRole;
	private String guestRole;
	private String groupSearchBase;
	private String userRole;
	private String userSearchBase;
	private String ssidSearchBase;	
	private String ldapUrl;

}
