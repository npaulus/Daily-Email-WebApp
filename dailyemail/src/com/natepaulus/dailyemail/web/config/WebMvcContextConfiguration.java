package com.natepaulus.dailyemail.web.config;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;

@Configuration
@EnableWebMvc
@ImportResource("WEB-INF/lib/applicationContext.xml")
@ComponentScan(basePackages = "com.natepaulus.dailyemail")
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/public-resources/")
				.setCachePeriod(31556926);
	}

	@Bean
	public MessageSource messageSource() {
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

	@Override
	public void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(simpleMappingExceptionResolver());
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver;
		exceptionResolver = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("AuthenticationException", "login");

		Properties statusCodes = new Properties();
		statusCodes.setProperty("login",
				String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));

		exceptionResolver.setExceptionMappings(mappings);
		exceptionResolver.setStatusCodes(statusCodes);

		return exceptionResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		@SuppressWarnings("unused")
		InterceptorRegistration registration = registry
				.addInterceptor(new SecurityHandlerInterceptor())
				.addPathPatterns("/account", "/account/*");
	}

}
