package com.lmig.ci.lmbc.empr.muw.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;

@Configuration
@EnableJpaAuditing
@Profile(EnvironmentConstants.NOT_JUNIT)
class SpringSecurityAuditorAware implements AuditorAware<String> {

  public String getCurrentAuditor() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }

    return authentication.getName();
  }
}