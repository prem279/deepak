/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Apr 8, 2016
 */

package com.lmig.ci.lmbc.empr.common.discovery;

/**
 * @Class AccountLeaveEnvironment
 * @author n0169128
 * <P>
 * @Description:
 * <p>
 */
public final class ServiceDiscoveryEnvironment {
	
	public static final String LOCAL = "local";
	public static final String JUNIT = "junit";
	public static final String DEVELOPMENT_1 = "dev1";
	public static final String DEVELOPMENT_2 = "dev2";
	public static final String TEST_1 = "test1";
	public static final String TEST_2 = "test2";
	public static final String PERFORMANCE = "performance";
	public static final String SANDBOX = "sandbox";

	public static final String NOT_LOCAL = "!" + LOCAL;
	public static final String NOT_JUNIT = "!" + JUNIT;
	public static final String NOT_DEVELOPMENT_1 = "!" + DEVELOPMENT_1;
	public static final String NOT_DEVELOPMENT_2 = "!" + DEVELOPMENT_2;
	
	private ServiceDiscoveryEnvironment() {}

}
