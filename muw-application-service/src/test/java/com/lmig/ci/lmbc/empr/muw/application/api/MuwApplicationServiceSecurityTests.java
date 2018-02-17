package com.lmig.ci.lmbc.empr.muw.application.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.service.FullApplicationService;

public class MuwApplicationServiceSecurityTests extends MuwApplicationServiceTests {

	@MockBean
	public FullApplicationService fullApplicationService;

	@Autowired
	WebApplicationContext webContext;

	private String basePath = EnvironmentConstants.BASE_PATH;

	@Value("${management.context-path}")
	private String actuatorBasePath;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = webAppContextSetup(webContext).apply(springSecurity()).build();

		ApplicationSubmissionResourceResponse applicationSubmissionResourceResponse = new ApplicationSubmissionResourceResponse();
		ResponseEntity<ApplicationSubmissionResourceResponse> response = new ResponseEntity<ApplicationSubmissionResourceResponse>(
				applicationSubmissionResourceResponse, HttpStatus.CREATED);

		when(fullApplicationService.createApplicationSubmission(any(ApplicationSubmissionResource.class)))
				.thenReturn(response);
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_READ" })
	public void getProductsAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/products"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_CREATE" })
	public void getProductsAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/products"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYER_READ" })
	public void getCondensedApplicationAsAllowedUser() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get(basePath + "/condensed_applications?employerId=1&applicationStatus=open"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_CREATE" })
	public void getCondensedApplicationAsForbiddenUser() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get(basePath + "/condensed_applications?employerId=1&applicationStatus=open"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_CREATE" })
	public void createApplicationAsAllowedUser() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

		Resource appResource = new ClassPathResource("json/application_submissions_POST.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/application_submissions")
				.contentType(MediaType.APPLICATION_JSON).content(app))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_READ" })
	public void createApplicationAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/application_submissions")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_CREATE" })
	public void createBasicApplicationAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/application_submission/basic_application")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_READ" })
	public void createBasicApplicationAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/application_submission/basic_application")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_UPDATE" })
	public void updateBasicApplicationAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/application_submission/basic_application/1")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_READ" })
	public void updateBasicApplicationAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/application_submission/basic_application/1")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_READ" })
	public void getApplicationAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/application_submission/99999")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICATION_CREATE" })
	public void getApplicationAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/application_submission/99999")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICANT_CREATE" })
	public void createApplicantAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/application_submission/99999/applicants")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICANT_READ" })
	public void createApplicantAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/application_submission/99999/applicants")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICANT_READ" })
	public void getApplicantAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/application_submission/99999/applicants/1")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICANT_CREATE" })
	public void getApplicantAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/application_submission/99999/applicants/1")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICANT_UPDATE" })
	public void updateApplicantAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/application_submission/99999/applicants/1")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICANT_CREATE" })
	public void updateApplicantAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/application_submission/99999/applicants/1")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICANT_READ" })
	public void getApplicantsAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/applicants/99999")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithMockUser(username = "user", roles = { "APPLICANT_CREATE" })
	public void getApplicantsAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/applicants/1").contentType(MediaType.APPLICATION_JSON)
				.content("{}")).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	public void getActuatorHealthAsAnonymous() throws Exception {
		this.mockMvc.perform(get(actuatorBasePath + "/health").accept("application/json")).andExpect(status().isOk());
	}
}
