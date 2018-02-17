package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductResource {
	private String productCode;
	private List<String> applicantTypeCodes;
}
