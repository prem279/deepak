package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.util.CustomDateDeserializer;
import com.lmig.ci.lmbc.empr.common.util.CustomDateSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "application")
@ToString(exclude = "application")
@Relation(collectionRelation = "applications", value = "application")
public class ApplicationProductResource extends ResourceSupport {
	
	private Integer applicationProductId;
	
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{3}$")
	private String productCode;
	
    @Pattern(regexp = "^[A-Za-z]{6}$")
	private String statusTypeCode;
	
	private Integer concurrencyQuantity;

	@JsonBackReference
	private ApplicationResource application;
}
