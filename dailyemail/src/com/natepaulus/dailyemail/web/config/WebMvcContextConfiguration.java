package com.natepaulus.dailyemail.web.config;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
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
@EnableAsync
@EnableScheduling
@ImportResource("WEB-INF/lib/applicationContext.xml")
@ComponentScan(basePackages = "com.natepaulus.dailyemail")
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

	final Logger logger = LoggerFactory.getLogger(WebMvcContextConfiguration.class);
	
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
		mappings.setProperty("AuthenticationException", "index");

		Properties statusCodes = new Properties();
		statusCodes.setProperty("login",
				String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
		statusCodes.setProperty("index",
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
	
/*	@Bean
	public JavaMailSender mailSender(){
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		Properties javaMailProperties = new Properties();
		
		
		
		sender.setJavaMailProperties(javaMailProperties);
		
		return sender;
	}*/
	
	@Bean
	public VelocityEngineFactoryBean velocityEngine(){
		VelocityEngineFactoryBean velocityFactoryBean = new VelocityEngineFactoryBean();
		Properties velocityProperties = new Properties();
		velocityProperties.put("resource.loader", "class");
		velocityProperties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityFactoryBean.setVelocityProperties(velocityProperties);
		return velocityFactoryBean;
	}

}
