package com.natepaulus.dailyemail.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	
	@RequestMapping(value="/logout")
	public String logoutUser(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}
	
}
