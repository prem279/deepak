

package com.lmig.ci.lmbc.empr.muw.contact.resource;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.ToString;

/**
 * @author N0296170
 *
 */
@Data
@ToString(exclude = "party")
public class PartyPhoneNumberResource implements Serializable {

	private static final long serialVersionUID = 4928016513423571520L;
	
	@Id
	private Integer partyPhoneNumberId;
	
	private String voiceIndicator;
	
	private String textIndicator;
	
	private String countryCode;
	
	private String phoneNumber;
	
	private String extension;
	
	private String phoneNumberType;
	
	private String timeZone;
	


	@JsonBackReference
	private PartyResource party;
	
	@JsonManagedReference
	private List<PartyPhoneNumActyResource> partyPhoneNumAct;
	
	public boolean validate() {
		if(party.getPartyId() == null || party.getPartyId() <=0)
			return false;
		if(partyPhoneNumberId == null || partyPhoneNumberId <= 0)
			return false;
		if(timeZone == null)
			return false;
		return true;
	}
	
}