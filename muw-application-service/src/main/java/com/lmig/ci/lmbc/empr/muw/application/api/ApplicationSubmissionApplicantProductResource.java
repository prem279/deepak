package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "applicant")
@ToString(exclude = "applicant")
@Relation(collectionRelation = "applicantProducts", value = "applicantProduct")
public class ApplicationSubmissionApplicantProductResource extends ResourceSupport {

	private Integer applicationApplicantProductId;

	private Integer applicationProductId;

	@NotNull
	@Pattern(regexp = "^[A-Za-z]{3}$")
	private String productCode;

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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	private Date coverageEligibilityDate;

	private List<String> miscProductData;

	@JsonBackReference
	private ApplicationSubmissionApplicantResource applicant;

	private Integer concurrencyQuantity;
}
