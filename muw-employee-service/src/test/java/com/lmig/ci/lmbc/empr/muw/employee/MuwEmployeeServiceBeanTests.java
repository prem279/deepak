package com.lmig.ci.lmbc.empr.muw.employee;

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
import com.lmig.ci.lmbc.empr.muw.employee.api.EmailAddressResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeEventResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.EmployeeResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.PhoneNumberResource;
import com.lmig.ci.lmbc.empr.muw.employee.api.PhysicalAddressResource;
import com.lmig.ci.lmbc.empr.muw.employee.config.EmployeeConfig;
import com.lmig.ci.lmbc.empr.muw.employee.config.MuwEmployeeSecurityProperties;
import com.lmig.ci.lmbc.empr.muw.employee.config.ServiceProperties;
import com.lmig.ci.lmbc.empr.muw.employee.domain.Employee;
import com.lmig.ci.lmbc.empr.muw.employee.domain.EmployeeEvent;
import com.lmig.ci.lmbc.empr.muw.employer.resource.EmployerResource;
import com.lmig.ci.lmbc.empr.muw.employer.resource.EoiProductResource;
import com.lmig.ci.lmbc.empr.muw.employer.resource.LabelResource;
import com.lmig.ci.lmbc.empr.muw.employer.resource.PreferenceResource;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class MuwEmployeeServiceBeanTests extends MuwEmployeeServiceTests {

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
		beanTester.getFactoryCollection().addFactory(java.sql.Timestamp.class, new TimestampFactory());
		beanTester.getFactoryCollection().addFactory(java.sql.Date.class, new DateFactory());
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
	public void testEmployeeEquals() {

		EqualsVerifier.forClass(Employee.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				// .withPrefabValues(Employee.class, obj1, obj2)
				.withRedefinedSuperclass().withIgnoredFields("createDatetime", "lastUpdateDatetime",
						"createUserIdNumber", "lastUpdatedUserIdNumber", "concurrencyQuantity")
				.verify();
	}

	@Test
	public void testEmployeeResourceEquals() {
		EqualsVerifier.forClass(EmployeeResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2)).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testEmployerResourceEquals() {
		PreferenceResource pref1 = new PreferenceResource();
		PreferenceResource pref2 = new PreferenceResource();
		pref2.setApplicantIdentificationMethodCode("ABC");

		EoiProductResource prod1 = new EoiProductResource();
		EoiProductResource prod2 = new EoiProductResource();
		prod2.setProductCode("ABC");

		PhysicalAddressResource add1 = new PhysicalAddressResource();
		PhysicalAddressResource add2 = new PhysicalAddressResource();
		add2.setAddressLine1Text("dover");

		EqualsVerifier.forClass(EmployerResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(EoiProductResource.class, prod1, prod2)
				.withPrefabValues(PhysicalAddressResource.class, add1, add2)
				.withPrefabValues(PreferenceResource.class, pref1, pref2)
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
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links", "employer", "createUserIdNum", "lastUpdateUserIdNum", "concurrencyQuantity",
						"createDatetime", "lastUpdateDatetime")
				.verify();
	}

	@Test
	public void testPhysicalAddressResourceEquals() {
		EqualsVerifier.forClass(PhysicalAddressResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testEmailAddressResourceEquals() {
		EqualsVerifier.forClass(PhysicalAddressResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testPhoneNumberResourceEquals() {
		EqualsVerifier.forClass(PhysicalAddressResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
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
		pref2.setApplicantIdentificationMethodCode("ABC");

		PhysicalAddressResource add1 = new PhysicalAddressResource();
		PhysicalAddressResource add2 = new PhysicalAddressResource();
		add2.setAddressLine1Text("dover");

		EqualsVerifier.forClass(EoiProductResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(EmployerResource.class, empl1, empl2)
				.withPrefabValues(PhysicalAddressResource.class, add1, add2)
				.withPrefabValues(PreferenceResource.class, pref1, pref2)
				.withPrefabValues(Link.class, new Link("123"), new Link("abc")).withRedefinedSuperclass()
				.withIgnoredFields("links", "employer", "createUserIdNum", "lastUpdateUserIdNum", "concurrencyQuantity",
						"createDatetime", "lastUpdateDatetime")

				.verify();
	}

	@Test
	public void testPartyReferenceResourceEquals() {
		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		EqualsVerifier.forClass(PartyReferenceResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2).withIgnoredFields("links", "party")
				.withRedefinedSuperclass().verify();

	}

	@Test
	public void testPartyResourceEquals() {
		PartyRoleResource partyRoleResource1 = new PartyRoleResource();
		PartyRoleResource partyRoleResource2 = new PartyRoleResource();
		partyRoleResource2.setRoleType("ZZZ");

		PartyPhoneNumberResource partyPhoneNumberResource1 = new PartyPhoneNumberResource();
		PartyPhoneNumberResource partyPhoneNumberResource2 = new PartyPhoneNumberResource();
		partyPhoneNumberResource2.setPhoneNumber("1234567890");

		PartyReferenceResource partyReferenceResource1 = new PartyReferenceResource();
		PartyReferenceResource partyReferenceResource2 = new PartyReferenceResource();
		partyReferenceResource2.setReferenceType("MUWEE");

		PartyPhysicalAddressResource partyPhysicalAddressResource1 = new PartyPhysicalAddressResource();
		PartyPhysicalAddressResource partyPhysicalAddressResource2 = new PartyPhysicalAddressResource();
		partyPhysicalAddressResource2.setAddressLine1Text("150 Liberty Way");

		PartyEmailAddressResource partyEmailAddressResource1 = new PartyEmailAddressResource();
		PartyEmailAddressResource partyEmailAddressResource2 = new PartyEmailAddressResource();
		partyEmailAddressResource2.setElectronicAddressText("bob@abc.com");

		EqualsVerifier.forClass(PartyResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyRoleResource.class, partyRoleResource1, partyRoleResource2)
				.withPrefabValues(PartyPhoneNumberResource.class, partyPhoneNumberResource1, partyPhoneNumberResource2)
				.withPrefabValues(PartyReferenceResource.class, partyReferenceResource1, partyReferenceResource2)
				.withPrefabValues(PartyPhysicalAddressResource.class, partyPhysicalAddressResource1,
						partyPhysicalAddressResource2)
				.withPrefabValues(PartyEmailAddressResource.class, partyEmailAddressResource1,
						partyEmailAddressResource2)
				.withIgnoredFields("links").withRedefinedSuperclass().verify();
	}

	@Test
	public void testEmployeeEventEquals() {
		EqualsVerifier.forClass(EmployeeEvent.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Timestamp.class, new Timestamp(1), new Timestamp(2)).withRedefinedSuperclass()
				.verify();
	}

	@Test
	public void testEmployeeEventResourceEquals() {
		EqualsVerifier.forClass(EmployeeEventResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(Timestamp.class, new Timestamp(1), new Timestamp(2)).withRedefinedSuperclass()
				.withIgnoredFields("links").verify();
	}

	@Test
	public void testPartyRoleResourceEquals() {
		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		EqualsVerifier.forClass(PartyRoleResource.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2).withRedefinedSuperclass();
	}

	@Test
	public void testPartyEmailResourceEquals() {
		PartyEmailAddressActyResource partyEmailAddressActyResource1 = new PartyEmailAddressActyResource();
		PartyEmailAddressActyResource partyEmailAddressActyResource2 = new PartyEmailAddressActyResource();
		partyEmailAddressActyResource2.setActivityType("WORK");

		PartyResource obj1 = new PartyResource();
		PartyResource obj2 = new PartyResource();
		obj2.setFirstName("Deepak");

		PartyReferenceResource ref1 = new PartyReferenceResource();
		PartyReferenceResource ref2 = new PartyReferenceResource();
		ref2.setReferenceNumber("AMc");

		EqualsVerifier.forClass(PartyEmailAddressResource.class)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
				.withPrefabValues(Date.class, new Date(1), new Date(2))
				.withPrefabValues(PartyEmailAddressActyResource.class, partyEmailAddressActyResource1,
						partyEmailAddressActyResource2)
				.withPrefabValues(PartyResource.class, obj1, obj2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2).withRedefinedSuperclass()
				.withIgnoredFields("links", "party").verify();

	}

	@Test
	public void testPartyEmailActyResourceEquals() {
		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

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
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2).withIgnoredFields("partyEmailAddress")
				.withRedefinedSuperclass().verify();
	}

	@Test
	public void testPartyPhoneNumberResourceEquals() {
		PartyEmailAddressActyResource emlAct1 = new PartyEmailAddressActyResource();
		PartyEmailAddressActyResource emlAct2 = new PartyEmailAddressActyResource();
		emlAct2.setPartyEmailAddressActivityId(1);

		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

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
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyEmailAddressActyResource.class, emlAct1, emlAct2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2).withRedefinedSuperclass();

	}

	@Test
	public void testPartyPhoneNumberActyResourceEquals() {

		PartyEmailAddressActyResource emlAct1 = new PartyEmailAddressActyResource();
		PartyEmailAddressActyResource emlAct2 = new PartyEmailAddressActyResource();
		emlAct2.setPartyEmailAddressActivityId(1);

		PartyEmailAddressResource eml1 = new PartyEmailAddressResource();
		PartyEmailAddressResource eml2 = new PartyEmailAddressResource();
		eml2.setElectronicAddressReasonCode("WORK");

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
				.withPrefabValues(PartyEmailAddressResource.class, eml1, eml2)
				.withPrefabValues(PartyEmailAddressActyResource.class, emlAct1, emlAct2)
				.withPrefabValues(PartyReferenceResource.class, ref1, ref2).withIgnoredFields("partyPhoneNumber")
				.withRedefinedSuperclass();

	}

	private static List<Class<?>> getLobokClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		classList.add(Employee.class);
		classList.add(EmployeeEvent.class);
		classList.add(EmployeeEventResource.class);

		classList.add(EmployeeConfig.class);
		classList.add(MuwEmployeeSecurityProperties.class);
		classList.add(ServiceProperties.class);

		classList.add(ErrorMessage.class);
		classList.add(ErrorResource.class);

		classList.add(LabelResource.class);
		classList.add(EmployeeResource.class);
		classList.add(EmployerResource.class);
		classList.add(EoiProductResource.class);
		classList.add(PreferenceResource.class);
		classList.add(PhysicalAddressResource.class);
		classList.add(EmailAddressResource.class);
		classList.add(PhoneNumberResource.class);

		classList.add(PartyPhoneNumActyResource.class);
		classList.add(PartyPhoneNumberResource.class);
		classList.add(PartyPhysicalAddressActyResource.class);
		classList.add(PartyPhysicalAddressResource.class);
		classList.add(PartyReferenceResource.class);
		classList.add(PartyResource.class);
		classList.add(PartyRoleResource.class);
		classList.add(PartyEmailAddressResource.class);
		classList.add(PartyEmailAddressActyResource.class);
		classList.add(ContactDetailsResource.class);

		return classList;
	}

	private static List<Class<?>> getNonCircularClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		classList.add(MuwEmployeeSecurityProperties.class);
		classList.add(ServiceProperties.class);
		classList.add(ErrorMessage.class);
		classList.add(ErrorResource.class);

		return classList;
	}

	private static List<Class<?>> getSerializableClasses() {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		classList.add(Employee.class);
		classList.add(EmployeeEvent.class);

		classList.add(EmailAddressResource.class);
		classList.add(PhoneNumberResource.class);
		classList.add(EmployeeResource.class);

		classList.add(LabelResource.class);
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

		classList.add(EmployeeResource.class);
		classList.add(EoiProductResource.class);
		classList.add(PreferenceResource.class);
		classList.add(PhysicalAddressResource.class);

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