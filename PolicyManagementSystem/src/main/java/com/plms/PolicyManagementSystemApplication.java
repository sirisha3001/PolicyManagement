package com.plms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan("com.plms")

public class PolicyManagementSystemApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(PolicyManagementSystemApplication.class, args);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new SessionTimerInterceptor()).addPathPatterns("/retrievePolicies","/retrievePoliciesForUser","/editPolicy");
	}
	
	  
}