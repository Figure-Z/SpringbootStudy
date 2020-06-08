package com.zsq.SpringBootDemo.modules.test.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zsq.SpringBootDemo.modules.test.entity.City;
import com.zsq.SpringBootDemo.modules.test.entity.Country;
import com.zsq.SpringBootDemo.modules.test.service.CityService;
import com.zsq.SpringBootDemo.modules.test.service.CountryService;
import com.zsq.SpringBootDemo.modules.test.vo.ApplicationTest;



@Controller
@RequestMapping("/test")
public class TestController{
	
	@Autowired
	private CityService cityService;

	@Autowired
	private CountryService countryService;
	/**
	 * 页面展示
	 * 127.0.0.1/text/index
	 * @return
	 */
	@RequestMapping("/index")
	public String indexPage(ModelMap modelMap) {
		int countryId = 522;
		List<City> cities = cityService.getCitiesByCountryId(countryId);
		cities = cities.stream().limit(10).collect(Collectors.toList());
		Country country = countryService.getCountryByCountryId(countryId);
		
		
		modelMap.addAttribute("template","test/index"); //test文件夹下的index，页面中判断替换body中的部分
		
		modelMap.addAttribute("thymeleafTitle","abcdefg");
		modelMap.addAttribute("checked",true);
		modelMap.addAttribute("currentNumber", 99);
		modelMap.addAttribute("changeType", "checkbox");
		modelMap.addAttribute("baiduUrl","/test/log");
		modelMap.addAttribute("city", cities.get(0));
		modelMap.addAttribute("shopLogo"," http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
		modelMap.addAttribute("country", country);
		modelMap.addAttribute("cities", cities);
		modelMap.addAttribute("updateCityUri", "/api/city");
		modelMap.addAttribute("template", "test/index");
		
		return "index";   //template最外层的index页面
	}
	
	
	
	
	
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
	 * 访问地址127.0.0.1/test/desc?key=fuck
	 * @RequestParam 是用于接收？这种形式的
	 * @PathVariable 是用于接收/522这种类型的数据
	 * 
	 * @return
	 */
	@RequestMapping("/desc")
	@ResponseBody   //代表该方法是一个接口 
	public String testDesc(HttpServletRequest req,@RequestParam String key){
		String key2 = req.getParameter("key");
		return "This is test modle desc." +key + key2;
	}
}
