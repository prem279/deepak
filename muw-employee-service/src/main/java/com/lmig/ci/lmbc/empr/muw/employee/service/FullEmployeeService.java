package com.lmig.ci.lmbc.empr.muw.employee.service;

import org.springframework.http.ResponseEntity;

import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResource;
import com.lmig.ci.lmbc.empr.muw.employee.domain.Employee;

public interface FullEmployeeService {

	ResponseEntity<EmployeeResource> create(Employee employee);
}
