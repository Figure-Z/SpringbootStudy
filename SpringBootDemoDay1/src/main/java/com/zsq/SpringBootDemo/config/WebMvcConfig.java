package com.zsq.SpringBootDemo.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zsq.SpringBootDemo.filter.ParameterFilter;
import com.zsq.SpringBootDemo.interceptor.UrlInterceptor;

/**
 * 自定义配置类，配置http
 * @author ZengShiQi
 * 
 * 要实现拦截器要实现WebMvcConfigurer类并重写addInterceptors来添加新的拦截器
 *
 */
@Configuration   //表示配置类   用@Bean来创建对象
@AutoConfigureAfter({WebMvcAutoConfiguration.class})   //覆盖系统配置类，如果有重复的配置以后面的（自定义）为准
public class WebMvcConfig implements WebMvcConfigurer{

	@Value("${server.http.port}")
	private int httpPort;
	
	@Autowired
	private UrlInterceptor urlInterceptor;
	
	@Bean
	public Connector connector() {
		Connector connector = new Connector();   //创建连接器
		connector.setScheme("http");
		connector.setPort(httpPort);
		return connector;
	}
	
	//要将连接器放入一个接口中
	@Bean
	public ServletWebServerFactory servletWebServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();  //创建工厂对象
		factory.addAdditionalTomcatConnectors(connector());   //将自定义连接器注入
		return factory;
	}
	
	//配置自定义过滤器
	@Bean
	public FilterRegistrationBean<ParameterFilter> filter(){
		FilterRegistrationBean<ParameterFilter> registeRegistrationBean = new FilterRegistrationBean<>(); //创建一个过滤器对象
		registeRegistrationBean.setFilter(new ParameterFilter()); //将自己的过滤器添加进去
		return registeRegistrationBean;
	}

	//添加拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(urlInterceptor).addPathPatterns("/**");
	}
	
	
	
}
