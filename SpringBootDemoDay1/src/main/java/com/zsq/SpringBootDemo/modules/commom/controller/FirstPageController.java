package com.zsq.SpringBootDemo.modules.commom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class FirstPageController {

	/**
	 * 通过这里到的方法保持页面在主页面维持住，在通过index里的${template}来判断拿到sidebar中设定的地址，通过拦截器将template添加，最终拿到页面
	 * @return
	 */
	@RequestMapping("/firstPage")
	public String firstPage() {
		return "index";     
	}
}
