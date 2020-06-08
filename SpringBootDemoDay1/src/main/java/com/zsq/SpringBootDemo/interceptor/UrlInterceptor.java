package com.zsq.SpringBootDemo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器类
 * @author ZengShiQi
 *
 */
@Component //注册
public class UrlInterceptor implements HandlerInterceptor{

	private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UrlInterceptor.class);
	
	//之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOGGER.debug("preHandle contriller------------------");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	//并行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LOGGER.debug("postHandle contriller------------------");
		//如果没有页面要返回或重定向的路径则直接返回
		if(modelAndView == null || modelAndView.getViewName().startsWith("redirect")) {
			return;
		}
		//获取路径
		String path= request.getServletPath();
		//获取看看map中有没有设定好的路径如果有则使用设定的
		String template = (String) modelAndView.getModelMap().get("template");
		if(StringUtils.isBlank(template)) {
			if(path.startsWith("/")) {
				path = path.substring(1);//切割字符串，将开头的/切割掉
			}
			modelAndView.getModelMap().addAttribute("template",path.toLowerCase());//toLowerCase将大写字符转换成小写的
		}
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	//之后
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LOGGER.debug("afterCompletion contriller------------------");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
}
