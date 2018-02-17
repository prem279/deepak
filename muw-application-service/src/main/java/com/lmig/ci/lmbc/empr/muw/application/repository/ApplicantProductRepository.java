/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 12, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;

/**
 * @author n0296170
 *
 */
@RepositoryRestResource(collectionResourceRel = "applicant_products", path = "applicant_products", exported = false)
public interface ApplicantProductRepository extends JpaRepository<ApplicantProduct, Integer> {
}
