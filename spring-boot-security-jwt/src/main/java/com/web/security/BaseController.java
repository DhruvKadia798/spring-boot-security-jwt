package com.web.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.web.mode.User;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public class BaseController {
	
	Locale currentLocale = Locale.getDefault();
	
	public String getIpAddress(HttpServletRequest request) {
		return request.getRemoteAddr();
	}
	
	public String getMetaData(HttpServletRequest request) {
		System.out.println(currentLocale.getDisplayLanguage());
		System.out.println(currentLocale.getDisplayCountry());
		 
		System.out.println(currentLocale.getLanguage());
		System.out.println(currentLocale.getCountry());
		 
		System.out.println(System.getProperty("user.country"));
		System.out.println(System.getProperty("user.language"));
		return request.getRemoteAddr();
	}
	
	public Object getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication.getName().equals("anonymousUser"))
			return null;
		if(authentication.getPrincipal() instanceof AuthenticatedUser) {
			AuthenticatedUser customUserDetails = (AuthenticatedUser) authentication.getPrincipal();
			return customUserDetails.getUser();
		}
		else {
			return authentication.getPrincipal();
		}
		
	}

	public void authanticateUser(User users,HttpServletRequest request) {
		
		AuthenticatedUser auth = new AuthenticatedUser(users);
		request.getSession();
		Authentication authentication = new UsernamePasswordAuthenticationToken(auth, null, auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
}
