/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jan 22, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.lmig.ci.lmbc.empr.muw.application.config.EurekaClientConfig;
import com.lmig.reuse.properties.encryption.spring.annotation.EnableEncryptableProperties;
import com.lmig.reuse.properties.encryption.spring.annotation.ResourceBasedKey;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Class UserApplication
 * @author Josh Merritt
 *         <P>
 * @Description:
 *               <p>
 */
@EnableDiscoveryClient
@EnableEncryptableProperties
@ResourceBasedKey(profile = { EnvironmentConstants.JUNIT,
		EnvironmentConstants.LOCAL }, location = "classpath:key/muwapp-local-keys.key")
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "com.lmig.ci.lmbc.empr.auth.util", "com.lmig.ci.lmbc.empr.muw.application" })
@EnableResourceServer
public class MuwApplicationService {

	private static final Logger logger = LoggerFactory.getLogger(MuwApplicationService.class);

	public static void main(String[] args) {
		try {

			SpringApplication.run(MuwApplicationService.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Starting Spring Boot Application: Application Service");
	}

	@Profile(EnvironmentConstants.NOT_JUNIT)
	@Bean
	public EurekaClientConfig createEurekaClientConfig() {
		return new EurekaClientConfig();
	}

	@Bean
	public FlywayMigrationStrategy migrateStrategy(@Value("${flyway.migrate.goals}") final String migrateGoals) {
		FlywayMigrationStrategy strategy = new FlywayMigrationStrategy() {

			@Override
			public void migrate(Flyway flyway) {
				String goal = migrateGoals;
				String baselineVersion = "false";

				logger.info("running with migration goal of '" + goal + "' and baselineVersion of '" + baselineVersion
						+ "'");
				logger.info("pass in --migration.goal=[info, migrate, repair, validate] to run with a different goal");

				try {
					if (goal.contains("repair")) {
						// log the current status and any outstanding (pending)
						// migrations
						logInfo(flyway);
						// Remove failed migration entries (only for databases
						// that do NOT support DDL transactions)
						// Realign the checksums of the applied migrations to
						// the ones of the available migrations
						flyway.repair();
					}

					if (goal.contains("clean")) {
						// log the current status and any outstanding (pending)
						// migrations
						logInfo(flyway);
						// Remove failed migration entries (only for databases
						// that do NOT support DDL transactions)
						// Realign the checksums of the applied migrations to
						// the ones of the available migrations
						logger.info("running flyway clean");
						flyway.clean();
					}

					if (goal.contains("migrate")) {
						// log the current status and any outstanding (pending)
						// migrations
						logInfo(flyway);
						logger.info("running flyway migrate");
						flyway.migrate();
					}

					if (goal.contains("validate")) {
						// log the current status and any outstanding (pending)
						// migrations
						logInfo(flyway);
						// Validates the applied migrations against the
						// available ones
						flyway.validate();
					}

					// always run info per default or after migrating/repairing
					logInfo(flyway);

				} catch (FlywayException e) {
					logger.error("failed running migration goal of '" + goal + "'", e);
					throw e;
				}
			}
		};

		return strategy;
	}

	private void logInfo(Flyway flyway) {
		MigrationInfo[] migrationInfos = flyway.info().all();

		// installed_rank version description type script checksum installed_by
		// installed_on execution_time success
		// 1 1.0.1 theBaselineDescription BASELINE theBaselineDescription (null)
		// dbo 2016-07-25 15:01:05.513 0 1
		// 2 1.0.2 alter person table SQL V1_0_2__alter_person_table.sql
		// -629934056 dbo 2016-07-28 20:22:17.730 16 0

		String delim = ",\t";
		logger.info(
				"installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success");
		StringBuilder sb = new StringBuilder();
		for (MigrationInfo mi : migrationInfos) {
			sb.setLength(0);

			sb.append(mi.getInstalledRank()).append(delim);
			sb.append(mi.getVersion()).append(delim);
			sb.append(mi.getDescription()).append(delim);
			sb.append(mi.getType()).append(delim);
			sb.append(mi.getScript()).append(delim);
			sb.append(mi.getType()).append(delim);
			sb.append(mi.getChecksum()).append(delim);
			sb.append(mi.getInstalledBy()).append(delim);
			sb.append(mi.getInstalledOn()).append(delim);
			sb.append(mi.getExecutionTime()).append(delim);
			sb.append(mi.getState()).append(delim);

			logger.info(sb.toString());
		}
	}
}
