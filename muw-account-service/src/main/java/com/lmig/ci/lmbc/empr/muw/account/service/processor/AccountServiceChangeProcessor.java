/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 16 Nov 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.service.processor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.JsonDiff;
import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLog;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLogConstants;

/**
 * @author n0256907
 *
 */
@Component
public class AccountServiceChangeProcessor {

	public AccountServiceEventLog processChange(JsonNode beforeEmployer, JsonNode afterEmployer) throws Exception {
		AccountServiceEventLog accountSrvcEvntLog;

		accountSrvcEvntLog = buildEventLog(beforeEmployer, afterEmployer);

		return accountSrvcEvntLog;
	}

	private AccountServiceEventLog buildEventLog(JsonNode beforeEmployer, JsonNode afterEmployer) throws Exception {
		AccountServiceEventLog accountSrvcEvntLog = new AccountServiceEventLog();
		accountSrvcEvntLog.setProcessedIndicator("N");

		accountSrvcEvntLog.setSubjectAreaCode(AccountServiceEventLogConstants.MUW_EMPLOYER_CONFIGURATIONS_SAC);

		if (beforeEmployer == null && afterEmployer != null) {
			accountSrvcEvntLog.setSubjectAreaId(afterEmployer.get("employerId").asInt());
			accountSrvcEvntLog.setChangeEventCode(AccountServiceEventLogConstants.CREATE);
		} else if (beforeEmployer != null && afterEmployer == null) {
			// TODO we currently don't support delete for Employers
			/*
			 * accountSrvcEvntLog.setSubjectAreaId(beforeEmployer.getEmployerId(
			 * )); accountSrvcEvntLog.setChangeEventCode(
			 * AccountServiceEventLogConstants.DELETE);
			 */
		} else if (beforeEmployer != null && afterEmployer != null) {
			if (!beforeEmployer.get("employerId").equals(afterEmployer.get("employerId"))) {
				// TODO do we need this?
				throw new DataIntegrityViolationException(
						"The before and after MUW Employer IDs don't match, something loco happened.");
			}

			accountSrvcEvntLog.setSubjectAreaId(afterEmployer.get("employerId").asInt());
			accountSrvcEvntLog.setChangeEventCode(AccountServiceEventLogConstants.UPDATE);
		}

		// TODO need some try/catch logic here surround the Utils I think

		String beforeEmployerString = JsonParserUtil.convertObjectToJsonString(beforeEmployer);
		String afterEmployerString = JsonParserUtil.convertObjectToJsonString(afterEmployer);

		accountSrvcEvntLog.setBeforeEventData(beforeEmployerString);
		accountSrvcEvntLog.setAfterEventData(afterEmployerString);

		JsonNode beforeEmployerNode = JsonParserUtil.stringtoJsonNode(beforeEmployerString);
		JsonNode afterEmployerNode = JsonParserUtil.stringtoJsonNode(afterEmployerString);

		JsonNode employerJsonPatchNode = JsonDiff.asJson(beforeEmployerNode, afterEmployerNode);
		accountSrvcEvntLog.setChangeSet(employerJsonPatchNode.toString());

		return accountSrvcEvntLog;
	}

}
