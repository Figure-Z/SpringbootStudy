package com.zsq.SpringBootDemo.modules.test.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zsq.SpringBootDemo.config.ResourceConfigBean;
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
	
	@Autowired
	private ResourceConfigBean resourceConfigBean;
	
	//引入日志
	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	/**
	 * 文件下载
	 */
	@RequestMapping("/download")
	@ResponseBody
	public ResponseEntity<Resource> download(@RequestParam String fileName){
		
		String resourcePath = resourceConfigBean.getResourcePath()+fileName;
		try {
			Resource resource = new UrlResource(ResourceUtils.getURL(resourcePath));
			/**
			 * CONTENT_TYPE -----下载类型--------application/octet-stream等于* ，表示全类型
			 * CONTENT_DISPOSITION-----想当于扩展MINE-------后面则是对下载的名称进行格式化，防止乱码
			 * body则是下载主体（真实路径下的对象文件）
			 */
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE,"application/octet-stream")
					.header(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment; filename=%s", fileName))
					.body(resource);	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 单文件上传
	 * @param modelMap
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping(value = "/file" , consumes = "multipart/form-data")
	public String uploadFile(ModelMap modelMap,@RequestParam MultipartFile file,RedirectAttributes redirectAttributes) {
	
		//重定向要想在页面上获取到，要用到redirectAttributes.addFlashAttribute
		if(file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message","please select file");
			return "redirect:/test/index";
		}
		
		//可以使用流来完成，这里用MultipartFile提供的方法完成
		try {
			//硬编码绝对路径方式
			//String resourcePath = "/java/upload/" +file.getOriginalFilename();
			//设定上传路径地址
			//String destFilePath = "D:" + resourcePath; //getOriginalFilename得到文件原始名字
			
			//通过静态资源配置路径来完成  ==/java/upload/+要上传的文件名  ----获取spring的映射地址
			String resourcePath = resourceConfigBean.getResourcePath() + file.getOriginalFilename();
			//通过自带的ResourceUtils类方法获取路径------通过映射来找到真实地址
			File destFile = new File(ResourceUtils.getURL(resourcePath).getPath()); //个人认为它是将上传地址封装到要上传的文件上，来让文件有一个明确的上传位置，由transferTo确认上传
			file.transferTo(destFile); //上传到指定路径下
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message","upload fail");
			return "redirect:/test/index";
		}
		//重定向下ModelMap是不能添加返回（return）信息的
		redirectAttributes.addFlashAttribute("message","upload success");
		return "redirect:/test/index";
	}
	
	/**
	 * 多文件上传
	 * @param modelMap
	 * @param files
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping(value = "/files" , consumes = "multipart/form-data")
	public String uploadFiles(ModelMap modelMap,@RequestParam MultipartFile[] files,RedirectAttributes redirectAttributes) {
		boolean isEmpty = true; //判断是否选择了文件
		for(MultipartFile file : files) {
			if(file.isEmpty()) {
				continue;
			}
			
			try {
				String destFilePath = "D:\\java\\upload\\" + file.getOriginalFilename(); //getOriginalFilename得到文件原始名字
				File destFile = new File(destFilePath);
				file.transferTo(destFile);
				isEmpty=false;
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message","upload fail");
				return "redirect:/test/index";
			}
		}
		
		if(isEmpty) {
			redirectAttributes.addFlashAttribute("message","please select file");
		}else {
			redirectAttributes.addFlashAttribute("message","upload success");
		}
		
		return"redirect:/test/index";
	}
	
	
	
	/**
	 * 页面展示
	 * 127.0.0.1/test/index
	 * @return
	 */
	@RequestMapping("/index")
	public String indexPage(ModelMap modelMap) {
		int countryId = 522;
		List<City> cities = cityService.getCitiesByCountryId(countryId);
		cities = cities.stream().limit(10).collect(Collectors.toList());
		Country country = countryService.getCountryByCountryId(countryId);
		
		
		//modelMap.addAttribute("template","test/index"); //test文件夹下的index，页面中判断替换body中的部分
		
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
		//modelMap.addAttribute("template", "test/index");
		
		//添加路径为静态资源路径，在WebMvcConfig中配置了，可以用自定义的path来代替绝对路径
		modelMap.addAttribute("resourceLogo","/upload/111.jpg");
		
		return "index";   //template最外层的index页面
	}
	
	
	
	
	
	
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
