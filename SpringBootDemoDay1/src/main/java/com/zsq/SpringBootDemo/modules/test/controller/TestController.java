package com.zsq.SpringBootDemo.modules.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zsq.SpringBootDemo.modules.test.vo.ApplicationTest;



@Controller
@RequestMapping("/test")
public class TestController{

	//引入日志
	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	//测试日志输出接口
	@RequestMapping("/log")
	@ResponseBody
	public String logTest() {
		//日志级别：TRACE<DEBUG<INFO<WARN<ERROR ，设置为debug只会输出该级别和比它级别高的日志
		LOGGER.trace("This is trace log");
		LOGGER.debug("This is debug log");
		LOGGER.info("This is info log");
		LOGGER.warn("This is warn log");
		LOGGER.error("This is error log");
		return "This is log test";
	}
	
	
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
	
	@RequestMapping("/configinfo")
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
	 * 访问地址127.0.0.1:8080/desc
	 * @return
	 */
	@RequestMapping("/desc")
	@ResponseBody   //代表该方法是一个接口 
	public String testDesc(){
		return "This is test modle desc.";
	}
}
