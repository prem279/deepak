package com.lmig.ci.lmbc.empr.muw.contact.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "party")
@ToString(exclude = "party")
public class PartyPhysicalAddressResource   implements Serializable {
	private static final long serialVersionUID = -3425674207782199970L;

	@Id
	private Integer partyAddressId;
	
	private String addressReasonCode;
	
	private String addressLine1Text;
	
	private String addressLine2Text;
	
	private String addressLine3Text;
	
	private String cityName;
	
	private String stateProvienceCode;
	
	private String postalCode;
	
	private String countryCode;
	
	@Valid
	@JsonManagedReference
	private List<PartyPhysicalAddressActyResource> partyPhysicalAddressActy = new ArrayList<>();
	
	@Valid
	@JsonBackReference
	private PartyResource party;
}

