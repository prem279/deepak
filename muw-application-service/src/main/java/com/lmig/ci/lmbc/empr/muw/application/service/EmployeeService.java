package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchEmployeeResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionEmployeeResource;
import com.lmig.ci.lmbc.empr.muw.application.config.ServiceProperties;

@Service
public class EmployeeService {
	@Autowired
	private ServiceProperties serviceProps;

	public ApplicationSearchEmployeeResource getEmployee(String lastName, String stakeholderLedgerNumber,
			String stakeholderSerialNumber, String ssn, String employeeIdentificationNumber) {

		String url = serviceProps.getEmployeeServiceZuulUrl() + "employees?lastName=" + lastName + "&div="
				+ stakeholderLedgerNumber + "&serial=" + stakeholderSerialNumber;

		if (ssn != null) {
			url += "&employeeSsn=" + ssn;
		} else if (employeeIdentificationNumber != null) {
			url += "&employeeIdNumber=" + employeeIdentificationNumber;
		}

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> loopConverter : converters) {
			if (loopConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) loopConverter;
				jsonConverter.setObjectMapper(new ObjectMapper());
				jsonConverter.setSupportedMediaTypes(ImmutableList.of(MediaType.APPLICATION_JSON));
			}
		}

		HttpHeaders headers = buildHttpHeaders();

		HttpEntity<String> request = new HttpEntity<String>(headers);

		ApplicationSearchEmployeeResource employee;

		ResponseEntity<ApplicationSearchEmployeeResource> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				request, ApplicationSearchEmployeeResource.class);

		employee = responseEntity.getBody();

		return employee;
	}

	public ApplicationSubmissionEmployeeResource getEmployee(Integer employeeId) {

		String url = serviceProps.getEmployeeServiceZuulUrl() + "employees/" + employeeId;

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> loopConverter : converters) {
			if (loopConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) loopConverter;
				jsonConverter.setObjectMapper(new ObjectMapper());
				jsonConverter.setSupportedMediaTypes(ImmutableList.of(MediaType.APPLICATION_JSON));
			}
		}

		HttpHeaders headers = buildHttpHeaders();

		HttpEntity<String> request = new HttpEntity<String>(headers);

		ApplicationSubmissionEmployeeResource employee;

		ResponseEntity<ApplicationSubmissionEmployeeResource> responseEntity = restTemplate.exchange(url,
				HttpMethod.GET, request, ApplicationSubmissionEmployeeResource.class);

		employee = responseEntity.getBody();

		return employee;
	}

	public ApplicationSubmissionEmployeeResource createEmployee(
			ApplicationSubmissionEmployeeResource employeeResource) {

		String url = serviceProps.getEmployeeServiceZuulUrl() + "employees";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new Jackson2HalModule());

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

		converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));

		converter.setObjectMapper(mapper);

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> loopConverter : converters) {
			if (loopConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) loopConverter;
				jsonConverter.setObjectMapper(new ObjectMapper());
				jsonConverter.setSupportedMediaTypes(ImmutableList.of(MediaType.APPLICATION_JSON));
			}
		}

		HttpHeaders headers = buildHttpHeaders();

		HttpEntity<ApplicationSubmissionEmployeeResource> request = new HttpEntity<ApplicationSubmissionEmployeeResource>(
				employeeResource, headers);

		ResponseEntity<ApplicationSubmissionEmployeeResource> responseEntity = restTemplate.postForEntity(url, request,
				ApplicationSubmissionEmployeeResource.class);

		return responseEntity.getBody();
	}

	private HttpHeaders buildHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();

		String id = serviceProps.getMuwServiceId();
		String pw = serviceProps.getMuwServicePassword();
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
}
