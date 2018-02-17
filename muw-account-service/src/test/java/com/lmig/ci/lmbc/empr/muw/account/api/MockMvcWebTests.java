package com.lmig.ci.lmbc.empr.muw.account.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.account.MuwAccountServiceTests;
import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;
import com.lmig.ci.lmbc.empr.muw.account.repository.EmployerRepository;
import com.lmig.ci.lmbc.empr.muw.account.service.MuwEmployerConfigurationContactService;
import com.lmig.ci.lmbc.empr.muw.account.util.ValidateAccountUtil;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;

/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jan 27, 2016
 */

/**
 * @Class MockMvcWebTests
 * @author n0190093
 *         <P>
 * @Description:
 *               <p>
 */
public class MockMvcWebTests extends MuwAccountServiceTests {
	@Autowired
	WebApplicationContext webContext;

	@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	private EmployerResourceAssembler employerResourceAssembler;

	private String basePath = EnvironmentConstants.BASE_PATH;

	@Value("${management.context-path}")
	private String actuatorBasePath;

	private MockMvc mockMvc;

	static int increment = 1;

	@MockBean
	MuwEmployerConfigurationContactService contactService;

	ResponseEntity<PartyResource> responseEntity = null;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(webContext).build();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Resource partyResourceJson = new ClassPathResource("data/contactServiceResponse.txt");
		String partyString = IOUtils.toString(partyResourceJson.getInputStream());

		PartyResource partyResource = mapper.readValue(partyString, new TypeReference<PartyResource>() {
		});

		ResponseEntity<PartyResource> responseEntity = new ResponseEntity<PartyResource>(partyResource, HttpStatus.OK);
		ResponseEntity<PhysicalAddressResource> responseEntity2 = new ResponseEntity<PhysicalAddressResource>(
				employerResourceAssembler.createPhysicalAddressResource(partyResource), HttpStatus.OK);
		when(contactService.create(any(EmployerResource.class))).thenReturn(responseEntity);
		when(contactService.getParty(anyString(), anyString())).thenReturn(responseEntity);
		when(contactService.getPrimaryAddress(anyString(), anyString())).thenReturn(responseEntity2);
		when(contactService.update(any(EmployerResource.class))).thenReturn(responseEntity);

	}

	@Test
	@Transactional
	public void testCreateEmployerConfiguration() throws Exception {
		increment++;
		postAccount("data/fullEmployer.txt", increment);
	}

	@Test
	@Transactional
	public void testUpdateEmployerConfiguration() throws Exception {
		increment++;
		updateAccount("data/fullEmployer.txt", increment);
	}

	@Test
	public void testGetEmployerConfigurationOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employer_configurations" + "/1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testGetEmployerConfiguration1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employer_configurations?div=85&serial=123456"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testGetEmployerConfiguration2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employer_configurations?div=094&serial=123456"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	private void postAccount(String jsonFile, Integer serial) throws Exception {
		Resource resource = new ClassPathResource(jsonFile);
		String resourceStr = IOUtils.toString(resource.getInputStream());

		resourceStr = resourceStr.replace("000000", StringUtils.leftPad(serial.toString(), 6, "0"));
		JsonNode source = JsonParserUtil.stringtoJsonNode(resourceStr);
		byte[] resourceByte = resourceStr.getBytes();
		MvcResult result = mockMvc.perform(post("/services/v1/employer_configurations").content(resourceByte)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		String content = result.getResponse().getContentAsString();
		JsonNode destination = JsonParserUtil.stringtoJsonNode(content);

		// Validate that Employer was created
		assertTrue("Employer ID is null or 0",
				null != destination.get("employerId") && destination.get("employerId").asInt() > 0);

		// Validate fields
		ValidateAccountUtil.compareAccount(source, destination);

		Employer employer = employerRepository.findOne(destination.get("employerId").asInt());
		JsonNode dest = JsonParserUtil.convertObjectToJson(employer);
		ValidateAccountUtil.compareAccount(source, dest);
	}

	private void updateAccount(String jsonFile, Integer serial) throws Exception {
		Resource resource = new ClassPathResource(jsonFile);
		String resourceStr = IOUtils.toString(resource.getInputStream());

		resourceStr = resourceStr.replace("000000", StringUtils.leftPad(serial.toString(), 6, "0"));
		JsonNode source = JsonParserUtil.stringtoJsonNode(resourceStr);
		byte[] resourceByte = resourceStr.getBytes();
		MvcResult result = mockMvc.perform(post("/services/v1/employer_configurations").content(resourceByte)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();

		String content = result.getResponse().getContentAsString();
		JsonNode destination = JsonParserUtil.stringtoJsonNode(content);

		// Validate that Employer was created
		assertTrue("Employer ID is null or 0",
				null != destination.get("employerId") && destination.get("employerId").asInt() > 0);

		// Validate fields
		ValidateAccountUtil.compareAccount(source, destination);

		// Update fields
		resourceStr = content.replace("Test", "Employer Name");
		source = JsonParserUtil.stringtoJsonNode(resourceStr);

		resourceByte = resourceStr.getBytes();
		result = mockMvc.perform(put("/services/v1/employer_configurations/" + destination.get("employerId").asText())
				.content(resourceByte).contentType(MediaType.APPLICATION_JSON)).andReturn();

		destination = JsonParserUtil.stringtoJsonNode(result.getResponse().getContentAsString());

		// Validate fields
		ValidateAccountUtil.compareAccount(source, destination);

		Employer employer = employerRepository.findOne(destination.get("employerId").asInt());
		JsonNode dest = JsonParserUtil.convertObjectToJson(employer);
		ValidateAccountUtil.compareAccount(source, dest);

		assertEquals("employerName", "Employer Name", destination.get("employerName").asText());
	}
}
