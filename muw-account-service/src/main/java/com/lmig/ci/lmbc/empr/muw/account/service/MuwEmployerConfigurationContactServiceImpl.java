package com.lmig.ci.lmbc.empr.muw.account.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.account.api.EmployerResource;
import com.lmig.ci.lmbc.empr.muw.account.api.EmployerResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.account.api.PhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.account.config.ServiceProperties;
import com.lmig.ci.lmbc.empr.muw.contact.resource.ContactDetailsResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyReferenceResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;

@Service
public class MuwEmployerConfigurationContactServiceImpl implements MuwEmployerConfigurationContactService {

	@Autowired
	private ServiceProperties serviceProps;

	@Autowired
	private EmployerResourceAssembler employerResourceAssembler;

	@Override
	public ResponseEntity<PartyResource> create(EmployerResource employer) {

		PartyResource partyResource = employerResourceAssembler.createPartyResource(employer);
		ContactDetailsResource contactDetailsResource = new ContactDetailsResource();
		contactDetailsResource.setParty(partyResource);
		return createContactDetailsResource(contactDetailsResource);
	}

	@Override
	public ResponseEntity<PhysicalAddressResource> getPrimaryAddress(String partyRefTypCode, String partyRefNum) {
		try {
			ResponseEntity<PartyResource> response = getParty(partyRefTypCode, partyRefNum);
			PhysicalAddressResource physicalAddressResource = employerResourceAssembler
					.createPhysicalAddressResource(response.getBody());
			ResponseEntity<PhysicalAddressResource> returnResponseEntity = new ResponseEntity<PhysicalAddressResource>(
					physicalAddressResource, response.getStatusCode());
			return returnResponseEntity;

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@Override
	public ResponseEntity<PartyResource> getParty(String partyRefTypCode, String partyRefNum) {
		try {
			String url = serviceProps.getContactServiceUrl()
					+ "contacts/search/findContactByReferenceTypeAndReferenceNumber?" + "referenceType="
					+ partyRefTypCode + "&referenceNumber=" + partyRefNum;

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

			converters.add(new FormHttpMessageConverter());
			converters.add(new StringHttpMessageConverter());

			HttpHeaders headers = buildHttpHeaders();

			try {
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				restTemplate.setRequestFactory(requestFactory);
			} catch (Exception e) {
				e.printStackTrace();
			}
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("partyRefTypCode", partyRefTypCode);
			map.add("partyRefNum", partyRefNum);

			HttpEntity<MultiValueMap> request = new HttpEntity<MultiValueMap>(map, headers);

			ResponseEntity<ContactDetailsResource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request,
					ContactDetailsResource.class);

			// TODO: Remove this when the contact service have been changed to
			// VARCHAR(9)
			for (PartyPhysicalAddressResource partyPhysicalAddressResource : responseEntity.getBody().getParty()
					.getPartyPhysicalAddresses()) {
				partyPhysicalAddressResource.setPostalCode(partyPhysicalAddressResource.getPostalCode().trim());
			}

			ResponseEntity<PartyResource> returnResponseEntity = new ResponseEntity<PartyResource>(
					responseEntity.getBody().getParty(), responseEntity.getStatusCode());

			return returnResponseEntity;

		} catch (HttpClientErrorException exception) {

			return ResponseEntity.status(exception.getStatusCode()).body(null);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	public ResponseEntity<PartyResource> createContactDetailsResource(ContactDetailsResource contactDetailsResource) {
		try {

			String url = serviceProps.getContactServiceUrl() + "contact_submission";

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

			JsonNode node = JsonParserUtil.convertObjectToJson(contactDetailsResource);
			System.out.println(node);
			HttpHeaders headers = buildHttpHeaders();

			try {
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				restTemplate.setRequestFactory(requestFactory);
			} catch (Exception e) {
				e.printStackTrace();
			}

			HttpEntity<ContactDetailsResource> request = new HttpEntity<ContactDetailsResource>(contactDetailsResource,
					headers);

			ResponseEntity<ContactDetailsResource> responseEntity = restTemplate.postForEntity(url, request,
					ContactDetailsResource.class);

			ResponseEntity<PartyResource> returnResponseEntity = new ResponseEntity<PartyResource>(
					responseEntity.getBody().getParty(), responseEntity.getStatusCode());

			return returnResponseEntity;

		} catch (HttpClientErrorException e) {

			return ResponseEntity.status(e.getStatusCode()).body(null);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	private HttpHeaders buildHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();

		String id = serviceProps.getContactServiceId();
		String pw = serviceProps.getContactServicePassword();
		String creds = id + ":" + pw;
		byte[] credsBytes = creds.getBytes();
		byte[] base64CredsBytes = Base64.getEncoder().encode(credsBytes);
		String base64Creds = new String(base64CredsBytes);

		headers.add("Authorization", "Basic " + base64Creds);

		List<MediaType> acceptMediaTypes = new ArrayList<MediaType>();
		acceptMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(acceptMediaTypes);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@Override
	public ResponseEntity<PartyResource> update(EmployerResource employer) {
		HttpMethod method = HttpMethod.PUT;
		String url = serviceProps.getContactServiceUrl() + "contact_submission";
		PartyResource partyResource = getParty("MUWEMP", employer.getEmployerId().toString()).getBody();
		ContactDetailsResource contactDetails = new ContactDetailsResource();
		if (null != partyResource) {
			url = url + "/" + partyResource.getPartyId();
			boolean primaryAddressFound = false;
			for (PartyPhysicalAddressResource partyPhysicalAddressResource : partyResource
					.getPartyPhysicalAddresses()) {
				primaryAddressFound = mapPrimaryAddress(partyPhysicalAddressResource, employer);
			}

			if (!primaryAddressFound) {
				PartyPhysicalAddressResource partyPhysicalAddressResource = employerResourceAssembler
						.createPartyPhysicalAddressResource(employer);
				partyResource.getPartyPhysicalAddresses().add(partyPhysicalAddressResource);
			}
			String divSerial = employer.getEmployerStakeholderLedgerNumber()
					+ employer.getEmployerStakeholderSerialNumber();
			for (PartyReferenceResource partyReferenceResource : partyResource.getPartyReferences()) {
				if (partyReferenceResource.getReferenceType().equals("DIVSRL")
						&& !partyReferenceResource.getReferenceNumber().equals(divSerial)) {
					partyReferenceResource.setReferenceNumber(divSerial);
				}
			}
		} else {
			partyResource = employerResourceAssembler.createPartyResource(employer);
			method = HttpMethod.POST;
		}
		contactDetails.setParty(partyResource);
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
				e.printStackTrace();
			}
			HttpEntity<ContactDetailsResource> request = new HttpEntity<ContactDetailsResource>(contactDetails,
					headers);
			ResponseEntity<ContactDetailsResource> responseEntity = restTemplate.exchange(url, method, request,
					ContactDetailsResource.class);

			ResponseEntity<PartyResource> returnResponseEntity = new ResponseEntity<PartyResource>(
					responseEntity.getBody().getParty(), responseEntity.getStatusCode());

			return returnResponseEntity;

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	private boolean mapPrimaryAddress(PartyPhysicalAddressResource partyPhysicalAddressResource,
			EmployerResource employer) {
		boolean primaryAddressFound = false;
		for (PartyPhysicalAddressActyResource partyPhysicalAddressActyResource : partyPhysicalAddressResource
				.getPartyPhysicalAddressActy()) {
			if (partyPhysicalAddressActyResource.getActivityType().equals("MUWPA")) {
				PhysicalAddressResource physicalAddressResource = employer.getAddresses();
				partyPhysicalAddressResource.setAddressReasonCode(physicalAddressResource.getAddressReasCode());
				partyPhysicalAddressResource.setAddressLine1Text(physicalAddressResource.getAddressLine1Text());
				partyPhysicalAddressResource.setAddressLine2Text(physicalAddressResource.getAddressLine2Text());
				partyPhysicalAddressResource.setCityName(physicalAddressResource.getCityName());
				partyPhysicalAddressResource.setStateProvienceCode(physicalAddressResource.getStateProvinceCode());
				partyPhysicalAddressResource.setPostalCode(physicalAddressResource.getPostalCode());
				partyPhysicalAddressResource.setCountryCode(physicalAddressResource.getCountryCode());
				primaryAddressFound = true;
			}
		}
		return primaryAddressFound;
	}

}
