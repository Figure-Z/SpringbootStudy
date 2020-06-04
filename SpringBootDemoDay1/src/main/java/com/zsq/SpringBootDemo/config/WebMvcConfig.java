package com.zsq.SpringBootDemo.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义配置类，配置http
 * @author ZengShiQi
 *
 */
@Configuration   //表示配置类   用@Bean来创建对象
@AutoConfigureAfter({WebMvcAutoConfiguration.class})   //覆盖系统配置类，如果有重复的配置以后面的（自定义）为准
public class WebMvcConfig {

	@Value("${server.http.port}")
	private int httpPort;
	
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
}
