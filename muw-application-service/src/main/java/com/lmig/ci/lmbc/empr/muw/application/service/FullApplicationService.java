package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantExtendedResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchResourceResponse;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionResourceResponse;
import com.lmig.ci.lmbc.empr.muw.application.api.BasicApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

public interface FullApplicationService {

	public ResponseEntity<ApplicationSubmissionResourceResponse> createApplicationSubmission(
			ApplicationSubmissionResource applicationSubmission);

	public ResponseEntity<ApplicationSubmissionResourceResponse> updateApplicationSubmission(Integer applicationId,
			ApplicationSubmissionResource applicationSubmission);

	public ResponseEntity<BasicApplicationResource> createBasicApplication(Application application);

	public ResponseEntity<BasicApplicationResource> updateBasicApplication(Application application);

	public ResponseEntity<BasicApplicationResource> getBasicApplication(Integer applicationId);

	public ResponseEntity<ApplicantResource> createApplicant(Integer applicationId, Applicant applicant,
			boolean fromFullApplicationSubmission);

	public ResponseEntity<List<ApplicantResource>> getApplicants(Integer applicationId);

	public ResponseEntity<ApplicantResource> updateApplicant(Integer applicationId, Integer applicantId,
			Applicant applicant);

	public ResponseEntity<ApplicantResource> getApplicant(Integer applicationId, Integer applicantId);

	public ResponseEntity<List<ApplicantExtendedResource>> getPreviousApplicants(Integer applicationApplicantId);

	public ResponseEntity<ApplicationSearchResourceResponse> getApplications(String lastName,
			String stakeholderLedgerNumber, String stakeholderSerialNumber, String ssn,
			String employeeIdentificationNumber);

	ResponseEntity<ApplicationSubmissionResource> getApplication(Integer applicationId);

}
