package com.lmig.ci.lmbc.empr.muw.employee.domain;

import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmig.ci.lmbc.empr.muw.employee.MuwEmployeeServiceTests;
import com.lmig.ci.lmbc.empr.muw.employee.repository.EmployeeRepository;
import com.lmig.ci.lmbc.empr.muw.employee.repository.RepositoryTestsCommon;

public class MuwEmployeeServiceAuditFieldTests extends MuwEmployeeServiceTests {
	@Autowired
	EmployeeRepository empRepostiory;

	@Test
	public void testAuditFields() throws Exception {

		Date beforeSaveTime = new Date(new java.util.Date().getTime());

		Employee employee = new Employee();
		employee.setEmployerId(RepositoryTestsCommon.EMPLOYER_ID);
		employee.setEmployeeSsn("123456789");
		employee.setHireDate(new Date(new java.util.Date().getTime()));
		employee.setCommunicationMethodCode("EML");
		employee.setConcurrencyQuantity(3);
		employee.setLastUpdatedUserIdNumber("N0296170");
		employee.setCreateUserIdNumber("n0123456");

		// wrap in sleep to ensure time delay for compare
		TimeUnit.SECONDS.sleep(1);
		Employee savedEmp = empRepostiory.saveAndFlush(employee);
		TimeUnit.SECONDS.sleep(1);

		Date afterSaveTime = new Date(new java.util.Date().getTime());

		// test created fields
		assertTrue(savedEmp.getCreateUserIdNumber().equals("JUNIT"));
		assertTrue(savedEmp.getCreateDatetime().compareTo(beforeSaveTime) > 0);
		assertTrue(savedEmp.getCreateDatetime().compareTo(afterSaveTime) < 0);

		// test updated fields
		savedEmp.setEmployeeClassCode("ABCD");

		Date beforeUpdateTime = new Date(new java.util.Date().getTime());

		// wrap in sleep to ensure time delay for compare
		TimeUnit.SECONDS.sleep(1);
		savedEmp = empRepostiory.saveAndFlush(savedEmp);
		TimeUnit.SECONDS.sleep(1);

		Date afterUpdateTime = new Date(new java.util.Date().getTime());

		assertTrue(savedEmp.getLastUpdatedUserIdNumber().equals("JUNIT"));
		assertTrue(savedEmp.getLastUpdateDatetime().compareTo(beforeUpdateTime) > 0);
		assertTrue(savedEmp.getLastUpdateDatetime().compareTo(afterUpdateTime) < 0);
	}
}
