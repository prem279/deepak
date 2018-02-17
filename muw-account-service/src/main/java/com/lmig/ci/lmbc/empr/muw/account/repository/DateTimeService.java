/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on May 11, 2016
 */

package com.lmig.ci.lmbc.empr.muw.account.repository;

import java.text.ParseException;
import java.util.Date;

/**
 * @Class DateTimeService
 * @author n0296170
 * <P>
 * @Description:
 * <p>
 */
public interface DateTimeService {
	Date getCurrentDate() throws ParseException;

}
