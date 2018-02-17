/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 14, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employer.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.util.CustomDateOnlyDeserializer;
import com.lmig.ci.lmbc.empr.common.util.CustomDateSerializer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author n0159479
 *
 */
@Data
@EqualsAndHashCode(exclude = "employer")
@ToString(exclude = { "employer" })
public class EoiProductResource extends AbstractAuditableTableResource {

	@ApiModelProperty(value = "Employer Product's Unique ID", example = "15", required = true)
	private Integer productId;

	@ApiModelProperty(value = "Code Abbreviation for Product Requiring EOI", example = "BCL", required = true)
	@NotNull
	@Pattern(regexp = "^[A-Z]{3}$")
	private String productCode;

	@ApiModelProperty(value = "List of labels for customer product names")
	private List<LabelResource> labels = new ArrayList<LabelResource>();

	@ApiModelProperty(value = "Date of Product Cancellation from Employer Policy", example = "2011-12-24", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = CustomDateOnlyDeserializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date cancellationDate;

	@ApiModelProperty(value = "Begin Date of Product On Employer Policy", example = "2011-12-24", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = CustomDateOnlyDeserializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date beginDate;

	@JsonBackReference
	private EmployerResource employer;
}
