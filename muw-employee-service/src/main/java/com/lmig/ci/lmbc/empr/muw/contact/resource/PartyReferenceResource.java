/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 9 Sep 2016
 */

package com.lmig.ci.lmbc.empr.muw.contact.resource;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author N0287705
 *
 */

@Data
@EqualsAndHashCode(callSuper = false, exclude = "party")
@ToString(exclude = "party")
public class PartyReferenceResource extends ResourceSupport  implements Serializable {

	private static final long serialVersionUID = -6815873847246667019L;
	
	private Integer partyReferenceId;
	
	@Size(max=6, message="error.size.referenceType")
	@NotNull(message = "error.null.referenceType")
	private String referenceType;
	
	@Size(max=1024, message="error.size.referenceNumber")
	@NotNull(message = "error.null.referenceNumber")
	private String referenceNumber;
	
	@Valid
	@JsonBackReference
	private PartyResource party;
	
	public boolean validate() {
		if(referenceType==null || referenceType.isEmpty())
			return false;
		if(referenceNumber == null || referenceNumber.isEmpty())
			return false;
		return true;
	}
}
