package com.natepaulus.dailyemail.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String registerSuccess() {		
		System.out.println("Get Page");
		return "registerSuccess";				
	}
}
