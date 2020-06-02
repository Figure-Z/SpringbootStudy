package com.zsq.SpringBootDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController{

	/**
	 * 访问以下地址就能访问到该方法
	 * 访问地址127.0.0.1:8080/test/desc
	 * @return
	 */
	@RequestMapping("/test/desc")
	@ResponseBody   //代表该方法是一个接口 
	public String testDesc(){
		return "This is test modle desc.";
	}
}
