package com.zsq.SpringBootDemo.modules.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class LoginController {

	@RequestMapping("/login")
	public String loginPage() {
		return "indexSimple";
	}
	
	@RequestMapping("/register")
	public String registerPage() {
		return "indexSimple";
	}
}

