/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 11, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.lmig.ci.lmbc.empr.muw.account.api.AccountServiceEventLogResource;
import com.lmig.ci.lmbc.empr.muw.account.api.EmployerResource;
import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;

/**
 * @author n0159479
 *
 */
public interface MuwEmployerConfigurationService {

	ResponseEntity<EmployerResource> getEmployerConfiguration(Integer employerId);

	HttpEntity<EmployerResource> findByDivAndSerial(String div, String serial);

	ResponseEntity<List<AccountServiceEventLogResource>> getAccountEvents(Integer eventId);

	ResponseEntity<EmployerResource> create(Employer employer, EmployerResource resource)
			throws DataIntegrityViolationException;

	ResponseEntity<EmployerResource> update(Integer employerId, Employer employer, EmployerResource resource);
}
