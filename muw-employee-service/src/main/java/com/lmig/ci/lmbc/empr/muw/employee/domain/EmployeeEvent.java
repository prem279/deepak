/* 
 * Copyright (C) 2015, Liberty Mutual Group 
 * 
 * Created on April 04, 2016 
 */
package com.lmig.ci.lmbc.empr.muw.employee.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author n0296170
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="emplr_emp_event_t")
public class EmployeeEvent extends AbstractAuditableTableEntity implements Serializable{
	
	private static final long serialVersionUID = 6153747264695099571L;

	@Id
	@Column(name="event_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="emplr_emp_event_seq")
	@SequenceGenerator(name="emplr_emp_event_seq", sequenceName="EMPLR_EMP_EVENT_SEQ", allocationSize = 1)
    private Integer eventId;
	
	@Column(name="sjct_area_id")
	private int subjectAreaId;
	
	@Column(name="sjct_area_cde")
	private String subjectAreaCode;
    
    @Column(name="chng_event_cde")
    private String changeEventCode;
    
    @Column(name="busn_event_cde")
	private String businessEventCode;
	
    @Column(name="bfr_evnt_data_txt")
	private String beforeEventData;
	
	@Column(name="chng_set_txt")
	private String changeSet;
	
	@Column(name="aftr_evnt_data_txt")
	private String afterEventData;
	
	@Column(name="lock_owner_name")
	private String lockOwnerName;
	
	@Column(name="lock_dtm")
	private Timestamp lockTime;
	
	@Column(name="prcsd_i")
	private String processedIndicator;

	@Column(name="prcsd_dtm")
	private Timestamp processedTime;

}
