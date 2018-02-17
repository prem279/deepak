
package com.lmig.ci.lmbc.empr.muw.employee.service;

import org.springframework.http.ResponseEntity;

import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.exception.BadRequestException;

public interface EmployeeEmployerService {

	public ResponseEntity<EmployeeResource> getEmployeeResource(String div, String serial, String lastName,
			String employeeSsn, String employeeIdentificationNumber) throws BadRequestException;

	public ResponseEntity<EmployeeResource> createEmployee(EmployeeResource employeeResource);

	public ResponseEntity<EmployeeResource> updateEmployee(EmployeeResource employeeResource,
			Integer employerEmployeeId);

	public ResponseEntity<EmployeeResource> getEmployee(Integer employerEmployeeId);
}
