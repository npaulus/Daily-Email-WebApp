package com.natepaulus.dailyemail.web.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * The Class ViewConfiguration configures velocity as the view resolver.
 */
@Configuration
public class ViewConfiguration {

	/**
	 * Velocity configurer.
	 *
	 * @return the velocity configurer
	 */
	@Bean
	public VelocityConfigurer velocityConfigurer() {
		VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
		velocityConfigurer.setResourceLoaderPath("/WEB-INF/views/");		
		Properties velocityProperties = new Properties();
		velocityProperties.setProperty("input.encoding", "UTF-8");
		velocityProperties.setProperty("output.encoding", "UTF-8");
		velocityConfigurer.setVelocityProperties(velocityProperties);
		
		return velocityConfigurer;
	}

	/**
	 * Velocity view resolver.
	 *
	 * @return the view resolver
	 */
	@Bean
	public ViewResolver velocityViewResolver() {
		VelocityViewResolver viewResolver = new VelocityViewResolver();
		viewResolver.setSuffix(".vm");
		viewResolver.setExposeSpringMacroHelpers(true);
		viewResolver.setOrder(1);
		viewResolver.setContentType("text/html; charset=UTF-8");
		return viewResolver;
	}

}
