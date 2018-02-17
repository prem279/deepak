/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 17, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.account.domain.EoiProduct;

/**
 * @author n0296170
 *
 */
@RepositoryRestResource(collectionResourceRel = "products", path = "products", exported = false)
public interface EoiProductRepository extends JpaRepository<EoiProduct, Integer> {
}
