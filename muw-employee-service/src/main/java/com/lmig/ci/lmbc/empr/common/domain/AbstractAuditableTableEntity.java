/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jan 20, 2016
 */

package com.lmig.ci.lmbc.empr.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lmig.ci.lmbc.empr.common.util.CustomDateSerializer;

import lombok.Data;

/**
 * @Class AbstractAuditableTableEntity
 * @author n0296170
 *         <P>
 * @Description:
 *               <p>
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@CreatedBy
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "crt_usr_id_num", updatable = false)
	private String createUserIdNumber;

	@LastModifiedBy
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "last_updt_usr_id_num", insertable = false)
	private String lastUpdatedUserIdNumber;

	@Version
	@Column(name = "ccrcy_qty")
	private Integer concurrencyQuantity;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Etc/UTC")
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "crt_dtm", updatable = false)
	private Date createDatetime;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Etc/UTC")
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "last_updt_dtm", insertable = false)
	private Date lastUpdateDatetime;

}
