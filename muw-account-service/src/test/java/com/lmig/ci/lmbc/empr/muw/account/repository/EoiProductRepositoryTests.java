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

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmig.ci.lmbc.empr.muw.account.MuwAccountServiceTests;
//import com.lmig.ci.lmbc.empr.muw.account.domain.Address;
import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;
import com.lmig.ci.lmbc.empr.muw.account.domain.EoiProduct;
import com.lmig.ci.lmbc.empr.muw.account.domain.StakeholderLedgerSerialNumberPk;

/**
 * @author n0296170
 *
 */
public class EoiProductRepositoryTests extends MuwAccountServiceTests {

	@Autowired
	private EmployerRepository empRepository;

	@Autowired
	private EoiProductRepository productRepository;

	@Test
	@Transactional
	public void testRepository() {

		StakeholderLedgerSerialNumberPk spk = new StakeholderLedgerSerialNumberPk();
		spk.setEmployerStakeholderLedgerNumber("55");
		spk.setEmployerStakeholderSerialNumber("123123");
		Employer empl = new Employer();
		empl.setStakeholderLedgerSerialNumberPk(spk);
		Date dd = new Date(new java.util.Date().getTime());
		empl.setBeginDate(dd);
		empl.setCancellationDate(dd);
		empl.setCompanyAccessCode("Test");
		empl.setEmployerName("Test");
		empl.setAnnvDate(dd);
		empl.setSitusStateCode("NH");

		empRepository.saveAndFlush(empl);

		EoiProduct pro = new EoiProduct();
		pro.setEmployer(empl);

		pro.setProductCode("BCL");
		// pro.setProductLabelName("Aa"); TODO: fix
		pro.setBeginDate(dd);
		pro.setCancellationDate(dd);

		long count = productRepository.count();
		productRepository.saveAndFlush(pro);

		assertEquals(productRepository.count(), count + 1);

		List<EoiProduct> lst = productRepository.findAll();

		Iterator<EoiProduct> itr = lst.iterator();
		EoiProduct temp = null;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getProductId() == pro.getProductId()) {
				break;
			}
		}
		assertEquals(temp.getEmployer().getEmployerName(), (empl.getEmployerName()));

		String newProdCode = "BCL";
		temp.setProductCode(newProdCode);
		productRepository.saveAndFlush(temp);

		EoiProduct ep = productRepository.findOne(temp.getProductId());
		assertEquals(ep.getProductCode(), (newProdCode));

		productRepository.delete(ep);
		empRepository.delete(empl);

		assertEquals(productRepository.count(), count);
	}
}
