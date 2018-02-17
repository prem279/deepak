/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Sep 16, 2016
 */

package com.lmig.ci.lmbc.empr.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

	public static Date getCurrentUtcDatetime() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
	    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); 
	    String dateString = dateFormat.format(new Date());
	    
	    SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date currentDate = dateParser.parse(dateString);
	    return currentDate;
	}
	
	public static Date getCurrentAmericaNYDatetime() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
	    dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York")); 
	    String dateString = dateFormat.format(new Date());
	    
	    SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date currentDate = dateParser.parse(dateString);
	    return currentDate;
	}
	
	public static Date getDefaultDate() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String date = "1900-01-01 00:00:00.000";
		
		return dateFormat.parse(date);
	}
}
