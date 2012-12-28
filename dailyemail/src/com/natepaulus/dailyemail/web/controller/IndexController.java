package com.natepaulus.dailyemail.web.controller;

import org.springframework.stereotype.Controller;

@Controller
public class IndexController {
	
	public String home() {
		System.out.println("Test");
		return "home";
	}
}
