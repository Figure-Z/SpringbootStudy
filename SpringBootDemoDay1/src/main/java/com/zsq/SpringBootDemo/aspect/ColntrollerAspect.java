package com.zsq.SpringBootDemo.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zsq.SpringBootDemo.interceptor.UrlInterceptor;
/**
 * aop切面
 * @author ZengShiQi
 *
 */
@Aspect //切面标识
@Component  //注册为组件
public class ColntrollerAspect {
	
	private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UrlInterceptor.class);
	
	/**
	 * 关联在切面上的切点
	 * 第一个*是指任何返回类型都可以
	 * 第二个*值包下所有类下的控制器
	 * 第三个*代表控制器下所有类
	 * 第四个*代表所有方法
	 * (..)代表参数类型不限
	 * 
	 * @Order(1) 代表优先级，数字越小优先级越高
	 */
	@Pointcut("execution(public * com.zsq.SpringBootDemo.modules.*.controller.*.*(..))")
	@Order(1)
	public void controllerPointCut() {}
	
	
	@Before(value ="com.zsq.SpringBootDemo.aspect.ColntrollerAspect.controllerPointCut()" )
	public void beforeController(JoinPoint joinPoint) {
		LOGGER.debug("beforeController-----------");
		ServletRequestAttributes attributes = 
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		LOGGER.debug("请求来源：" + request.getRemoteAddr());
		LOGGER.debug("请求URL：" + request.getRequestURL().toString());
		LOGGER.debug("请求方式：" + request.getMethod());
		LOGGER.debug("响应方法：" + joinPoint.getSignature().getDeclaringTypeName() + "." + 
				joinPoint.getSignature().getName());
		LOGGER.debug("请求参数：" + Arrays.toString(joinPoint.getArgs()));
	}
	
	
	@After(value = "com.zsq.SpringBootDemo.aspect.ColntrollerAspect.controllerPointCut()")
	public void afterController() {
		LOGGER.debug("afterController-----------");
	}
	
	/**
	 * 环绕类型需要返回object类对象来进入控制器
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "com.zsq.SpringBootDemo.aspect.ColntrollerAspect.controllerPointCut()")
	public Object aroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		LOGGER.debug("aroundController-----------");
		return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
	}
}
