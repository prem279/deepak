package com.lmig.ci.lmbc.empr.muw.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lmig.ci.lmbc.empr.muw.employee.EmployeeEventConstants;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeEventResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.employee.domain.Employee;
import com.lmig.ci.lmbc.empr.muw.employee.domain.EmployeeEvent;
import com.lmig.ci.lmbc.empr.muw.employee.repository.EmployeeEventRepository;
import com.lmig.ci.lmbc.empr.muw.employee.service.processor.EmployeeChangeProcessor;

@Service
public class EmployeeEventService {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeEventService.class);

	@Autowired
	private EmployeeChangeProcessor employeeChangeProcessor;

	@Autowired
	private EmployeeEventRepository employeeEventRepository;

	@Autowired
	private EmployeeResourceAssembler employeeResourceAssembler;

	public void createEvent(Employee beforeEmployee, Employee afterEmployee, String eventType) {
		EmployeeEvent event = new EmployeeEvent();
		if (eventType.equals(EmployeeEventConstants.UPDATE)) {
			event = publishChange(beforeEmployee, afterEmployee);
		} else {
			event = publishChange(null, afterEmployee);
		}

		if (event != null && event.getBeforeEventData() != null && !event.getBeforeEventData().equals("")
				&& event.getAfterEventData() != null && !event.getAfterEventData().equals("")) {
			employeeEventRepository.saveAndFlush(event);
		} else {
			LOG.error("Could not create employee event.  Employee ID:" + afterEmployee.getEmployerEmployeeId());
			throw new DataIntegrityViolationException(
					"Could not create employee event.  Employee ID:" + afterEmployee.getEmployerEmployeeId());
		}

	}

	private EmployeeEvent publishChange(Employee beforeEmployee, Employee afterEmployee) {
		EmployeeEvent employeeEvent = new EmployeeEvent();
		employeeEvent = employeeChangeProcessor.processChange(beforeEmployee, afterEmployee);

		return employeeEvent;
	}

	public ResponseEntity<List<EmployeeEventResource>> getEmployeeEvents(Integer eventId) {
		List<EmployeeEvent> employeeEvents = employeeEventRepository
				.findTop1000ByEventIdGreaterThanOrderByEventIdAsc(eventId);
		List<EmployeeEventResource> employeeEventResources = new ArrayList<>();
		for (EmployeeEvent employeeEvent : employeeEvents) {
			employeeEventResources.add(employeeResourceAssembler.toEmployeeEventResource(employeeEvent));
		}
		return new ResponseEntity<List<EmployeeEventResource>>(employeeEventResources, HttpStatus.OK);
	}

}
