package com.lmig.ci.lmbc.empr.muw.account.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLog;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLogConstants;
import com.lmig.ci.lmbc.empr.muw.account.repository.AccountServiceEventLogRepository;
import com.lmig.ci.lmbc.empr.muw.account.service.processor.AccountServiceChangeProcessor;

@Service
public class MuwEmployerConfigurationEventServiceImpl implements MuwEmployerConfigurationEventService {

	private static final Logger LOG = LoggerFactory.getLogger(MuwEmployerConfigurationEventServiceImpl.class);

	@Autowired
	private AccountServiceEventLogRepository accountServiceEventLogRepository;

	@Autowired
	private AccountServiceChangeProcessor changeProcessor;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AccountServiceEventLog createEvent(JsonNode beforeEmployer, JsonNode afterEmployer, String eventType) {

		AccountServiceEventLog event = new AccountServiceEventLog();
		if (eventType.equals(AccountServiceEventLogConstants.UPDATE)) {
			try {
				event = publishChange(beforeEmployer, afterEmployer);
				if (event == null) {
					LOG.error("Issue while publishing Update change set. Employer Request: "
							+ afterEmployer.get("employerId"));
				}
			} catch (Exception e) {
				LOG.error("Error Building Change Set for Employer Request " + afterEmployer.get("employerId"));
				LOG.error(e.toString());
			}
		} else {
			try {
				event = publishChange(null, afterEmployer);
				if (event == null) {
					LOG.error("Issue while publishing INSERT change set. Employer Request: "
							+ afterEmployer.get("employerId"));
				}
			} catch (Exception e) {
				LOG.error("Error Building Change Set for Employer Request " + afterEmployer.get("employerId"));
				LOG.error(e.toString());
			}

		}
		return event;

	}

	@Override
	public AccountServiceEventLog publishChange(JsonNode beforeEmployer, JsonNode afterEmployer) {
		AccountServiceEventLog accountServiceEventLog = new AccountServiceEventLog();
		try {
			accountServiceEventLog = changeProcessor.processChange(beforeEmployer, afterEmployer);
			// TODO is there a reason we are catching Throwable? Typically this
			// would just be Exception, unless we are at the higest level or
			// developing some sort of framework
			// Error (which Throwable can catch) is unrecoverable so we are
			// screwed anyway :)
			accountServiceEventLogRepository.save(accountServiceEventLog);
		} catch (Throwable e) {
			LOG.error("Unable to process event changes -- " + e.getMessage());
			return null;
		}

		return accountServiceEventLog;
	}
}
