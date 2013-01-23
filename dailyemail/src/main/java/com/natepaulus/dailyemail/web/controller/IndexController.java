package com.natepaulus.dailyemail.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String displayIndex() {
		return "index";
	}

	/**
	 * Catch all display the main page.
	 * 
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/index*")
	public String catchAllDisplayIndex() {
		return "index";
	}
}
