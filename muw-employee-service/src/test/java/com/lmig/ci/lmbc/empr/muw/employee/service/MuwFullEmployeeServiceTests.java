package com.lmig.ci.lmbc.empr.muw.employee.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;
import com.lmig.ci.lmbc.empr.muw.employee.MuwEmployeeServiceTests;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResource;

public class MuwFullEmployeeServiceTests extends MuwEmployeeServiceTests {

	@Autowired
	EmployeeEmployerService employeeService;

	@MockBean
	MuwEmployeeContactService contactService;

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
		new ResponseEntity<EmployeeResource>(employeeResource, HttpStatus.OK);
		Resource partyResourceJson = new ClassPathResource("data/contactReturn.txt");
		String partyString = IOUtils.toString(partyResourceJson.getInputStream());

		PartyResource partyResource = mapper.readValue(partyString, new TypeReference<PartyResource>() {
		});
		ResponseEntity<PartyResource> responseEntity = new ResponseEntity<PartyResource>(partyResource, HttpStatus.OK);
		when(contactService.create(any(EmployeeResource.class))).thenReturn(responseEntity);
		when(contactService.getParty(anyString(), anyString())).thenReturn(responseEntity);
		when(contactService.update(any(EmployeeResource.class))).thenReturn(responseEntity);

	}

	@Test
	public void testCreateEmployeeResource() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource appResource = new ClassPathResource("data/employeePost.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		EmployeeResource employee = mapper.readValue(app, new TypeReference<EmployeeResource>() {
		});
		ResponseEntity<EmployeeResource> employeeResourceEntity = employeeService.createEmployee(employee);

		assertNotNull(employeeResourceEntity.getBody().getEmployerEmployeeId());
	}

	@Test
	public void testUpdateEmployeeResource() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource appResource = new ClassPathResource("data/employeePost.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		EmployeeResource employee = mapper.readValue(app, new TypeReference<EmployeeResource>() {
		});
		ResponseEntity<EmployeeResource> employeeResourceEntity = employeeService.createEmployee(employee);

		ResponseEntity<EmployeeResource> employeeResourceEntity2 = employeeService.updateEmployee(
				employeeResourceEntity.getBody(), employeeResourceEntity.getBody().getEmployerEmployeeId());

		assertNotNull(employeeResourceEntity2.getBody().getEmployerEmployeeId());

	}

	@Test
	public void testGetEmployee() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource appResource = new ClassPathResource("data/employeePost.txt");
		String app = IOUtils.toString(appResource.getInputStream());

		EmployeeResource employee = mapper.readValue(app, new TypeReference<EmployeeResource>() {
		});
		ResponseEntity<EmployeeResource> employeeResourceEntity = employeeService.createEmployee(employee);

		assertNotNull(employeeResourceEntity.getBody().getEmployerEmployeeId());

		ResponseEntity<EmployeeResource> employeeResponseEntity = employeeService
				.getEmployee(employeeResourceEntity.getBody().getEmployerEmployeeId());

		assertFalse(employeeResponseEntity.getBody().getEmployeeClassCode().isEmpty());
	}

}
