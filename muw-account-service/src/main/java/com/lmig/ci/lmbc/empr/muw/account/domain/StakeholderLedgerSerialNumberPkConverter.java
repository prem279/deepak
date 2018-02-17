/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Oct 12, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.domain;

import org.springframework.core.convert.converter.Converter;

/**
 * @author n0159479
 *
 */
public class StakeholderLedgerSerialNumberPkConverter implements Converter<String, StakeholderLedgerSerialNumberPk> {

	public StakeholderLedgerSerialNumberPk convert(String pk)
	{
		if(!pk.isEmpty() && pk.contains("-"))
		{
			String[] pieces = pk.split("-");
			String ledgerNumber = pieces[0];
			String serialNumber = pieces[1];
			
			return new StakeholderLedgerSerialNumberPk(ledgerNumber, serialNumber);
		}
		else
		{
			return new StakeholderLedgerSerialNumberPk();
		}		
	}
	
}
