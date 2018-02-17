package com.lmig.ci.lmbc.empr.muw.account.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;
import com.lmig.ci.reuse.vv.config.ValidValuesConfig;
import com.lmig.ci.reuse.vv.service.ValidValuesService;

@Configuration
@Profile(EnvironmentConstants.JUNIT)
public class MuwAccountValidValuesJunitConfig {

	@PostConstruct
	public void validValuesConfig() {
		ValidValuesConfig config = new ValidValuesConfig.Builder()
				.configFile("src/test/resources/valid-values-test-config.xml").build();

		ValidValuesService.initialize(config);
	}
}
