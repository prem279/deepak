/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 9 Sep 2016
 */

package com.lmig.ci.lmbc.empr.muw.contact.resource;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;

/**
 * @author N0296170
 *
 */

@Data
public class ContactDetailsResource extends ResourceSupport  implements Serializable {
	private static final long serialVersionUID = -4872512045229035478L;
	
	@Valid 
	private PartyResource party;
	
	public boolean validate() {
		if(party==null) 
			return false;
		return true;
	}
}
