package com.natepaulus.dailyemail.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.natepaulus.dailyemail.web.domain.EmailData;
import com.natepaulus.dailyemail.web.service.interfaces.EmailService;

@Controller
public class QuickViewController {
	
	/** The email service. */
	@Autowired
	EmailService emailService;
	
	@RequestMapping(value ="/quickview/{urlCode}", method=RequestMethod.GET)
	public ModelAndView generateQuickViewEmail(@PathVariable String urlCode){
		
		EmailData data = emailService.generateQuickView(urlCode);
		
		return new ModelAndView("quickview", "data", data);
	}
	
}
