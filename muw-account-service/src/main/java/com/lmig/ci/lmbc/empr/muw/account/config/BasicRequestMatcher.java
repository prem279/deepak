package com.lmig.ci.lmbc.empr.muw.account.config;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

public class BasicRequestMatcher implements RequestMatcher {
	@Override
	public boolean matches(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		return (auth != null && auth.startsWith("Basic"));
	}
}