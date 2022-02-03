package com.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

public class UserController {

	@RequestMapping({"logout", "/logout"})
	public String logout() {
		return "logout success";
	}
	
}
