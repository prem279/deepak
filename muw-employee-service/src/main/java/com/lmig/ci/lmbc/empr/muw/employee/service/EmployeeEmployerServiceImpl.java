package com.lmig.ci.lmbc.empr.muw.employee.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;
import com.lmig.ci.lmbc.empr.muw.employee.EmployeeEventConstants;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.employee.api.PhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.exception.BadRequestException;
import com.lmig.ci.lmbc.empr.muw.employee.api.exception.RequestedResourceNotFoundException;
import com.lmig.ci.lmbc.empr.muw.employee.config.ServiceProperties;
import com.lmig.ci.lmbc.empr.muw.employee.domain.Employee;
import com.lmig.ci.lmbc.empr.muw.employee.repository.EmployeeRepository;
import com.lmig.ci.lmbc.empr.muw.employer.resource.EmployerResource;

@Service
public class EmployeeEmployerServiceImpl implements EmployeeEmployerService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeEmployerServiceImpl.class);

	@Autowired
	ServiceProperties serviceProps;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	MuwEmployeeContactService contactService;

	@Autowired
	private EmployeeResourceAssembler employeeAssembler;

	@Autowired
	private EmployeeEventService employeeEventService;

	@PersistenceContext
	private EntityManager entityManager;

	public ResponseEntity<EmployerResource> getEmployerResource(String div, String serial) {
		try {
			String url = serviceProps.getEmployerServiceUrl() + "employer_configurations?div=" + div + "&serial="
					+ serial;

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
				logger.error(e.getMessage(), e);
			}

			HttpEntity<String> request = new HttpEntity<String>(headers);

			ResponseEntity<EmployerResource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request,
					EmployerResource.class);
			return responseEntity;

		} catch (HttpClientErrorException exception) {
			logger.error(exception.getMessage(), exception);
			return ResponseEntity.status(exception.getStatusCode()).body(null);

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
	public ResponseEntity<EmployeeResource> getEmployeeResource(String div, String serial, String lastName,
			String employeeSsn, String employeeIdentificationNumber) throws BadRequestException {

		if (employeeSsn == null && employeeIdentificationNumber == null) {
			throw new BadRequestException("Invalid Arguments - employeeSsn or employeeIdNumber is required");
		}

		if (employeeSsn != null && employeeIdentificationNumber != null) {
			throw new BadRequestException(
					"Invalid Arguments - Please provide employeeSsn or employeeIdNumber, but not both");
		}

		ResponseEntity<EmployerResource> employerResourceEntity = getEmployerResource(div, serial);
		if (employerResourceEntity.getBody() == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		EmployerResource employerResource = employerResourceEntity.getBody();

		Employee employee = new Employee();
		if (employeeSsn != null) {
			employee = employeeRepository.findOneByEmployeeSsnAndEmployerId(employeeSsn,
					employerResource.getEmployerId());

		} else if (employeeIdentificationNumber != null) {
			employee = employeeRepository.findOneByEmployeeIdentificationNumberAndEmployerId(
					employeeIdentificationNumber, employerResource.getEmployerId());
		}

		if (employee == null || employee.getEmployerEmployeeId() == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		EmployeeResource employeeResource = employeeAssembler.toResource(employee);
		ResponseEntity<PartyResource> partyResourceResponse = contactService.getParty("MUWEE",
				employee.getEmployerEmployeeId().toString());
		PartyResource partyResource = partyResourceResponse.getBody();

		if (partyResource != null && lastName.equalsIgnoreCase(partyResource.getLastName())) {
			mapPartyToEmployeeMapping(partyResource, employeeResource);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}

		ResponseEntity<EmployeeResource> returnResponseEntity = new ResponseEntity<EmployeeResource>(employeeResource,
				HttpStatus.OK);

		return returnResponseEntity;
	}

	// This is marked as transactional so that if the save to the eventing table
	// fails, it will rollback the employee as well
	@Transactional
	@Override
	public ResponseEntity<EmployeeResource> createEmployee(EmployeeResource employeeResource) {

		Employee existingEmployee = employeeRepository
				.findOneByEmployeeSsnAndEmployerId(employeeResource.getEmployeeSsn(), employeeResource.getEmployerId());

		if (existingEmployee != null && existingEmployee.getEmployerEmployeeId() != null) {
			Integer employerEmployeeId = existingEmployee.getEmployerEmployeeId();
			employeeResource.setEmployerEmployeeId(employerEmployeeId);
			employeeResource.setConcurrencyQuantity(existingEmployee.getConcurrencyQuantity());
			return updateEmployee(employeeResource, employerEmployeeId);
		}

		Employee employee = employeeAssembler.toDomainObject(employeeResource);

		Employee savedEmployee = employeeRepository.saveAndFlush(employee);
		entityManager.detach(savedEmployee);

		if (savedEmployee == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(employeeResource);
		}
		employeeResource.setEmployerEmployeeId(savedEmployee.getEmployerEmployeeId());

		Employee employeeFromDb = employeeRepository.findOne(savedEmployee.getEmployerEmployeeId());

		EmployeeResource employeeResourceResult = employeeAssembler.toResource(employeeFromDb);

		ResponseEntity<PartyResource> partyResourceResponse = contactService.create(employeeResource);
		PartyResource partyResource = partyResourceResponse.getBody();

		if (partyResource == null || (partyResourceResponse.getStatusCode() != HttpStatus.CREATED
				&& partyResourceResponse.getStatusCode() != HttpStatus.OK)) {
			throw new DataIntegrityViolationException(
					"Could not save employee to Contact Service.  Resonse status code: "
							+ partyResourceResponse.getStatusCode());
		}
		mapPartyToEmployeeMapping(partyResource, employeeResourceResult);
		employeeEventService.createEvent(new Employee(), employeeFromDb, EmployeeEventConstants.CREATE);
		return new ResponseEntity<EmployeeResource>(employeeResourceResult, HttpStatus.CREATED);
	}

	// This is marked as transactional so that if the save to the eventing table
	// fails, it will rollback the employee as well

	@Transactional
	@Override
	public ResponseEntity<EmployeeResource> updateEmployee(EmployeeResource employeeResource,
			Integer employerEmployeeId) {

		Employee employeeFromDb = employeeRepository.findOne(employerEmployeeId);
		if (employeeFromDb == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		JsonParserUtil.convertObjectToJson(employeeFromDb);
		entityManager.detach(employeeFromDb);

		Employee employee = employeeAssembler.toDomainObject(employeeResource);
		Employee savedEmployee = employeeRepository.saveAndFlush(employee);

		if (savedEmployee == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(employeeResource);
		}
		EmployeeResource employeeResourceResult = employeeAssembler.toResource(savedEmployee);
		ResponseEntity<PartyResource> partyResourceResponse = contactService.update(employeeResource);
		PartyResource partyResource = partyResourceResponse.getBody();

		if (partyResource == null || (partyResourceResponse.getStatusCode() != HttpStatus.CREATED
				&& partyResourceResponse.getStatusCode() != HttpStatus.OK)) {
			throw new DataIntegrityViolationException(
					"Could not save employee to Contact Service.  Resonse status code: "
							+ partyResourceResponse.getStatusCode());
		}

		entityManager.detach(employee);
		Employee savedEmployeeFromDb = employeeRepository.findOne(employerEmployeeId);
		mapPartyToEmployeeMapping(partyResource, employeeResourceResult);
		employeeEventService.createEvent(employeeFromDb, savedEmployeeFromDb, EmployeeEventConstants.UPDATE);
		return new ResponseEntity<EmployeeResource>(employeeResourceResult, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EmployeeResource> getEmployee(Integer employerEmployeeId) {

		Employee employee = employeeRepository.findOne(employerEmployeeId);

		if (employee == null) {
			throw new RequestedResourceNotFoundException(
					"No records returned for given employeeId: " + employerEmployeeId);
		}
		EmployeeResource employeeResource = employeeAssembler.toResource(employee);
		ResponseEntity<PartyResource> partyResourceResponse = contactService.getParty("MUWEE",
				employee.getEmployerEmployeeId().toString());
		PartyResource partyResource = partyResourceResponse.getBody();
		if (partyResource != null) {
			mapPartyToEmployeeMapping(partyResource, employeeResource);
		}
		ResponseEntity<EmployeeResource> returnResponseEntity = new ResponseEntity<EmployeeResource>(employeeResource,
				HttpStatus.OK);

		return returnResponseEntity;
	}

	private void mapPartyToEmployeeMapping(PartyResource partyResource, EmployeeResource employeeResource) {

		employeeResource.setFirstName(partyResource.getFirstName());
		employeeResource.setLastName(partyResource.getLastName());
		employeeResource.setMiddleInitial(partyResource.getMiddleName());
		employeeResource.setEmployeeHomeAddress(employeeAssembler.createPhysicalAddressResource(partyResource, "HOME"));
		employeeResource
				.setEmployeeMailingAddress(employeeAssembler.createPhysicalAddressResource(partyResource, "MAILG"));
		employeeResource.setEmployeeEmailAddress(employeeAssembler.createEmailAddressResource(partyResource));
		employeeResource.setEmployeeTelephone(employeeAssembler.createPhoneNumberResource(partyResource));

		PhysicalAddressResource homeAddress = employeeResource.getEmployeeHomeAddress();
		PhysicalAddressResource mailingAddress = employeeResource.getEmployeeMailingAddress();
		if (homeAddress != null && !homeAddress.isNull() && (mailingAddress == null || mailingAddress.isNull())) {
			employeeResource.setMailingAddressSame(true);
		}

	}

}
