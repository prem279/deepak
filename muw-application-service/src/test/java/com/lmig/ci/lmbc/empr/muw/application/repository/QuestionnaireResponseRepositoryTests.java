package com.lmig.ci.lmbc.empr.muw.application.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantCondition;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.domain.QuestionnaireResponse;

public class QuestionnaireResponseRepositoryTests extends MuwApplicationServiceTests {

	@Autowired
	public WebApplicationContext context;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private ApplicantConditionRepository applicantConditionRepository;

	@Autowired
	private QuestionnaireResponseRepository questionResponseRepository;

	@Autowired
	private ApplicantRepository applicantRepository;

	@Test
	public void testContext() {
		assertNotNull(context);
	}
	@Test
	public void testApplicantConditionRepository() {

		// Instantiate Application
		Application application = new Application();
		application.setEmployerId(1);
		application.setEmployerEmployeeId(1);
		application.setApplicationReceivedDate(new Date(new java.util.Date().getTime()));
		application.setFamilySttsChangeEventDate(new Date(new java.util.Date().getTime()));
		application.setReasonCode("asdf");
		application.setSubmissionMethodCode("ONLN");

		// Save (Create) Application
		long applicationCount = applicationRepository.count();
		System.out.println("initial application count is: " + applicationCount);
		applicationRepository.save(application);
		System.out.println("after save application count is: " + applicationRepository.count());
		assertEquals(applicationRepository.count(), applicationCount + 1);
		Application savedApplication = applicationRepository.findOne(application.getApplicationId());
		assertEquals(application.getReasonCode(), savedApplication.getReasonCode());

		// Instantiate Applicant
		Applicant applicant = new Applicant();
		// applicant.setApplicationId(savedApplication.getApplicationId());
		applicant.setApplication(savedApplication);
		applicant.setFirstName("Parson");
		applicant.setLastName("James");
		applicant.setApplicantSSN("245678913");
		applicant.setBirthCountryCode("USA");

		// Save (Create) Applicant
		long applicantCount = applicantRepository.count();
		System.out.println("initial applicant count is: " + applicantCount);
		applicantRepository.save(applicant);
		System.out.println("after save applicant count is: " + applicantRepository.count());
		assertEquals(applicantRepository.count(), applicantCount + 1);
		Applicant savedApplicant = applicantRepository.findOne(applicant.getApplicationApplicantId());
		assertEquals(applicant.getApplicantSSN(), savedApplicant.getApplicantSSN());

		// Instantiate Applicant Condition

		ApplicantCondition condition = new ApplicantCondition();

		condition.setApplicant(applicant);
		condition.setConditionCode("ABC4");
		condition.setOtherConditionText("ha ha");
		condition.setConditionRecoveryDate(new Date(new java.util.Date().getTime()));
		condition.setConditionOnsetDate(new Date(new java.util.Date().getTime()));
		condition.setTreatmentHealthFullName("Deepak");
		condition.setTreatmentReceivedText("Ragam");

		// Save (Create) ApplicantCondition
		long conditionCount = applicantConditionRepository.count();
		System.out.println("initial applicantcondition count is: " + conditionCount);
		applicantConditionRepository.save(condition);
		System.out.println(condition.toString());
		System.out.println("after save applicantCondition count is: " + applicantConditionRepository.count());
		assertEquals(applicantConditionRepository.count(), conditionCount + 1);
		ApplicantCondition savedCondition = applicantConditionRepository.findOne(condition.getApplicantConditionId());
		assertEquals(condition.getTreatmentHealthFullName(), savedCondition.getTreatmentHealthFullName());

		// Instantiate Applicant Question Response

		QuestionnaireResponse response = new QuestionnaireResponse();
		
		response.setApplicantCondition(condition);
		response.setQuestionTypeCode("codeee");
		response.setAdditionalQuestionCode("CODEEE");
		response.setQuestionCode("CODEEE");

		// Save (Create) Applicant Questionnaire Response
		long repsonseCount = questionResponseRepository.count();
		System.out.println("initial questionResponse count is: " + repsonseCount);
		questionResponseRepository.save(response);
		System.out.println(response.toString());
		System.out.println("after save questionResponse count is: " + questionResponseRepository.count());
		assertEquals(questionResponseRepository.count(), repsonseCount + 1);
		QuestionnaireResponse savedResponse = questionResponseRepository
				.findOne(response.getApplicantQuestionResponseId());
		assertEquals(response.getQuestionTypeCode(), savedResponse.getQuestionTypeCode());

		// Update Applicant Questionnaire Response
		savedResponse.setQuestionTypeCode("change");
		questionResponseRepository.save(savedResponse);
		QuestionnaireResponse updatedQuestion = questionResponseRepository
				.findOne(savedResponse.getApplicantQuestionResponseId());
		assertNotEquals(response.getQuestionTypeCode(), updatedQuestion.getQuestionTypeCode());

		// Delete Applicant
		System.out.println("Before delete count is: " + questionResponseRepository.count());
		questionResponseRepository.delete(updatedQuestion.getApplicantQuestionResponseId());
		System.out.println("After delete count is: " + questionResponseRepository.count());
		assertEquals(questionResponseRepository.count(), repsonseCount);
	}
}
