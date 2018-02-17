/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 16 Nov 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.service.processor;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.JsonDiff;
import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.employee.EmployeeEventConstants;
import com.lmig.ci.lmbc.empr.muw.employee.domain.Employee;
import com.lmig.ci.lmbc.empr.muw.employee.domain.EmployeeEvent;

/**
 * @author n0256907
 *
 */
@Component
public class EmployeeChangeProcessor {

	private Logger logger = LoggerFactory.getLogger(EmployeeChangeProcessor.class);

	public EmployeeEvent processChange(Employee beforeEmployee, Employee afterEmployee) {
		return buildEventLog(beforeEmployee, afterEmployee);
	}

	private EmployeeEvent buildEventLog(Employee beforeEmployee, Employee afterEmployee) {
		EmployeeEvent employeeEvent = new EmployeeEvent();
		employeeEvent.setProcessedIndicator("N");
		employeeEvent.setSubjectAreaCode("EMPLOYEE");

		// TODO: Fix auditing fields
		employeeEvent.setCreateUserIdNumber("SYSTEM");
		employeeEvent.setCreateDatetime(new Date());

		if (beforeEmployee == null && afterEmployee != null) {
			employeeEvent.setSubjectAreaId(afterEmployee.getEmployerEmployeeId());
			employeeEvent.setChangeEventCode(EmployeeEventConstants.CREATE);
		} else if (beforeEmployee != null && afterEmployee == null) {
			// TODO we currently don't support delete for Employees
			logger.error("We do not currently support delete for employees.  "
					+ "Eventing needs to be updated to support it.");
			return null;
		} else if (beforeEmployee != null && afterEmployee != null) {
			if (!beforeEmployee.getEmployerEmployeeId().equals(afterEmployee.getEmployerEmployeeId())) {
				throw new DataIntegrityViolationException(
						"The before and after MUW Employee IDs don't match, something loco happened.");
			}

			employeeEvent.setSubjectAreaId(beforeEmployee.getEmployerEmployeeId());
			employeeEvent.setChangeEventCode(EmployeeEventConstants.UPDATE);
		}

		try {
			String beforeEmployeeString = JsonParserUtil.convertObjectToJsonString(beforeEmployee);
			String afterEmployeeString = JsonParserUtil.convertObjectToJsonString(afterEmployee);

			employeeEvent.setBeforeEventData(beforeEmployeeString);
			employeeEvent.setAfterEventData(afterEmployeeString);

			JsonNode beforeEmployeeNode = JsonParserUtil.stringtoJsonNode(beforeEmployeeString);
			JsonNode afterEmployeeNode = JsonParserUtil.stringtoJsonNode(afterEmployeeString);

			JsonNode employeeJsonPatchNode = JsonDiff.asJson(beforeEmployeeNode, afterEmployeeNode);
			employeeEvent.setChangeSet(employeeJsonPatchNode.toString());
		} catch (JsonParseException jpe) {
			logger.error("Error parsing JSON in eventing service. " + jpe.getMessage(), jpe);
			return null;
		} catch (IOException ioe) {
			logger.error("Error processing JSON in eventing service. " + ioe.getMessage(), ioe);
			return null;
		}

		return employeeEvent;
	}

}
