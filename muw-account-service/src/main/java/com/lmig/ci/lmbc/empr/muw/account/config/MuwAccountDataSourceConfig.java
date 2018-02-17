/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Sep 9, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author n0159479
 *
 */

@Configuration
public class MuwAccountDataSourceConfig {
    
    
    
    @Bean(destroyMethod = "close")
    @Primary
    @ConfigurationProperties(prefix="datasource.app")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(destroyMethod = "close")
    @FlywayDataSource
    @ConfigurationProperties(prefix="datasource.flyway")
    public DataSource flywayDataSource() {
        return DataSourceBuilder.create().build();
    }

}
