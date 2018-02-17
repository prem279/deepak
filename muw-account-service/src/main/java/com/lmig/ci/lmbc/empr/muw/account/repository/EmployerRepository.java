/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 12, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;

/**
 * @author n0159479
 *
 */
@RepositoryRestResource(collectionResourceRel = "employers", path = "employers", exported = false)
public interface EmployerRepository extends JpaRepository<Employer, Integer> {

public Employer findOneByStakeholderLedgerSerialNumberPkEmployerStakeholderLedgerNumberAndStakeholderLedgerSerialNumberPkEmployerStakeholderSerialNumber(String div ,String serial);
}