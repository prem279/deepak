package com.lmig.ci.lmbc.empr.muw.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lmig.ci.lmbc.empr.muw.application.domain.QuestionnaireResponse;


/**
 * @author n0296170
 *
 */

@RepositoryRestResource(collectionResourceRel = "applicant_ques_res", path = "applicant_ques_res", exported = false)
public interface QuestionnaireResponseRepository extends JpaRepository<QuestionnaireResponse, Integer> {
}