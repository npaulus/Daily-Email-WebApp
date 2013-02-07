package com.natepaulus.dailyemail.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Class RegisterSuccessController.
 */
@Controller
public class RegisterSuccessController {
	
	/**
	 * Register success displays the success message for a user that just signed up successfully.
	 *
	 * @return the view to display
	 */
	@RequestMapping(value = "/registerSuccess")
	public ModelAndView registerSuccess() {		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pageTitle", "Daily Email Service");
		return new ModelAndView("registerSuccess", model);				
	}
}
