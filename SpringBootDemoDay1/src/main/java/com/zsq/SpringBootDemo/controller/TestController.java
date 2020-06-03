package com.zsq.SpringBootDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zsq.SpringBootDemo.vo.ApplicationTest;

@Controller
public class TestController{

	@Value("${server.port}")   //读取配置文件,全局配置文件全部是以application开头的
	private int port;
	@Value("${com.test.name}")
	private String name;
	@Value("${com.test.age}")
	private int age;
	@Value("${com.test.desc}")
	private String desc;
	@Value("${com.test.random}")
	private String random;
	
	@Autowired
	private ApplicationTest applicationtest;
	
	@RequestMapping("/test/configinfo")
	@ResponseBody
	public String configInfo() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(port).append("----");
		sBuffer.append(name).append("----");
		sBuffer.append(age).append("----");
		sBuffer.append(desc).append("----");
		sBuffer.append(random).append("<br>");
		
		sBuffer.append(applicationtest.getName1()).append("----")
			   .append(applicationtest.getAge1()).append("----")
			   .append(applicationtest.getDesc1()).append("----")
			   .append(applicationtest.getRandom1()).append("<br>");
		
		
		return sBuffer.toString();
	}
	
	
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
