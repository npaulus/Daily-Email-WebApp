package com.natepaulus.dailyemail.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.natepaulus.dailyemail.web.config.WebMvcContextConfiguration;

/**
 * The Class DailyEmailWebApplicationInitializer initializers the spring based
 * web application and completes the basic configuration for the daily email
 * app.
 */
public class DailyEmailWebApplicationInitializer implements
		WebApplicationInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet
	 * .ServletContext)
	 */
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		registerDispatcherServlet(servletContext);
	}

	/**
	 * Register dispatcher servlet and specify it will handle requests for the
	 * daily email app at the root context of the app. It also registers the
	 * name of the dispatcher.
	 * 
	 * @param servletContext
	 *            the servlet context
	 */
	private void registerDispatcherServlet(final ServletContext servletContext) {
		WebApplicationContext dispatcherContext = createContext(WebMvcContextConfiguration.class);
		DispatcherServlet dispatcherServlet = new DispatcherServlet(
				dispatcherContext);
		ServletRegistration.Dynamic dispatcher;
		dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

	/**
	 * Creates the context.
	 * 
	 * @param annotatedClasses
	 *            the annotated classes
	 * @return the web application context
	 */
	private WebApplicationContext createContext(
			final Class<?>... annotatedClasses) {
		AnnotationConfigWebApplicationContext context;
		context = new AnnotationConfigWebApplicationContext();
		context.register(annotatedClasses);
		return context;
	}
}
