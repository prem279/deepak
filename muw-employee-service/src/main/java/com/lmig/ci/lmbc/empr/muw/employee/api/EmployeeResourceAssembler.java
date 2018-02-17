/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 11, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employee.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lmig.ci.lmbc.empr.common.util.BeanDefaulter;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyEmailAddressActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyEmailAddressResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhoneNumActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhoneNumberResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyReferenceResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;
import com.lmig.ci.lmbc.empr.muw.employee.domain.Employee;
import com.lmig.ci.lmbc.empr.muw.employee.domain.EmployeeEvent;

/**
 * @author n0159479
 *
 */
@Component
public class EmployeeResourceAssembler extends ResourceAssemblerSupport<Employee, EmployeeResource> {

	// TODO: Remove hard coded time zone
	private static final String TIME_ZONE = "EST";

	@Resource(name = "employeeDataDefaulter")
	private BeanDefaulter employeeDataDefaulter;

	@Autowired
	private Mapper mapper;

	private final String EMPLOYEE_ACTIVITY = "MUWEE";

	public EmployeeResourceAssembler() {
		super(EmployeeController.class, EmployeeResource.class);
	}

	@Override
	public EmployeeResource toResource(Employee employee) {

		EmployeeResource employeeResource = new EmployeeResource();

		employeeResource = mapper.map(employee, EmployeeResource.class);

		return employeeResource;
	}

	public Employee toDomainObject(EmployeeResource resource) {
		Employee domainObject = new Employee();

		domainObject = mapper.map(resource, Employee.class);

		return domainObject;
	}

	public EmployeeEventResource toEmployeeEventResource(EmployeeEvent employeeEvent) {
		return mapper.map(employeeEvent, EmployeeEventResource.class);
	}

	public PartyResource createPartyResource(EmployeeResource employeeResource) {

		PartyResource partyResource = new PartyResource();

		partyResource.setPartyType("PRSN");
		updatePartyNames(partyResource, employeeResource);

		List<PartyReferenceResource> partyReferenceResources = new ArrayList<PartyReferenceResource>();
		partyReferenceResources.add(
				createPartyReferenceResource(EMPLOYEE_ACTIVITY, employeeResource.getEmployerEmployeeId().toString()));
		partyReferenceResources.add(createPartyReferenceResource("SSN", employeeResource.getEmployeeSsn()));
		partyResource.setPartyReferences(partyReferenceResources);

		List<PartyPhysicalAddressResource> partyPhysicalAddressResources = new ArrayList<PartyPhysicalAddressResource>();

		PhysicalAddressResource physicalAddressResourceHome = employeeResource.getEmployeeHomeAddress();

		if (physicalAddressResourceHome != null && !physicalAddressResourceHome.isNull()) {
			partyPhysicalAddressResources.add(createPartyPhysicalAddressResource(physicalAddressResourceHome, "HOME"));
		}

		PhysicalAddressResource physicalAddressResourceMailing = employeeResource.getEmployeeMailingAddress();
		if (!employeeResource.isMailingAddressSame() && physicalAddressResourceMailing != null
				&& !physicalAddressResourceMailing.isNull()) {

			partyPhysicalAddressResources
					.add(createPartyPhysicalAddressResource(physicalAddressResourceMailing, "MAILG"));
		}

		partyResource.setPartyPhysicalAddresses(partyPhysicalAddressResources);

		EmailAddressResource emailAddressResource = employeeResource.getEmployeeEmailAddress();
		if (emailAddressResource != null && !emailAddressResource.isNull()) {

			List<PartyEmailAddressResource> partyEmailAddressResources = new ArrayList<PartyEmailAddressResource>();
			partyEmailAddressResources.add(createPartyEmailAddressResource(emailAddressResource));
			partyResource.setPartyEmailAddresses(partyEmailAddressResources);

		}

		PhoneNumberResource phoneNumberResource = employeeResource.getEmployeeTelephone();
		if (phoneNumberResource != null && !phoneNumberResource.isNull()) {
			List<PartyPhoneNumberResource> partyPhoneNumberResources = new ArrayList<PartyPhoneNumberResource>();
			partyPhoneNumberResources.add(createPartyPhoneNumberResource(phoneNumberResource));
			partyResource.setPartyPhoneNumbers(partyPhoneNumberResources);
		}

		return partyResource;
	}

	public PartyReferenceResource createPartyReferenceResource(String referenceType, String referenceNumber) {
		PartyReferenceResource partyReferenceResource = new PartyReferenceResource();
		partyReferenceResource.setReferenceType(referenceType);
		partyReferenceResource.setReferenceNumber(referenceNumber);
		return partyReferenceResource;
	}

	public void updatePartyReferenceResource(PartyReferenceResource partyReferenceResource, String referenceType,
			String referenceNumber) {
		partyReferenceResource.setReferenceType(referenceType);
		partyReferenceResource.setReferenceNumber(referenceNumber);
	}

	public PartyPhoneNumberResource createPartyPhoneNumberResource(PhoneNumberResource phoneNumberResource) {
		PartyPhoneNumberResource partyPhoneNumberResource = new PartyPhoneNumberResource();

		updatePartyPhoneNumberResource(partyPhoneNumberResource, phoneNumberResource);

		List<PartyPhoneNumActyResource> partyPhoneNumActyResources = new ArrayList<PartyPhoneNumActyResource>();
		PartyPhoneNumActyResource partyPhoneNumActyResource = new PartyPhoneNumActyResource();
		partyPhoneNumActyResource.setActive("Y");
		partyPhoneNumActyResource.setActivityType(EMPLOYEE_ACTIVITY);

		partyPhoneNumberResource.setPartyPhoneNumAct(partyPhoneNumActyResources);
		partyPhoneNumActyResources.add(partyPhoneNumActyResource);
		return partyPhoneNumberResource;
	}

	public PartyEmailAddressResource createPartyEmailAddressResource(EmailAddressResource emailAddressResource) {
		PartyEmailAddressResource partyEmailAddressResource = new PartyEmailAddressResource();

		updatePartyEmailAddressResource(partyEmailAddressResource, emailAddressResource);

		List<PartyEmailAddressActyResource> partyEmailActyResources = new ArrayList<PartyEmailAddressActyResource>();
		PartyEmailAddressActyResource partyEmailActyResource = new PartyEmailAddressActyResource();
		partyEmailActyResource.setActive("Y");
		partyEmailActyResource.setActivityType(EMPLOYEE_ACTIVITY);
		partyEmailActyResources.add(partyEmailActyResource);
		partyEmailAddressResource.setPartyEmailAddressActy(partyEmailActyResources);
		return partyEmailAddressResource;
	}

	public PartyPhysicalAddressResource createPartyPhysicalAddressResource(
			PhysicalAddressResource physicalAddressResource, String addressReasonCode) {
		PartyPhysicalAddressResource partyPhysicalAddressResource = new PartyPhysicalAddressResource();
		updatePartyPhysicalAddressResource(partyPhysicalAddressResource, physicalAddressResource, addressReasonCode);

		List<PartyPhysicalAddressActyResource> partyPhysicalAddressActyResources = new ArrayList<PartyPhysicalAddressActyResource>();
		PartyPhysicalAddressActyResource partyPhysicalAddressActyResource = new PartyPhysicalAddressActyResource();
		partyPhysicalAddressActyResource.setActive("Y");
		partyPhysicalAddressActyResource.setActivityType(EMPLOYEE_ACTIVITY);
		partyPhysicalAddressActyResources.add(partyPhysicalAddressActyResource);
		partyPhysicalAddressResource.setPartyPhysicalAddressActy(partyPhysicalAddressActyResources);

		return partyPhysicalAddressResource;

	}

	public PhysicalAddressResource createPhysicalAddressResource(PartyResource partyResource, String addressType) {
		PhysicalAddressResource physicalAddressResource = new PhysicalAddressResource();
		if (null != partyResource) {
			List<PartyPhysicalAddressResource> partyPhysicalAddressResources = partyResource
					.getPartyPhysicalAddresses();
			if (null != partyPhysicalAddressResources && !partyPhysicalAddressResources.isEmpty()) {
				for (PartyPhysicalAddressResource partyPhysicalAddressResource : partyPhysicalAddressResources) {
					mapAddress(physicalAddressResource, partyPhysicalAddressResource, addressType);
				}
				return physicalAddressResource;
			}
		}
		return null;
	}

	private void mapAddress(PhysicalAddressResource physicalAddressResource,
			PartyPhysicalAddressResource partyPhysicalAddressResource, String addressType) {
		if (null != partyPhysicalAddressResource.getAddressReasonCode()
				&& partyPhysicalAddressResource.getAddressReasonCode().equals(addressType)) {
			for (PartyPhysicalAddressActyResource partyPhysicalAddressActyResource : partyPhysicalAddressResource
					.getPartyPhysicalAddressActy()) {
				if (null != partyPhysicalAddressActyResource.getActivityType()
						&& partyPhysicalAddressActyResource.getActivityType().equals(EMPLOYEE_ACTIVITY)) {
					physicalAddressResource.setAddressReasCode(partyPhysicalAddressResource.getAddressReasonCode());
					physicalAddressResource.setAddressLine1Text(partyPhysicalAddressResource.getAddressLine1Text());
					physicalAddressResource.setAddressLine2Text(partyPhysicalAddressResource.getAddressLine2Text());
					physicalAddressResource.setCityName(partyPhysicalAddressResource.getCityName());
					physicalAddressResource.setStateProvinceCode(partyPhysicalAddressResource.getStateProvienceCode());
					physicalAddressResource.setPostalCode(partyPhysicalAddressResource.getPostalCode().trim());
					physicalAddressResource.setCountryCode(partyPhysicalAddressResource.getCountryCode());
					physicalAddressResource.setAddressId(partyPhysicalAddressResource.getPartyAddressId());
					partyPhysicalAddressResource
							.setPartyPhysicalAddressActy(new ArrayList<PartyPhysicalAddressActyResource>());
				}
			}
		}
	}

	public void updatePartyNames(PartyResource partyResource, EmployeeResource employeeResource) {
		partyResource.setFirstName(employeeResource.getFirstName());
		partyResource.setLastName(employeeResource.getLastName());
		partyResource.setMiddleName(employeeResource.getMiddleInitial());
	}

	public EmailAddressResource createEmailAddressResource(PartyResource partyResource) {
		EmailAddressResource emailAddressResource = new EmailAddressResource();
		if (null != partyResource) {
			List<PartyEmailAddressResource> partyEmailAddressResources = partyResource.getPartyEmailAddresses();
			if (null != partyEmailAddressResources && !partyEmailAddressResources.isEmpty()) {
				for (PartyEmailAddressResource partyEmailAddressResource : partyEmailAddressResources) {
					mapEmailAddress(partyEmailAddressResource, emailAddressResource);
				}
			}
		}
		return emailAddressResource;
	}

	private void mapEmailAddress(PartyEmailAddressResource partyEmailAddressResource,
			EmailAddressResource emailAddressResource) {
		for (PartyEmailAddressActyResource partyEmailAddressActyResource : partyEmailAddressResource
				.getPartyEmailAddressActy()) {
			if (partyEmailAddressActyResource.getActivityType().equals(EMPLOYEE_ACTIVITY)) {
				emailAddressResource
						.setElectronicAddressReasonCode(partyEmailAddressResource.getElectronicAddressReasonCode());
				emailAddressResource.setElectronicAddressText(partyEmailAddressResource.getElectronicAddressText());
				emailAddressResource.setPartyEmailAddressId(partyEmailAddressResource.getPartyEmailAddressId());
			}
		}
	}

	public PhoneNumberResource createPhoneNumberResource(PartyResource partyResource) {
		PhoneNumberResource phoneNumberResource = new PhoneNumberResource();
		if (null != partyResource) {
			List<PartyPhoneNumberResource> partyPhoneNumberResources = partyResource.getPartyPhoneNumbers();
			if (null != partyPhoneNumberResources && !partyPhoneNumberResources.isEmpty()) {
				for (PartyPhoneNumberResource partyPhoneNumberResource : partyPhoneNumberResources) {
					mapPhoneNumber(partyPhoneNumberResource, phoneNumberResource);
				}
			}
		}
		return phoneNumberResource;
	}

	private void mapPhoneNumber(PartyPhoneNumberResource partyPhoneNumberResource,
			PhoneNumberResource phoneNumberResource) {
		for (PartyPhoneNumActyResource partyPhoneNumActyResource : partyPhoneNumberResource.getPartyPhoneNumAct()) {
			if (partyPhoneNumActyResource.getActivityType().equals(EMPLOYEE_ACTIVITY)) {
				phoneNumberResource.setCountryCode(partyPhoneNumberResource.getCountryCode());
				phoneNumberResource.setExtension(partyPhoneNumberResource.getExtension());
				phoneNumberResource.setPhoneNumber(partyPhoneNumberResource.getPhoneNumber());
				phoneNumberResource.setPhoneNumberType(partyPhoneNumberResource.getPhoneNumberType());
				phoneNumberResource.setTimeZone(partyPhoneNumberResource.getTimeZone());
				phoneNumberResource.setPartyPhoneNumberId(partyPhoneNumberResource.getPartyPhoneNumberId());
			}
		}
	}

	public void updatePartyEmailAddressResource(PartyEmailAddressResource partyEmailAddressResource,
			EmailAddressResource emailAddress) {
		partyEmailAddressResource.setElectronicAddressReasonCode(emailAddress.getElectronicAddressReasonCode());
		partyEmailAddressResource.setElectronicAddressText(emailAddress.getElectronicAddressText());
	}

	public void updatePartyPhysicalAddressResource(PartyPhysicalAddressResource partyPhysicalAddressResource,
			PhysicalAddressResource physicalAddressResource, String addressReasonCode) {
		partyPhysicalAddressResource.setAddressReasonCode(addressReasonCode);
		partyPhysicalAddressResource.setAddressLine1Text(physicalAddressResource.getAddressLine1Text());
		partyPhysicalAddressResource.setAddressLine2Text(physicalAddressResource.getAddressLine2Text());
		partyPhysicalAddressResource.setCityName(physicalAddressResource.getCityName());
		partyPhysicalAddressResource.setStateProvienceCode(physicalAddressResource.getStateProvinceCode());
		partyPhysicalAddressResource.setPostalCode(physicalAddressResource.getPostalCode());
		partyPhysicalAddressResource.setCountryCode(physicalAddressResource.getCountryCode());

	}

	public void updatePartyPhoneNumberResource(PartyPhoneNumberResource partyPhoneNumberResource,
			PhoneNumberResource phoneNumberResource) {
		partyPhoneNumberResource.setCountryCode(phoneNumberResource.getCountryCode());
		partyPhoneNumberResource.setExtension(phoneNumberResource.getExtension());
		partyPhoneNumberResource.setPhoneNumber(phoneNumberResource.getPhoneNumber());
		partyPhoneNumberResource.setPhoneNumberType(phoneNumberResource.getPhoneNumberType());
		partyPhoneNumberResource.setTimeZone(TIME_ZONE);

	}

	public void removeTextVoiceIndicator(PartyPhoneNumberResource partyPhoneNumberResource) {
		partyPhoneNumberResource.setVoiceIndicator(null);
		partyPhoneNumberResource.setTextIndicator(null);
	}

}
