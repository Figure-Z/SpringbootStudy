package com.zsq.SpringBootDemo.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取非全局的配置文件，并注册为spring组件
 * 需要创建一个配置类，
 * 来对应你的非全局配置文件
 * 同样也可以使用value的方式来读取
 * 
 * 自定配置文件也可以读取其他配置文件的内容
 * @author ZengShiQi
 */

@Component    //注册为spring组件
@PropertySource("classpath:config/applicationTest.properties") //连接你的配置文件
@ConfigurationProperties(prefix = "com.mytest")  //表示共同前缀
public class ApplicationTest {
	
	private String name1;
	private int age1;
	private String desc1;
	private String random1;
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public int getAge1() {
		return age1;
	}
	public void setAge1(int age1) {
		this.age1 = age1;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public String getRandom1() {
		return random1;
	}
	public void setRandom1(String random1) {
		this.random1 = random1;
	}
	
	
}
