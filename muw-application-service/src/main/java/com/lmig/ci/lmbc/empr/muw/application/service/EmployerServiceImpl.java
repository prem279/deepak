package com.lmig.ci.lmbc.empr.muw.application.service;

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
import com.lmig.ci.lmbc.empr.muw.application.config.ServiceProperties;
import com.lmig.ci.lmbc.empr.muw.employer.resource.EmployerResource;

@Service
public class EmployerServiceImpl implements EmployerService {

	private static final Logger logger = LoggerFactory.getLogger(EmployerServiceImpl.class);

	@Autowired
	ServiceProperties serviceProps;

	@Override
	public ResponseEntity<EmployerResource> getEmployerResource(String div, String serial) {
		try {
			String url = serviceProps.getEmployerServiceZuulUrl() + "employer_configurations?div=" + div + "&serial="
					+ serial;

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

			converters.add(new FormHttpMessageConverter());
			converters.add(new StringHttpMessageConverter());

			HttpHeaders headers = buildHttpHeaders();

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

		String id = serviceProps.getMuwServiceId();
		String pw = serviceProps.getMuwServicePassword();
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

}
