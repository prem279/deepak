/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 12, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author n0159479
 *
 */
@Embeddable
public class StakeholderLedgerSerialNumberPk implements Serializable {

	private static final long serialVersionUID = 4431291627044672385L;

	public StakeholderLedgerSerialNumberPk() {

	}

	public StakeholderLedgerSerialNumberPk(String ledgerNumber, String serialNumber) {
		employerStakeholderLedgerNumber = ledgerNumber;
		employerStakeholderSerialNumber = serialNumber;
	}

	@NotNull
	@Pattern(regexp = "^\\d{2}$")
	@Column(name="emplr_stkr_ldgr_num")
	private String employerStakeholderLedgerNumber;
	
	@NotNull
	@Pattern(regexp = "^[A-z0-9]{6}$")
	@Column(name="emplr_stkr_srl_num")
	private String employerStakeholderSerialNumber;

	@Override
	public int hashCode() {
		return employerStakeholderLedgerNumber.hashCode() + employerStakeholderSerialNumber.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof StakeholderLedgerSerialNumberPk) {
			StakeholderLedgerSerialNumberPk slsnPk = (StakeholderLedgerSerialNumberPk) obj;
			if (null == slsnPk.employerStakeholderLedgerNumber && null == this.employerStakeholderLedgerNumber
					&& null == slsnPk.employerStakeholderSerialNumber && null == this.employerStakeholderSerialNumber) {
				return true;
			}
			return (slsnPk.employerStakeholderLedgerNumber.equals(this.employerStakeholderLedgerNumber))
					&& (slsnPk.employerStakeholderSerialNumber.equals(this.employerStakeholderSerialNumber));
		}

		return false;
	}

	/**
	 * @return the employerStakeholderLedgerNumber
	 */
	public String getEmployerStakeholderLedgerNumber() {
		return employerStakeholderLedgerNumber;
	}

	/**
	 * @param employerStakeholderLedgerNumber the employerStakeholderLedgerNumber to set
	 */
	public void setEmployerStakeholderLedgerNumber(String employerStakeholderLedgerNumber) {
		this.employerStakeholderLedgerNumber = employerStakeholderLedgerNumber;
	}

	/**
	 * @return the employerStakeholderSerialNumber
	 */
	public String getEmployerStakeholderSerialNumber() {
		return employerStakeholderSerialNumber;
	}

	/**
	 * @param employerStakeholderSerialNumber the employerStakeholderSerialNumber to set
	 */
	public void setEmployerStakeholderSerialNumber(String employerStakeholderSerialNumber) {
		this.employerStakeholderSerialNumber = employerStakeholderSerialNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return employerStakeholderLedgerNumber + "-" + employerStakeholderSerialNumber;
	}
	
	
}
