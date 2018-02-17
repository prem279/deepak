package com.lmig.ci.lmbc.empr.muw.application.domain;

import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;
import com.lmig.ci.lmbc.empr.muw.application.repository.ApplicationRepository;

public class MuwApplicationServiceAuditFieldTests extends MuwApplicationServiceTests {
	@Autowired
	ApplicationRepository applicationRepostiory;
	
	@Test
	public void testAuditFields() throws Exception {

		Date beforeSaveTime = new Date(new java.util.Date().getTime());
		
		Application application = new Application();
		application.setEmployerId(1);
		application.setEmployerEmployeeId(2);
		application.setReasonCode("FMLY");
		application.setSubmissionMethodCode("ONLN");
		
		//wrap in sleep to ensure time delay for compare
		TimeUnit.SECONDS.sleep(1);
		Application savedApplication = applicationRepostiory.saveAndFlush(application);
		TimeUnit.SECONDS.sleep(1);

		Date afterSaveTime = new Date(new java.util.Date().getTime());

		//test created fields
		assertTrue(savedApplication.getConcurrencyQuantity().equals(0));
		assertTrue(savedApplication.getCreateUserIdNum().equals("JUNIT"));
		assertTrue(savedApplication.getCreateDatetime().compareTo(beforeSaveTime) > 0);
		assertTrue(savedApplication.getCreateDatetime().compareTo(afterSaveTime) < 0);

		//test updated fields
		savedApplication.setReasonCode("ANNL");
		
		Date beforeUpdateTime = new Date(new java.util.Date().getTime());
		
		//wrap in sleep to ensure time delay for compare
		TimeUnit.SECONDS.sleep(1);
		savedApplication = applicationRepostiory.saveAndFlush(savedApplication);
		TimeUnit.SECONDS.sleep(1);
		
		Date afterUpdateTime = new Date(new java.util.Date().getTime());

		assertTrue(savedApplication.getConcurrencyQuantity().equals(1));
		assertTrue(savedApplication.getLastUpdateUserIdNum().equals("JUNIT"));
		assertTrue(savedApplication.getLastUpdateDatetime().compareTo(beforeUpdateTime) > 0);
		assertTrue(savedApplication.getLastUpdateDatetime().compareTo(afterUpdateTime) < 0);
	}
}
