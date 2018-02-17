/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 11, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.util.CustomDateDeserializer;
import com.lmig.ci.lmbc.empr.common.util.CustomDateSerializer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author n0159479
 *
 */
@Data
// Don't call super for equals and hash code
@EqualsAndHashCode(callSuper = false)
// Define the collection inside _embedded
@Relation(collectionRelation = "employers", value = "employer")
public class EmployerResource extends AbstractAuditableTableResource {
	private static final long serialVersionUID = 5411894560103147879L;

	@ApiModelProperty(value = "Employer's Unique ID", example = "10", required = true)
	private Integer employerId;

	@ApiModelProperty(value = "Employer's Division", example = "03", required = true)
	@NotNull
	@Pattern(regexp = "^\\d{2}$")
	private String employerStakeholderLedgerNumber;

	@ApiModelProperty(value = "Employer's Serial", example = "A012B11", required = true)
	@NotNull
	@Pattern(regexp = "^[A-z0-9]{6}$")
	private String employerStakeholderSerialNumber;

	@ApiModelProperty(value = "Employer's Name", example = "Joe's Bar and Grill", required = true)
	@NotNull
	@Pattern(regexp = "(^$)|(^([A-Za-z0-9@\'.,#&!-][ ]?)*([A-Za-z0-9\'&@.,!-]{1,132})+$)")
	private String employerName;

	@ApiModelProperty(value = "My Liberty Connection Access Code", example = "JOESBARNGRILL", required = false)
	@Pattern(regexp = "(^$)|(^([A-Za-z0-9@\'.,#&!-][ ]?)*([A-Za-z0-9\'&@.,!-]{1,20})+$)")
	private String companyAccessCode;

	@ApiModelProperty(value = "Employer's Policy State/Province Code", example = "OK", required = true)
	@Pattern(regexp = "^[A-Za-z]{2}$")
	@JsonProperty(access = Access.READ_ONLY)
	private String situsStateCode;

	@ApiModelProperty(value = "Date of Employer Required EOI Beginning", example = "2011-06-17", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date beginDate;

	@ApiModelProperty(value = "Date of Employer Cancellation of Policies Requiring EOI", example = "2008-09-20", required = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date cancellationDate;

	@ApiModelProperty(value = "Employer EOI Begin Date Anniversary", example = "2017-06-20", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date annvDate;

	@ApiModelProperty(value = "Employer's Alternate Name", example = "Joe's", required = false)
	@Pattern(regexp = "(^$)|(^([A-Za-z0-9@\'.,#&!-][ ]?)*([A-Za-z0-9\'&@.,!-]{1,132})+$)")
	private String alternateName;

	@Valid
	private PhysicalAddressResource addresses;

	@Valid
	@JsonManagedReference
	private List<EoiProductResource> products = new ArrayList<EoiProductResource>();

	@Valid
	@JsonManagedReference
	private PreferenceResource preference;

	public EmployerResource() {

	}

	public String getSitusStateCode() {
		if (addresses != null && addresses.getStateProvinceCode() != null) {
			return addresses.getStateProvinceCode();
		} else {
			return situsStateCode;
		}
	}

}
