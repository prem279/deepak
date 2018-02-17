/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.muw.zuul;

import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.zuul.EnvironmentConstants;

/**
 * @Class CwbUserApplicationTests
 * @author n0189985
 * <P>
 * @Description:
 * <p>
 */
@ActiveProfiles(EnvironmentConstants.JUNIT)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=MuwZuulService.class)
@WebAppConfiguration
public class MuwZuulServiceTests {

	@Autowired
	public WebApplicationContext context;

	@Test
	public void testContext() {
		assertNotNull(context);
	}

	public static RequestPostProcessor adminUser() {
		return httpBasic("admin", "admin");
	}

	public static RequestPostProcessor guestUser() {
		return httpBasic("guest", "guest");
	}

	public static RequestPostProcessor userUser() {
		return httpBasic("user", "user");
	}
	
}
