package com.lmig.ci.lmbc.empr.muw.account.service;

import org.springframework.http.ResponseEntity;

import com.lmig.ci.lmbc.empr.muw.account.api.EmployerResource;
import com.lmig.ci.lmbc.empr.muw.account.api.PhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;

public interface MuwEmployerConfigurationContactService {
	
	ResponseEntity<PartyResource> create(EmployerResource employer);
	ResponseEntity<PhysicalAddressResource> getPrimaryAddress(String partyRefTypCode, String partyRefNum);
	ResponseEntity<PartyResource> update(EmployerResource employer);
	ResponseEntity<PartyResource> getParty(String partyRefTypCode, String partyRefNum);
}