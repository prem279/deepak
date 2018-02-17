/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jan 28, 2016
 */
package com.lmig.ci.lmbc.empr.muw.account;

public final class EnvironmentConstants
{
	public static final String LOCAL = "local";
	public static final String JUNIT = "junit";
	public static final String DEVELOPMENT_1 = "dev1";
	public static final String TEST = "test1";
	public static final String STAGING = "stage1";
	public static final String PRODUCTION = "prod1";
	public static final String CLOUD = "cloud1";
	


	public static final String NOT_LOCAL = "!" + LOCAL;
	public static final String NOT_JUNIT = "!" + JUNIT;
	public static final String NOT_DEVELOPMENT_1 = "!" + DEVELOPMENT_1;

	
	public static final String BASE_PATH = "/services/v1";
	
	public static final String DIV_SERIAL_ERROR = "DIV_SERIAL_UNIQUE_INDEX";	
	public static final String NO_PRODUCTS_ERROR = "NO_PRODUCTS_ERROR";
	
	private EnvironmentConstants() 
	{
		
	}
}
