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

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.account.MuwAccountServiceTests;
import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;
import com.lmig.ci.lmbc.empr.muw.account.domain.StakeholderLedgerSerialNumberPk;

public class EmployerRepositoryTests extends MuwAccountServiceTests{

	@Autowired
	private EmployerRepository empRepository;

	@Autowired
	public WebApplicationContext context;

	@Test
	public void testContext() {
		assertNotNull(context);
	}

	@Test
	@Transactional
	public void testRepository() {

		StakeholderLedgerSerialNumberPk spk = new StakeholderLedgerSerialNumberPk();
		spk.setEmployerStakeholderLedgerNumber("55");
		spk.setEmployerStakeholderSerialNumber("771245");
		Employer empl = new Employer();
		empl.setStakeholderLedgerSerialNumberPk(spk);

		Date dd = new Date(new java.util.Date().getTime());

		empl.setBeginDate(dd);
		empl.setCancellationDate(dd);
		empl.setCompanyAccessCode("Test");
		empl.setEmployerName("Test");
		empl.setAnnvDate(dd);
		empl.setSitusStateCode("MA");

		long count = empRepository.count();
		empRepository.save(empl);

		assertEquals(empRepository.count(), count + 1);

		Employer empl2 = empRepository.findOne(empl.getEmployerId());

		assertEquals(empl2.getEmployerName(), ("Test"));

		// update
		empl2.setEmployerName("Test Changed");
		empRepository.save(empl2);

		Employer rs = empRepository.findOne(empl2.getEmployerId());
		assertEquals(rs.getEmployerName(), ("Test Changed"));

		// FindAll
		List<Employer> empls = empRepository.findAll();
		Employer temp = null;
		Iterator<Employer> itr = empls.iterator();

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getEmployerId() == rs.getEmployerId())
				break;
		}
		assertEquals(temp.getEmployerName(), ("Test Changed"));

		// delete
		empRepository.delete(rs);

		assertEquals(empRepository.count(), count);
	}
}
