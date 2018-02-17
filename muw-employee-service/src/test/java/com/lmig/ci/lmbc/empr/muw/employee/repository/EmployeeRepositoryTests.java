package com.lmig.ci.lmbc.empr.muw.employee.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.lmig.ci.lmbc.empr.muw.employee.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.employee.domain.Employee;

@ActiveProfiles(EnvironmentConstants.JUNIT)
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTests {

	@Autowired
	public WebApplicationContext context;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void testContext() {
		assertNotNull(context);
	}

	@Test
	public void testRepository() {

		Employee employee = new Employee();

		employee.setEmployerEmployeeId(111);
		employee.setEmployerId(RepositoryTestsCommon.EMPLOYER_ID);
		employee.setEmployeeSsn("123456789");
		employee.setHireDate(new Date(new java.util.Date().getTime()));
		employee.setCommunicationMethodCode("EML");
		employee.setConcurrencyQuantity(3);
		employee.setLastUpdatedUserIdNumber("N0296170");
		employee.setCreateUserIdNumber("n0123456");
		RepositoryTestsCommon.setDateFields(employee);

		// Save Employee
		long employeeCount = employeeRepository.count();
		Employee savedEmployee = employeeRepository.save(employee);
		assertEquals(employeeRepository.count(), employeeCount + 1);
		assertEquals(employee.getCommunicationMethodCode(), savedEmployee.getCommunicationMethodCode());

		// Update Employee
		savedEmployee.setCommunicationMethodCode("USP");
		employeeRepository.save(savedEmployee);
		Employee updatedEmployee = employeeRepository.findOne(savedEmployee.getEmployerEmployeeId());
		assertNotEquals(employee.getCommunicationMethodCode(), updatedEmployee.getCommunicationMethodCode());

		// Delete Employee
		employeeRepository.delete(updatedEmployee);
		assertEquals(employeeRepository.count(), employeeCount);

	}
}
