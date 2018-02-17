/* 
 * Copyright (C) 2015, Liberty Mutual Group 
 * 
 * Created on May 10, 2016 
 */
package com.lmig.ci.lmbc.empr.muw.account.domain;

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
 * @author n0132389
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="muw_emplr_evnt_t")
public class AccountServiceEventLog extends AbstractAuditableTableEntity implements Serializable{
	private static final long serialVersionUID = -8732330204929109692L;
	
    @Id
	@Column(name="evnt_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="muw_emplr_evnt_seq")
	@SequenceGenerator(name="muw_emplr_evnt_seq", sequenceName="MUW_EMPLR_EVNT_SEQ", allocationSize = 1)
    private Integer eventId;
    
    @Column(name="chng_evnt_cde")
    private String changeEventCode;
    
    @Column(name="busn_evnt_cde")
	private String businessEventCode;
	
	@Column(name="sjct_area_cde")
	private String subjectAreaCode;
	
	@Column(name="sjct_area_id")
	private int subjectAreaId;
	
	@Column(name="bfr_evnt_data_txt")
	private String beforeEventData;
	
	@Column(name="chng_set_txt")
	private String changeSet;
	
	@Column(name="aftr_evnt_data_txt")
	private String afterEventData;
	
	@Column(name="prcsd_i")
	private String processedIndicator;

	@Column(name="prcsd_dtm")
	private Timestamp processedTime;

}
