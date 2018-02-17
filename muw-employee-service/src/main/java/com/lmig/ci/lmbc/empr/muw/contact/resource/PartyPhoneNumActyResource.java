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
	

	@Override 
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partyPhoneNumber.getParty().getPartyId()  == null) ? 0 : partyPhoneNumber.getParty().getPartyId().hashCode());
		result = prime * result + ((partyPhoneNumber.getPartyPhoneNumberId() == null) ? 0 : partyPhoneNumber.getPartyPhoneNumberId().hashCode());
		result = prime * result + ((communicationMethod == null) ? 0 : communicationMethod.hashCode());
		result = prime * result + ((activityType == null) ? 0 : activityType.hashCode());
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartyPhoneNumActyResource other = (PartyPhoneNumActyResource) obj;
		
		if(partyPhoneNumberActivityId == null && other.partyPhoneNumberActivityId == null
				&& communicationMethod == null && other.communicationMethod == null
				&& activityType == null && other.activityType == null
				&& active == null && other.active == null)
			return true;
		if(!partyPhoneNumberActivityId.equals(other.partyPhoneNumberActivityId))
			return false;
		if(!partyPhoneNumber.getParty().getPartyId().equals(other.partyPhoneNumber.getParty().getPartyId()))
			return false;
		if(!partyPhoneNumber.getPartyPhoneNumberId().equals(other.partyPhoneNumber.getPartyPhoneNumberId()))
			return false;
		if(!activityType.equals(other.activityType))
			return false;
		if(!communicationMethod.equals(other.communicationMethod))
			return false;
		if(!active.equals(other.active))
			return false;
		
		return true;
	}
	
}