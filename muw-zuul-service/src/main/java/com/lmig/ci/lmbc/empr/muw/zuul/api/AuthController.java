package com.lmig.ci.lmbc.empr.muw.zuul.api;
/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Mar 23, 2016
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    /**
     * Get the current user.
     */
	@RequestMapping(value="/userdetails", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Object getUserDetails(OAuth2Authentication auth) {
		if(auth != null && auth.getUserAuthentication() != null){
			return auth.getUserAuthentication();
		}
		else{
			return null;
		}
	}
    
    /**
     * GET  /authenticate -> check if the user is authenticated, and return its login.
     */
    @RequestMapping(value = "/isauthenticated", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isAuthenticated(OAuth2Authentication auth) {
        return auth != null && auth.isAuthenticated();
    }


}
