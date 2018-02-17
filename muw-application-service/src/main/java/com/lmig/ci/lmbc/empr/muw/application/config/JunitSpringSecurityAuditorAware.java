package com.lmig.ci.lmbc.empr.muw.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;

@Configuration
@EnableJpaAuditing
@Profile(EnvironmentConstants.JUNIT)
class JunitSpringSecurityAuditorAware implements AuditorAware<String> {

	public String getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return "JUNIT";
		}

		return authentication.getName();

	}
}