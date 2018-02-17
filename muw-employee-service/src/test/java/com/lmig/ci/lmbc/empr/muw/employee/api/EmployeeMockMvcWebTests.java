package com.lmig.ci.lmbc.empr.muw.employee.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;
import com.lmig.ci.lmbc.empr.muw.employee.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.employee.MuwEmployeeServiceTests;
import com.lmig.ci.lmbc.empr.muw.employee.service.EmployeeEmployerService;
import com.lmig.ci.lmbc.empr.muw.employee.service.MuwEmployeeContactService;

public class EmployeeMockMvcWebTests extends MuwEmployeeServiceTests {

	@Autowired
	EmployeeEmployerService employeeService;

	@MockBean
	MuwEmployeeContactService contactService;

	@Autowired
	private WebApplicationContext webContext;

	private MockMvc mockMvc;

	private String basePath = EnvironmentConstants.BASE_PATH;

	EmployeeResource employeeResource = new EmployeeResource();
	ResponseEntity<PartyResource> responseEntity = null;

	@Before
	public void setUp() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource employeeJson = new ClassPathResource("data/employeeGet.txt");
		String employee = IOUtils.toString(employeeJson.getInputStream());

		employeeResource = mapper.readValue(employee, new TypeReference<EmployeeResource>() {
		});

		Resource partyResourceJson = new ClassPathResource("data/contactReturn.txt");
		String partyString = IOUtils.toString(partyResourceJson.getInputStream());

		PartyResource partyResource = mapper.readValue(partyString, new TypeReference<PartyResource>() {
		});
		ResponseEntity<PartyResource> responseEntity = new ResponseEntity<PartyResource>(partyResource, HttpStatus.OK);
		when(contactService.create(any(EmployeeResource.class))).thenReturn(responseEntity);
		when(contactService.getParty(anyString(), anyString())).thenReturn(responseEntity);
		when(contactService.update(any(EmployeeResource.class))).thenReturn(responseEntity);

		this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void testGetEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get(basePath + "/employees?lastName=ragam&div=85&serial=123456&employeeSsn=123456"))// +
																										// "/1"))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void testGetEmployee1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employees" + "/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testPostEmployee() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource appResource = new ClassPathResource("data/employeePost.txt");
		String requestJson = IOUtils.toString(appResource.getInputStream());

		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/employees/").contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	// Negitive test case
	@Test
	public void testPostEmployee1() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource appResource = new ClassPathResource("data/employeeBadJson");
		String requestJson = IOUtils.toString(appResource.getInputStream());

		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/employees/").contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testPutEmployee() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource appResource = new ClassPathResource("data/employeePost.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		EmployeeResource employee = mapper.readValue(app, new TypeReference<EmployeeResource>() {
		});
		ResponseEntity<EmployeeResource> employeeResourceEntity = employeeService.createEmployee(employee);
		employeeResourceEntity.getBody().setCommunicationMethodCode("MAL");
		String requestJson = mapper.writeValueAsString(employeeResourceEntity.getBody());
		Integer key = employeeResourceEntity.getBody().getEmployerEmployeeId();

		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/employees/" + key)
				.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetEmployeeEvent() throws Exception {
		testPostEmployee(); // Posting an employee will create an event
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(EnvironmentConstants.BASE_PATH + "/event?eventId=0"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		List<EmployeeEventResource> applicationEventResources = mapper.readValue(content,
				new TypeReference<List<EmployeeEventResource>>() {
				});

		assertTrue(!applicationEventResources.isEmpty());
	}

}
