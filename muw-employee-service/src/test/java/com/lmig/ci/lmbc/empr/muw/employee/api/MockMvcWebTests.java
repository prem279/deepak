package com.lmig.ci.lmbc.empr.muw.employee.api;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.employee.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.employee.MuwEmployeeServiceTests;


/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jan 27, 2016
 */

/**
 * @Class MockMvcWebTests
 * @author n0190093
 * <P>
 * @Description:
 * <p>
 */
@ActiveProfiles(EnvironmentConstants.JUNIT)
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class MockMvcWebTests extends MuwEmployeeServiceTests {

	@Autowired
	WebApplicationContext webContext;

	@Value("${spring.data.rest.basePath}")
	private String basePath;

	@Value("${management.context-path}")
	private String actuatorBasePath;  
	
	private MockMvc mockMvc;
	
	static int increment = 1;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webContext).build();
	}
	@Test
	public void testGetEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employees?lastName=ragam&div=85&serial=123456&employeeSsn=123456"))// + "/1"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	

}




