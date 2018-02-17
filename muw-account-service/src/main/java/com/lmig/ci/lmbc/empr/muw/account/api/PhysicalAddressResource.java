package com.lmig.ci.lmbc.empr.muw.account.api;

import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PhysicalAddressResource extends ResourceSupport {
	
	private Integer employerId;
	
	@Pattern(regexp="^[A-Za-z]{0,6}$", message="Address reason code must be a maximum of 6 characters")
	private String addressReasCode;
	
	@Pattern(regexp="^[ -~]{0,70}$", message="Address line 1 must be a maximum of 70 characters")
    private String addressLine1Text;
	
	@Pattern(regexp="^[ -~]{0,70}$", message="Address line 2 must be a maximum of 70 characters")
	private String addressLine2Text;
	
	@Pattern(regexp="^[ -~]{0,70}$", message="City name must be a maximum of 70 characters")
	private String cityName;
	
	@Pattern(regexp="^[A-Za-z]{2}$", message="State/Province must be two characters")
    private String stateProvinceCode;
	
	@Pattern(regexp="^[0-9]{0,9}$", message="Postal code must be numeric and between 0-9 digits")
	private String postalCode;
	
	@Pattern(regexp="^[A-Za-z]{3}$", message="Country code must be 3 characters")
	private String countryCode;

}

