/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Sep 19, 2016
 */

package com.lmig.ci.lmbc.empr.muw.zuul.filter.pre;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.mina.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmig.ci.lmbc.empr.muw.zuul.config.MuwZuulSecurityProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class BasicAuthorizationHeaderFilter extends ZuulFilter {
	
	@Autowired
	MuwZuulSecurityProperties muwZuulSecurityProperties;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 10;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
	    HttpServletRequest request = ctx.getRequest();

	    String requestURL = request.getRequestURL().toString();
	    
	    System.out.println("com.lmig.jennings zuul.requestURL: " + requestURL);
		
		if(requestURL.contains("xxxxxx"))
		{
			String id = muwZuulSecurityProperties.getServiceId();
	    	String pw = muwZuulSecurityProperties.getServicePassword();
			String creds = id + ":" + pw;
			byte[] credsBytes = creds.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(credsBytes);
			String base64Creds = new String(base64CredsBytes);
			
			ctx.addZuulRequestHeader("Authorization", "Basic " + base64Creds);
			System.out.println("com.lmig.jennings added Basic Authorization credentials");
		}		
		
		return null;
	}

}
