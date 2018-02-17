package com.lmig.ci.lmbc.empr.muw.application.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author n0159479
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "products")
public class MuwApplicationProductConfig {

    	public  Map<String, List<String>> productConfiguration = new HashMap <String, List<String>>();
    	
    }
