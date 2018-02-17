package com.lmig.ci.lmbc.empr.muw.employee.service;

import org.springframework.http.ResponseEntity;

import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResource;

public interface MuwEmployeeContactService {

	ResponseEntity<PartyResource> create(EmployeeResource employee);

	ResponseEntity<PartyResource> update(EmployeeResource employee);

	ResponseEntity<PartyResource> getParty(String partyRefTypCode, String partyRefNum);

}