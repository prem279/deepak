/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on April 04, 2017
 */

package com.lmig.ci.lmbc.empr.muw.employee.repository;

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

import com.lmig.ci.lmbc.empr.muw.employee.MuwEmployeeServiceTests;
import com.lmig.ci.lmbc.empr.muw.employee.domain.EmployeeEvent;

public class EmployeeEventRepositoryTests extends MuwEmployeeServiceTests {

	@Autowired
	private EmployeeEventRepository empRepository;

	@Autowired
	public WebApplicationContext context;

	@Test
	public void testContext() {
		assertNotNull(context);
	}

	@Test
	@Transactional
	public void testRepositoryCreate() {
		EmployeeEvent evt = new EmployeeEvent();
		evt.setBusinessEventCode("BUSEVTCDE");
		evt.setChangeEventCode("CREATE");
		evt.setChangeSet("change set representation of json patch");
		evt.setProcessedIndicator("N");
		evt.setSubjectAreaId(123);
		evt.setSubjectAreaCode("abc");
		evt.setChangeSet("eventData");
		evt.setLockOwnerName("name");
		evt.setCreateUserIdNumber("JUNIT");

		long count = empRepository.count();

		// Create
		empRepository.saveAndFlush(evt);
		assertEquals(empRepository.count(), count + 1);
		List<EmployeeEvent> act2 = empRepository.findBySubjectAreaId(evt.getSubjectAreaId());
		for (int i = 0; i < act2.size(); i++) {
			assertEquals(act2.get(i).getChangeEventCode(), ("CREATE"));
		}
		// Update
		evt.setChangeEventCode("changed");
		empRepository.saveAndFlush(evt);

		List<EmployeeEvent> rs = empRepository.findBySubjectAreaId(123);
		System.out.println(rs);

		// Find All
		List<EmployeeEvent> acts = empRepository.findAll();
		EmployeeEvent temp = null;
		Iterator<EmployeeEvent> itr = acts.iterator();
		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getSubjectAreaId() == evt.getSubjectAreaId()) {
				break;
			}
		}
		assertEquals(temp.getChangeEventCode(), ("changed"));

		// Delete
		empRepository.delete(evt);

		assertEquals(empRepository.count(), count);
	}
}
