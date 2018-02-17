package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.List;

import org.springframework.http.HttpEntity;

import com.lmig.ci.lmbc.empr.muw.application.api.CondensedApplicationResource;

public interface ApplicationService {
	
	// TODO this appears to be missing some method definitions...
	
	
	
	public HttpEntity<List<CondensedApplicationResource>> findByEmployerIdAndApplicationStatus(int applicationId ,String statusTypeCode);

}

