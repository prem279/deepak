/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 29, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationMedicalFactor;

/**
 * @author n0159479
 *
 */
@RepositoryRestResource(collectionResourceRel = "application_medical_factors", path = "application_medical_factors", exported = false)
public interface ApplicationMedicalFactorRepository extends JpaRepository<ApplicationMedicalFactor, Integer> {
}
