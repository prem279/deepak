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
public class PhysicalAddressResource extends ResourceSupport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9095725801598783558L;

	@Id
	private Integer addressId;
	
	@Pattern(regexp="^[A-Za-z]{0,6}$", message="Address reason code must be a maximum of 6 characters")
	private String addressReasCode;
	
	@Pattern(regexp="^[ -~]{0,70}$", message="Address line 1 must be a maximum of 70 characters")
	private String addressLine1Text;
	
	@Pattern(regexp="^[ -~]{0,70}$", message="Address line 2 must be a maximum of 70 characters")
	private String addressLine2Text;
	
	@Pattern(regexp="^[ -~]{0,70}$", message="City name must be a maximum of 70 characters")
	private String cityName;
	
	@Pattern(regexp="^[A-Za-z]{2}|{0}$", message="State/Province must be two characters")
	private String stateProvinceCode;
	
	@Pattern(regexp="^[0-9]{0,9}$", message="Postal code must be numeric and between 0-9 digits")
	private String postalCode;
	
	@Pattern(regexp="^[A-Za-z]{3}|{0}$", message="Country code must be 3 characters")
	private String countryCode;

	@JsonIgnore
	public boolean isNull() {
		return (   addressId == null
				&& addressReasCode == null
				&& addressLine1Text == null
				&& addressLine2Text == null
				&& cityName == null
				&& stateProvinceCode == null
				&& postalCode == null
				&& countryCode == null);
	}
}

