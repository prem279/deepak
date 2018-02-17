/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 8 Sep 2016
 */

package com.lmig.ci.lmbc.empr.muw.contact.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @author N0287705
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PartyResource  extends ResourceSupport implements Serializable {
	private static final long serialVersionUID = -3425674207782199970L;

	private Integer partyId;
	
	@Size(max=6, message="error.size.partyType")
	@NotNull(message = "error.null.partyType")
	private String partyType;
	
	@Size(max=70, message="error.size.organisationName")
	private String organisationName;
	
	@Size(max=6, message="error.size.organisationType")
	private String organisationType;
	
	@Size(max=6, message="error.size.namePrefix")
	private String namePrefix;
	
	@Size(max=35, message="error.size.firstName")
	private String firstName;
	
	@Size(max=35, message="error.size.middleName")
	private String middleName;
	
	@Size(max=35, message="error.size.lastName")
	private String lastName;
	
	@Size(max=6, message="error.size.nameSuffix")
	private String nameSuffix;
	
	@Valid
	@JsonManagedReference
	private List<PartyRoleResource> partyRoles = new ArrayList<>();
	
	@Valid
	@JsonManagedReference
	private List<PartyPhoneNumberResource> partyPhoneNumbers = new ArrayList<>();
	
	@Valid
	@JsonManagedReference
	private List<PartyReferenceResource> partyReferences = new ArrayList<>();
	
	@Valid
	@JsonManagedReference
	private List<PartyPhysicalAddressResource> partyPhysicalAddresses = new ArrayList<>();
	
	@Valid
	@JsonManagedReference
	private List<PartyEmailAddressResource> partyEmailAddresses = new ArrayList<>();
	
}