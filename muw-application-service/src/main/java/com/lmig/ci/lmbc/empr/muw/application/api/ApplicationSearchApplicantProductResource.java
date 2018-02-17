package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "applicant")
@ToString(exclude = "applicant")
@Relation(collectionRelation = "applicantProducts", value = "applicantProduct")
public class ApplicationSearchApplicantProductResource extends ResourceSupport {
	
	private Integer applicationApplicantProductId;
	
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{3}$")
	private String productCode;

    @NotNull
	@Pattern(regexp = "^[A-Za-z]{6}$")
    private String statusTypeCode;
    
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
	
	private Date productApproveEffectiveDate;
	
	private Date statusDeterminationDate;

	@JsonBackReference
	private ApplicationSearchApplicantResource applicant;

	private Integer concurrencyQuantity;
}
