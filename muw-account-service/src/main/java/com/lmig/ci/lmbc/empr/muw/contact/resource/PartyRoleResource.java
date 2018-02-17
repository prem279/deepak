/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 9 Sep 2016
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
@ToString(exclude = "party")
public class PartyRoleResource implements Serializable {
	private static final long serialVersionUID = -7282646462204472331L;
	
	@Id
	private Integer partyRoleId;
	
	private String roleType;
	
	
	@JsonBackReference
	private PartyResource party;
	
	public boolean validate() {
		if(partyRoleId == null || partyRoleId <= 0)
			return false;
		if(roleType == null)
			return false;
		return true;
	}
	
}