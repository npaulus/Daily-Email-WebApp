package com.natepaulus.dailyemail.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Class IndexController for handling request to see the main page.
 */
@Controller
public class IndexController {

	/**
	 * Display the main page.
	 * 
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/")
	public ModelAndView displayIndex() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pageTitle", "Daily Email Service");
		return new ModelAndView("index", model);
	}

	/**
	 * Catch all display the main page.
	 * 
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/index*")
	public ModelAndView catchAllDisplayIndex() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pageTitle", "Daily Email Service");
		return new ModelAndView("index", model);
	}
}
