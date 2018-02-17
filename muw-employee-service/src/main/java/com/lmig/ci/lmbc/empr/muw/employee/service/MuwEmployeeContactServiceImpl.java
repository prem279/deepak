package com.lmig.ci.lmbc.empr.muw.employee.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.lmig.ci.lmbc.empr.muw.contact.resource.ContactDetailsResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyEmailAddressActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyEmailAddressResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhoneNumActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhoneNumberResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyReferenceResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmailAddressResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.employee.api.PhoneNumberResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.PhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.employee.config.ServiceProperties;

@Service
public class MuwEmployeeContactServiceImpl implements MuwEmployeeContactService {

	@Autowired
	private ServiceProperties serviceProps;

	@Autowired
	private EmployeeResourceAssembler employeeResourceAssembler;

	private static final Logger logger = LoggerFactory.getLogger(MuwEmployeeContactServiceImpl.class);

	private static final String MUW_EE_ACTIVITY = "MUWEE";

	private static final String SSN = "SSN";

	@Override
	public ResponseEntity<PartyResource> create(EmployeeResource employee) {

		PartyResource partyResource = employeeResourceAssembler.createPartyResource(employee);
		ContactDetailsResource contactDetailsResource = new ContactDetailsResource();
		contactDetailsResource.setParty(partyResource);
		ResponseEntity<PartyResource> response = createContactDetailsResource(contactDetailsResource);

		if (response.getStatusCode() == HttpStatus.CONFLICT) {
			PartyResource party = getParty(SSN, employee.getEmployeeSsn()).getBody();
			for (PartyReferenceResource partyReference : party.getPartyReferences()) {
				if (partyReference.getReferenceType().equals(MUW_EE_ACTIVITY)
						&& employee.getEmployerEmployeeId().toString().equals(partyReference.getReferenceNumber())) {
					// This was a true duplicate, return 409
					return response;
				}
			}
			// Existing contact in the contact service for SSN, but not tied to
			// employee Id. Update contact to add employee Id to party
			// references
			response = update(employee);
		}

		return response;
	}

	@Override
	public ResponseEntity<PartyResource> getParty(String partyRefTypCode, String partyRefNum) {
		String url = serviceProps.getContactServiceUrl()
				+ "contacts/search/findContactByReferenceTypeAndReferenceNumber?" + "referenceType=" + partyRefTypCode
				+ "&referenceNumber=" + partyRefNum;

		return callContactService(url, HttpMethod.GET, null);
	}

	public ResponseEntity<PartyResource> createContactDetailsResource(ContactDetailsResource contactDetailsResource) {
		String url = serviceProps.getContactServiceUrl() + "contact_submission";
		return callContactService(url, HttpMethod.POST, contactDetailsResource);
	}

	private ResponseEntity<PartyResource> callContactService(String url, HttpMethod method,
			ContactDetailsResource contactDetailsResource) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.registerModule(new Jackson2HalModule());

			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

			converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));

			converter.setObjectMapper(mapper);

			RestTemplate restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
			for (HttpMessageConverter<?> loopConverter : converters) {
				if (loopConverter instanceof MappingJackson2HttpMessageConverter) {
					MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) loopConverter;
					jsonConverter.setObjectMapper(new ObjectMapper());
					jsonConverter.setSupportedMediaTypes(ImmutableList.of(MediaType.APPLICATION_JSON));
				}
			}

			HttpHeaders headers = buildHttpHeaders();

			try {
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				restTemplate.setRequestFactory(requestFactory);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			HttpEntity<ContactDetailsResource> request = new HttpEntity<ContactDetailsResource>(contactDetailsResource,
					headers);

			ResponseEntity<ContactDetailsResource> responseEntity = restTemplate.exchange(url, method, request,
					ContactDetailsResource.class);

			ResponseEntity<PartyResource> returnResponseEntity = new ResponseEntity<PartyResource>(
					responseEntity.getBody().getParty(), responseEntity.getStatusCode());

			return returnResponseEntity;

		} catch (HttpClientErrorException e) {

			return ResponseEntity.status(e.getStatusCode()).body(null);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	private HttpHeaders buildHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();

		String id = serviceProps.getEmployerServiceId();
		String pw = serviceProps.getEmployerServicePassword();
		String creds = id + ":" + pw;
		byte[] credsBytes = creds.getBytes();
		byte[] base64CredsBytes = Base64.getEncoder().encode(credsBytes);
		String base64Creds = new String(base64CredsBytes);

		headers.add("Authorization", "Basic " + base64Creds);

		// UserDetails activeUser =
		// (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// headers.add("lmig-on-behalf-of-user", activeUser.getUsername());

		List<MediaType> acceptMediaTypes = new ArrayList<MediaType>();
		acceptMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(acceptMediaTypes);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@Override
	public ResponseEntity<PartyResource> update(EmployeeResource employeeResource) {
		ResponseEntity<PartyResource> partyResourceEntity = getParty(MUW_EE_ACTIVITY,
				employeeResource.getEmployerEmployeeId().toString());
		PartyResource partyResource = partyResourceEntity.getBody();

		// If no party found with Employee Id, try using SSN
		if (partyResource == null) {
			partyResourceEntity = getParty(SSN, employeeResource.getEmployeeSsn());
			partyResource = partyResourceEntity.getBody();
		}
		if (null != partyResource) {
			employeeResourceAssembler.updatePartyNames(partyResource, employeeResource);
			boolean foundSsn = false;
			boolean foundEmployeeId = false;
			for (PartyReferenceResource partyReferenceResource : partyResource.getPartyReferences()) {
				if (partyReferenceResource.getReferenceType().equals(SSN)) {
					foundSsn = true;
					if (!partyReferenceResource.getReferenceNumber().equals(employeeResource.getEmployeeSsn())) {
						employeeResourceAssembler.updatePartyReferenceResource(partyReferenceResource, SSN,
								employeeResource.getEmployeeSsn());
					}
				} else if (partyReferenceResource.getReferenceType().equals(MUW_EE_ACTIVITY) && partyReferenceResource
						.getReferenceNumber().equals(employeeResource.getEmployerEmployeeId().toString())) {
					foundEmployeeId = true;
				}
			}

			if (!foundSsn) {
				partyResource.getPartyReferences().add(
						employeeResourceAssembler.createPartyReferenceResource(SSN, employeeResource.getEmployeeSsn()));
			}

			if (!foundEmployeeId) {
				partyResource.getPartyReferences().add(employeeResourceAssembler.createPartyReferenceResource(
						MUW_EE_ACTIVITY, employeeResource.getEmployerEmployeeId().toString()));
			}

			Integer partyId = partyResource.getPartyId();
			PhysicalAddressResource homeAddress = employeeResource.getEmployeeHomeAddress();
			PhysicalAddressResource mailingAddress = employeeResource.getEmployeeMailingAddress();
			List<Object> objectsToRemove = new ArrayList<>();
			boolean homeAddressUpdated = false;
			boolean mailingAddressUpdated = false;
			if (!partyResource.getPartyPhysicalAddresses().isEmpty()) {
				for (PartyPhysicalAddressResource partyPhysicalAddressResource : partyResource
						.getPartyPhysicalAddresses()) {
					if (homeAddress != null && !homeAddress.isNull()
							&& partyPhysicalAddressResource.getAddressReasonCode().equals("HOME")) {
						employeeResourceAssembler.updatePartyPhysicalAddressResource(partyPhysicalAddressResource,
								homeAddress, "HOME");
						homeAddressUpdated = true;
					} else if (mailingAddress != null && !mailingAddress.isNull()
							&& partyPhysicalAddressResource.getAddressReasonCode().equals("MAILG")) {
						employeeResourceAssembler.updatePartyPhysicalAddressResource(partyPhysicalAddressResource,
								mailingAddress, "MAILG");
						mailingAddressUpdated = true;
					} else if (!partyPhysicalAddressResource.getPartyPhysicalAddressActy().isEmpty()) {
						for (PartyPhysicalAddressActyResource partyPhysicalAddressActyResource : partyPhysicalAddressResource
								.getPartyPhysicalAddressActy()) {
							if (partyPhysicalAddressActyResource.getActivityType().equals(MUW_EE_ACTIVITY)) {
								objectsToRemove.add(partyPhysicalAddressResource);
							}
						}
					}
				}
			} else {
				if (homeAddress != null && !homeAddress.isNull()) {
					partyResource.getPartyPhysicalAddresses()
							.add(employeeResourceAssembler.createPartyPhysicalAddressResource(homeAddress, "HOME"));
				}
				if (mailingAddress != null && !mailingAddress.isNull()) {
					partyResource.getPartyPhysicalAddresses()
							.add(employeeResourceAssembler.createPartyPhysicalAddressResource(mailingAddress, "MAILG"));
				}
			}
			for (Object object : objectsToRemove) {
				partyResource.getPartyPhysicalAddresses().remove(object);
			}
			objectsToRemove.clear();

			if (homeAddress != null && !homeAddress.isNull() && !homeAddressUpdated) {
				partyResource.getPartyPhysicalAddresses()
						.add(employeeResourceAssembler.createPartyPhysicalAddressResource(homeAddress, "HOME"));
			}
			if (mailingAddress != null && !mailingAddress.isNull() && !mailingAddressUpdated) {
				partyResource.getPartyPhysicalAddresses()
						.add(employeeResourceAssembler.createPartyPhysicalAddressResource(mailingAddress, "MAILG"));
			}

			EmailAddressResource emailAddress = employeeResource.getEmployeeEmailAddress();
			if (!partyResource.getPartyEmailAddresses().isEmpty()) {
				for (PartyEmailAddressResource partyEmailAddressResource : partyResource.getPartyEmailAddresses()) {
					if (null != emailAddress && partyEmailAddressResource.getPartyEmailAddressId()
							.equals(emailAddress.getPartyEmailAddressId())) {
						employeeResourceAssembler.updatePartyEmailAddressResource(partyEmailAddressResource,
								emailAddress);
					} else if (!partyEmailAddressResource.getPartyEmailAddressActy().isEmpty()) {
						for (PartyEmailAddressActyResource partyEmailAddressActyResource : partyEmailAddressResource
								.getPartyEmailAddressActy()) {
							if (partyEmailAddressActyResource.getActivityType().equals(MUW_EE_ACTIVITY)) {
								objectsToRemove.add(partyEmailAddressResource);
							}
						}
					}
				}

			} else if (emailAddress != null && !emailAddress.isNull()) {
				List<PartyEmailAddressResource> partyEmailAddressResources = new ArrayList<>();
				partyEmailAddressResources.add(employeeResourceAssembler.createPartyEmailAddressResource(emailAddress));
				partyResource.setPartyEmailAddresses(partyEmailAddressResources);
			}

			for (Object object : objectsToRemove) {
				partyResource.getPartyEmailAddresses().remove(object);
			}
			objectsToRemove.clear();

			PhoneNumberResource phoneNumberResource = employeeResource.getEmployeeTelephone();
			if (!partyResource.getPartyPhoneNumbers().isEmpty()) {
				for (PartyPhoneNumberResource partyPhoneNumberResource : partyResource.getPartyPhoneNumbers()) {
					if (phoneNumberResource != null && !phoneNumberResource.isNull() && partyPhoneNumberResource
							.getPartyPhoneNumberId().equals(phoneNumberResource.getPartyPhoneNumberId())) {
						employeeResourceAssembler.removeTextVoiceIndicator(partyPhoneNumberResource);
						employeeResourceAssembler.updatePartyPhoneNumberResource(partyPhoneNumberResource,
								phoneNumberResource);
					} else if (!partyPhoneNumberResource.getPartyPhoneNumAct().isEmpty()) {
						for (PartyPhoneNumActyResource partyPhoneNumActyResource : partyPhoneNumberResource
								.getPartyPhoneNumAct()) {
							if (partyPhoneNumActyResource.getActivityType().equals(MUW_EE_ACTIVITY)) {
								objectsToRemove.add(partyPhoneNumberResource);
							}
						}
					}
				}
			} else if (phoneNumberResource != null && !phoneNumberResource.isNull()) {
				List<PartyPhoneNumberResource> partyPhoneNumberResources = new ArrayList<>();
				partyPhoneNumberResources
						.add(employeeResourceAssembler.createPartyPhoneNumberResource(phoneNumberResource));
				partyResource.setPartyPhoneNumbers(partyPhoneNumberResources);
			}

			for (Object object : objectsToRemove) {
				partyResource.getPartyPhoneNumbers().remove(object);
			}
			objectsToRemove.clear();

			String Ssn = employeeResource.getEmployeeSsn();
			for (PartyReferenceResource partyReferenceResource : partyResource.getPartyReferences()) {
				if (partyReferenceResource.getReferenceType().equals(SSN)
						&& !partyReferenceResource.getReferenceNumber().equals(Ssn)) {
					partyReferenceResource.setReferenceNumber(Ssn);
				}
			}

			return updatePartyResource(partyId, partyResource);

		} else {

			return create(employeeResource);

		}

	}

	public ResponseEntity<PartyResource> updatePartyResource(Integer contactId, PartyResource partyResourceEntity) {
		// HttpMethod method = HttpMethod.PUT;
		String url = serviceProps.getContactServiceUrl() + "contact_submission";
		ContactDetailsResource contactDetails = new ContactDetailsResource();

		url = url + "/" + contactId;
		contactDetails.setParty(partyResourceEntity);
		return callContactService(url, HttpMethod.PUT, contactDetails);
	}

}
