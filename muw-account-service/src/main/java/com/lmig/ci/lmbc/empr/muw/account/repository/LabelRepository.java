/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 17, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.account.domain.Label;

/**
 * @author n0282721
 *
 */
@RepositoryRestResource(collectionResourceRel = "labels", path = "labels", exported = false)
public interface LabelRepository extends JpaRepository<Label, Integer> {
}
