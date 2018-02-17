/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 15 Sep 2016
 */

package com.lmig.ci.lmbc.empr.common.domain;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.domain.Auditable;

import com.lmig.ci.lmbc.empr.common.util.DateTimeUtil;
/**
 * 
 * @author n0296170
 *  
 */
public class AmericaNYAuditingEntityListener {
	
	public static String ROW_CREATE_UPDATE_NAME = "SYSTEM";
	
	public static String getRowCreateUpdateName() {
		return ROW_CREATE_UPDATE_NAME;
	}
	
	
	@PrePersist
	public void touchForCreate(AbstractAuditableTableEntity abstractAuditableTableEntity) throws ParseException {
	    Date currentDate = DateTimeUtil.getCurrentAmericaNYDatetime();
	    abstractAuditableTableEntity.setCreateDatetime(currentDate);
	}

	/**
	 * Sets modification and creation date and auditor on the target object in case it implements {@link Auditable} on
	 * update events. Set the time in UTC format
	 * 
	 * @param target
	 * @throws ParseException 
	 */
	@PreUpdate
	public void touchForUpdate(AbstractAuditableTableEntity abstractAuditableTableEntity) throws ParseException {
		abstractAuditableTableEntity.setLastUpdateDatetime(DateTimeUtil.getCurrentAmericaNYDatetime());
	    //abstractAuditableTableEntity.setRowLastChngTimeStamp(DateTimeUtil.getCurrentAmericaNYDatetime());
		//abstractAuditableTableEntity.setRowOperName(ROW_CREATE_UPDATE_NAME);
	}
}
