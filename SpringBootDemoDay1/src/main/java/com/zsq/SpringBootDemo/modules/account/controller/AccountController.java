package com.zsq.SpringBootDemo.modules.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

	@RequestMapping("/users")
	public String UsersPage() {
		return "index";
	}
	
	@RequestMapping("/roles")
	public String RolePage() {
		return "index";
	}
	
	@RequestMapping("/resources")
	public String ResourcePage() {
		return "index";
	}
}
