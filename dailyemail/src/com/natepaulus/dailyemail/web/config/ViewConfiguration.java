package com.natepaulus.dailyemail.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

@Configuration
public class ViewConfiguration {

	@Bean
	public VelocityConfigurer velocityConfigurer() {
		VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
		velocityConfigurer.setResourceLoaderPath("/WEB-INF/views/");
		return velocityConfigurer;
	}

	@Bean
	public ViewResolver velocityViewResolver() {
		VelocityViewResolver viewResolver = new VelocityViewResolver();
		viewResolver.setSuffix(".vm");
		viewResolver.setExposeSpringMacroHelpers(true);
		viewResolver.setOrder(1);
		return viewResolver;
	}

}
