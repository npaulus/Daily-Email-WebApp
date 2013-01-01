package com.natepaulus.dailyemail.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dailyemail")
public class IndexController {
	
	public String home() {
		return "index";
	}
}
