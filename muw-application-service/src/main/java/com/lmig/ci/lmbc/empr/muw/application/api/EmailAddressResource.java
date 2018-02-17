package com.lmig.ci.lmbc.empr.muw.application.api;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmailAddressResource extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 3757607709907649286L;

	@Id
	private Integer partyEmailAddressId;

	@Pattern(regexp="^[A-Z]{4}$|{0}", message="enter valid ReasonCode")
	private String electronicAddressReasonCode;

	@Pattern(regexp="^[!-~]+@[A-z0-9]+\\.[A-Za-z]+$|{0}", message="enter valid email")
	private String electronicAddressText;
	
	@JsonIgnore
	public boolean isNull() {
		return (
			(partyEmailAddressId == null) &&
			(electronicAddressReasonCode == null || electronicAddressReasonCode.equals("")) &&
			(electronicAddressText == null || electronicAddressText.equals("")) 
		);
				
	}
}

