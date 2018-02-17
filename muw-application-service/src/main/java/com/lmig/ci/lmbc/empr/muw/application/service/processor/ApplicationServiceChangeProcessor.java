/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 16 Nov 2016
 */

package com.lmig.ci.lmbc.empr.muw.application.service.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.JsonDiff;
import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationEvent;
import com.lmig.ci.lmbc.empr.muw.application.service.Eventable;

/**
 * @author n0256907
 *
 */
@Component
public class ApplicationServiceChangeProcessor {

	private Logger logger = LoggerFactory.getLogger(ApplicationServiceChangeProcessor.class);

	public ApplicationEvent processChange(Eventable before, Eventable after) throws Throwable {
		ApplicationEvent accountSrvcEvntLog;

		accountSrvcEvntLog = buildEventLog(before, after);

		return accountSrvcEvntLog;
	}

	private ApplicationEvent buildEventLog(Eventable before, Eventable after) throws Throwable {
		ApplicationEvent applicationEvent = new ApplicationEvent();
		applicationEvent.setProcessedIndicator("N");

		applicationEvent.setSubjectAreaCode("APPLN");

		if (before == null && after != null) {
			applicationEvent.setSubjectAreaId(after.getSubjectAreaId());
		} else if (before != null && after == null) {
			// TODO we currently don't support delete for Applications
		} else if (before != null && after != null) {
			if (!before.getSubjectAreaId().equals(after.getSubjectAreaId())) {
				throw new DataIntegrityViolationException(
						"The before and after IDs don't match, something loco happened.");
			}

			applicationEvent.setSubjectAreaId(before.getSubjectAreaId());
		}

		try {
			String beforeApplicationString = JsonParserUtil.convertObjectToJsonString(before);
			String afterApplicationString = JsonParserUtil.convertObjectToJsonString(after);

			applicationEvent.setBeforeEventData(beforeApplicationString);
			applicationEvent.setAfterEventData(afterApplicationString);

			JsonNode beforeApplicationNode = JsonParserUtil.stringtoJsonNode(beforeApplicationString);
			JsonNode afterApplicationNode = JsonParserUtil.stringtoJsonNode(afterApplicationString);

			JsonNode applicationJsonPatchNode = JsonDiff.asJson(beforeApplicationNode, afterApplicationNode);
			applicationEvent.setChangeSet(applicationJsonPatchNode.toString());
		} catch (Exception e) {
			logger.error("Error generating Application change event.", e);
			throw e;
		}

		return applicationEvent;
	}

}
