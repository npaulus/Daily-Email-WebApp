package com.natepaulus.dailyemail.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterSuccessController {
	
	@RequestMapping(value = "/registerSuccess")
	public String registerSuccess() {		
		System.out.println("Get Page");
		return "registerSuccess";				
	}
}
