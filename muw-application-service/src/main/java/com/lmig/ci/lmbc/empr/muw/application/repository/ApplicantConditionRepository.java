/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Jan 5, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantCondition;

/**
 * @author n0296170
 *
 */


@RepositoryRestResource(collectionResourceRel = "applicant_condition", path = "applicant_conditions", exported = false)
public interface ApplicantConditionRepository extends JpaRepository<ApplicantCondition, Integer> {
}
