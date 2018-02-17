package com.lmig.ci.lmbc.empr.muw.application.repository;

import java.util.List;

import com.lmig.ci.lmbc.empr.muw.application.domain.Application;

public interface ApplicationRepositoryCustom {

	
	
	public List<Application> findByEmployerIdAndApplicationStatus(int employerId ,String statusTypeCode);
	
	// URL-- condensed_applications?employerId=1&applicationStatus=open

}
