/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.muw.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.springframework.hateoas.Link;

import com.lmig.ci.lmbc.empr.common.domain.ErrorMessage;
import com.lmig.ci.lmbc.empr.common.domain.ErrorResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantConditionResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantExtendedResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantMedicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantProductExtendedResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantProductResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicantResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationEventResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationMedicalFactorResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationProductResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchApplicantConditionResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchApplicantMedicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchApplicantProductResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchApplicantResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchApplicationProductResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchEmployeeResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchMedicalFactorResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSearchResourceResponse;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicantConditionResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicantMedicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicantProductResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicantQuestionnaireResponseResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicantResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionEmployeeResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionMedicalFactorResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ApplicationSubmissionResourceResponse;
import com.lmig.ci.lmbc.empr.muw.application.api.BasicApplicationMedicalFactorResource;
import com.lmig.ci.lmbc.empr.muw.application.api.BasicApplicationProductResource;
import com.lmig.ci.lmbc.empr.muw.application.api.BasicApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.CondensedApplicationResource;
import com.lmig.ci.lmbc.empr.muw.application.api.PhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.application.api.ProductResource;
import com.lmig.ci.lmbc.empr.muw.application.api.QuestionnaireResponseResource;
import com.lmig.ci.lmbc.empr.muw.application.config.MuwApplicationProductConfig;
import com.lmig.ci.lmbc.empr.muw.application.config.MuwApplicationSecurityProperties;
import com.lmig.ci.lmbc.empr.muw.application.config.ServiceProperties;
import com.lmig.ci.lmbc.empr.muw.application.domain.Applicant;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantCondition;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantMedication;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.Application;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationEvent;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationMedicalFactor;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicationProduct;
import com.lmig.ci.lmbc.empr.muw.application.domain.QuestionnaireResponse;
import com.lmig.ci.lmbc.empr.muw.employer.resource.EmployerResource;
import com.lmig.ci.lmbc.empr.muw.employer.resource.EoiProductResource;
import com.lmig.ci.lmbc.empr.muw.employer.resource.LabelResource;
import com.lmig.ci.lmbc.empr.muw.employer.resource.PreferenceResource;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class MuwApplicationServiceBeanTests extends MuwApplicationServiceTests {
	private static final List<Class<?>> lobokClasses = getLobokClasses();
	private static final List<Class<?>> serializableClasses = getSerializableClasses();
	private static final List<Class<?>> nonCircularClasses = getNonCircularClasses();

	@Test
	public void testSerializableClasses() {
		try {
			for (Class<?> clazz : serializableClasses) {
				testIsSerializable(clazz);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testGettersAndSetters() {
		BeanTester beanTester = new BeanTester();

		// add this because Date doesnt have a no arg constructor
		beanTester.getFactoryCollection().addFactory(java.sql.Date.class, new DateFactory());
		beanTester.getFactoryCollection().addFactory(java.sql.Timestamp.class, new TimestampFactory());
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("employer").ignoreProperty("preference")
				.build();
		for (Class<?> clazz : lobokClasses) {
			beanTester.testBean(clazz, configuration);
		}
	}

	@Test
	public void testConfigClassesEquals() {
		for (Class<?> clazz : nonCircularClasses) {
			EqualsVerifier.forClass(clazz).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
		}
	}

	@Test
	public void testApplicantEquals() {
		Application obj1 = new Application();
		Application obj2 = new Application();
		obj2.setApplicationId(1);

		ApplicantMedication med1 = new ApplicantMedication();
		ApplicantMedication med2 = new ApplicantMedication();
		med2.setMedicationCode("ABC");

		ApplicantCondition con1 = new ApplicantCondition();
		ApplicantCondition con2 = new ApplicantCondition();
		con2.setConditionCode("ABC");

		ApplicantProduct prod1 = new ApplicantProduct();
		ApplicantProduct prod2 = new ApplicantProduct();
		prod2.setCurrentProductQuantity(1.0);

		EqualsVerifier.forClass(Applicant.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2)).withPrefabValues(Application.class, obj1, obj2)
				.withPrefabValues(ApplicantMedication.class, med1, med2)
				.withPrefabValues(ApplicantCondition.class, con1, con2)
				.withPrefabValues(ApplicantProduct.class, prod1, prod2).withRedefinedSuperclass()
				.withIgnoredFields("application").verify();
	}

	@Test
	public void testApplicantConditionEquals() {
		Application obj1 = new Application();
		Application obj2 = new Application();
		obj2.setApplicationId(1);

		Applicant applicant1 = new Applicant();
		Applicant applicant2 = new Applicant();
		applicant2.setApplicantSSN("123123123");

		ApplicantMedication med1 = new ApplicantMedication();
		ApplicantMedication med2 = new ApplicantMedication();
		med2.setMedicationCode("ABC");

		QuestionnaireResponse res1 = new QuestionnaireResponse();
		QuestionnaireResponse res2 = new QuestionnaireResponse();
		res2.setAdditionalQuestionCode("ABCDSS");

		ApplicantProduct prod1 = new ApplicantProduct();
		ApplicantProduct prod2 = new ApplicantProduct();
		prod2.setCurrentProductQuantity(1.0);

		EqualsVerifier.forClass(ApplicantCondition.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2)).withPrefabValues(Application.class, obj1, obj2)
				.withPrefabValues(ApplicantMedication.class, med1, med2)
				.withPrefabValues(QuestionnaireResponse.class, res1, res2)
				.withPrefabValues(Applicant.class, applicant1, applicant2)
				.withPrefabValues(ApplicantProduct.class, prod1, prod2).withRedefinedSuperclass()
				.withIgnoredFields("applicant", "createUserIdNum", "createDatetime", "lastUpdateDatetime",
						"lastUpdateUserIdNum", "concurrencyQuantity")
				.verify();
	}

	@Test
	public void testApplicantMedicationEquals() {
		Application obj1 = new Application();
		Application obj2 = new Application();
		obj2.setApplicationId(1);

		Applicant applicant1 = new Applicant();
		Applicant applicant2 = new Applicant();
		applicant2.setApplicantSSN("123123123");
		ApplicantCondition con1 = new ApplicantCondition();
		ApplicantCondition con2 = new ApplicantCondition();
		con2.setConditionCode("ABC");

		ApplicantProduct prod1 = new ApplicantProduct();
		ApplicantProduct prod2 = new ApplicantProduct();
		prod2.setCurrentProductQuantity(1.0);

		EqualsVerifier.forClass(ApplicantMedication.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2)).withPrefabValues(Application.class, obj1, obj2)
				.withPrefabValues(ApplicantCondition.class, con1, con2)
				.withPrefabValues(Applicant.class, applicant1, applicant2)
				.withPrefabValues(ApplicantProduct.class, prod1, prod2).withRedefinedSuperclass()
				.withIgnoredFields("applicantCondition", "createUserIdNum", "createDatetime", "lastUpdateDatetime",
						"lastUpdateUserIdNum", "concurrencyQuantity")
				.verify();
	}

	@Test
	public void testQuestionResponseEquals() {
		Application obj1 = new Application();
		Application obj2 = new Application();
		obj2.setApplicationId(1);

		Applicant applicant1 = new Applicant();
		Applicant applicant2 = new Applicant();
		applicant2.setApplicantSSN("123123123");

		ApplicantCondition con1 = new ApplicantCondition();
		ApplicantCondition con2 = new ApplicantCondition();
		con2.setConditionCode("ABC");

		ApplicantProduct prod1 = new ApplicantProduct();
		ApplicantProduct prod2 = new ApplicantProduct();
		prod2.setCurrentProductQuantity(1.0);

		EqualsVerifier.forClass(QuestionnaireResponse.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2)).withPrefabValues(Application.class, obj1, obj2)
				.withPrefabValues(ApplicantCondition.class, con1, con2)
				.withPrefabValues(Applicant.class, applicant1, applicant2)
				.withPrefabValues(ApplicantProduct.class, prod1, prod2).withRedefinedSuperclass()
				.withIgnoredFields("applicantCondition", "createUserIdNum", "createDatetime", "lastUpdateDatetime",
						"lastUpdateUserIdNum", "concurrencyQuantity")
				.verify();
	}

	@Test
	public void testApplicantProductEquals() {
		Application obj1 = new Application();
		Application obj2 = new Application();
		obj2.setApplicationId(1);

		Applicant applicant1 = new Applicant();
		Applicant applicant2 = new Applicant();
		applicant2.setApplicantSSN("123123123");

		ApplicantCondition con1 = new ApplicantCondition();
		ApplicantCondition con2 = new ApplicantCondition();
		con2.setConditionCode("ABC");

		ApplicantMedication med1 = new ApplicantMedication();
		ApplicantMedication med2 = new ApplicantMedication();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicantProduct.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2)).withPrefabValues(Application.class, obj1, obj2)
				.withPrefabValues(ApplicantCondition.class, con1, con2)
				.withPrefabValues(Applicant.class, applicant1, applicant2)
				.withPrefabValues(ApplicantMedication.class, med1, med2).withRedefinedSuperclass()
				.withIgnoredFields("applicant", "createUserIdNum", "createDatetime", "lastUpdateDatetime",
						"lastUpdateUserIdNum", "concurrencyQuantity")
				.verify();
	}

	@Test
	public void testApplicationEquals() {

		ApplicantProduct prod1 = new ApplicantProduct();
		ApplicantProduct prod2 = new ApplicantProduct();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationProduct appProd1 = new ApplicationProduct();
		ApplicationProduct appProd2 = new ApplicationProduct();
		appProd2.setProductCode("STD");

		ApplicationMedicalFactor appMed1 = new ApplicationMedicalFactor();
		ApplicationMedicalFactor appMed2 = new ApplicationMedicalFactor();
		appMed2.setMedicalFactorIndicator(true);

		Applicant applicant1 = new Applicant();
		Applicant applicant2 = new Applicant();
		applicant2.setApplicantSSN("123123123");

		ApplicantCondition con1 = new ApplicantCondition();
		ApplicantCondition con2 = new ApplicantCondition();
		con2.setConditionCode("ABC");

		ApplicantMedication med1 = new ApplicantMedication();
		ApplicantMedication med2 = new ApplicantMedication();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(Application.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(ApplicantProduct.class, prod1, prod2)
				.withPrefabValues(ApplicantCondition.class, con1, con2)
				.withPrefabValues(Applicant.class, applicant1, applicant2)
				.withPrefabValues(ApplicantMedication.class, med1, med2)
				.withPrefabValues(ApplicationProduct.class, appProd1, appProd2)
				.withPrefabValues(ApplicationMedicalFactor.class, appMed1, appMed2).withRedefinedSuperclass().verify();
	}

	@Test
	public void testApplicationMedicalFactorEquals() {
		Application application1 = new Application();
		Application application2 = new Application();
		application2.setApplicationId(1);

		ApplicantProduct prod1 = new ApplicantProduct();
		ApplicantProduct prod2 = new ApplicantProduct();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationProduct appProd1 = new ApplicationProduct();
		ApplicationProduct appProd2 = new ApplicationProduct();
		appProd2.setProductCode("STD");

		Applicant applicant1 = new Applicant();
		Applicant applicant2 = new Applicant();
		applicant2.setApplicantSSN("123123123");

		ApplicantCondition con1 = new ApplicantCondition();
		ApplicantCondition con2 = new ApplicantCondition();
		con2.setConditionCode("ABC");

		ApplicantMedication med1 = new ApplicantMedication();
		ApplicantMedication med2 = new ApplicantMedication();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationMedicalFactor.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(ApplicantProduct.class, prod1, prod2)
				.withPrefabValues(ApplicantCondition.class, con1, con2)
				.withPrefabValues(Applicant.class, applicant1, applicant2)
				.withPrefabValues(ApplicantMedication.class, med1, med2)
				.withPrefabValues(ApplicationProduct.class, appProd1, appProd2)
				.withPrefabValues(Application.class, application1, application2).withRedefinedSuperclass()
				.withIgnoredFields("application", "createUserIdNum", "createDatetime", "lastUpdateDatetime",
						"lastUpdateUserIdNum", "concurrencyQuantity")

				.verify();
	}

	@Test
	public void testApplicationProductEquals() {
		Application application1 = new Application();
		Application application2 = new Application();
		application2.setApplicationId(1);

		ApplicantProduct prod1 = new ApplicantProduct();
		ApplicantProduct prod2 = new ApplicantProduct();
		prod2.setCurrentProductQuantity(1.0);

		Applicant applicant1 = new Applicant();
		Applicant applicant2 = new Applicant();
		applicant2.setApplicantSSN("123123123");

		ApplicantCondition con1 = new ApplicantCondition();
		ApplicantCondition con2 = new ApplicantCondition();
		con2.setConditionCode("ABC");

		ApplicantMedication med1 = new ApplicantMedication();
		ApplicantMedication med2 = new ApplicantMedication();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationProduct.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(ApplicantProduct.class, prod1, prod2)
				.withPrefabValues(ApplicantCondition.class, con1, con2)
				.withPrefabValues(Applicant.class, applicant1, applicant2)
				.withPrefabValues(ApplicantMedication.class, med1, med2)
				.withPrefabValues(Application.class, application1, application2).withRedefinedSuperclass()
				.withIgnoredFields("application", "createUserIdNum", "createDatetime", "lastUpdateDatetime",
						"lastUpdateUserIdNum", "concurrencyQuantity")
				.verify();
	}

	@Test
	public void testApplicationResourceEquals() {
		ApplicantProductResource prod1 = new ApplicantProductResource();
		ApplicantProductResource prod2 = new ApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationProductResource appProd1 = new ApplicationProductResource();
		ApplicationProductResource appProd2 = new ApplicationProductResource();
		appProd2.setProductCode("STD");

		ApplicationMedicalFactorResource appMed1 = new ApplicationMedicalFactorResource();
		ApplicationMedicalFactorResource appMed2 = new ApplicationMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicantResource applicant1 = new ApplicantResource();
		ApplicantResource applicant2 = new ApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicantConditionResource con1 = new ApplicantConditionResource();
		ApplicantConditionResource con2 = new ApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicantMedicationResource med1 = new ApplicantMedicationResource();
		ApplicantMedicationResource med2 = new ApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationProductResource.class, appProd1, appProd2)
				.withPrefabValues(ApplicationMedicalFactorResource.class, appMed1, appMed2).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testApplicantConditionResourceEquals() {
		ApplicantProductResource prod1 = new ApplicantProductResource();
		ApplicantProductResource prod2 = new ApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationProductResource appProd1 = new ApplicationProductResource();
		ApplicationProductResource appProd2 = new ApplicationProductResource();
		appProd2.setProductCode("STD");

		ApplicationMedicalFactorResource appMed1 = new ApplicationMedicalFactorResource();
		ApplicationMedicalFactorResource appMed2 = new ApplicationMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicantResource applicant1 = new ApplicantResource();
		ApplicantResource applicant2 = new ApplicantResource();
		applicant2.setApplicantSSN("123123123");

		QuestionnaireResponseResource res1 = new QuestionnaireResponseResource();
		QuestionnaireResponseResource res2 = new QuestionnaireResponseResource();
		res2.setAdditionalQuestionCode("ABCDSS");

		ApplicantMedicationResource med1 = new ApplicantMedicationResource();
		ApplicantMedicationResource med2 = new ApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicantConditionResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(QuestionnaireResponseResource.class, res1, res2)
				.withPrefabValues(ApplicationProductResource.class, appProd1, appProd2)
				.withPrefabValues(ApplicationMedicalFactorResource.class, appMed1, appMed2).withRedefinedSuperclass()
				.withIgnoredFields("applicant", "links").verify();
	}

	@Test
	public void testApplicantMedicationResourceEquals() {
		ApplicantProductResource prod1 = new ApplicantProductResource();
		ApplicantProductResource prod2 = new ApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationProductResource appProd1 = new ApplicationProductResource();
		ApplicationProductResource appProd2 = new ApplicationProductResource();
		appProd2.setProductCode("STD");

		ApplicationMedicalFactorResource appMed1 = new ApplicationMedicalFactorResource();
		ApplicationMedicalFactorResource appMed2 = new ApplicationMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicantResource applicant1 = new ApplicantResource();
		ApplicantResource applicant2 = new ApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicantConditionResource con1 = new ApplicantConditionResource();
		ApplicantConditionResource con2 = new ApplicantConditionResource();
		con2.setConditionCode("ABC");

		EqualsVerifier.forClass(ApplicantMedicationResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationProductResource.class, appProd1, appProd2)
				.withPrefabValues(ApplicationMedicalFactorResource.class, appMed1, appMed2).withRedefinedSuperclass()
				.withIgnoredFields("applicantCondition", "links").verify();
	}

	@Test
	public void testQuestionResponseResourceEquals() {
		ApplicantProductResource prod1 = new ApplicantProductResource();
		ApplicantProductResource prod2 = new ApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationProductResource appProd1 = new ApplicationProductResource();
		ApplicationProductResource appProd2 = new ApplicationProductResource();
		appProd2.setProductCode("STD");

		ApplicationMedicalFactorResource appMed1 = new ApplicationMedicalFactorResource();
		ApplicationMedicalFactorResource appMed2 = new ApplicationMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicantResource applicant1 = new ApplicantResource();
		ApplicantResource applicant2 = new ApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicantConditionResource con1 = new ApplicantConditionResource();
		ApplicantConditionResource con2 = new ApplicantConditionResource();
		con2.setConditionCode("ABC");

		EqualsVerifier.forClass(QuestionnaireResponseResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationProductResource.class, appProd1, appProd2)
				.withPrefabValues(ApplicationMedicalFactorResource.class, appMed1, appMed2).withRedefinedSuperclass()
				.withIgnoredFields("applicantCondition", "links").verify();
	}

	@Test
	public void testApplicantProductResourceEquals() {

		ApplicationMedicalFactorResource appMed1 = new ApplicationMedicalFactorResource();
		ApplicationMedicalFactorResource appMed2 = new ApplicationMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicantResource applicant1 = new ApplicantResource();
		ApplicantResource applicant2 = new ApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicantConditionResource con1 = new ApplicantConditionResource();
		ApplicantConditionResource con2 = new ApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicantMedicationResource med1 = new ApplicantMedicationResource();
		ApplicantMedicationResource med2 = new ApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicantProductResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationMedicalFactorResource.class, appMed1, appMed2).withRedefinedSuperclass()
				.withIgnoredFields("applicant", "links").verify();
	}

	@Test
	public void testApplicantResourceEquals() {

		ApplicationResource application1 = new ApplicationResource();
		ApplicationResource application2 = new ApplicationResource();
		application2.setApplicationId(1);

		ApplicantProductResource prod1 = new ApplicantProductResource();
		ApplicantProductResource prod2 = new ApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationProductResource appProd1 = new ApplicationProductResource();
		ApplicationProductResource appProd2 = new ApplicationProductResource();
		appProd2.setProductCode("STD");

		ApplicationMedicalFactorResource appMed1 = new ApplicationMedicalFactorResource();
		ApplicationMedicalFactorResource appMed2 = new ApplicationMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicantConditionResource con1 = new ApplicantConditionResource();
		ApplicantConditionResource con2 = new ApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicantMedicationResource med1 = new ApplicantMedicationResource();
		ApplicantMedicationResource med2 = new ApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicantResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationResource.class, application1, application2)
				.withPrefabValues(ApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationProductResource.class, appProd1, appProd2)
				.withPrefabValues(ApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicationMedicalFactorResource.class, appMed1, appMed2).withRedefinedSuperclass()
				.withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testApplicationMedicalFactorResourceEquals() {

		ApplicationResource application1 = new ApplicationResource();
		ApplicationResource application2 = new ApplicationResource();
		application2.setApplicationId(1);

		ApplicantProductResource prod1 = new ApplicantProductResource();
		ApplicantProductResource prod2 = new ApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationProductResource appProd1 = new ApplicationProductResource();
		ApplicationProductResource appProd2 = new ApplicationProductResource();
		appProd2.setProductCode("STD");

		ApplicantConditionResource con1 = new ApplicantConditionResource();
		ApplicantConditionResource con2 = new ApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicantMedicationResource med1 = new ApplicantMedicationResource();
		ApplicantMedicationResource med2 = new ApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationMedicalFactorResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationResource.class, application1, application2)
				.withPrefabValues(ApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationProductResource.class, appProd1, appProd2)
				.withPrefabValues(ApplicantProductResource.class, prod1, prod2).withRedefinedSuperclass()
				.withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testApplicationProductResourceEquals() {

		ApplicationResource application1 = new ApplicationResource();
		ApplicationResource application2 = new ApplicationResource();
		application2.setApplicationId(1);

		ApplicantProductResource prod1 = new ApplicantProductResource();
		ApplicantProductResource prod2 = new ApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicantConditionResource con1 = new ApplicantConditionResource();
		ApplicantConditionResource con2 = new ApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicantMedicationResource med1 = new ApplicantMedicationResource();
		ApplicantMedicationResource med2 = new ApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationProductResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationResource.class, application1, application2)
				.withPrefabValues(ApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicantProductResource.class, prod1, prod2).withRedefinedSuperclass()
				.withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testApplicantProductExtendedResourceEquals() {

		ApplicationResource application1 = new ApplicationResource();
		ApplicationResource application2 = new ApplicationResource();
		application2.setApplicationId(1);

		ApplicantResource applicant1 = new ApplicantResource();
		ApplicantResource applicant2 = new ApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicantConditionResource con1 = new ApplicantConditionResource();
		ApplicantConditionResource con2 = new ApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicantMedicationResource med1 = new ApplicantMedicationResource();
		ApplicantMedicationResource med2 = new ApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicantProductExtendedResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationResource.class, application1, application2)
				.withPrefabValues(ApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicantResource.class, applicant1, applicant2).withRedefinedSuperclass()
				.withIgnoredFields("applicant", "links").verify();
	}

	@Test
	public void testApplicantExtendedResourceEquals() {

		ApplicationResource application1 = new ApplicationResource();
		ApplicationResource application2 = new ApplicationResource();
		application2.setApplicationId(1);

		ApplicantProductExtendedResource prod1 = new ApplicantProductExtendedResource();
		ApplicantProductExtendedResource prod2 = new ApplicantProductExtendedResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicantConditionResource con1 = new ApplicantConditionResource();
		ApplicantConditionResource con2 = new ApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicantMedicationResource med1 = new ApplicantMedicationResource();
		ApplicantMedicationResource med2 = new ApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicantExtendedResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationResource.class, application1, application2)
				.withPrefabValues(ApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicantProductExtendedResource.class, prod1, prod2).withRedefinedSuperclass()
				.withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testApplicationSubmissionApplicationResourceEquals() {
		ApplicationSubmissionApplicantProductResource prod1 = new ApplicationSubmissionApplicantProductResource();
		ApplicationSubmissionApplicantProductResource prod2 = new ApplicationSubmissionApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSubmissionMedicalFactorResource appMed1 = new ApplicationSubmissionMedicalFactorResource();
		ApplicationSubmissionMedicalFactorResource appMed2 = new ApplicationSubmissionMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSubmissionApplicantResource applicant1 = new ApplicationSubmissionApplicantResource();
		ApplicationSubmissionApplicantResource applicant2 = new ApplicationSubmissionApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicationSubmissionApplicantConditionResource con1 = new ApplicationSubmissionApplicantConditionResource();
		ApplicationSubmissionApplicantConditionResource con2 = new ApplicationSubmissionApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicationSubmissionApplicantMedicationResource med1 = new ApplicationSubmissionApplicantMedicationResource();
		ApplicationSubmissionApplicantMedicationResource med2 = new ApplicationSubmissionApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationSubmissionApplicationResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSubmissionApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicationSubmissionApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSubmissionApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicationSubmissionApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSubmissionMedicalFactorResource.class, appMed1, appMed2)
				.withRedefinedSuperclass().withIgnoredFields("links").verify();
	}

	@Test
	public void testApplicationSubmissionApplicantConditionResourceEquals() {
		ApplicationSubmissionApplicantProductResource prod1 = new ApplicationSubmissionApplicantProductResource();
		ApplicationSubmissionApplicantProductResource prod2 = new ApplicationSubmissionApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSubmissionMedicalFactorResource appMed1 = new ApplicationSubmissionMedicalFactorResource();
		ApplicationSubmissionMedicalFactorResource appMed2 = new ApplicationSubmissionMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSubmissionApplicantResource applicant1 = new ApplicationSubmissionApplicantResource();
		ApplicationSubmissionApplicantResource applicant2 = new ApplicationSubmissionApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicationSubmissionApplicantMedicationResource med1 = new ApplicationSubmissionApplicantMedicationResource();
		ApplicationSubmissionApplicantMedicationResource med2 = new ApplicationSubmissionApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		ApplicationSubmissionApplicantQuestionnaireResponseResource qRes1 = new ApplicationSubmissionApplicantQuestionnaireResponseResource();
		ApplicationSubmissionApplicantQuestionnaireResponseResource qRes2 = new ApplicationSubmissionApplicantQuestionnaireResponseResource();
		qRes2.setQuestionCode("QUES2");

		EqualsVerifier.forClass(ApplicationSubmissionApplicantConditionResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSubmissionApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicationSubmissionApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicationSubmissionApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSubmissionMedicalFactorResource.class, appMed1, appMed2)
				.withPrefabValues(ApplicationSubmissionApplicantQuestionnaireResponseResource.class, qRes1, qRes2)
				.withRedefinedSuperclass().withIgnoredFields("applicant", "links").verify();
	}

	@Test
	public void testApplicationSubmissionApplicantMedicationResourceEquals() {
		ApplicationSubmissionApplicantProductResource prod1 = new ApplicationSubmissionApplicantProductResource();
		ApplicationSubmissionApplicantProductResource prod2 = new ApplicationSubmissionApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSubmissionMedicalFactorResource appMed1 = new ApplicationSubmissionMedicalFactorResource();
		ApplicationSubmissionMedicalFactorResource appMed2 = new ApplicationSubmissionMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSubmissionApplicantResource applicant1 = new ApplicationSubmissionApplicantResource();
		ApplicationSubmissionApplicantResource applicant2 = new ApplicationSubmissionApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicationSubmissionApplicantConditionResource con1 = new ApplicationSubmissionApplicantConditionResource();
		ApplicationSubmissionApplicantConditionResource con2 = new ApplicationSubmissionApplicantConditionResource();
		con2.setConditionCode("ABC");

		EqualsVerifier.forClass(ApplicationSubmissionApplicantMedicationResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSubmissionApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicationSubmissionApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicationSubmissionApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSubmissionMedicalFactorResource.class, appMed1, appMed2)
				.withRedefinedSuperclass().withIgnoredFields("applicantCondition", "links").verify();
	}

	@Test
	public void testApplicationSubmissionApplicantProductResourceEquals() {

		ApplicationSubmissionMedicalFactorResource appMed1 = new ApplicationSubmissionMedicalFactorResource();
		ApplicationSubmissionMedicalFactorResource appMed2 = new ApplicationSubmissionMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSubmissionApplicantResource applicant1 = new ApplicationSubmissionApplicantResource();
		ApplicationSubmissionApplicantResource applicant2 = new ApplicationSubmissionApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicationSubmissionApplicantConditionResource con1 = new ApplicationSubmissionApplicantConditionResource();
		ApplicationSubmissionApplicantConditionResource con2 = new ApplicationSubmissionApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicationSubmissionApplicantMedicationResource med1 = new ApplicationSubmissionApplicantMedicationResource();
		ApplicationSubmissionApplicantMedicationResource med2 = new ApplicationSubmissionApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationSubmissionApplicantProductResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSubmissionApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSubmissionApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicationSubmissionApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSubmissionMedicalFactorResource.class, appMed1, appMed2)
				.withRedefinedSuperclass().withIgnoredFields("applicant", "links").verify();
	}

	@Test
	public void testApplicationSubmissionApplicantResourceEquals() {

		ApplicationSubmissionApplicationResource application1 = new ApplicationSubmissionApplicationResource();
		ApplicationSubmissionApplicationResource application2 = new ApplicationSubmissionApplicationResource();
		application2.setReasonCode("XXX");

		ApplicationSubmissionApplicantProductResource prod1 = new ApplicationSubmissionApplicantProductResource();
		ApplicationSubmissionApplicantProductResource prod2 = new ApplicationSubmissionApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSubmissionMedicalFactorResource appMed1 = new ApplicationSubmissionMedicalFactorResource();
		ApplicationSubmissionMedicalFactorResource appMed2 = new ApplicationSubmissionMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSubmissionApplicantConditionResource con1 = new ApplicationSubmissionApplicantConditionResource();
		ApplicationSubmissionApplicantConditionResource con2 = new ApplicationSubmissionApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicationSubmissionApplicantMedicationResource med1 = new ApplicationSubmissionApplicantMedicationResource();
		ApplicationSubmissionApplicantMedicationResource med2 = new ApplicationSubmissionApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationSubmissionApplicantResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSubmissionApplicationResource.class, application1, application2)
				.withPrefabValues(ApplicationSubmissionApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSubmissionApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSubmissionApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicationSubmissionMedicalFactorResource.class, appMed1, appMed2)
				.withRedefinedSuperclass().withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testApplicationSubmissionMedicalFactorResourceEquals() {

		ApplicationSubmissionApplicationResource application1 = new ApplicationSubmissionApplicationResource();
		ApplicationSubmissionApplicationResource application2 = new ApplicationSubmissionApplicationResource();
		application2.setReasonCode("XXX");

		ApplicationSubmissionApplicantProductResource prod1 = new ApplicationSubmissionApplicantProductResource();
		ApplicationSubmissionApplicantProductResource prod2 = new ApplicationSubmissionApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSubmissionApplicantConditionResource con1 = new ApplicationSubmissionApplicantConditionResource();
		ApplicationSubmissionApplicantConditionResource con2 = new ApplicationSubmissionApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicationSubmissionApplicantMedicationResource med1 = new ApplicationSubmissionApplicantMedicationResource();
		ApplicationSubmissionApplicantMedicationResource med2 = new ApplicationSubmissionApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationSubmissionMedicalFactorResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSubmissionApplicationResource.class, application1, application2)
				.withPrefabValues(ApplicationSubmissionApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSubmissionApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSubmissionApplicantProductResource.class, prod1, prod2)
				.withRedefinedSuperclass().withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testApplicationSubmissionEmployeeResourceEquals() {
		EqualsVerifier.forClass(ApplicationSubmissionEmployeeResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testApplicationSubmissionResourceEquals() {

		ApplicationSubmissionApplicationResource application1 = new ApplicationSubmissionApplicationResource();
		ApplicationSubmissionApplicationResource application2 = new ApplicationSubmissionApplicationResource();
		application2.setReasonCode("XXX");

		EqualsVerifier.forClass(ApplicationSubmissionResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSubmissionApplicationResource.class, application1, application2)
				.withRedefinedSuperclass().withIgnoredFields("links").verify();
	}

	@Test
	public void testApplicationSubmissionResourceResponseEquals() {

		EqualsVerifier.forClass(ApplicationSubmissionResourceResponse.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testApplicationSearchApplicationResourceEquals() {

		ApplicationSearchApplicationProductResource appProd1 = new ApplicationSearchApplicationProductResource();
		ApplicationSearchApplicationProductResource appProd2 = new ApplicationSearchApplicationProductResource();
		appProd2.setProductCode("BCL");

		ApplicationSearchApplicantProductResource prod1 = new ApplicationSearchApplicantProductResource();
		ApplicationSearchApplicantProductResource prod2 = new ApplicationSearchApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSearchMedicalFactorResource appMed1 = new ApplicationSearchMedicalFactorResource();
		ApplicationSearchMedicalFactorResource appMed2 = new ApplicationSearchMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSearchApplicantResource applicant1 = new ApplicationSearchApplicantResource();
		ApplicationSearchApplicantResource applicant2 = new ApplicationSearchApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicationSearchApplicantConditionResource con1 = new ApplicationSearchApplicantConditionResource();
		ApplicationSearchApplicantConditionResource con2 = new ApplicationSearchApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicationSearchApplicantMedicationResource med1 = new ApplicationSearchApplicantMedicationResource();
		ApplicationSearchApplicantMedicationResource med2 = new ApplicationSearchApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationSearchApplicationResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSearchApplicationProductResource.class, appProd1, appProd2)
				.withPrefabValues(ApplicationSearchApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicationSearchApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSearchApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicationSearchApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSearchMedicalFactorResource.class, appMed1, appMed2)
				.withRedefinedSuperclass().withIgnoredFields("links").verify();
	}

	@Test
	public void testApplicationSearchApplicantConditionResourceEquals() {
		ApplicationSearchApplicantProductResource prod1 = new ApplicationSearchApplicantProductResource();
		ApplicationSearchApplicantProductResource prod2 = new ApplicationSearchApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSearchMedicalFactorResource appMed1 = new ApplicationSearchMedicalFactorResource();
		ApplicationSearchMedicalFactorResource appMed2 = new ApplicationSearchMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSearchApplicantResource applicant1 = new ApplicationSearchApplicantResource();
		ApplicationSearchApplicantResource applicant2 = new ApplicationSearchApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicationSearchApplicantMedicationResource med1 = new ApplicationSearchApplicantMedicationResource();
		ApplicationSearchApplicantMedicationResource med2 = new ApplicationSearchApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationSearchApplicantConditionResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSearchApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicationSearchApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicationSearchApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSearchMedicalFactorResource.class, appMed1, appMed2)
				.withRedefinedSuperclass().withIgnoredFields("applicant", "links").verify();
	}

	@Test
	public void testApplicationSearchApplicantMedicationResourceEquals() {
		ApplicationSearchApplicantProductResource prod1 = new ApplicationSearchApplicantProductResource();
		ApplicationSearchApplicantProductResource prod2 = new ApplicationSearchApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSearchMedicalFactorResource appMed1 = new ApplicationSearchMedicalFactorResource();
		ApplicationSearchMedicalFactorResource appMed2 = new ApplicationSearchMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSearchApplicantResource applicant1 = new ApplicationSearchApplicantResource();
		ApplicationSearchApplicantResource applicant2 = new ApplicationSearchApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicationSearchApplicantConditionResource con1 = new ApplicationSearchApplicantConditionResource();
		ApplicationSearchApplicantConditionResource con2 = new ApplicationSearchApplicantConditionResource();
		con2.setConditionCode("ABC");

		EqualsVerifier.forClass(ApplicationSearchApplicantMedicationResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSearchApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicationSearchApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicationSearchApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSearchMedicalFactorResource.class, appMed1, appMed2)
				.withRedefinedSuperclass().withIgnoredFields("applicantCondition", "links").verify();
	}

	@Test
	public void testApplicationSearchApplicantProductResourceEquals() {

		ApplicationSearchMedicalFactorResource appMed1 = new ApplicationSearchMedicalFactorResource();
		ApplicationSearchMedicalFactorResource appMed2 = new ApplicationSearchMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSearchApplicantResource applicant1 = new ApplicationSearchApplicantResource();
		ApplicationSearchApplicantResource applicant2 = new ApplicationSearchApplicantResource();
		applicant2.setApplicantSSN("123123123");

		ApplicationSearchApplicantConditionResource con1 = new ApplicationSearchApplicantConditionResource();
		ApplicationSearchApplicantConditionResource con2 = new ApplicationSearchApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicationSearchApplicantMedicationResource med1 = new ApplicationSearchApplicantMedicationResource();
		ApplicationSearchApplicantMedicationResource med2 = new ApplicationSearchApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationSearchApplicantProductResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSearchApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSearchApplicantResource.class, applicant1, applicant2)
				.withPrefabValues(ApplicationSearchApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSearchMedicalFactorResource.class, appMed1, appMed2)
				.withRedefinedSuperclass().withIgnoredFields("applicant", "links").verify();
	}

	@Test
	public void testApplicationSearchApplicantResourceEquals() {

		ApplicationSearchApplicationResource application1 = new ApplicationSearchApplicationResource();
		ApplicationSearchApplicationResource application2 = new ApplicationSearchApplicationResource();
		application2.setReasonCode("XXX");

		ApplicationSearchApplicantProductResource prod1 = new ApplicationSearchApplicantProductResource();
		ApplicationSearchApplicantProductResource prod2 = new ApplicationSearchApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSearchMedicalFactorResource appMed1 = new ApplicationSearchMedicalFactorResource();
		ApplicationSearchMedicalFactorResource appMed2 = new ApplicationSearchMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		ApplicationSearchApplicantConditionResource con1 = new ApplicationSearchApplicantConditionResource();
		ApplicationSearchApplicantConditionResource con2 = new ApplicationSearchApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicationSearchApplicantMedicationResource med1 = new ApplicationSearchApplicantMedicationResource();
		ApplicationSearchApplicantMedicationResource med2 = new ApplicationSearchApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationSearchApplicantResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSearchApplicationResource.class, application1, application2)
				.withPrefabValues(ApplicationSearchApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSearchApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSearchApplicantProductResource.class, prod1, prod2)
				.withPrefabValues(ApplicationSearchMedicalFactorResource.class, appMed1, appMed2)
				.withRedefinedSuperclass().withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testApplicationSearchMedicalFactorResourceEquals() {

		ApplicationSearchApplicationResource application1 = new ApplicationSearchApplicationResource();
		ApplicationSearchApplicationResource application2 = new ApplicationSearchApplicationResource();
		application2.setReasonCode("XXX");

		ApplicationSearchApplicantProductResource prod1 = new ApplicationSearchApplicantProductResource();
		ApplicationSearchApplicantProductResource prod2 = new ApplicationSearchApplicantProductResource();
		prod2.setCurrentProductQuantity(1.0);

		ApplicationSearchApplicantConditionResource con1 = new ApplicationSearchApplicantConditionResource();
		ApplicationSearchApplicantConditionResource con2 = new ApplicationSearchApplicantConditionResource();
		con2.setConditionCode("ABC");

		ApplicationSearchApplicantMedicationResource med1 = new ApplicationSearchApplicantMedicationResource();
		ApplicationSearchApplicantMedicationResource med2 = new ApplicationSearchApplicantMedicationResource();
		med2.setMedicationCode("ABC");

		EqualsVerifier.forClass(ApplicationSearchMedicalFactorResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSearchApplicationResource.class, application1, application2)
				.withPrefabValues(ApplicationSearchApplicantMedicationResource.class, med1, med2)
				.withPrefabValues(ApplicationSearchApplicantConditionResource.class, con1, con2)
				.withPrefabValues(ApplicationSearchApplicantProductResource.class, prod1, prod2)
				.withRedefinedSuperclass().withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testApplicationSearchEmployeeResourceEquals() {
		EqualsVerifier.forClass(ApplicationSearchEmployeeResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testApplicationSearchResourceResponseEquals() {
		ApplicationSearchApplicationResource app1 = new ApplicationSearchApplicationResource();
		ApplicationSearchApplicationResource app2 = new ApplicationSearchApplicationResource();
		app2.setReasonCode("ABCD");
		EqualsVerifier.forClass(ApplicationSearchResourceResponse.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(ApplicationSearchApplicationResource.class, app1, app2).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testBasicApplicationResourceEquals() {

		BasicApplicationMedicalFactorResource appMed1 = new BasicApplicationMedicalFactorResource();
		BasicApplicationMedicalFactorResource appMed2 = new BasicApplicationMedicalFactorResource();
		appMed2.setMedicalFactorIndicator(true);

		BasicApplicationProductResource appProd1 = new BasicApplicationProductResource();
		BasicApplicationProductResource appProd2 = new BasicApplicationProductResource();
		appProd2.setProductCode("STD");

		EqualsVerifier.forClass(BasicApplicationResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(BasicApplicationMedicalFactorResource.class, appMed1, appMed2)
				.withPrefabValues(BasicApplicationProductResource.class, appProd1, appProd2).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testBasicApplicationMedicalFactorResourceEquals() {

		BasicApplicationResource app1 = new BasicApplicationResource();
		BasicApplicationResource app2 = new BasicApplicationResource();
		app2.setApplicationId(1);

		EqualsVerifier.forClass(BasicApplicationMedicalFactorResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(BasicApplicationResource.class, app1, app2).withRedefinedSuperclass()
				.withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testBasicApplicationProductResourceEquals() {

		BasicApplicationResource app1 = new BasicApplicationResource();
		BasicApplicationResource app2 = new BasicApplicationResource();
		app2.setApplicationId(1);

		EqualsVerifier.forClass(BasicApplicationProductResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withPrefabValues(BasicApplicationResource.class, app1, app2).withRedefinedSuperclass()
				.withIgnoredFields("application", "links").verify();
	}

	@Test
	public void testCondensedApplicationResourceEquals() {

		EqualsVerifier.forClass(CondensedApplicationResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testProductResourceEquals() {

		EqualsVerifier.forClass(ProductResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass().verify();
	}

	@Test
	public void testPreferenceResource1Equals() {

		EmployerResource empl1 = new EmployerResource();
		EmployerResource empl2 = new EmployerResource();
		empl2.setEmployerId(2);

		PreferenceResource pref1 = new PreferenceResource();
		PreferenceResource pref2 = new PreferenceResource();
		pref2.setWaitingPeriodDaysLimitQuantity("50");

		EoiProductResource prod1 = new EoiProductResource();
		EoiProductResource prod2 = new EoiProductResource();
		prod2.setProductCode("ABC");

		PhysicalAddressResource add1 = new PhysicalAddressResource();
		PhysicalAddressResource add2 = new PhysicalAddressResource();
		add2.setAddressLine1Text("dover");

		EqualsVerifier.forClass(PreferenceResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(EmployerResource.class, empl1, empl2)
				.withPrefabValues(EoiProductResource.class, prod1, prod2)
				.withPrefabValues(PhysicalAddressResource.class, add1, add2)
				.withPrefabValues(PreferenceResource.class, pref1, pref2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links", "employer", "createUserIdNum", "lastUpdateUserIdNum", "concurrencyQuantity",
						"createDatetime", "lastUpdateDatetime")
				.verify();
	}

	@Test
	public void testEoiProductResourceEquals() {

		EmployerResource empl1 = new EmployerResource();
		EmployerResource empl2 = new EmployerResource();
		empl2.setEmployerId(2);

		PreferenceResource pref1 = new PreferenceResource();
		PreferenceResource pref2 = new PreferenceResource();
		pref2.setWaitingPeriodDaysLimitQuantity("50");

		EoiProductResource prod1 = new EoiProductResource();
		EoiProductResource prod2 = new EoiProductResource();
		prod2.setProductCode("ABC");

		PhysicalAddressResource add1 = new PhysicalAddressResource();
		PhysicalAddressResource add2 = new PhysicalAddressResource();
		add2.setAddressLine1Text("dover");

		EqualsVerifier.forClass(EoiProductResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(EmployerResource.class, empl1, empl2)
				.withPrefabValues(EoiProductResource.class, prod1, prod2)
				.withPrefabValues(PhysicalAddressResource.class, add1, add2)
				.withPrefabValues(PreferenceResource.class, pref1, pref2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links", "employer", "createUserIdNum", "lastUpdateUserIdNum", "concurrencyQuantity",
						"createDatetime", "lastUpdateDatetime")

				.verify();
	}

	@Test
	public void testEmployerResourceEquals() {

		PreferenceResource pref1 = new PreferenceResource();
		PreferenceResource pref2 = new PreferenceResource();
		pref2.setWaitingPeriodDaysLimitQuantity("50");

		EoiProductResource prod1 = new EoiProductResource();
		EoiProductResource prod2 = new EoiProductResource();
		prod2.setProductCode("ABC");

		PhysicalAddressResource add1 = new PhysicalAddressResource();
		PhysicalAddressResource add2 = new PhysicalAddressResource();
		add2.setAddressLine1Text("dover");

		EqualsVerifier.forClass(EmployerResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(EoiProductResource.class, prod1, prod2)
				.withPrefabValues(PreferenceResource.class, pref1, pref2)
				.withPrefabValues(PhysicalAddressResource.class, add1, add2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withIgnoredFields("links", "createUserIdNum", "lastUpdateUserIdNum", "concurrencyQuantity",
						"createDatetime", "lastUpdateDatetime")
				.withRedefinedSuperclass().verify();
	}

	@Test
	public void testLabelResourceEquals() {

		new LabelResource();
		LabelResource obj2 = new LabelResource();
		obj2.setLabelText("Label Text");

		EqualsVerifier.forClass(LabelResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Timestamp.class, new Timestamp(1), new Timestamp(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withIgnoredFields("links", "createUserIdNum", "lastUpdateUserIdNum", "concurrencyQuantity",
						"createDatetime", "lastUpdateDatetime")
				.withRedefinedSuperclass().verify();
	}

	/**
	 * @return list of classes in the project configured with lombok. These
	 *         classes must have a no arg constructor or tests will fail
	 */
	private static List<Class<?>> getLobokClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		classList.add(Applicant.class);
		classList.add(ApplicantCondition.class);
		classList.add(ApplicantMedication.class);
		classList.add(ApplicantProduct.class);
		classList.add(Application.class);
		classList.add(ApplicationMedicalFactor.class);
		classList.add(ApplicationProduct.class);
		classList.add(QuestionnaireResponse.class);

		classList.add(ApplicationEvent.class);
		classList.add(ApplicationEventResource.class);

		classList.add(MuwApplicationProductConfig.class);
		classList.add(MuwApplicationSecurityProperties.class);
		classList.add(ServiceProperties.class);

		classList.add(ApplicationResource.class);
		classList.add(ApplicantConditionResource.class);
		classList.add(ApplicantMedicationResource.class);
		classList.add(ApplicantProductResource.class);
		classList.add(ApplicantResource.class);
		classList.add(ApplicationMedicalFactorResource.class);
		classList.add(ApplicationProductResource.class);
		classList.add(QuestionnaireResponseResource.class);

		classList.add(ApplicantProductExtendedResource.class);
		classList.add(ApplicantExtendedResource.class);

		classList.add(ApplicationSubmissionApplicantConditionResource.class);
		classList.add(ApplicationSubmissionApplicantMedicationResource.class);
		classList.add(ApplicationSubmissionApplicantProductResource.class);
		classList.add(ApplicationSubmissionApplicantResource.class);
		classList.add(ApplicationSubmissionApplicationResource.class);
		classList.add(ApplicationSubmissionMedicalFactorResource.class);
		classList.add(ApplicationSubmissionEmployeeResource.class);
		classList.add(ApplicationSubmissionResource.class);
		classList.add(ApplicationSubmissionResourceResponse.class);

		classList.add(ApplicationSearchApplicantConditionResource.class);
		classList.add(ApplicationSearchApplicantMedicationResource.class);
		classList.add(ApplicationSearchApplicantProductResource.class);
		classList.add(ApplicationSearchApplicantResource.class);
		classList.add(ApplicationSearchApplicationResource.class);
		classList.add(ApplicationSearchMedicalFactorResource.class);
		classList.add(ApplicationSearchEmployeeResource.class);
		classList.add(ApplicationSearchResourceResponse.class);

		classList.add(BasicApplicationResource.class);
		classList.add(BasicApplicationMedicalFactorResource.class);
		classList.add(BasicApplicationProductResource.class);

		classList.add(CondensedApplicationResource.class);

		classList.add(ProductResource.class);

		classList.add(ErrorMessage.class);
		classList.add(ErrorResource.class);

		classList.add(EmployerResource.class);
		classList.add(EoiProductResource.class);
		classList.add(LabelResource.class);
		classList.add(PhysicalAddressResource.class);
		classList.add(PreferenceResource.class);

		return classList;
	}

	/**
	 * @return list of serializable classes. These classes must have a no arg
	 *         constructor or tests will fail
	 */
	private static List<Class<?>> getSerializableClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		classList.add(Applicant.class);
		classList.add(ApplicantCondition.class);
		classList.add(ApplicantMedication.class);
		classList.add(ApplicantProduct.class);
		classList.add(Application.class);
		classList.add(ApplicationMedicalFactor.class);
		classList.add(ApplicationProduct.class);
		classList.add(ApplicationEvent.class);
		classList.add(QuestionnaireResponse.class);

		return classList;
	}

	/**
	 * @return classes that do not have circular references and can be tested by
	 *         EqualsVerifier without prefab values and ignores
	 */
	private static List<Class<?>> getNonCircularClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		classList.add(MuwApplicationProductConfig.class);
		classList.add(MuwApplicationSecurityProperties.class);
		classList.add(ServiceProperties.class);
		classList.add(ErrorMessage.class);
		classList.add(ErrorResource.class);

		return classList;
	}

	public void testIsSerializable(Class<?> clazz) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Constructor<?> ctor = clazz.getConstructor();
		Object obj = ctor.newInstance(new Object[] {});
		final byte[] serializedObj = SerializationUtils.serialize((Serializable) obj);
		final Object deserializedCustomer = clazz.cast(SerializationUtils.deserialize(serializedObj));
		assertEquals(obj, deserializedCustomer);
	}

	class DateFactory implements Factory<java.sql.Date> {
		@Override
		public java.sql.Date create() {
			return new Date(new java.util.Date().getTime());
		}
	}

	class TimestampFactory implements Factory<java.sql.Timestamp> {
		@Override
		public java.sql.Timestamp create() {
			return new java.sql.Timestamp(new java.util.Date().getTime());
		}
	}
}
