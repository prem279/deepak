/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 12, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct;

/**
 * @author n0296170
 *
 */
@RepositoryRestResource(collectionResourceRel = "application_products", path = "application_products", exported = false)
public interface ApplicationProductRepository extends JpaRepository<ApplicationProduct, Integer> {
	
	ApplicationProduct findOneByApplicationApplicationIdAndProductCode(Integer applicationId, String productCode);
	
}
