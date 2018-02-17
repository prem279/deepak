/*
Label2 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 17, 2016
 */

/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 17, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.lmig.ci.lmbc.empr.common.domain.AbstractAuditableTableEntity;
import com.lmig.ci.lmbc.empr.muw.account.annotation.MaxBytes;

import lombok.Data;

/**
 * @author n0282721
 *
 */

@Entity
@Data
@Table(name = "lbl_txt_t")
public class Label extends AbstractAuditableTableEntity implements Serializable {

	private static final long serialVersionUID = 9208860975207694336L;

	@Id
	@Column(name = "lbl_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lbl_txt_seq")
	@SequenceGenerator(name = "lbl_txt_seq", sequenceName = "LBL_TXT_SEQ", allocationSize = 1)
	private Integer labelId;

	@NotNull
	@Column(name = "lang_cde")
	@Length(max = 3)
	private String languageCode;

	@NotNull
	@MaxBytes(max = 500)
	@Column(name = "lbl_txt")
	private String labelText;
}
