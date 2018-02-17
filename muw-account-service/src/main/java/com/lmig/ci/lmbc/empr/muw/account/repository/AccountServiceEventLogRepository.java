/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 12, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLog;

/**
 * @author n0159479
 *
 */
@RepositoryRestResource(collectionResourceRel = "accountServiceEventLog", path = "accountServiceEventLog") // ,excerptProjection
																											// =
																											// EmployerFull.class)
public interface AccountServiceEventLogRepository extends JpaRepository<AccountServiceEventLog, Integer> {

	@Override
	@RestResource
	List<AccountServiceEventLog> findAll();

	List<AccountServiceEventLog> findBySubjectAreaId(int subjectAreaId);

	List<AccountServiceEventLog> findAllByEventIdGreaterThan(Integer eventId);
}
