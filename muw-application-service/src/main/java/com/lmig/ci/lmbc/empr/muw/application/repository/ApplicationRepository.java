/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 12, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

/**
 * @author n0159479
 *
 */
@RepositoryRestResource(collectionResourceRel = "applications", path = "applications", exported = false)
public interface ApplicationRepository extends JpaRepository<Application, Integer>, ApplicationRepositoryCustom {

	List<Application> findByEmployerEmployeeIdOrderByApplicationReceivedDateDesc(Integer employerEmployeeId);
	List<Application> findByEmployerEmployeeId(Integer employerEmployeeId);
	
}
