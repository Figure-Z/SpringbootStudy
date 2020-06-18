package com.zsq.SpringBootDemo.modules.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zsq.SpringBootDemo.modules.account.service.UserService;

@Controller
@RequestMapping("/account")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String loginPage() {
		return "indexSimple";
	}
	
	@RequestMapping("/register")
	public String registerPage() {
		return "indexSimple";
	}
	
	@RequestMapping("/logout")
	public String logOut(ModelMap modelMap) {
		userService.logout();
		modelMap.addAttribute("template", "account/login");
		return "indexSimple";
	}
}

