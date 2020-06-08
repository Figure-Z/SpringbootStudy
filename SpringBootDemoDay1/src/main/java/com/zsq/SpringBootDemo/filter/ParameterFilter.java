package com.zsq.SpringBootDemo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 * 过滤器类
 * 
 * 定义类并重写方法，在doFilter方法中重写包装类HttpServletRequestWrapper里面的方法来对获取到的参数进行判断修改
 */

@WebFilter(filterName = "ParameterFilter",urlPatterns = "/**")
public class ParameterFilter implements Filter{

	private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ParameterFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.debug("ParameterFilter dofilter---------");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {

			@Override
			public String getParameter(String name) {
				String value = httpRequest.getParameter(name);
				if(org.apache.commons.lang3.StringUtils.isNotBlank(value)&&value.contains("fuck")) {
					return value.replace("fuck","***");
				}
				
				return super.getParameter(name);
			}

			@Override
			public String[] getParameterValues(String name) {
				String[] values = httpRequest.getParameterValues(name);
				for (int i=0; i<values.length;i++) {
					String temp = values[i];
					if(org.apache.commons.lang3.StringUtils.isNotBlank(temp)&&temp.contains("fuck")) {
						values[i] = temp.replaceAll("fuck","***");
					}
				}
				return  values;
			}
			
			
		};
		chain.doFilter(wrapper, response);  //释放filter
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("ParameterFilter init---------");
	}

	@Override
	public void destroy() {
		LOGGER.debug("ParameterFilter destroy---------");
	}

}
