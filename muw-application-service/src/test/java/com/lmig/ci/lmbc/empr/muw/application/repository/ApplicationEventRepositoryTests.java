/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on April 04, 2017
 */

package com.lmig.ci.lmbc.empr.muw.application.repository;

/**
 * @author n0296170
 *
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationEvent;

public class ApplicationEventRepositoryTests extends MuwApplicationServiceTests {

	@Autowired
	private ApplicationEventRepository appRepository;

	@Autowired
	public WebApplicationContext context;

	@Test
	public void testContext() {
		assertNotNull(context);
	}

	@Test
	@Transactional
	public void testRepositoryCreate() {
		Date dd = new Date(new java.util.Date().getTime());

		ApplicationEvent evt = new ApplicationEvent();
		evt.setBusinessEventCode("BUSEVTCDE");
		evt.setChangeEventCode("CREATE");
		evt.setChangeSet("change set representation of json patch");
		evt.setProcessedIndicator("N");
		evt.setSubjectAreaId(123);
		evt.setSubjectAreaCode("abc");
		evt.setChangeSet("eventData");
		evt.setLockOwnerName("name");

		long count = appRepository.count();

		// Create
		appRepository.saveAndFlush(evt);
		assertEquals(appRepository.count(), count + 1);
		List<ApplicationEvent> act2 = appRepository.findBySubjectAreaId(evt.getSubjectAreaId());
		for (int i = 0; i < act2.size(); i++) {
			assertEquals(act2.get(i).getChangeEventCode(), ("CREATE"));
		}
		// Update
		evt.setChangeEventCode("changed");
		appRepository.saveAndFlush(evt);

		List<ApplicationEvent> rs = appRepository.findBySubjectAreaId(123);
		System.out.println(rs);

		// Find All
		List<ApplicationEvent> acts = appRepository.findAll();
		ApplicationEvent temp = null;
		Iterator<ApplicationEvent> itr = acts.iterator();
		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getSubjectAreaId() == evt.getSubjectAreaId()) {
				break;
			}
		}
		assertEquals(temp.getChangeEventCode(), ("changed"));

		// Delete
		appRepository.delete(evt);

		assertEquals(appRepository.count(), count);
	}
}
