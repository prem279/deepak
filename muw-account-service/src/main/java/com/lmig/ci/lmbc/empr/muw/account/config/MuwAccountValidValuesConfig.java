package com.lmig.ci.lmbc.empr.muw.account.config;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;
import com.lmig.ci.reuse.vv.config.ValidValueRestConfig;
import com.lmig.ci.reuse.vv.config.ValidValuesConfig;
import com.lmig.ci.reuse.vv.service.ValidValuesService;

@Configuration
@Profile(EnvironmentConstants.NOT_JUNIT)
public class MuwAccountValidValuesConfig {

	@Autowired
	ServiceProperties serviceProperties;

	@Autowired
	MuwAccountSecurityProperties muwApplicationSecurityProperties;

	@PostConstruct
	public void validValuesConfig() throws IOException {
		// Define our REST config
		ValidValueRestConfig rConfig = new ValidValueRestConfig();

		rConfig.setUsername(muwApplicationSecurityProperties.getServiceId());
		rConfig.setPassword(muwApplicationSecurityProperties.getServicePassword());

		rConfig.setUrl(serviceProperties.getValidValuesUrl() + "/services/v2/valid-value-sets/");

		try {
			File file = new ClassPathResource("valid-values-config.xml").getFile();
			ValidValuesConfig configuration = new ValidValuesConfig.Builder().configFile(file)
					.addExternalResource("rest/vv", rConfig).build();
			ValidValuesService.initialize(configuration);
		} catch (IOException ex) {
			throw ex;
		}

	}
}
