package com.blu.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(HttpServletRequest req,Exception e) throws Exception {
		logger.error("Request URL : {}, Exception : {}", req.getRequestURI(), e);
		
		//如果存在状态码则让SpringBoot处理
		if(AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class)!=null){
			throw e;
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("url",req.getRequestURL());
		mv.addObject("exception", e);
		mv.setViewName("error/error");
		return mv;
	}
}
