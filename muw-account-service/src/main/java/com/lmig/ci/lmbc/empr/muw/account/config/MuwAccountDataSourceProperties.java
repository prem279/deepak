/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Sep 9, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author n0159479
 *
 */
@Data
@ConfigurationProperties(prefix = "datasource.config")
public class MuwAccountDataSourceProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String schema;
}
