/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 29, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.repository;

/**
 * @author n0296170
 *
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.account.MuwAccountServiceTests;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLog;

public class AccountServiceEventLogRepositoryTests extends MuwAccountServiceTests{

	@Autowired
	private AccountServiceEventLogRepository actRepository;

	@Autowired
	public WebApplicationContext context;

	@Test
	public void testContext() {
		assertNotNull(context);
	}

	@Test
	@Transactional
	public void testRepositoryCreate() {

		AccountServiceEventLog act = new AccountServiceEventLog();
		act.setBusinessEventCode("BUSEVTCDE");
		act.setChangeEventCode("CREATE");
		act.setChangeSet("change set representation of json patch");
		act.setAfterEventData("after json representation of object");
		act.setBeforeEventData("EventData");
		act.setProcessedIndicator("N");
		act.setSubjectAreaId(123);		

		long count = actRepository.count();
		
		// Create
		actRepository.saveAndFlush(act);

		assertEquals(actRepository.count(), count + 1);

		List<AccountServiceEventLog> act2 = actRepository.findBySubjectAreaId(act.getSubjectAreaId());

		for (int i = 0; i < act2.size(); i++) {
			assertEquals(act2.get(i).getBeforeEventData(), ("EventData"));
		}

		// Update
		act.setBeforeEventData("Test Changed");
		actRepository.saveAndFlush(act);

		List<AccountServiceEventLog> rs = actRepository.findBySubjectAreaId(123);

		for (int i = 0; i < rs.size(); i++) {
			assertEquals(rs.get(i).getBeforeEventData(), ("Test Changed"));
		}

		// Find All
		List<AccountServiceEventLog> acts = actRepository.findAll();
		AccountServiceEventLog temp = null;
		Iterator<AccountServiceEventLog> itr = acts.iterator();

		// TODO not sure what the point of this is?
		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getSubjectAreaId() == act.getSubjectAreaId())
			{
				break;
			}
		}
		// TODO not sure what the point of this is?
		assertEquals(temp.getBeforeEventData(), ("Test Changed"));

		// Delete
		actRepository.delete(act);

		assertEquals(actRepository.count(), count);
	}
}
