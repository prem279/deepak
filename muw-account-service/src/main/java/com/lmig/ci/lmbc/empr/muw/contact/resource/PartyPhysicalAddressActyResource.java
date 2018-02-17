package com.lmig.ci.lmbc.empr.muw.contact.resource;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "partyPhysicalAddress")
@ToString(exclude = "partyPhysicalAddress")
public class PartyPhysicalAddressActyResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1659677995462114112L;


	@Id
	private Integer partyPhysicalAddressActivityId;
	
	
	private String activityType;
	
	private String active;
	
	@Valid
	@JsonBackReference
	private PartyPhysicalAddressResource partyPhysicalAddress;
}

