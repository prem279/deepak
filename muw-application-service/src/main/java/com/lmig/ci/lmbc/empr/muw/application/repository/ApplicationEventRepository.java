/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on April 04, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationEvent;

/**
 * @author n0296170
 *
 */
@RepositoryRestResource(collectionResourceRel = "appln_event", path = "appln_event", exported = false)
public interface ApplicationEventRepository extends JpaRepository<ApplicationEvent, Integer> {
	List<ApplicationEvent> findBySubjectAreaId(int subjectAreaId);

	List<ApplicationEvent> findTop25ByEventIdGreaterThanOrderByEventIdAsc(int eventId);
}
