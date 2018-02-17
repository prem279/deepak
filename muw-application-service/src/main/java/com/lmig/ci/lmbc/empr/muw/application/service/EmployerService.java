
package com.lmig.ci.lmbc.empr.muw.application.service;

import org.springframework.http.ResponseEntity;

import com.lmig.ci.lmbc.empr.muw.employer.resource.EmployerResource;

public interface EmployerService {

	public ResponseEntity<EmployerResource> getEmployerResource(String div, String serial);
}
