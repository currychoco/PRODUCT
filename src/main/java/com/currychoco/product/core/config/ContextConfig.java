package com.currychoco.product.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.currychoco.product.core.interceptor.LoginInterceptor;

@Configuration
public class ContextConfig implements WebMvcConfigurer {
	
	@Autowired
	private LoginInterceptor loginInteceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInteceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/members/login")
			.excludePathPatterns("/members/join");
	}
}
