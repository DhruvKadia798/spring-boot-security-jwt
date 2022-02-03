package com.web.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.mode.User;
import com.web.security.BaseController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/author")
public class AuthorController extends BaseController {

	@RequestMapping({"","/"})
	public String index() {
		Object obj = getLoggedInUser();
		User user = new User();
		if(obj instanceof User)
			user = (User) obj;
		System.out.println("Email: "+user.getEmail());
		return "author home";
	}
	
	
	
}
