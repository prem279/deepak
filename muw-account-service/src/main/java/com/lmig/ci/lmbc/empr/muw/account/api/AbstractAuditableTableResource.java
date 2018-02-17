/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jan 20, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.api;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public abstract class AbstractAuditableTableResource extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 9033088152254874282L;

	private String createUserIdNum;

	private String lastUpdateUserIdNum;

	public Integer concurrencyQuantity;

	@JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Etc/UTC")
	private Date createDatetime;

	@JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Etc/UTC")
	private Date lastUpdateDatetime;
}
