
package com.lmig.ci.lmbc.empr.muw.employee.api;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author N0296170
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PhoneNumberResource extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 4928016513423571520L;

	@Id
	private Integer partyPhoneNumberId;

	@Pattern(regexp = "^[0-9]{0,3}$", message = "Country code must be 3 characters")
	private String countryCode;

	@Pattern(regexp = "^[0-9]{0,35}$", message = "Enter valid phone number")
	private String phoneNumber;

	@Pattern(regexp = "^[0-9]{0,35}$", message = "Enter valid extension")
	private String extension;

	@Pattern(regexp = "^[A-Z]{0,6}$", message = "Enter valid phone number Type")
	private String phoneNumberType;

	@Pattern(regexp = "^[A-Za-z]{0,80}$", message = "Enter valid phone number")
	private String timeZone;

	@JsonIgnore
	public boolean isNull() {
		return ((null == partyPhoneNumberId) && (null == countryCode || countryCode.equals(""))
				&& (null == phoneNumber || phoneNumber.equals("")) && (null == extension || extension.equals(""))
				&& (null == phoneNumberType || phoneNumberType.equals(""))
				&& (null == timeZone || timeZone.equals("")));
	}
}