package com.lmig.ci.lmbc.empr.muw.application.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantCondition;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantMedication;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationMedicalFactor;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicantRepository;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicationRepository;
import com.lmig.ci.lmbc.empr.muw.application.service.EmployeeService;

/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jan 27, 2016
 */

/**
 * @Class MockMvcWebTests
 * @author n0189985
 *         <P>
 * @Description:
 *               <p>
 */
public class MockMvcWebTests extends MuwApplicationServiceTests {

	@Autowired
	WebApplicationContext webContext;

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	ApplicantRepository applicantRepository;

	@Value("${management.context-path}")
	private String actuatorBasePath;

	private MockMvc mockMvc;

	@MockBean
	EmployeeService employeeService;

	ResponseEntity<ApplicationSubmissionEmployeeResource> responseEntity = null;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(webContext).build();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource employeeResponseJson = new ClassPathResource("json/employee_response.json");
		String employeeString = IOUtils.toString(employeeResponseJson.getInputStream());

		ApplicationSubmissionEmployeeResource employeeResource = mapper.readValue(employeeString,
				new TypeReference<ApplicationSubmissionEmployeeResource>() {
				});

		when(employeeService.createEmployee(any(ApplicationSubmissionEmployeeResource.class)))
				.thenReturn(employeeResource);
		when(employeeService.getEmployee(any(Integer.class))).thenReturn(employeeResource);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webContext).build();
	}

	@Test
	public void testGetBasicApplication() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get(EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(MockMvcRequestBuilders
				.get(EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/0"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testPostBasicApplication() throws Exception {
		Application app = new Application();
		app.setEmployerEmployeeId(1);
		app.setApplicationReceivedDate(new Date(new java.util.Date().getTime()));
		app.setFamilySttsChangeEventDate(new Date(new java.util.Date().getTime()));
		app.setReasonCode("FMLY");
		app.setSubmissionMethodCode("ONLN");
		app.setEmployerId(1);

		Set<ApplicationProduct> applicationProducts = new HashSet<ApplicationProduct>();
		ApplicationProduct applicationProduct = new ApplicationProduct();
		applicationProduct.setProductCode("STD");

		applicationProducts.add(applicationProduct);
		app.setApplicationProducts(applicationProducts);

		Set<ApplicationMedicalFactor> applicationMedicalFactors = new HashSet<ApplicationMedicalFactor>();
		ApplicationMedicalFactor applicationMedicalFactor = new ApplicationMedicalFactor();

		applicationMedicalFactor.setApplicationRoleCode("EMPLY");
		applicationMedicalFactor.setMedicalFactorQuestionCode("ABSNCE");
		applicationMedicalFactor.setMedicalFactorIndicator(true);
		applicationMedicalFactor.setMedicalFactorText(
				"In the past year, have you been absent from work for 5 or more consecutive days due to illness and/or medical treatment, been home-confined, or made a claim for or received benefits, compensation, or pension for any injury, sickness, disability, or impaired condition?");

		applicationMedicalFactors.add(applicationMedicalFactor);
		app.setApplicationMedicalFactors(applicationMedicalFactors);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(app);

		mockMvc.perform(MockMvcRequestBuilders
				.post(EnvironmentConstants.BASE_PATH + "/application_submission/basic_application")
				.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Transactional
	@Test
	public void testPutBasicApplication() throws Exception {
		Application app = applicationRepository.findOne(1);

		String reasonChange = "XXXX";
		app.setReasonCode(reasonChange);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(app);

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders
								.put(EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/"
										+ app.getApplicationId())
								.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		// get newly created BasicApp
		String content = result.getResponse().getContentAsString();
		BasicApplicationResource updatedResource = mapper.readValue(content,
				new TypeReference<BasicApplicationResource>() {
				});

		// validate name change
		assertTrue(updatedResource.getReasonCode().equals(reasonChange));

	}

	@Test
	public void testGetApplicants() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get(EnvironmentConstants.BASE_PATH + "/application_submission/2/applicants"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		mockMvc.perform(
				MockMvcRequestBuilders.get(EnvironmentConstants.BASE_PATH + "/application_submission/2/applicants/2"))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void testPostApplicant() throws Exception {
		Applicant applicant = new Applicant();
		applicant.setFirstName("Parson");
		applicant.setLastName("James");
		applicant.setApplicantSSN("245678913");
		applicant.setBirthCountryCode("USA");

		ApplicantCondition condition = new ApplicantCondition();
		condition.setConditionCode("ABC4");
		condition.setOtherConditionText("ha ha");
		condition.setConditionRecoveryDate(new Date(new java.util.Date().getTime()));
		condition.setConditionOnsetDate(new Date(new java.util.Date().getTime()));
		condition.setTreatmentHealthFullName("Deepak");
		condition.setTreatmentReceivedText("Ragam");

		ApplicantMedication medication = new ApplicantMedication();

		medication.setMedicationCode("ABC6");
		medication.setOtherMedicationText("he he");
		medication.setPrescriptionDate(new Date(new java.util.Date().getTime()));

		List<ApplicantCondition> conditions = new ArrayList<ApplicantCondition>();
		conditions.add(condition);
		List<ApplicantMedication> medications = new ArrayList<ApplicantMedication>();
		medications.add(medication);

		condition.setMedications(medications);
		applicant.setConditions(conditions);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(applicant);

		mockMvc.perform(
				MockMvcRequestBuilders.post(EnvironmentConstants.BASE_PATH + "/application_submission/2/applicants")
						.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isCreated());

	}

	@Transactional
	@Test
	public void testPutApplicant() throws Exception {
		Applicant applicant = applicantRepository.findOne(1);

		String nameChange = "Beyonce";
		applicant.setFirstName(nameChange);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(applicant);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
						.put(EnvironmentConstants.BASE_PATH + "/application_submission/"
								+ applicant.getApplication().getApplicationId() + "/applicants/"
								+ applicant.getApplicationApplicantId())
						.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		// get newly created BasicApp
		String content = result.getResponse().getContentAsString();
		ApplicantResource updatedResource = mapper.readValue(content, new TypeReference<ApplicantResource>() {
		});

		// validate name change
		assertTrue(updatedResource.getFirstName().equals(nameChange));

	}

	@Test
	public void testGetCondensedApplication() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get(EnvironmentConstants.BASE_PATH + "/condensed_applications?employerId=1&applicationStatus=open"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetApplicationProductStatus() throws Exception {
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
						.get(EnvironmentConstants.BASE_PATH + "/application_submission/basic_application/1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

		String content = result.getResponse().getContentAsString();
		ApplicationResource updatedResource = mapper.readValue(content, new TypeReference<ApplicationResource>() {
		});

		int deniedCount = 0;
		for (ApplicationProductResource product : updatedResource.getApplicationProducts()) {
			if (product.getStatusTypeCode() != null && product.getStatusTypeCode().equals("DENIED")) {
				deniedCount++;
			}
		}

		assertTrue(deniedCount == 2);
	}

	@Test
	public void testPostApplicationSubmissions() throws Exception {

		ApplicationSubmissionResource applicationSubmission = new ApplicationSubmissionResource();

		// Create new Employee Resource
		ApplicationSubmissionEmployeeResource employee = new ApplicationSubmissionEmployeeResource();
		employee.setEmployerId(1);
		employee.setEmployeeSsn("001559876");
		employee.setCommunicationMethodCode("EMAIL");

		// Set employee resource on the Application Submission
		applicationSubmission.setEmployee(employee);

		// Create new Application Submission Application Resource
		ApplicationSubmissionApplicationResource application = new ApplicationSubmissionApplicationResource();

		application.setApplicationReceivedDate(new Date(new java.util.Date().getTime()));
		application.setFamilySttsChangeEventDate(new Date(new java.util.Date().getTime()));
		application.setReasonCode("FMLY");
		application.setSubmissionMethodCode("ONLN");
		application.setEmployerId(1);

		// Create Medical Factor Resource
		List<ApplicationSubmissionMedicalFactorResource> applicationMedicalFactors = new ArrayList<ApplicationSubmissionMedicalFactorResource>();
		ApplicationSubmissionMedicalFactorResource applicationMedicalFactor = new ApplicationSubmissionMedicalFactorResource();

		applicationMedicalFactor.setApplicationRoleCode("EMPLY");
		applicationMedicalFactor.setMedicalFactorQuestionCode("ABSNCE");
		applicationMedicalFactor.setMedicalFactorIndicator(true);
		applicationMedicalFactors.add(applicationMedicalFactor);

		// Set Medical Factor list on Application Resource
		application.setApplicationMedicalFactors(applicationMedicalFactors);

		// Create Applicant Resources

		ApplicationSubmissionApplicantResource applicant = new ApplicationSubmissionApplicantResource();
		applicant.setFirstName("Parson");
		applicant.setLastName("James");
		applicant.setApplicantSSN("245678913");
		applicant.setBirthCountryCode("USA");
		applicant.setApplicationRoleCode("SPUSE");

		// Create Applicant Product Resources
		ApplicationSubmissionApplicantProductResource applicantProduct = new ApplicationSubmissionApplicantProductResource();
		applicantProduct.setProductCode("LTD");
		applicantProduct.setCurrentProductAmount(1.0);

		List<ApplicationSubmissionApplicantProductResource> applicantProducts = new ArrayList<ApplicationSubmissionApplicantProductResource>();
		applicantProducts.add(applicantProduct);
		applicant.setApplicantProducts(applicantProducts);

		// Create Applicant Condition Resources
		ApplicationSubmissionApplicantConditionResource condition = new ApplicationSubmissionApplicantConditionResource();
		condition.setConditionCode("ABC4");
		condition.setOtherConditionText("ha ha");
		condition.setConditionRecoveryDate(new Date(new java.util.Date().getTime()));
		condition.setConditionOnsetDate(new Date(new java.util.Date().getTime()));
		condition.setTreatmentHealthFullName("Deepak");
		condition.setTreatmentReceivedText("Ragam");

		// Create Applicant Questionnaire Reponses
		ApplicationSubmissionApplicantQuestionnaireResponseResource qResponse = new ApplicationSubmissionApplicantQuestionnaireResponseResource();
		qResponse.setAdditionalQuestionCode("MOREQs");
		qResponse.setQuestionCode("THISQ1");
		qResponse.setQuestionTypeCode("LEVEL1");
		qResponse.setResponseText("Yeah i got that");
		qResponse.setResponseAmount(null);
		qResponse.setResponseIndicator(true);
		qResponse.setResponseNumber("1");

		// Create Applicant Medication Resources
		ApplicationSubmissionApplicantMedicationResource medication = new ApplicationSubmissionApplicantMedicationResource();

		medication.setMedicationCode("ABC6");
		medication.setOtherMedicationText("he he");
		medication.setPrescriptionDate(new Date(new java.util.Date().getTime()));

		List<ApplicationSubmissionApplicantConditionResource> conditions = new ArrayList<ApplicationSubmissionApplicantConditionResource>();
		conditions.add(condition);

		List<ApplicationSubmissionApplicantQuestionnaireResponseResource> questionnaireResponses = new ArrayList<ApplicationSubmissionApplicantQuestionnaireResponseResource>();
		questionnaireResponses.add(qResponse);

		List<ApplicationSubmissionApplicantMedicationResource> medications = new ArrayList<ApplicationSubmissionApplicantMedicationResource>();
		medications.add(medication);

		// Set Questionnaire Responses on Condition Resource
		condition.setQuestionnaireResponses(questionnaireResponses);

		// Set Medication Resources on Condition Resource
		condition.setMedications(medications);

		// Set Condition Resources on Applicant Resource
		applicant.setConditions(conditions);

		List<ApplicationSubmissionApplicantResource> applicants = new ArrayList<ApplicationSubmissionApplicantResource>();
		applicants.add(applicant);

		// Set Applicant Resources on Application Resource
		application.setApplicants(applicants);

		applicationSubmission.setApplication(application);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(applicationSubmission);

		mockMvc.perform(MockMvcRequestBuilders.post(EnvironmentConstants.BASE_PATH + "/application_submissions")
				.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isCreated());

	}

	@Test
	public void testPutApplicationSubmission() throws Exception {
		List<Application> applications = applicationRepository.findAll();
		if (applications.isEmpty()) {
			testPostApplicationSubmissions();
			applications = applicationRepository.findAll();
		}
		String applicationId = applications.get(applications.size() - 1).getApplicationId().toString();
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
						.get(EnvironmentConstants.BASE_PATH + "/application_submissions/" + applicationId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		String content = result.getResponse().getContentAsString();

		ApplicationSubmissionResource applicationSubmission = mapper.readValue(content,
				new TypeReference<ApplicationSubmissionResource>() {
				});

		applicationSubmission.getEmployee().setFirstName("Bob");
		applicationSubmission.getApplication().setReasonCode("TEST");

		String requestJson = JsonParserUtil.convertObjectToJsonString(applicationSubmission);

		mockMvc.perform(
				MockMvcRequestBuilders.put(EnvironmentConstants.BASE_PATH + "/application_submissions/" + applicationId)
						.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testGetProducts() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(EnvironmentConstants.BASE_PATH + "/products"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		String content = result.getResponse().getContentAsString();

		List<ProductResource> products = mapper.readValue(content, new TypeReference<List<ProductResource>>() {
		});

		assertTrue(products.size() > 0);
	}

	@Test
	public void testGetApplicationEvent() throws Exception {
		testPostApplicant(); // Posting an applicant will create an event
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(EnvironmentConstants.BASE_PATH + "/event?eventId=0"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		List<ApplicationEventResource> applicationEventResources = mapper.readValue(content,
				new TypeReference<List<ApplicationEventResource>>() {
				});

		assertTrue(!applicationEventResources.isEmpty());
	}

}
