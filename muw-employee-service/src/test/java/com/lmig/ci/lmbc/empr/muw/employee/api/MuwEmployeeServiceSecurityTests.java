package com.lmig.ci.lmbc.empr.muw.employee.api;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;
import com.lmig.ci.lmbc.empr.muw.employee.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.employee.MuwEmployeeServiceTests;
import com.lmig.ci.lmbc.empr.muw.employee.service.EmployeeEmployerService;
import com.lmig.ci.lmbc.empr.muw.employee.service.MuwEmployeeContactService;

public class MuwEmployeeServiceSecurityTests extends MuwEmployeeServiceTests {

	@Autowired
	EmployeeEmployerService employeeService;

	@MockBean
	MuwEmployeeContactService contactService;

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

		PartyResource partyResource = new PartyResource();

		ResponseEntity<PartyResource> responseEntity = new ResponseEntity<PartyResource>(partyResource, HttpStatus.OK);
		when(contactService.create(any(EmployeeResource.class))).thenReturn(responseEntity);
		when(contactService.getParty(anyString(), anyString())).thenReturn(responseEntity);
		when(contactService.update(any(EmployeeResource.class))).thenReturn(responseEntity);

	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_READ" })
	public void getEmployeeAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employees/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_CREATE" })
	public void getEmployeesAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employees/1"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_UPDATE" })
	public void getEmployeesAsForbiddenUser1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employees/1"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithAnonymousUser
	public void getEmployeeAsAnonymous() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employees" + "/1"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_CREATE" })
	public void createEmployeeAsAllowedUser() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

		Resource appResource = new ClassPathResource("data/employeePost.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/employees").contentType(MediaType.APPLICATION_JSON)
				.content(app)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_UPDATE" })
	public void createEmployeeAsForbiddenUser() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

		Resource appResource = new ClassPathResource("data/employeePost.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/employees").contentType(MediaType.APPLICATION_JSON)
				.content(app)).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_READ" })
	public void createEmployeeAsForbiddenUser1() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

		Resource appResource = new ClassPathResource("data/employeePost.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/employees").contentType(MediaType.APPLICATION_JSON)
				.content(app)).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_UPDATE" })
	public void updateEmployeeAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/employees/1").contentType(MediaType.APPLICATION_JSON)
				.content("{}")).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_CREATE" })
	public void updateEmployeeAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/employees/1").contentType(MediaType.APPLICATION_JSON)
				.content("{}")).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_READ" })
	public void updateEmployeeAsForbiddenUser1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/employees/1").contentType(MediaType.APPLICATION_JSON)
				.content("{}")).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	public void getActuatorHealthAsAnonymous() throws Exception {
		this.mockMvc.perform(get(actuatorBasePath + "/health").accept("application/json")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_EVENT" })
	public void getEmployeeEventAsAllowedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/event?eventId=1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_CREATE" })
	public void getEmployeeEventAsForbiddenUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/event?eventId=1"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_UPDATE" })
	public void getEmployeeEventAsForbiddenUser1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/event?eventId=1"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user", roles = { "EMPLOYEE_READ" })
	public void getEmployeeEventAsForbiddenUser2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/event?eventId=1"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithAnonymousUser
	public void getEmployeeEventAsAnonymous() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/event?eventId=1"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

}
