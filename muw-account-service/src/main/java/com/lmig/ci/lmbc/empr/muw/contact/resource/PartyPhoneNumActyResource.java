/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 16 Sep 2016
 */

package com.lmig.ci.lmbc.empr.muw.contact.resource;

import java.io.Serializable;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString;

/**
 * @author N0296170
 *
 */
@Data
@ToString(exclude = "partyPhoneNumber")
public class PartyPhoneNumActyResource implements Serializable {

	private static final long serialVersionUID = 1924764682644097996L;
	
	@Id
	private Integer partyPhoneNumberActivityId;
	
	private String communicationMethod;
	
	private String activityType;
	
	private String active;
	
	@JsonBackReference
	private PartyPhoneNumberResource partyPhoneNumber;
	
	public boolean validate() {
		if(partyPhoneNumberActivityId == null || partyPhoneNumberActivityId<=0)
			return false;
		if(partyPhoneNumber.getParty().getPartyId() == null || partyPhoneNumber.getParty().getPartyId() <=0)
			return false;
		if(partyPhoneNumber.getPartyPhoneNumberId() == null || partyPhoneNumber.getPartyPhoneNumberId()<=0)
			return false;
		if(activityType == null)
			return false;
		
		return true;
	}
	
}