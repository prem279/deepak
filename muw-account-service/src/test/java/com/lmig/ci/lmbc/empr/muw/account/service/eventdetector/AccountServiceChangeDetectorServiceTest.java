/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 26 Aug 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.service.eventdetector;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.account.MuwAccountServiceTests;

/**
 * @author n0256907
 *
 */

public class AccountServiceChangeDetectorServiceTest extends MuwAccountServiceTests {

	private final String BEFORE_EMPLOYER_FILEPATH = "data/beforeEmployer.txt";
	private final String AFTER_EMPLOYER_FILEPATH = "data/afterEmployer.txt";
	private final String UTF_EIGHT = "UTF-8";
	// Note: pretty print adds a space between the brackets, don't let it fool
	// you
	private final String EMPTY_JSON_PATCH = "[]";

	private Boolean printJsonToConsole = true;

	@Rule
	public TestName name = new TestName();

	@Test
	public void testGenerateJsonPatch() throws IOException {

		ClassPathResource beforeEmployer = new ClassPathResource(BEFORE_EMPLOYER_FILEPATH);
		ClassPathResource afterEmployer = new ClassPathResource(AFTER_EMPLOYER_FILEPATH);
		JsonNode source = JsonParserUtil.stringtoJsonNode(IOUtils.toString(beforeEmployer.getInputStream(), UTF_EIGHT));
		JsonNode target = JsonParserUtil.stringtoJsonNode(IOUtils.toString(afterEmployer.getInputStream(), UTF_EIGHT));

		JsonNode jsonPatch = JsonDiff.asJson(source, target);

		if (printJsonToConsole) {
			printJsonToConsole(jsonPatch);
		}

		assertTrue("Expecting JSON Patch to be non-null and non-empty",
				jsonPatch != null && !jsonPatch.isNull() && !jsonPatch.toString().trim().equals(EMPTY_JSON_PATCH));
	}

	@Test
	public void testGenerateEmptyJsonPatch() throws IOException {

		ClassPathResource beforeEmployer = new ClassPathResource(BEFORE_EMPLOYER_FILEPATH);
		ClassPathResource afterEmployer = new ClassPathResource(BEFORE_EMPLOYER_FILEPATH);

		JsonNode source = JsonParserUtil.stringtoJsonNode(IOUtils.toString(beforeEmployer.getInputStream(), UTF_EIGHT));
		JsonNode target = JsonParserUtil.stringtoJsonNode(IOUtils.toString(afterEmployer.getInputStream(), UTF_EIGHT));

		JsonNode jsonPatch = JsonDiff.asJson(source, target);

		if (printJsonToConsole) {
			printJsonToConsole(jsonPatch);
		}

		assertTrue("Expecting JSON Patch to be empty",
				jsonPatch != null && jsonPatch.toString().trim().equals(EMPTY_JSON_PATCH));
	}

	private void printJsonToConsole(JsonNode jsonNode) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(jsonNode.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			System.out.println("JSON Patch from " + name.getMethodName());
			System.out.println(indented + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
