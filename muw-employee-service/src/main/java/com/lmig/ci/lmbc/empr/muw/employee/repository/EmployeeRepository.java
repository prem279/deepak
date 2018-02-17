/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 4, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.lmig.ci.lmbc.empr.muw.employee.domain.Employee;

/**
 * @author n0296170
 *
 */

@RepositoryRestResource(collectionResourceRel = "employees", path = "employees", exported = false)
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	@Override
	@RestResource
	List<Employee> findAll();

	public Employee findOneByEmployeeSsnAndEmployerId(String employeeSsn, Integer employerId);
	public Employee findOneByEmployeeIdentificationNumberAndEmployerId(String employeeIdentificationNumber, Integer employerId);
	
}
