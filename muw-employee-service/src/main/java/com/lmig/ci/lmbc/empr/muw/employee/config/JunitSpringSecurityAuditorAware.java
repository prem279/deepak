package com.lmig.ci.lmbc.empr.muw.employee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.lmig.ci.lmbc.empr.muw.employee.EnvironmentConstants;

@Configuration
@EnableJpaAuditing
@Profile(EnvironmentConstants.JUNIT)
class JunitSpringSecurityAuditorAware implements AuditorAware<String> {

  public String getCurrentAuditor() {
    return "JUNIT";
  }
}