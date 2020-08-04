package com.blu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		/* 未登录 */
		if (request.getSession().getAttribute("user")==null) {
			response.sendRedirect("/admin");
			//不再往下执行
			return false;
		}
		
		return true;
	}
	
	
}
