//package com.codewithashish.blog.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Aspect
//public class AopConfig {
//
//	/*
//	 * Logging 
//	 * Exception Handling 
//	 * Time taken
//	 */
//	
//	private Logger log = LoggerFactory.getLogger(AopConfig.class);
//	
//	@Before(value = "execution(* com.codewithashish.blog.controllers.*.*(..))")
//	public void logStatementBefore(JoinPoint joinPoint) {
//		log.info("Executing {}",joinPoint);
//	}
//}
