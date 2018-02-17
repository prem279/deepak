package com.lmig.ci.lmbc.empr.muw.application.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantExtendedResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchEmployeeResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchResourceResponse;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionEmployeeResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionResourceResponse;
import com.lmig.ci.lmbc.empr.muw.application.api.BasicApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.BasicApplicationResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.application.api.exception.BadRequestException;
import com.lmig.ci.lmbc.empr.muw.application.api.exception.RequestedResourceNotFoundException;

public class MuwFullApplicationServiceTests extends MuwApplicationServiceTests {

	@MockBean
	public EmployeeService employeeService;

	@Autowired
	public FullApplicationService fullApplicationService;

	@Autowired
	BasicApplicationResourceAssembler basicApplicationResourceAssembler;

	ApplicationSearchEmployeeResource employeeResource = new ApplicationSearchEmployeeResource();
	ApplicationSubmissionEmployeeResource employeeSubmissionResource = new ApplicationSubmissionEmployeeResource();

	@Before
	public void setUp() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

		Resource employeeJson = new ClassPathResource("json/employee_response_GET.json");
		String employee = IOUtils.toString(employeeJson.getInputStream());

		employeeResource = mapper.readValue(employee, new TypeReference<ApplicationSearchEmployeeResource>() {
		});
		employeeSubmissionResource = mapper.readValue(employee,
				new TypeReference<ApplicationSubmissionEmployeeResource>() {
				});

		when(employeeService.getEmployee(anyString(), anyString(), anyString(), anyString(), anyString()))
				.thenReturn(employeeResource);
		when(employeeService.createEmployee(any(ApplicationSubmissionEmployeeResource.class)))
				.thenReturn(employeeSubmissionResource);

	}

	@Test
	@Transactional
	public void testGetApplications() throws Exception {

		ResponseEntity<ApplicationSearchResourceResponse> applicationsResponseEntity = fullApplicationService
				.getApplications("Testlast", "07", "A3as4s", "623474321", null);

		assertFalse(applicationsResponseEntity.getBody().getApplications().isEmpty());

		for (ApplicationSearchApplicationResource application : applicationsResponseEntity.getBody()
				.getApplications()) {
			assertEquals(employeeResource.getEmployerEmployeeId(), application.getEmployerEmployeeId());
		}

	}

	@Test(expected = BadRequestException.class)
	public void testGetApplicationsBadRequestNullParams() throws Exception {
		fullApplicationService.getApplications("Testlast", "07", "A3as4s", null, null);
	}

	@Test(expected = BadRequestException.class)
	public void testGetApplicationsBadRequestInvalidParams() throws Exception {
		fullApplicationService.getApplications("Testlast", "07", "A3as4s", "623474321", "EMP1636");
	}

	@Test(expected = RequestedResourceNotFoundException.class)
	public void testGetApplicationsEmployeeNotFound() {

		when(employeeService.getEmployee(anyString(), anyString(), anyString(), anyString(), anyString()))
				.thenReturn(null);

		fullApplicationService.getApplications("Testlast", "07", "A3as4s", "623474321", null);
	}

	@Test
	public void testCreateApplicationSubmission() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource appResource = new ClassPathResource("json/application_submissions_POST.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		ApplicationSubmissionResource applicationSubmission = mapper.readValue(app,
				new TypeReference<ApplicationSubmissionResource>() {
				});

		ResponseEntity<ApplicationSubmissionResourceResponse> applicationSubmissionResponseEntity = fullApplicationService
				.createApplicationSubmission(applicationSubmission);

		assertNotNull(applicationSubmissionResponseEntity.getBody().getApplicationId());
	}

	@Test
	@WithMockUser(username = "ci_lmbc_empr_muw_ff")
	public void testCreateFileFeedApplicationSubmission() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource appResource = new ClassPathResource("json/application_submissions_POST_file_feed.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		ApplicationSubmissionResource applicationSubmission = mapper.readValue(app,
				new TypeReference<ApplicationSubmissionResource>() {
				});

		ResponseEntity<ApplicationSubmissionResourceResponse> applicationSubmissionResponseEntity = fullApplicationService
				.createApplicationSubmission(applicationSubmission);

		assertNotNull(applicationSubmissionResponseEntity.getBody().getApplicationId());
	}

	@Test
	public void testCreateBasicApplication() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource appResource = new ClassPathResource("json/application_search_GET(1).json");
		String app = IOUtils.toString(appResource.getInputStream());

		BasicApplicationResource application = mapper.readValue(app, new TypeReference<BasicApplicationResource>() {
		});

		ResponseEntity<BasicApplicationResource> applicationSubmissionResponseEntity = fullApplicationService
				.createBasicApplication(basicApplicationResourceAssembler.toDomainObject(application));

		assertNotNull(applicationSubmissionResponseEntity.getBody().getApplicationId());
	}

	@Test
	public void testGetPreviousApplicants() throws Exception {
		ResponseEntity<List<ApplicantExtendedResource>> applicantListResponse = fullApplicationService
				.getPreviousApplicants(1);

		assertFalse(applicantListResponse.getBody().isEmpty());
	}
}
