package com.lmig.ci.lmbc.empr.muw.employee.repository;

import java.sql.Date;

import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;

public class RepositoryTestsCommon {
	
	public static int EMPLOYER_ID = 1;
	
	public static void setDateFields (AbstractAuditableTableEntity object) {
		Date date = new Date(new java.util.Date().getTime());
		object.setCreateDatetime(date);
		object.setLastUpdateDatetime(date);
//		object.setRowEffectiveDatetime(date);
//		object.setRowExpirationDatetime(date);
	}

}
