/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.springframework.hateoas.Link;

import com.lmig.ci.lmbc.empr.common.domain.ErrorMessage;
import com.lmig.ci.lmbc.empr.common.domain.ErrorResource;
import com.lmig.ci.lmbc.empr.muw.account.api.AccountServiceEventLogResource;
import com.lmig.ci.lmbc.empr.muw.account.api.EmployerResource;
import com.lmig.ci.lmbc.empr.muw.account.api.EoiProductResource;
import com.lmig.ci.lmbc.empr.muw.account.api.LabelResource;
import com.lmig.ci.lmbc.empr.muw.account.api.PhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.account.api.PreferenceResource;
import com.lmig.ci.lmbc.empr.muw.account.config.MuwAccountDataSourceProperties;
import com.lmig.ci.lmbc.empr.muw.account.config.MuwAccountSecurityProperties;
import com.lmig.ci.lmbc.empr.muw.account.config.ServiceProperties;
import com.lmig.ci.lmbc.empr.muw.account.domain.AccountServiceEventLog;
import com.lmig.ci.lmbc.empr.muw.account.domain.Employer;
import com.lmig.ci.lmbc.empr.muw.account.domain.EoiProduct;
import com.lmig.ci.lmbc.empr.muw.account.domain.Label;
import com.lmig.ci.lmbc.empr.muw.account.domain.Preference;
import com.lmig.ci.lmbc.empr.muw.account.domain.StakeholderLedgerSerialNumberPk;
import com.lmig.ci.lmbc.empr.muw.contact.resource.ContactDetailsResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyEmailAddressActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyEmailAddressResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhoneNumActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhoneNumberResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressActyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyPhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyReferenceResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyResource;
import com.lmig.ci.lmbc.empr.muw.contact.resource.PartyRoleResource;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class MuwAccountServiceBeanTests extends MuwAccountServiceTests {
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

	private static List<Class<?>> getNonCircularClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		classList.add(MuwAccountSecurityProperties.class);
		classList.add(ServiceProperties.class);
		classList.add(ErrorMessage.class);
		classList.add(ErrorResource.class);

		return classList;
	}

	@Test
	public void testEmployerEquals() {

		PreferenceResource pref1 = new PreferenceResource();
		PreferenceResource pref2 = new PreferenceResource();
		pref2.setWaitingPeriodDaysLimitQuantity("50");

		EoiProduct prod1 = new EoiProduct();
		EoiProduct prod2 = new EoiProduct();
		prod2.setProductCode("ABC");

		EqualsVerifier.forClass(Employer.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2)).withPrefabValues(EoiProduct.class, prod1, prod2)
				.withPrefabValues(PreferenceResource.class, pref1, pref2).withIgnoredFields("preference")
				.withRedefinedSuperclass().verify();
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
	public void testPreferenceEquals() {

		Employer empl1 = new Employer();
		Employer empl2 = new Employer();
		empl2.setEmployerId(2);

		EoiProduct prod1 = new EoiProduct();
		EoiProduct prod2 = new EoiProduct();
		prod2.setProductCode("ABC");

		Label label1 = new Label();
		Label label2 = new Label();
		label2.setLabelText("Label Text");

		EqualsVerifier.forClass(Preference.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2)).withPrefabValues(Employer.class, empl1, empl2)
				.withPrefabValues(EoiProduct.class, prod1, prod2).withPrefabValues(Label.class, label1, label2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc"))
				.withIgnoredFields("employeeLabelCode", "spouseLabelCode", "employeeIdLabelCode")
				.withRedefinedSuperclass().verify();
	}

	@Test
	public void testPhysicalAddressResourceEquals() {

		PhysicalAddressResource add1 = new PhysicalAddressResource();
		PhysicalAddressResource add2 = new PhysicalAddressResource();
		add2.setAddressLine1Text("dover");

		EqualsVerifier.forClass(PhysicalAddressResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PhysicalAddressResource.class, add1, add2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
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
	public void testEoiProductEquals() {

		Employer empl1 = new Employer();
		Employer empl2 = new Employer();
		empl2.setEmployerId(2);

		Preference pref1 = new Preference();
		Preference pref2 = new Preference();
		pref2.setWaitingPeriodDaysLimitQuantity("50");

		EoiProduct prod1 = new EoiProduct();
		EoiProduct prod2 = new EoiProduct();
		prod2.setProductCode("ABC");

		EqualsVerifier.forClass(EoiProduct.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2)).withPrefabValues(Employer.class, empl1, empl2)
				.withPrefabValues(EoiProduct.class, prod1, prod2).withPrefabValues(Preference.class, pref1, pref2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("employer").verify();
	}

	@Test
	public void testPartyReferenceResourceEquals() {
		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		EqualsVerifier.forClass(PartyReferenceResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2)
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links", "party").withRedefinedSuperclass().verify();

	}

	@Test
	public void testPartyResourceEquals() {
		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyPhoneNumberResource phn1 = new PartyPhoneNumberResource();
		PartyPhoneNumberResource phn2 = new PartyPhoneNumberResource();
		phn2.setExtension("1234");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		PartyPhysicalAddressResource add1 = new PartyPhysicalAddressResource();
		PartyPhysicalAddressResource add2 = new PartyPhysicalAddressResource();
		add2.setAddressLine2Text("Dover");

		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

		EqualsVerifier.forClass(PartyResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2)
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyPhoneNumberResource.class, phn1, phn2)
				.withPrefabValues(PartyPhysicalAddressResource.class, add1, add2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withIgnoredFields("links")
				.withRedefinedSuperclass().verify();
	}

	@Test
	public void testPartyRoleResourceEquals() {
		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		new PartyRoleResource();
		PartyRoleResource rol2 = new PartyRoleResource();
		rol2.setPartyRoleId(2);

		PartyPhoneNumberResource phn1 = new PartyPhoneNumberResource();
		PartyPhoneNumberResource phn2 = new PartyPhoneNumberResource();
		phn2.setExtension("1234");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		PartyPhysicalAddressResource add1 = new PartyPhysicalAddressResource();
		PartyPhysicalAddressResource add2 = new PartyPhysicalAddressResource();
		add2.setAddressLine2Text("Dover");

		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

		EqualsVerifier.forClass(PartyRoleResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2)
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyPhoneNumberResource.class, phn1, phn2)
				.withPrefabValues(PartyPhysicalAddressResource.class, add1, add2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass().verify();
	}

	@Test
	public void testPartyEmailResourceEquals() {

		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

		PartyEmailAddressActyResource act1 = new PartyEmailAddressActyResource();
		PartyEmailAddressActyResource act2 = new PartyEmailAddressActyResource();
		act2.setActivityType("WORK");

		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		EqualsVerifier.forClass(PartyEmailAddressResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyEmailAddressActyResource.class, act1, act2)
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links", "party").verify();

	}

	@Test
	public void testPartyPhysicalAddressResourceEquals() {

		new PartyPhysicalAddressResource();
		PartyPhysicalAddressResource add2 = new PartyPhysicalAddressResource();
		add2.setAddressLine2Text("Dover");

		PartyPhysicalAddressActyResource acty1 = new PartyPhysicalAddressActyResource();
		PartyPhysicalAddressActyResource acty2 = new PartyPhysicalAddressActyResource();
		acty2.setActivityType("WORK");

		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

		PartyEmailAddressActyResource act1 = new PartyEmailAddressActyResource();
		PartyEmailAddressActyResource act2 = new PartyEmailAddressActyResource();
		act2.setActivityType("WORK");

		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		EqualsVerifier.forClass(PartyPhysicalAddressResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyEmailAddressActyResource.class, act1, act2)
				.withPrefabValues(PartyPhysicalAddressActyResource.class, acty1, acty2)
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withIgnoredFields("party")
				.withRedefinedSuperclass().verify();

	}

	@Test
	public void testPartyPhysicalAddressActyResourceEquals() {

		PartyPhysicalAddressResource add1 = new PartyPhysicalAddressResource();
		PartyPhysicalAddressResource add2 = new PartyPhysicalAddressResource();
		add2.setAddressLine2Text("Dover");

		new PartyPhysicalAddressActyResource();
		PartyPhysicalAddressActyResource acti2 = new PartyPhysicalAddressActyResource();
		acti2.setActivityType("WORK");

		PartyPhoneNumberResource phn1 = new PartyPhoneNumberResource();
		PartyPhoneNumberResource phn2 = new PartyPhoneNumberResource();
		phn2.setCountryCode("USA");

		PartyPhoneNumActyResource acty1 = new PartyPhoneNumActyResource();
		PartyPhoneNumActyResource acty2 = new PartyPhoneNumActyResource();
		acty2.setActivityType("WORK");

		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

		new PartyEmailAddressActyResource();
		PartyEmailAddressActyResource act2 = new PartyEmailAddressActyResource();
		act2.setActivityType("WORK");

		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		EqualsVerifier.forClass(PartyPhysicalAddressActyResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyPhoneNumberResource.class, phn1, phn2)
				.withPrefabValues(PartyPhysicalAddressResource.class, add1, add2)
				.withPrefabValues(PartyPhoneNumActyResource.class, acty1, acty2)
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2).withRedefinedSuperclass()
				.withIgnoredFields("partyPhysicalAddress").verify();
	}

	@Test
	public void testPartyPhoneNumberResourceEquals() {

		new PartyPhoneNumberResource();
		PartyPhoneNumberResource phn2 = new PartyPhoneNumberResource();
		phn2.setCountryCode("USA");

		PartyPhoneNumActyResource acty1 = new PartyPhoneNumActyResource();
		PartyPhoneNumActyResource acty2 = new PartyPhoneNumActyResource();
		acty2.setActivityType("WORK");

		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

		PartyEmailAddressActyResource act1 = new PartyEmailAddressActyResource();
		PartyEmailAddressActyResource act2 = new PartyEmailAddressActyResource();
		act2.setActivityType("WORK");

		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		EqualsVerifier.forClass(PartyPhoneNumberResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyEmailAddressActyResource.class, act1, act2)
				.withPrefabValues(PartyPhoneNumActyResource.class, acty1, acty2)
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass().verify();

	}

	@Test
	public void testPartyPhoneNumberActyResourceEquals() {

		PartyPhoneNumberResource phn1 = new PartyPhoneNumberResource();
		PartyPhoneNumberResource phn2 = new PartyPhoneNumberResource();
		phn2.setCountryCode("USA");

		PartyPhoneNumActyResource acty1 = new PartyPhoneNumActyResource();
		PartyPhoneNumActyResource acty2 = new PartyPhoneNumActyResource();
		acty2.setActivityType("WORK");

		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

		new PartyEmailAddressActyResource();
		PartyEmailAddressActyResource act2 = new PartyEmailAddressActyResource();
		act2.setActivityType("WORK");

		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		EqualsVerifier.forClass(PartyPhoneNumActyResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyPhoneNumberResource.class, phn1, phn2)
				.withPrefabValues(PartyPhoneNumActyResource.class, acty1, acty2)
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2).withRedefinedSuperclass().verify();
	}

	@Test
	public void testPartyEmailActyResourceEquals() {
		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

		new PartyEmailAddressActyResource();
		PartyEmailAddressActyResource act2 = new PartyEmailAddressActyResource();
		act2.setActivityType("WORK");

		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		EqualsVerifier.forClass(PartyEmailAddressActyResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2).withRedefinedSuperclass()
				.withIgnoredFields("partyEmailAddress").verify();
	}

	@Test
	public void testEmployerEventEquals() {

		EqualsVerifier.forClass(AccountServiceEventLog.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Timestamp.class, new Timestamp(1), new Timestamp(2)).withRedefinedSuperclass()
				.verify();
	}

	@Transactional
	@Test
	public void testEmployerEventResourceEquals() {

		EqualsVerifier.forClass(AccountServiceEventLogResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Timestamp.class, new Timestamp(1), new Timestamp(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testLabelEquals() {

		EqualsVerifier.forClass(Label.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Timestamp.class, new Timestamp(1), new Timestamp(2))
				.withIgnoredFields("createUserIdNum", "lastUpdateUserIdNum", "concurrencyQuantity", "createDatetime",
						"lastUpdateDatetime")
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

	@Test
	public void testConfigClassesEquals() {
		for (Class<?> clazz : nonCircularClasses) {
			EqualsVerifier.forClass(clazz).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
		}
	}

	//custom test for EmployerResource because it has a custome getter for situsStateCode
	@Test
	public void testEmployerResourceGettersAndSetters() {
		BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection().addFactory(java.sql.Date.class, new DateFactory());
		beanTester.getFactoryCollection().addFactory(java.util.Date.class, new UtilDateFactory());
		beanTester.getFactoryCollection().addFactory(java.sql.Timestamp.class, new TimestampFactory());
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("situsStateCode")
				.ignoreProperty("preference").build();
		beanTester.testBean(EmployerResource.class, configuration);
	}

	@Test
	public void testGettersAndSetters() {
		BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection().addFactory(java.sql.Date.class, new DateFactory());
		beanTester.getFactoryCollection().addFactory(java.util.Date.class, new UtilDateFactory());
		beanTester.getFactoryCollection().addFactory(java.sql.Timestamp.class, new TimestampFactory());
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("employer").ignoreProperty("preference")
				.build();
		for (Class<?> clazz : lobokClasses) {
			beanTester.testBean(clazz, configuration);
		}
	}

	/**
	 * @return list of classes in the project configured with lombok. These
	 *         classes must have a no arg constructor or tests will fail
	 */
	private static List<Class<?>> getLobokClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		classList.add(AccountServiceEventLogResource.class);
		classList.add(EoiProductResource.class);
		classList.add(PhysicalAddressResource.class);
		classList.add(PreferenceResource.class);
		classList.add(MuwAccountDataSourceProperties.class);
		classList.add(MuwAccountSecurityProperties.class);
		classList.add(ServiceProperties.class);
		classList.add(AccountServiceEventLog.class);
		classList.add(Employer.class);
		classList.add(EoiProduct.class);
		classList.add(Preference.class);
		classList.add(Label.class);

		classList.add(ContactDetailsResource.class);
		classList.add(PartyPhoneNumActyResource.class);
		classList.add(PartyPhoneNumberResource.class);
		classList.add(PartyPhysicalAddressActyResource.class);
		classList.add(PartyPhysicalAddressResource.class);
		classList.add(PartyReferenceResource.class);
		classList.add(PartyResource.class);
		classList.add(PartyRoleResource.class);
		classList.add(PartyEmailAddressResource.class);
		classList.add(PartyEmailAddressActyResource.class);
		classList.add(LabelResource.class);

		return classList;
	}

	/**
	 * @return list of serializable classes. These classes must have a no arg
	 *         constructor or tests will fail
	 */
	private static List<Class<?>> getSerializableClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		classList.add(AccountServiceEventLog.class);
		classList.add(Employer.class);
		classList.add(EoiProduct.class);
		classList.add(Preference.class);
		classList.add(Label.class);
		classList.add(StakeholderLedgerSerialNumberPk.class);
		classList.add(ContactDetailsResource.class);
		classList.add(PartyPhoneNumActyResource.class);
		classList.add(PartyPhoneNumberResource.class);
		classList.add(PartyPhysicalAddressActyResource.class);
		classList.add(PartyPhysicalAddressResource.class);
		classList.add(PartyReferenceResource.class);
		classList.add(PartyResource.class);
		classList.add(PartyRoleResource.class);
		classList.add(PartyEmailAddressResource.class);
		classList.add(PartyEmailAddressActyResource.class);

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

	class UtilDateFactory implements Factory<java.util.Date> {
		@Override
		public java.util.Date create() {
			return new java.util.Date();
		}
	}

	class TimestampFactory implements Factory<java.sql.Timestamp> {
		@Override
		public java.sql.Timestamp create() {
			return new java.sql.Timestamp(new java.util.Date().getTime());
		}
	}
}
