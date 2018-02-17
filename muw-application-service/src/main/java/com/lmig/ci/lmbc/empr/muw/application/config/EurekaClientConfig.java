package com.lmig.ci.lmbc.empr.muw.application.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;

public class EurekaClientConfig extends EurekaClientConfigBean {

	@Autowired
	EurekaClientProperties eurekaClientProperties;

	@Autowired
	MuwApplicationSecurityProperties muwApplicationSecurityProperties;

	public static final String DEFAULT_ZONE = "defaultZone";

	private String defaultZoneUrl;

	@Override
	public List<String> getEurekaServerServiceUrls(String myZone) {

		defaultZoneUrl = eurekaClientProperties.getDefaultZone();

		defaultZoneUrl = defaultZoneUrl.replaceAll("username", muwApplicationSecurityProperties.getServiceId())
				.replaceAll("password", muwApplicationSecurityProperties.getServicePassword());

		return Arrays.asList(defaultZoneUrl);

	}
}
