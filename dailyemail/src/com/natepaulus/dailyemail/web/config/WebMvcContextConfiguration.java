package com.natepaulus.dailyemail.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.natepaulus.dailyemail")
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/public-resources/")
				.setCachePeriod(31556926);
	}
	
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource;
		messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("WEB-INF/lib/errors");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	@Bean
	public HandlerMapping controllerClassNameHandlerMapping() {
		return new ControllerClassNameHandlerMapping();
	}

}
