/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on April 04, 2017
 */

package com.lmig.ci.lmbc.empr.muw.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.employee.domain.EmployeeEvent;

/**
 * @author n0296170
 *
 */
@RepositoryRestResource(collectionResourceRel = "emplr_emp_event", path = "emplr_emp_event", exported = false)
public interface EmployeeEventRepository extends JpaRepository<EmployeeEvent, Integer> {
	List<EmployeeEvent> findBySubjectAreaId(int subjectAreaId);

	/**
	 * Returns a list of up to 1000 events with eventId greater than the eventId
	 * passed in, sorted ascending by event id.
	 *
	 * @param eventId
	 *            The last processed eventId
	 * @return A list of new events greater than the eventId passed in
	 */
	List<EmployeeEvent> findTop1000ByEventIdGreaterThanOrderByEventIdAsc(int eventId);
}
