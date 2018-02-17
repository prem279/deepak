package com.lmig.ci.lmbc.empr.muw.application.api;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "application")
@ToString(exclude = "application")
@Relation(collectionRelation = "applicationProducts", value = "applicationProduct")
public class ApplicationSearchApplicationProductResource extends ResourceSupport {
	
	private Integer applicationProductId;
	
	@NotNull
	private String productCode;
	
	@NotNull
	private String statusTypeCode;
	
	@JsonBackReference
	private ApplicationSearchApplicationResource application;

	private Integer concurrencyQuantity;
}
