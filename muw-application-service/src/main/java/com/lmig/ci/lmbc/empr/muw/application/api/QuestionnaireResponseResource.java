package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lmig.ci.lmbc.empr.muw.application.domain.ApplicantCondition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author n0296170
 *
 */
@Data
@EqualsAndHashCode(callSuper = false, exclude = "applicantCondition")
@ToString(exclude = "applicantCondition")
@Relation(collectionRelation = "questionnaireResponses", value = "questionnaireResponse")
public class QuestionnaireResponseResource extends ResourceSupport{
	
	@Id
	private Integer applicantQuestionResponseId;
	
	@NotNull
	private String questionTypeCode;
	
	@NotNull
	private String questionCode;
	
	private String additionalQuestionCode;
	
	private Double responseAmount;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	private Date responseDate;
	
	private Boolean responseIndicator;
	
	private String responseNumber;
	
	private Double responseQuantity;
	
	private String repsonseText;
	
	
	@JsonBackReference
	private ApplicantConditionResource applicantCondition;
	

}
