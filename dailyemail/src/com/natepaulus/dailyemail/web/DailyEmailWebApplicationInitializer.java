package com.natepaulus.dailyemail.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.natepaulus.dailyemail.web.config.WebMvcContextConfiguration;


public class DailyEmailWebApplicationInitializer implements
		WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		registerDispatcherServlet(servletContext);
	}
	
	private void registerDispatcherServlet(final ServletContext servletContext){
		WebApplicationContext dispatcherContext = createContext(WebMvcContextConfiguration.class);
		DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext);
		ServletRegistration.Dynamic dispatcher;
		dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
	
	private WebApplicationContext createContext (final Class<?>...annotatedClasses){
		AnnotationConfigWebApplicationContext context;
		context = new AnnotationConfigWebApplicationContext();
		context.register(annotatedClasses);
		return context;
	}
}
