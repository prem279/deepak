package com.lmig.ci.lmbc.empr.muw.contact.resource;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "partyEmailAddress")
@ToString(exclude = "partyEmailAddress")
public class PartyEmailAddressActyResource implements Serializable{
	
	private static final long serialVersionUID = -6334823432015527405L;
	@Id
	private Integer partyEmailAddressActivityId;
	
	private String activityType;
	
	private String active;
	
	@Valid
	@JsonBackReference
	private PartyEmailAddressResource partyEmailAddress;
}
