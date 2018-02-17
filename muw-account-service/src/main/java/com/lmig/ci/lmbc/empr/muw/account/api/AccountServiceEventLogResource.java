package com.lmig.ci.lmbc.empr.muw.account.api;

import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountServiceEventLogResource extends ResourceSupport {

	private Integer eventId;

	private int subjectAreaId;

	private String subjectAreaCode;

	private String changeEventCode;

	private String businessEventCode;

	private String beforeEventData;

	private String changeSet;

	private String afterEventData;

	private String processedIndicator;

	private Timestamp processedTime;

}