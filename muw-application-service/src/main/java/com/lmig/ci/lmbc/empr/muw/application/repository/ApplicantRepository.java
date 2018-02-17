/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Dec 29, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;

/**
 * @author n0159479
 *
 */
@RepositoryRestResource(collectionResourceRel = "applicants", path = "applicants", exported = false)
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
	
	List<Applicant> findByApplicationApplicationId(Integer applicationId);
	
	

	List<Applicant> findAllByApplicantSSN(String applicantSSN);
	
	
	}
