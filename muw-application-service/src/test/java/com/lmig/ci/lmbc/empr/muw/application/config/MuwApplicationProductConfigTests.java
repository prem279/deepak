package com.lmig.ci.lmbc.empr.muw.application.config;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lmig.ci.lmbc.empr.muw.application.MuwApplicationServiceTests;


public class MuwApplicationProductConfigTests extends MuwApplicationServiceTests {

	
		
	@Autowired
	private MuwApplicationProductConfig muwApplicationProductConfig;
	//private MuwApplicationProductConfig productMap;
	
	@Transactional
	@Test
	public void testGetProductByKey() {
		
	//	List<String> applicantCode = MuwApplicationProductConfig.eligibleApplicantRoleMap.get("BEL") ;
		List<String> applicantCode = muwApplicationProductConfig.productConfiguration.get("BEL") ;
	
		
		List<String> expectedApplicantCode = new ArrayList<String>();
		expectedApplicantCode.add("EMPLY");
//		assertEquals(productConfig.getBEL(), expectedApplicantCode);
	
		assertEquals(expectedApplicantCode,applicantCode);
		
				
	}
	@Test
	public void testGetProductByKeyMultiple() {
		
//	List<String> applicantCode = MuwApplicationProductConfig.eligibleApplicantRoleMap.get("BFL") ;
		List<String> applicantCode = muwApplicationProductConfig.productConfiguration.get("BFL") ;
	
		
		List<String> expectedApplicantCode = new ArrayList<String>();
		expectedApplicantCode.add("SPUSE");
		expectedApplicantCode.add("CHILD");
//		assertEquals(productConfig.getBEL(), expectedApplicantCode);
	
		assertEquals(expectedApplicantCode,applicantCode);
		
				
	}

	

}
