/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Jan 28, 2016
 */
package com.lmig.ci.lmbc.empr.muw.zuul;

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

	
	private EnvironmentConstants() 
	{
		
	}
}
