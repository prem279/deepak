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
@EqualsAndHashCode(callSuper = false, exclude = "applicant")
@ToString(exclude = "applicant")
@Relation(collectionRelation = "applicantProducts", value = "applicantProduct")
public class ApplicantProductExtendedResource extends ResourceSupport {
	
	@JsonBackReference
	private ApplicantResource applicant;
	
	private Integer applicationApplicantProductId;
	
	@Pattern(regexp = "^[A-Za-z]{3}$")
	private String currentProductAmountTypeCode;
	
	private Double currentProductAmount;
	
	private Double currentProductPercent;
	
	private Double currentProductQuantity;
	
	@Pattern(regexp = "^[A-Za-z]{3}$")
	private String requestedProductAmountTypeCode;
	
	private Double requestedProductAmount;
	
	private Double requestedProductPercent;
	
	private Double requestedProductQuantity;
	
	private Date requestedProductBeginDate;
	
	@NotNull
	private Date statusDeterminationDate;
		
	@Pattern(regexp = "^[A-Za-z]{6}$")
	private String statusTypeCode;
	    
	private Date productApproveEffectiveDate; 
	
	private Integer concurrencyQuantity;
	
	private Integer applicationProductId;
	
	private String applicantProductCode;
}