package com.lmig.ci.lmbc.empr.muw.application.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name="APPLN_APPLNT_QUESN_RSPN_T")
@EqualsAndHashCode(callSuper = false, exclude = "applicantCondition")
@ToString(exclude = "applicantCondition")
public class QuestionnaireResponse extends AbstractAuditableTableEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -8083208939533292656L;
	
	@Id
	@Column(name="applnt_quesn_rspn_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="applnt_quesn_rspn_seq")
	@SequenceGenerator(name="applnt_quesn_rspn_seq", sequenceName="applnt_quesn_rspn_seq", allocationSize = 1)
	private Integer applicantQuestionResponseId;
	
	@NotNull
	@Column(name="quesn_typ_cde")
	private String questionTypeCode;
	
	@NotNull
	@Column(name="ques_cde")
	private String questionCode;
	
	@Column(name="addl_ques_cde")
	private String additionalQuestionCode;
	
	@Column(name="rspn_amt")
	private Double responseAmount;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	@Column(name="rspn_dte")
	private Date responseDate;
		
	@Column(name="rspn_i")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private Boolean responseIndicator;
	
	@Column(name="rspn_num")
	private String responseNumber;
	
	@Column(name="rspn_qty")
	private Double responseQuantity;
	
	@Pattern(regexp = "^[._(),/@\\?=#'a-zA-Z0-9- ]+$")
	@Column(name="RSPN_TXT")
	private String responseText;
		

	@ManyToOne
	@JoinColumn(name="applnt_cond_id")
	@JsonBackReference
	private ApplicantCondition applicantCondition;
	

}
