package com.lmig.ci.lmbc.empr.muw.contact.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.validation.Valid;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = false, exclude = "party")
@ToString(exclude = "party")
public class PartyEmailAddressResource extends ResourceSupport implements Serializable  {
	
	private static final long serialVersionUID = 3757607709907649286L;

	@Id
	private Integer partyEmailAddressId;
	
	private String electronicAddressReasonCode;
	
	private String electronicAddressText;
	
	@Valid
	@JsonManagedReference
	private List<PartyEmailAddressActyResource> partyEmailAddressActy = new ArrayList<PartyEmailAddressActyResource>();
	
	@Valid
	@JsonBackReference
	private PartyResource party;

}
