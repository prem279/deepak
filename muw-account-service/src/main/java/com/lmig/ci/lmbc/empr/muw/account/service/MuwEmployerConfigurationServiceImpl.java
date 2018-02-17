/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 11, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.account.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.account.api.AccountServiceEventLogResource;
import com.lmig.ci.lmbc.empr.muw.account.api.EmployerResource;
import com.lmig.ci.lmbc.empr.muw.account.api.EmployerResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.account.api.exception.RequestedResourceNotFoundException;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLog;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLogConstants;
import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;
import com.lmig.ci.lmbc.empr.muw.account.domain.EoiProduct;
import com.lmig.ci.lmbc.empr.muw.account.repository.AccountServiceEventLogRepository;
import com.lmig.ci.lmbc.empr.muw.account.repository.EmployerRepository;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;

/**
 * @author n0159479
 *
 */
@Service
public class MuwEmployerConfigurationServiceImpl implements MuwEmployerConfigurationService {

	private static final Logger LOG = LoggerFactory.getLogger(MuwEmployerConfigurationServiceImpl.class);

	@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	private EmployerResourceAssembler employerAssembler;

	@Autowired
	private AccountServiceEventLogRepository accountServiceEventLogRepository;

	@Autowired
	private MuwEmployerConfigurationContactService contactService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	MuwEmployerConfigurationEventService muwEmployerConfigurationEventService;

	@Override
	@Transactional
	public ResponseEntity<EmployerResource> create(Employer employer, EmployerResource resource)
			throws DataIntegrityViolationException {

		LOG.debug("CREATE EMPLOYER: " + employer);

		List<EoiProduct> products = employer.getProducts();
		if (products != null && !products.isEmpty()) {
			for (EoiProduct ep : products) {
				ep.setEmployer(employer);
			}
		} else {
			throw new DataIntegrityViolationException(EnvironmentConstants.NO_PRODUCTS_ERROR);
		}

		Employer savedEmployer = employerRepository.saveAndFlush(employer);
		entityManager.refresh(savedEmployer);
		EmployerResource employerResource = employerAssembler.toResource(savedEmployer);
		muwEmployerConfigurationEventService.createEvent(JsonParserUtil.convertObjectToJson(new Employer()),
				JsonParserUtil.convertObjectToJson(savedEmployer), AccountServiceEventLogConstants.CREATE);

		employerResource.setAddresses(resource.getAddresses());

		ResponseEntity<PartyResource> responsePartyResource = contactService.create(employerResource);
		if (responsePartyResource.getStatusCode() == null
				|| (responsePartyResource.getStatusCode() != HttpStatus.CREATED
						&& responsePartyResource.getStatusCode() != HttpStatus.OK)) {
			throw new DataIntegrityViolationException("Could not save contact details.");
		}
		employerResource.setAddresses(employerAssembler.createPhysicalAddressResource(responsePartyResource.getBody()));
		ResponseEntity<EmployerResource> returnResponseEntity = new ResponseEntity<EmployerResource>(employerResource,
				HttpStatus.CREATED);

		return returnResponseEntity;
	}

	@Transactional
	@Override
	public ResponseEntity<EmployerResource> update(Integer employerId, Employer employer, EmployerResource resource) {

		LOG.debug("UPDATE EMPLOYER: " + employer);
		Employer existingEmployer = employerRepository.findOne(employerId);
		if (null == existingEmployer || !employerId.equals(existingEmployer.getEmployerId())) {
			throw new RequestedResourceNotFoundException("No records returned for given employer ID: " + employerId);
		}
		JsonNode beforeEmployer = JsonParserUtil.convertObjectToJson(existingEmployer);

		List<EoiProduct> products = employer.getProducts();
		if (products != null && !products.isEmpty()) {
			for (EoiProduct ep : products) {
				ep.setEmployer(employer);
			}
		} else {
			throw new DataIntegrityViolationException(EnvironmentConstants.NO_PRODUCTS_ERROR);
		}

		Employer savedEmployer = employerRepository.saveAndFlush(employer);
		entityManager.refresh(savedEmployer);

		JsonNode afterEmployer = JsonParserUtil.convertObjectToJson(savedEmployer);
		EmployerResource employerResource = employerAssembler.toResource(savedEmployer);

		employerResource.setAddresses(resource.getAddresses());
		ResponseEntity<PartyResource> responsePartyResource = contactService.update(employerResource);
		if (responsePartyResource.getStatusCode() == null
				|| (responsePartyResource.getStatusCode() != HttpStatus.CREATED
						&& responsePartyResource.getStatusCode() != HttpStatus.OK)) {
			throw new DataIntegrityViolationException("Could not save contact details.");
		}
		employerResource.setAddresses(employerAssembler.createPhysicalAddressResource(responsePartyResource.getBody()));

		muwEmployerConfigurationEventService.createEvent(beforeEmployer, afterEmployer,
				AccountServiceEventLogConstants.UPDATE);
		ResponseEntity<EmployerResource> returnResponseEntity = new ResponseEntity<EmployerResource>(employerResource,
				HttpStatus.OK);

		return returnResponseEntity;
	}

	/**
	 * @param employerId
	 * @return Employer Configuration with the given employerId
	 */
	@Transactional
	@Override
	public ResponseEntity<EmployerResource> getEmployerConfiguration(Integer employerId) {
		Employer employer = employerRepository.findOne(employerId);

		if (employer == null) {
			throw new RequestedResourceNotFoundException("No records returned for given employerId: " + employerId);
		}

		EmployerResource employerResource = employerAssembler.toResource(employer);
		ResponseEntity<EmployerResource> returnResponseEntity = new ResponseEntity<EmployerResource>(employerResource,
				HttpStatus.OK);

		return returnResponseEntity;
	}

	@Transactional
	@Override
	public HttpEntity<EmployerResource> findByDivAndSerial(String div, String serial) {
		Employer employer = employerRepository
				.findOneByStakeholderLedgerSerialNumberPkEmployerStakeholderLedgerNumberAndStakeholderLedgerSerialNumberPkEmployerStakeholderSerialNumber(
						div, serial);
		if (employer == null) {
			throw new RequestedResourceNotFoundException("No records returned for given Div and serial ");
		}
		EmployerResource employerResource = employerAssembler.toResource(employer);
		ResponseEntity<EmployerResource> returnResponseEntity = new ResponseEntity<EmployerResource>(employerResource,
				HttpStatus.OK);
		return returnResponseEntity;
	}

	@Override
	public ResponseEntity<List<AccountServiceEventLogResource>> getAccountEvents(Integer eventId) {
		List<AccountServiceEventLog> accountEvents = accountServiceEventLogRepository
				.findAllByEventIdGreaterThan(eventId);
		List<AccountServiceEventLogResource> accountEventResources = new ArrayList<>();
		for (AccountServiceEventLog accountEvent : accountEvents) {
			accountEventResources.add(employerAssembler.toAccountEventResource(accountEvent));
		}
		return new ResponseEntity<>(accountEventResources, HttpStatus.OK);
	}

}
