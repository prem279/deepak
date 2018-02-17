package com.lmig.ci.lmbc.empr.muw.account.api;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.account.MuwAccountServiceTests;

// TODO: CIUPA-1781 - Remove @Ignore
public class MuwAccountServiceSecurityTests extends MuwAccountServiceTests {

	@Autowired
	WebApplicationContext webContext;

	private String basePath = EnvironmentConstants.BASE_PATH;

	@Value("${management.context-path}")
	private String actuatorBasePath;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webContext).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void getEmployerConfigurationApiAsUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employer_configurations" + "/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void getEmployerConfigurationApiAsAdmin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employer_configurations" + "/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithAnonymousUser
	public void getEmployerConfigurationApiAsAnonymous() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/employer_configurations" + "/1"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void postEmployerConfigurationApiAsAdmin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/employer_configurations")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void postEmployerConfigurationApiAsUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/employer_configurations")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void putEmployerConfigurationApiAsAdmin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/employer_configurations/1")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void putEmployerConfigurationApiAsUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/employer_configurations")
				.contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	public void getActuatorHealthAsAnonymous() throws Exception {
		this.mockMvc.perform(get(actuatorBasePath + "/health").accept("application/json")).andExpect(status().isOk());
	}
}
