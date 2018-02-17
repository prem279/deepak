package com.lmig.ci.lmbc.empr.muw.account.service;

import org.springframework.dao.DataIntegrityViolationException;

import com.fasterxml.jackson.databind.JsonNode;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLog;

public interface MuwEmployerConfigurationEventService {

	AccountServiceEventLog createEvent(JsonNode beforeEmployer, JsonNode afterEmployer, String eventType)
			throws DataIntegrityViolationException;

	AccountServiceEventLog publishChange(JsonNode beforeEmployer, JsonNode afterEmployer);

}