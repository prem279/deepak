/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Class CwbUserApplicationTests
 * @author n0189985
 * <P>
 * @Description:
 * <p>
 */
@ActiveProfiles(EnvironmentConstants.JUNIT)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MuwEmployeeServiceTests {

	@Autowired
	public WebApplicationContext context;

	@Test
	public void testContext() {
		assertNotNull(context);
	}


	
}
