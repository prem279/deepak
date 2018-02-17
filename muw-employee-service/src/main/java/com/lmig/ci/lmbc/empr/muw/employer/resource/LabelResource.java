/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Nov 14, 2016
 */

package com.lmig.ci.lmbc.empr.muw.employer.resource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author n0159479
 *
 */
@Data
public class LabelResource extends AbstractAuditableTableResource {

	private static final long serialVersionUID = -2973902588269380559L;

	@ApiModelProperty(value = "Label Unique ID", example = "15", required = true)
	private Integer labelId;

	@ApiModelProperty(value = "Language code of the label text", example = "ENG", required = true)
	private String languageCode;

	@ApiModelProperty(value = "The label that is displayed", example = "Short Term Disability", required = true)
	private String labelText;
}
