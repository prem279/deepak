/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 11, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lmig.ci.lmbc.empr.common.util.BeanDefaulter;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLog;
import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyReferenceResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;

/**
 * @author n0159479
 *
 */
@Component
public class EmployerResourceAssembler extends ResourceAssemblerSupport<Employer, EmployerResource> {

	@Resource(name = "employerDataDefaulter")
	private BeanDefaulter employerDataDefaulter;

	@Autowired
	private Mapper mapper;

	public EmployerResourceAssembler() {
		super(EmployerController.class, EmployerResource.class);
	}

	@Override
	public EmployerResource toResource(Employer employer) {

		EmployerResource employerResource = new EmployerResource();

		employerResource = mapper.map(employer, com.lmig.ci.lmbc.empr.muw.account.api.EmployerResource.class);

		return employerResource;
	}

	public Employer toDomainObject(EmployerResource resource) {
		Employer domainObject = new Employer();

		// This is only a shallow copy (i.e. it will not copy the properties of
		// the Preferences object within the Employer object
		// BeanUtils.copyProperties(resource, domainObject);

		// Dozer can do a deep copy of the bean ...
		domainObject = mapper.map(resource, com.lmig.ci.lmbc.empr.muw.account.domain.Employer.class);

		if (resource != null) {
			if (resource.getAddresses() != null && resource.getAddresses().getStateProvinceCode() != null) {
				domainObject.setSitusStateCode(resource.getAddresses().getStateProvinceCode());
			} else {
				domainObject.setSitusStateCode(resource.getSitusStateCode());
			}
		}

		// employerDataDefaulter.setDefaultValues(domainObject);
		return domainObject;
	}
	/*
	 * public AccountServiceEventLog toEvent(Employer requestEntity, String
	 * eventType, Map<String, Object> changeSet) {
	 *
	 * AccountServiceEventLog event = new AccountServiceEventLog(); try { String
	 * employerRequestJson = jsonMapper.writeValueAsString(requestEntity);
	 * event.setSubjectAreaId(requestEntity.getEmployerId());
	 * event.setChangeEventCode(eventType);
	 * //event.setData(claimRequestJson.getBytes(Charsets.UTF_8)); //Stored as
	 * binary... changed to clear text event.setEventData(employerRequestJson);
	 * if (changeSet != null) {
	 * event.setChangeSet(jsonMapper.writeValueAsString(changeSet)); } } catch
	 * (IOException e) { throw new RuntimeException("ERROR Creating Event"); }
	 * return event; }
	 */

	public PartyResource createPartyResource(EmployerResource employerResource) {
		PartyResource partyResource = new PartyResource();
		partyResource.setOrganisationName(employerResource.getEmployerName());
		partyResource.setOrganisationType("CORP");
		partyResource.setPartyType("EMPLYR");

		List<PartyReferenceResource> partyReferenceResources = new ArrayList<PartyReferenceResource>();
		PartyReferenceResource partyReferenceResource1 = new PartyReferenceResource();
		partyReferenceResource1.setReferenceType("MUWEMP");
		partyReferenceResource1.setReferenceNumber("" + employerResource.getEmployerId());
		partyReferenceResources.add(partyReferenceResource1);
		PartyReferenceResource partyReferenceResource2 = new PartyReferenceResource();
		partyReferenceResource2.setReferenceType("DIVSRL");
		partyReferenceResource2.setReferenceNumber("" + employerResource.getEmployerStakeholderLedgerNumber()
				+ employerResource.getEmployerStakeholderSerialNumber());
		partyReferenceResources.add(partyReferenceResource2);
		partyResource.setPartyReferences(partyReferenceResources);

		List<PartyPhysicalAddressResource> partyPhysicalAddressResources = new ArrayList<PartyPhysicalAddressResource>();
		PartyPhysicalAddressResource partyPhysicalAddressResource = new PartyPhysicalAddressResource();
		PhysicalAddressResource physicalAddressResource = employerResource.getAddresses();
		partyPhysicalAddressResource.setAddressReasonCode(physicalAddressResource.getAddressReasCode());
		partyPhysicalAddressResource.setAddressLine1Text(physicalAddressResource.getAddressLine1Text());
		partyPhysicalAddressResource.setAddressLine2Text(physicalAddressResource.getAddressLine2Text());
		partyPhysicalAddressResource.setCityName(physicalAddressResource.getCityName());
		partyPhysicalAddressResource.setStateProvienceCode(physicalAddressResource.getStateProvinceCode());
		partyPhysicalAddressResource.setPostalCode(physicalAddressResource.getPostalCode());
		partyPhysicalAddressResource.setCountryCode(physicalAddressResource.getCountryCode());

		List<PartyPhysicalAddressActyResource> partyPhysicalAddressActyResources = new ArrayList<PartyPhysicalAddressActyResource>();
		PartyPhysicalAddressActyResource partyPhysicalAddressActyResource = new PartyPhysicalAddressActyResource();
		partyPhysicalAddressActyResource.setActivityType("MUWPA");
		partyPhysicalAddressActyResource.setActive("Y");
		partyPhysicalAddressActyResources.add(partyPhysicalAddressActyResource);
		partyPhysicalAddressResource.setPartyPhysicalAddressActy(partyPhysicalAddressActyResources);

		partyPhysicalAddressResources.add(partyPhysicalAddressResource);
		partyResource.setPartyPhysicalAddresses(partyPhysicalAddressResources);

		return partyResource;
	}

	public PhysicalAddressResource createPhysicalAddressResource(PartyResource partyResource) {
		PhysicalAddressResource physicalAddressResource = new PhysicalAddressResource();
		if (null != partyResource) {
			List<PartyPhysicalAddressResource> partyPhysicalAddressResources = partyResource
					.getPartyPhysicalAddresses();
			if (null != partyPhysicalAddressResources && !partyPhysicalAddressResources.isEmpty()) {
				for (PartyPhysicalAddressResource partyPhysicalAddressResource : partyPhysicalAddressResources) {
					for (PartyPhysicalAddressActyResource partyPhysicalAddressActyResource : partyPhysicalAddressResource
							.getPartyPhysicalAddressActy()) {
						if (null != partyPhysicalAddressActyResource
								&& partyPhysicalAddressActyResource.getActivityType().equals("MUWPA")) {
							physicalAddressResource
									.setAddressReasCode(partyPhysicalAddressResource.getAddressReasonCode());
							physicalAddressResource
									.setAddressLine1Text(partyPhysicalAddressResource.getAddressLine1Text());
							physicalAddressResource
									.setAddressLine2Text(partyPhysicalAddressResource.getAddressLine2Text());
							physicalAddressResource.setCityName(partyPhysicalAddressResource.getCityName());
							physicalAddressResource
									.setStateProvinceCode(partyPhysicalAddressResource.getStateProvienceCode());
							physicalAddressResource.setPostalCode(partyPhysicalAddressResource.getPostalCode());
							physicalAddressResource.setCountryCode(partyPhysicalAddressResource.getCountryCode());
						}
					}
				}
			}
		}
		return physicalAddressResource;
	}

	public PartyPhysicalAddressResource createPartyPhysicalAddressResource(EmployerResource employerResource) {
		PartyPhysicalAddressResource partyPhysicalAddressResource = new PartyPhysicalAddressResource();
		PhysicalAddressResource physicalAddressResource = employerResource.getAddresses();
		partyPhysicalAddressResource.setAddressReasonCode(physicalAddressResource.getAddressReasCode());
		partyPhysicalAddressResource.setAddressLine1Text(physicalAddressResource.getAddressLine1Text());
		partyPhysicalAddressResource.setAddressLine2Text(physicalAddressResource.getAddressLine2Text());
		partyPhysicalAddressResource.setCityName(physicalAddressResource.getCityName());
		partyPhysicalAddressResource.setStateProvienceCode(physicalAddressResource.getStateProvinceCode());
		partyPhysicalAddressResource.setPostalCode(physicalAddressResource.getPostalCode());
		partyPhysicalAddressResource.setCountryCode(physicalAddressResource.getCountryCode());

		List<PartyPhysicalAddressActyResource> partyPhysicalAddressActyResources = new ArrayList<PartyPhysicalAddressActyResource>();
		PartyPhysicalAddressActyResource partyPhysicalAddressActyResource = new PartyPhysicalAddressActyResource();
		partyPhysicalAddressActyResource.setActivityType("MUWPA");
		partyPhysicalAddressActyResource.setActive("Y");
		partyPhysicalAddressActyResources.add(partyPhysicalAddressActyResource);
		partyPhysicalAddressResource.setPartyPhysicalAddressActy(partyPhysicalAddressActyResources);

		return partyPhysicalAddressResource;
	}

	public AccountServiceEventLogResource toAccountEventResource(AccountServiceEventLog accountEvent) {
		return mapper.map(accountEvent, com.lmig.ci.lmbc.empr.muw.account.api.AccountServiceEventLogResource.class);
	}
}
