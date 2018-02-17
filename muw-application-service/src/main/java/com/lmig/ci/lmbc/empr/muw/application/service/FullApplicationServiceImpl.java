package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.lmig.ci.lmbc.empr.common.util.JsonParserUtil;
import com.lmig.ci.lmbc.empr.muw.application.ApplicationReasonConstants;
import com.lmig.ci.lmbc.empr.muw.application.ApplicationServiceEventConstants;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantExtendedResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantProductExtendedResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchApplicationResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchEmployeeResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchResourceResponse;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicantProductResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicantResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicationResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionEmployeeResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionResourceResponse;
import com.lmig.ci.lmbc.empr.muw.application.api.BasicApplicationProductResource;
import com.lmig.ci.lmbc.empr.muw.application.api.BasicApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.BasicApplicationResourceAssembler;
import com.lmig.ci.lmbc.empr.muw.application.api.exception.BadRequestException;
import com.lmig.ci.lmbc.empr.muw.application.api.exception.ExceptionMessageConstants;
import com.lmig.ci.lmbc.empr.muw.application.api.exception.InternalServerErrorException;
import com.lmig.ci.lmbc.empr.muw.application.api.exception.RequestedResourceNotFoundException;
import com.lmig.ci.lmbc.empr.muw.application.config.MuwApplicationSecurityProperties;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantCondition;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantMedication;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationMedicalFactor;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.QuestionnaireResponse;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicantRepository;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicationProductRepository;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicationRepository;
import com.lmig.ci.lmbc.empr.muw.employer.resource.EmployerResource;

@Service
public class FullApplicationServiceImpl implements FullApplicationService {

	private Logger logger = LoggerFactory.getLogger(FullApplicationServiceImpl.class);

	@Autowired
	private MuwApplicationSecurityProperties securityProperties;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployerService employerService;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private ApplicantRepository applicantRepository;

	@Autowired
	private ApplicationProductRepository applicationProductRepository;

	@Autowired
	private ApplicantResourceAssembler applicantAssembler;

	@Autowired
	private BasicApplicationResourceAssembler basicApplicationAssembler;

	@Autowired
	private ApplicantResourceAssembler assembler;

	@Autowired
	private ApplicationSubmissionApplicationResourceAssembler applicationSubmissionApplicationResourceAssembler;

	@Autowired
	private ApplicationSearchApplicationResourceAssembler applicationSearchApplicationResourceAssembler;

	@Autowired
	private ApplicationProductService applicationProductService;

	@Autowired
	private ApplicationEventServiceImpl applicationEventService;

	@Autowired
	private AuditorAware<String> springSecurityAuditorAware;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public ResponseEntity<ApplicationSubmissionResourceResponse> createApplicationSubmission(
			ApplicationSubmissionResource applicationSubmission) {

		Integer employerId = applicationSubmission.getApplication().getEmployerId();
		String div = applicationSubmission.getApplication().getDiv();
		String serial = applicationSubmission.getApplication().getSerial();

		if (employerId == null) {
			if (div == null || serial == null) {
				throw new BadRequestException("Employer Id or Div/Serial must be provided");
			}

			ResponseEntity<EmployerResource> response = employerService.getEmployerResource(div, serial);
			if (response == null || response.getBody() == null) {
				return ResponseEntity.status(response.getStatusCode()).body(null);
			}
			employerId = response.getBody().getEmployerId();
			if (null == employerId) {
				throw new BadRequestException("Could not find employer with div/serial of " + div + "/" + serial);
			}

			applicationSubmission.getApplication().setEmployerId(employerId);
		}
		applicationSubmission.getEmployee().setEmployerId(employerId);

		List<String> invalidReasons = new ArrayList<String>();

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ApplicationSubmissionResource>> constraintErrors = validator
				.validate(applicationSubmission);

		for (ConstraintViolation<ApplicationSubmissionResource> constraintViolation : constraintErrors) {
			invalidReasons.add(constraintViolation.toString());
		}

		boolean isFromFileFeed = securityProperties.getFileFeedUserIds()
				.contains(springSecurityAuditorAware.getCurrentAuditor());

		Integer employerEmployeeId = null;

		Application application = applicationSubmissionApplicationResourceAssembler
				.toDomainObject(applicationSubmission.getApplication());

		ApplicationSubmissionEmployeeResource employeeResource = applicationSubmission.getEmployee();

		for (Applicant applicant : application.getApplicants()) {
			// TODO can we hook this up to valid values service somehow?
			if (applicant.getApplicationRoleCode().equals("EMPLY")) {
				if (!applicant.getApplicantSSN().equals(employeeResource.getEmployeeSsn())) {
					logger.error("Request Error - Employee SSN doesn't match Employee Applicant SSN");

					throw new BadRequestException(
							"Employee SSN does not match the SSN of the applicant with a role of 'employee'");
				}
			}
		}
		try {
			ApplicationSubmissionEmployeeResource returnedEmployee = employeeService.createEmployee(employeeResource);

			employerEmployeeId = returnedEmployee.getEmployerEmployeeId();

		} catch (HttpClientErrorException hcee) {
			String body = getFormattedResponseBody(hcee.getResponseBodyAsString());
			throw new InternalServerErrorException(hcee.getMessage() + body, hcee.getStackTrace());
		} catch (Exception e) {

			throw new InternalServerErrorException(e.getMessage(), e.getStackTrace());
		}

		application.setEmployerEmployeeId(employerEmployeeId);

		// remove applicants so those don't exist yet
		application.setApplicants(new HashSet<>());

		Set<ApplicationMedicalFactor> applicationMedicalFactors = application.getApplicationMedicalFactors();
		if ((applicationMedicalFactors != null) && !applicationMedicalFactors.isEmpty()) {
			for (ApplicationMedicalFactor applicationMedicalFactor : applicationMedicalFactors) {
				applicationMedicalFactor.setApplication(application);
			}
		} else {
			invalidReasons.add("No Medical Factors were sent with the application and they are required.");
		}

		application.setFileFeedIndicator(isFromFileFeed);

		// Save application so we can get the Application ID
		Application savedApplication = applicationRepository.saveAndFlush(application);

		List<ApplicationSubmissionApplicantResource> applicantsResource = applicationSubmission.getApplication()
				.getApplicants();
		List<Applicant> applicantsWithApplicationProductIds = createApplicationProductsByApplicants(applicantsResource,
				savedApplication);

		if ((applicantsWithApplicationProductIds != null) && !applicantsWithApplicationProductIds.isEmpty()) {
			for (Applicant applicant : applicantsWithApplicationProductIds) {
				applicant.setApplication(application);

				for (ApplicantProduct ap : applicant.getApplicantProducts()) {
					// TODO what should this default to if it can't be null?
					Date now = new Date();
					java.sql.Date sqlDate = new java.sql.Date(now.getTime());
					ap.setStatusDeterminationDate(sqlDate);

					if (isFromFileFeed) {
						ap.setStatusTypeCode(ApplicationReasonConstants.APP_SENT);
					} else {
						ap.setStatusTypeCode(ApplicationReasonConstants.MANUAL_REVIEW);
					}
				}

				for (ApplicantCondition ac : applicant.getConditions()) {
					ac.setApplicant(applicant);

					for (ApplicantMedication am : ac.getMedications()) {
						am.setApplicantCondition(ac);
					}

					for (QuestionnaireResponse qr : ac.getQuestionnaireResponses()) {
						qr.setApplicantCondition(ac);
					}
				}

				createApplicant(savedApplication.getApplicationId(), applicant, true);
			}
		} else {
			invalidReasons.add("No Application products could be created with the supplied applicant products");
		}

		Application finalApplication = applicationRepository.findOne(savedApplication.getApplicationId());
		JsonParserUtil.convertObjectToJson(finalApplication);

		Integer triageOutcome = null;

		if (isFromFileFeed) {
			if (applicationSubmission.getApplication().getApplicationId() == null) {
				applicationSubmission.getApplication().setApplicationId(savedApplication.getApplicationId());
			}
			applicationEventService.createEvent(null, applicationSubmission, ApplicationServiceEventConstants.CREATE,
					ApplicationServiceEventConstants.FF_START);
		} else {
			triageOutcome = triageApplication(finalApplication);
		}
		applicationEventService.createEvent(new Application(), finalApplication,
				ApplicationServiceEventConstants.CREATE, null);

		ApplicationSubmissionResourceResponse applicationSubmissionResourceResponse = applicationSubmissionApplicationResourceAssembler
				.toApplicationSubmissionResponseResource(finalApplication);
		applicationSubmissionResourceResponse.setInvalidReasons(invalidReasons);
		applicationSubmissionResourceResponse.setApplicationAccepted(isApplicationComplete(finalApplication));
		applicationSubmissionResourceResponse.setTriageOutcome(triageOutcome);

		return new ResponseEntity<ApplicationSubmissionResourceResponse>(applicationSubmissionResourceResponse,
				HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<ApplicationSubmissionResourceResponse> updateApplicationSubmission(Integer applicationId,
			ApplicationSubmissionResource applicationSubmission) {

		Application existingApplication = applicationRepository.findOne(applicationId);
		JsonParserUtil.convertObjectToJson(existingApplication);
		entityManager.detach(existingApplication);

		if (existingApplication == null) {
			throw new RequestedResourceNotFoundException(
					ExceptionMessageConstants.APPLICATION_ID_RETURNED_NO_RECORDS + applicationId);
		}

		List<String> invalidReasons = new ArrayList<String>();

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ApplicationSubmissionResource>> constraintErrors = validator
				.validate(applicationSubmission);

		for (ConstraintViolation<ApplicationSubmissionResource> constraintViolation : constraintErrors) {
			invalidReasons.add(constraintViolation.toString());
		}

		Application application = applicationSubmissionApplicationResourceAssembler
				.toDomainObject(applicationSubmission.getApplication());

		ApplicationSubmissionEmployeeResource employeeResource = applicationSubmission.getEmployee();

		Integer employerEmployeeId = employeeResource.getEmployerEmployeeId();

		for (Applicant applicant : application.getApplicants()) {
			// TODO can we hook this up to valid values service somehow?
			if (applicant.getApplicationRoleCode().equals("EMPLY")) {
				if (!applicant.getApplicantSSN().equals(employeeResource.getEmployeeSsn())) {
					logger.error("Request Error - Employee SSN doesn't match Employee Applicant SSN");

					throw new BadRequestException(
							"Employee SSN does not match the SSN of the applicant with a role of 'employee'");
				}
			}
		}
		try {
			// Create also updates employees
			ApplicationSubmissionEmployeeResource returnedEmployee = employeeService.createEmployee(employeeResource);

			employerEmployeeId = returnedEmployee.getEmployerEmployeeId();

		} catch (HttpClientErrorException hcee) {
			String body = getFormattedResponseBody(hcee.getResponseBodyAsString());
			throw new InternalServerErrorException(hcee.getMessage() + body, hcee.getStackTrace());
		} catch (Exception e) {

			throw new InternalServerErrorException(e.getMessage(), e.getStackTrace());
		}

		application.setEmployerEmployeeId(employerEmployeeId);

		// remove applicants so those don't exist yet
		application.setApplicants(new HashSet<>());

		Set<ApplicationMedicalFactor> applicationMedicalFactors = application.getApplicationMedicalFactors();
		if ((applicationMedicalFactors != null) && !applicationMedicalFactors.isEmpty()) {
			for (ApplicationMedicalFactor applicationMedicalFactor : applicationMedicalFactors) {
				applicationMedicalFactor.setApplication(application);
			}
		} else {
			invalidReasons.add("No Medical Factors were sent with the application and they are required.");
		}

		// This endpoint is used for updating a partial application from portal,
		// so we expect the concurrency quantity to not be passed in. Set
		// concurrencyQuantity so we update the existing application rather than
		// creating a new one
		application.setConcurrencyQuantity(existingApplication.getConcurrencyQuantity());

		// Save application so we can get the Application ID
		Application savedApplication = applicationRepository.saveAndFlush(application);

		List<ApplicationSubmissionApplicantResource> applicantsResource = applicationSubmission.getApplication()
				.getApplicants();
		List<Applicant> applicantsWithApplicationProductIds = createApplicationProductsByApplicants(applicantsResource,
				savedApplication);

		if ((applicantsWithApplicationProductIds != null) && !applicantsWithApplicationProductIds.isEmpty()) {
			for (Applicant applicant : applicantsWithApplicationProductIds) {
				applicant.setApplication(application);

				for (ApplicantProduct ap : applicant.getApplicantProducts()) {
					// TODO what should this default to if it can't be null?
					Date now = new Date();
					java.sql.Date sqlDate = new java.sql.Date(now.getTime());
					ap.setStatusDeterminationDate(sqlDate);

					// if (isFromFileFeed) {
					// ap.setStatusTypeCode(ApplicationReasonConstants.APP_SENT);
					// } else {
					ap.setStatusTypeCode(ApplicationReasonConstants.MANUAL_REVIEW);
					// }
				}

				for (ApplicantCondition ac : applicant.getConditions()) {
					ac.setApplicant(applicant);

					for (ApplicantMedication am : ac.getMedications()) {
						am.setApplicantCondition(ac);
					}

					for (QuestionnaireResponse qr : ac.getQuestionnaireResponses()) {
						qr.setApplicantCondition(ac);
					}
				}

				createApplicant(savedApplication.getApplicationId(), applicant, true);
			}
		} else {
			invalidReasons.add("No Application products could be created with the supplied applicant products");
		}
		entityManager.detach(savedApplication);
		Application finalApplication = applicationRepository.findOne(savedApplication.getApplicationId());
		JsonParserUtil.convertObjectToJson(finalApplication);

		Integer triageOutcome = null;

		applicationEventService.createEvent(existingApplication, finalApplication,
				ApplicationServiceEventConstants.UPDATE, ApplicationServiceEventConstants.FF_START);

		ApplicationSubmissionResourceResponse applicationSubmissionResourceResponse = applicationSubmissionApplicationResourceAssembler
				.toApplicationSubmissionResponseResource(finalApplication);
		applicationSubmissionResourceResponse.setInvalidReasons(invalidReasons);
		applicationSubmissionResourceResponse.setApplicationAccepted(isApplicationComplete(finalApplication));
		applicationSubmissionResourceResponse.setTriageOutcome(triageOutcome);

		return new ResponseEntity<ApplicationSubmissionResourceResponse>(applicationSubmissionResourceResponse,
				HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<BasicApplicationResource> createBasicApplication(Application application) {

		// TODO this code will never throw an exception if it is the first thing
		// to run after coming from the controller
		// the resource object doesn't even have applicants on it, so they will
		// never be deserialized
		Set<Applicant> applicants = application.getApplicants();
		if ((applicants != null) && !applicants.isEmpty()) {
			throw new BadRequestException("Basic Application should not include Applicants");
		}

		Set<ApplicationProduct> applicationProducts = application.getApplicationProducts();
		if ((applicationProducts != null) && !applicationProducts.isEmpty()) {
			for (ApplicationProduct ap : applicationProducts) {
				ap.setApplication(application);
			}
		} else {
			throw new BadRequestException(
					"No Application Products were sent with the application and they are required.");
		}

		Set<ApplicationMedicalFactor> applicationMedicalFactors = application.getApplicationMedicalFactors();
		if ((applicationMedicalFactors != null) && !applicationMedicalFactors.isEmpty()) {
			for (ApplicationMedicalFactor applicationMedicalFactor : applicationMedicalFactors) {
				applicationMedicalFactor.setApplication(application);
			}
		} else {
			throw new BadRequestException("No Medical Factors were sent with the application and they are required.");
		}

		Application savedApplication = applicationRepository.saveAndFlush(application);

		applicationProductService.updateApplicantsProducts(savedApplication);
		applicationRepository.saveAndFlush(savedApplication);

		entityManager.detach(savedApplication);
		Application newApplication = applicationRepository.findOne(savedApplication.getApplicationId());
		JsonParserUtil.convertObjectToJson(newApplication);
		applicationEventService.createEvent(new Application(), newApplication, ApplicationServiceEventConstants.CREATE,
				null);

		BasicApplicationResource basicApplicationResource = basicApplicationAssembler.toResource(savedApplication);

		for (BasicApplicationProductResource basicApplicationProductResource : basicApplicationResource
				.getApplicationProducts()) {
			basicApplicationAssembler.setProductStatusBasedOnApplicant(basicApplicationProductResource,
					application.getApplicants());
		}

		return new ResponseEntity<BasicApplicationResource>(basicApplicationResource, HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<BasicApplicationResource> updateBasicApplication(Application application) {
		Application existingApplication = applicationRepository.findOne(application.getApplicationId());
		if (existingApplication == null) {
			throw new RequestedResourceNotFoundException(
					ExceptionMessageConstants.APPLICATION_ID_RETURNED_NO_RECORDS + application.getApplicationId());
		}

		JsonParserUtil.convertObjectToJson(existingApplication);
		entityManager.detach(existingApplication);

		Set<Applicant> applicants = existingApplication.getApplicants();
		application.setApplicants(applicants);
		if ((applicants != null) && !applicants.isEmpty()) {
			for (Applicant ap : applicants) {
				ap.setApplication(application);
			}
		}

		Set<ApplicationProduct> applicationProducts = application.getApplicationProducts();
		if ((applicationProducts != null) && !applicationProducts.isEmpty()) {
			for (ApplicationProduct ap : applicationProducts) {
				ap.setApplication(application);
			}
		} else {
			throw new BadRequestException(ExceptionMessageConstants.MISSING_APPLICATION_PRODUCTS);
		}

		Set<ApplicationMedicalFactor> applicationMedicalFactors = application.getApplicationMedicalFactors();
		if ((applicationMedicalFactors != null) && !applicationMedicalFactors.isEmpty()) {
			for (ApplicationMedicalFactor applicationMedicalFactor : applicationMedicalFactors) {
				applicationMedicalFactor.setApplication(application);
			}
		} else {
			throw new BadRequestException(ExceptionMessageConstants.MISSING_MEDICAL_FACTORS);
		}

		Application savedApplication = applicationRepository.saveAndFlush(application);

		applicationProductService.updateApplicantsProducts(savedApplication);
		applicationRepository.saveAndFlush(savedApplication);
		entityManager.detach(savedApplication);
		Application newApplication = applicationRepository.findOne(savedApplication.getApplicationId());
		JsonParserUtil.convertObjectToJson(newApplication);

		applicationEventService.createEvent(existingApplication, newApplication,
				ApplicationServiceEventConstants.UPDATE, null);

		BasicApplicationResource basicApplicationResource = basicApplicationAssembler.toResource(savedApplication);
		for (BasicApplicationProductResource basicApplicationProductResource : basicApplicationResource
				.getApplicationProducts()) {
			basicApplicationAssembler.setProductStatusBasedOnApplicant(basicApplicationProductResource,
					application.getApplicants());
		}

		return new ResponseEntity<BasicApplicationResource>(basicApplicationResource, HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<BasicApplicationResource> getBasicApplication(Integer applicationId) {
		Application application = applicationRepository.findOne(applicationId);
		if (application == null) {
			throw new RequestedResourceNotFoundException(
					"No records returned for given application ID: " + applicationId);
		}
		BasicApplicationResource applicationResource = basicApplicationAssembler.toResource(application);

		for (BasicApplicationProductResource basicApplicationProductResource : applicationResource
				.getApplicationProducts()) {
			basicApplicationAssembler.setProductStatusBasedOnApplicant(basicApplicationProductResource,
					application.getApplicants());
		}

		return new ResponseEntity<BasicApplicationResource>(applicationResource, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ApplicantResource> createApplicant(Integer applicationId, Applicant applicant,
			boolean fromFullApplicationSubmission) {

		Application application = applicationRepository.findOne(applicationId);
		JsonParserUtil.convertObjectToJson(application);
		entityManager.detach(application);

		applicant.setApplication(application);

		List<ApplicantProduct> applicantProducts = applicant.getApplicantProducts();

		if ((null != applicantProducts) && !applicantProducts.isEmpty()) {
			for (ApplicantProduct ap : applicantProducts) {
				ap.setApplicant(applicant);
			}
		}

		List<ApplicantCondition> conditions = applicant.getConditions();
		if ((null != conditions) && !conditions.isEmpty()) {
			for (ApplicantCondition condition : conditions) {
				condition.setApplicant(applicant);

				List<ApplicantMedication> medications = condition.getMedications();
				if ((null != medications) && !medications.isEmpty()) {
					for (ApplicantMedication medication : medications) {
						medication.setApplicantCondition(condition);
					}
				}
			}
		}

		Applicant savedApplicant = applicantRepository.saveAndFlush(applicant);

		// if we weren't sent any applicant products then create them from the
		// application products
		if ((savedApplicant.getApplicantProducts() == null) || savedApplicant.getApplicantProducts().isEmpty()) {
			applicationProductService.updateApplicantsProducts(application);
			savedApplicant = applicantRepository.saveAndFlush(savedApplicant);
		}

		if (!fromFullApplicationSubmission) {
			Application savedApplication = applicationRepository.findOne(application.getApplicationId());
			JsonParserUtil.convertObjectToJson(savedApplication);
			applicationEventService.createEvent(application, savedApplication, ApplicationServiceEventConstants.UPDATE,
					null);
		}

		ApplicantResource applicantResource = applicantAssembler.toResource(savedApplicant);

		return new ResponseEntity<ApplicantResource>(applicantResource, HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<List<ApplicantResource>> getApplicants(Integer applicationId) {
		Application application = applicationRepository.findOne(applicationId);
		if (application == null) {
			throw new RequestedResourceNotFoundException(
					"No records returned for given application ID: " + applicationId);
		}

		List<Applicant> applicants = applicantRepository.findByApplicationApplicationId(applicationId);
		List<ApplicantResource> applicantResources = new ArrayList<ApplicantResource>();

		if (applicants.isEmpty()) {
			throw new RequestedResourceNotFoundException(
					"No applicants found for given application ID: " + applicationId);
		}

		for (Applicant applicant : applicants) {
			applicantResources.add(assembler.toResource(applicant));
		}

		return new ResponseEntity<List<ApplicantResource>>(applicantResources, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ApplicantResource> updateApplicant(Integer applicationId, Integer applicantId,
			Applicant applicant) {

		if (applicant == null) {
			throw new BadRequestException("No Applicant information supplied but it is required.");
		}

		Applicant existingApplicant = applicantRepository.findOne(applicantId);
		Application application = applicationRepository.findOne(applicationId);
		JsonParserUtil.convertObjectToJson(application);
		entityManager.detach(application);
		if (application == null) {
			throw new RequestedResourceNotFoundException(
					"No records returned for given application ID: " + applicationId);
		}

		if (existingApplicant == null) {
			throw new RequestedResourceNotFoundException("No records returned for given applicant ID: " + applicantId);
		}

		// validate the application id matches the applicant's application id
		Application applicantsApplication = existingApplicant.getApplication();
		if ((applicantsApplication == null) || !applicantsApplication.getApplicationId().equals(applicationId)) {
			throw new RequestedResourceNotFoundException(
					"The given appliation ID doesnt match the given applicants application ID. applicant ID: "
							+ applicantId + " application ID: " + applicationId);
		}

		applicant.setApplication(application);
		applicant.setApplicationApplicantId(applicantId);

		List<ApplicantProduct> applicantProducts = applicant.getApplicantProducts();

		if ((null != applicantProducts) && !applicantProducts.isEmpty()) {
			for (ApplicantProduct ap : applicantProducts) {
				ap.setApplicant(applicant);
			}
		}

		List<ApplicantCondition> conditions = applicant.getConditions();
		if ((null != conditions) && !conditions.isEmpty()) {
			for (ApplicantCondition condition : conditions) {
				condition.setApplicant(applicant);

				List<ApplicantMedication> medications = condition.getMedications();
				if ((null != medications) && !medications.isEmpty()) {
					for (ApplicantMedication medication : medications) {
						medication.setApplicantCondition(condition);
					}
				}
			}
		}

		Applicant savedApplicant = applicantRepository.saveAndFlush(applicant);

		// if we weren't sent any applicant products then create them from the
		// application products
		if ((applicant.getApplicantProducts() == null) || applicant.getApplicantProducts().isEmpty()) {
			applicationProductService.updateApplicantsProducts(applicantsApplication);
			savedApplicant = applicantRepository.saveAndFlush(applicant);
		}
		JsonParserUtil.convertObjectToJson(savedApplicant);

		entityManager.clear(); // Force the application to reload from the
								// database. There is probably a better way to
								// do this, but detaching from the entities was
								// not working.

		Application savedApplication = applicationRepository.findOne(applicationId);
		JsonParserUtil.convertObjectToJson(savedApplication);
		applicationEventService.createEvent(application, savedApplication, ApplicationServiceEventConstants.UPDATE,
				null);

		ApplicantResource applicantResource = applicantAssembler.toResource(savedApplicant);

		return new ResponseEntity<ApplicantResource>(applicantResource, HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<ApplicantResource> getApplicant(Integer applicationId, Integer applicantId) {
		Application application = applicationRepository.findOne(applicationId);
		Applicant applicant = applicantRepository.findOne(applicantId);
		if (application == null) {
			throw new RequestedResourceNotFoundException(
					"No records returned for given application ID: " + applicationId);
		}

		if (applicant == null) {
			throw new RequestedResourceNotFoundException("No records returned for given applicant ID: " + applicantId);
		}

		// validate the application id matches the applicant's application id
		Application applicantsApplication = applicant.getApplication();
		if ((applicantsApplication == null) || !applicantsApplication.getApplicationId().equals(applicationId)) {
			throw new RequestedResourceNotFoundException(
					"The given appliation ID doesnt match the given applicants application ID. applicant ID: "
							+ applicantId + " application ID: " + applicationId);
		}

		ApplicantResource applicantResource = applicantAssembler.toResource(applicant);

		return new ResponseEntity<ApplicantResource>(applicantResource, HttpStatus.OK);
	}

	private static final Logger LOG = LoggerFactory.getLogger(FullApplicationServiceImpl.class);

	@Override
	@Transactional
	public ResponseEntity<List<ApplicantExtendedResource>> getPreviousApplicants(Integer applicationApplicantId) {
		LOG.debug("getPreviousApplicants: applicantId: " + applicationApplicantId);

		Applicant applicant = applicantRepository.findOne(applicationApplicantId);
		if (applicant == null) {
			throw new RequestedResourceNotFoundException(
					"No records returned for given query params.  applicationApplicantId: " + applicationApplicantId);
		}

		String applicantSSN = applicant.getApplicantSSN();

		if ((applicantSSN == null) || applicantSSN.equals("")) {

			return new ResponseEntity<List<ApplicantExtendedResource>>(new ArrayList<ApplicantExtendedResource>(),
					HttpStatus.NO_CONTENT);
		}

		List<Applicant> applicants = applicantRepository.findAllByApplicantSSN(applicantSSN);
		List<ApplicantExtendedResource> applicantResources = new ArrayList<ApplicantExtendedResource>();

		if (applicants.isEmpty()) {
			throw new RequestedResourceNotFoundException(
					"No records returned for given query params.  applicationApplicantId: " + applicationApplicantId);
		}

		for (Applicant applicant1 : applicants) {

			ApplicantExtendedResource applntresource2 = assembler.toExtendedResource(applicant1);

			Application app = applicant1.getApplication();

			applntresource2.setApplicationId(app.getApplicationId());

			for (ApplicationProduct app1 : app.getApplicationProducts()) {
				for (ApplicantProductExtendedResource app2 : applntresource2.getApplicantProducts()) {
					if (app1.getApplicationProductId() == app2.getApplicationProductId()) {
						app2.setApplicantProductCode(app1.getProductCode());
					}
				}
			}

			applicantResources.add(applntresource2);
		}

		return new ResponseEntity<List<ApplicantExtendedResource>>(applicantResources, HttpStatus.OK);
	}

	/**
	 * @param applicants
	 * @return
	 */
	private List<Applicant> createApplicationProductsByApplicants(
			List<ApplicationSubmissionApplicantResource> applicants, Application application) {

		List<String> productCodes = new ArrayList<String>();

		for (ApplicationSubmissionApplicantResource applicant : applicants) {
			List<ApplicationSubmissionApplicantProductResource> applicantProducts = applicant.getApplicantProducts();
			for (ApplicationSubmissionApplicantProductResource applicantProduct : applicantProducts) {
				String productCode = applicantProduct.getProductCode();
				if (!productCodes.contains(productCode)) {
					productCodes.add(productCode);
				}
			}
		}

		List<ApplicationProduct> savedApplicationProducts = new ArrayList<ApplicationProduct>();

		List<Applicant> applicantsDomain = new ArrayList<Applicant>();

		for (String productCode : productCodes) {
			ApplicationProduct product = new ApplicationProduct();
			product.setProductCode(productCode);
			product.setApplication(application);

			ApplicationProduct savedProduct = applicationProductRepository.saveAndFlush(product);
			savedApplicationProducts.add(savedProduct);
		}

		for (ApplicationSubmissionApplicantResource applicant : applicants) {

			Applicant applicantDomain = applicationSubmissionApplicationResourceAssembler.toDomainObject(applicant);

			List<ApplicationSubmissionApplicantProductResource> applicantProducts = applicant.getApplicantProducts();

			applicantDomain.setApplicantProducts(new ArrayList<ApplicantProduct>());

			for (ApplicationSubmissionApplicantProductResource applicantProduct : applicantProducts) {

				ApplicantProduct applicantProductDomain = applicationSubmissionApplicationResourceAssembler
						.toDomainObject(applicantProduct);
				String resourceProductCode = applicantProduct.getProductCode();

				Boolean matchFound = false;
				for (ApplicationProduct savedApplicationProduct : savedApplicationProducts) {
					if (resourceProductCode.equals(savedApplicationProduct.getProductCode())) {
						applicantProductDomain
								.setApplicationProductId(savedApplicationProduct.getApplicationProductId());
						matchFound = true;
						break;
					}
				}

				// TODO chained and that's bad, shame shame
				applicantDomain.getApplicantProducts().add(applicantProductDomain);

				if (!matchFound) {
					System.out.println("CRJ -- No match was found");
				}
			}

			applicantsDomain.add(applicantDomain);
		}

		return applicantsDomain;
	}

	@Override
	public ResponseEntity<ApplicationSearchResourceResponse> getApplications(String lastName,
			String stakeholderLedgerNumber, String stakeholderSerialNumber, String ssn,
			String employeeIdentificationNumber) {

		Integer employerEmployeeId;

		if (((ssn != null) && (employeeIdentificationNumber != null))
				|| ((ssn == null) && (employeeIdentificationNumber == null))) {
			throw new BadRequestException("Requires SSN or Employee ID; not both and not neither.",
					new Throwable().getStackTrace());
		}

		ApplicationSearchEmployeeResource returnedEmployee;

		try {
			returnedEmployee = employeeService.getEmployee(lastName, stakeholderLedgerNumber, stakeholderSerialNumber,
					ssn, employeeIdentificationNumber);
		} catch (HttpClientErrorException hcee) {
			String body = getFormattedResponseBody(hcee.getResponseBodyAsString());
			throw new InternalServerErrorException(hcee.getMessage() + body, hcee.getStackTrace());

		} catch (Exception e) {

			throw new InternalServerErrorException(e.getMessage(), e.getStackTrace());
		}

		if (returnedEmployee == null) {
			throw new RequestedResourceNotFoundException("No Employee found with given criteria.");
		}

		employerEmployeeId = returnedEmployee.getEmployerEmployeeId();

		List<Application> applications = null;

		applications = applicationRepository
				.findByEmployerEmployeeIdOrderByApplicationReceivedDateDesc(employerEmployeeId);

		ApplicationSearchResourceResponse applicationSearchResponse = new ApplicationSearchResourceResponse();

		applicationSearchResponse.setEmployee(returnedEmployee);

		List<ApplicationSearchApplicationResource> applicationResources = new ArrayList<ApplicationSearchApplicationResource>();

		for (Application app : applications) {
			ApplicationSearchApplicationResource appResource = applicationSearchApplicationResourceAssembler
					.toResource(app);
			applicationResources.add(appResource);
		}

		applicationSearchResponse.setApplications(applicationResources);

		return new ResponseEntity<ApplicationSearchResourceResponse>(applicationSearchResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApplicationSubmissionResource> getApplication(Integer applicationId) {

		ApplicationSubmissionResource applicationSubmissionResource = new ApplicationSubmissionResource();
		Application application = applicationRepository.findOne(applicationId);
		if (application == null) {
			throw new RequestedResourceNotFoundException(
					"No records returned for given Application Id.  applicationId: " + applicationId);
		}
		try {
			ApplicationSubmissionEmployeeResource returnedEmployee = employeeService
					.getEmployee(application.getEmployerEmployeeId());
			if (returnedEmployee == null) {
				throw new RequestedResourceNotFoundException("No Employee found with given criteria.");
			}

			ApplicationSubmissionApplicationResource asar = applicationSubmissionApplicationResourceAssembler
					.toResource(application);
			for (ApplicationProduct applicationProduct : application.getApplicationProducts()) {
				for (ApplicationSubmissionApplicantResource applicant : asar.getApplicants()) {
					for (ApplicationSubmissionApplicantProductResource applicantProduct : applicant
							.getApplicantProducts()) {
						if (applicationProduct.getApplicationProductId()
								.equals(applicantProduct.getApplicationProductId())) {
							applicantProduct.setProductCode(applicationProduct.getProductCode());
						}
					}
				}
			}
			applicationSubmissionResource.setApplication(asar);

			applicationSubmissionResource.setEmployee(returnedEmployee);

		} catch (HttpClientErrorException hcee) {
			String body = getFormattedResponseBody(hcee.getResponseBodyAsString());
			throw new InternalServerErrorException(hcee.getMessage() + body, hcee.getStackTrace());

		} catch (Exception e) {

			throw new InternalServerErrorException(e.getMessage(), e.getStackTrace());
		}

		return new ResponseEntity<ApplicationSubmissionResource>(applicationSubmissionResource, HttpStatus.OK);
	}

	/**
	 * @param responseBodyAsString
	 * @return
	 */
	private static String getFormattedResponseBody(String responseBodyAsString) {
		if (responseBodyAsString != null) {
			responseBodyAsString = responseBodyAsString.replaceAll("(\\r|\\n)", "");
		}
		return responseBodyAsString;
	}

	private Integer triageApplication(Application application) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param finalApplication
	 * @return
	 */
	private Boolean isApplicationComplete(Application application) {
		// TODO Auto-generated method stub
		return null;
	}
}
