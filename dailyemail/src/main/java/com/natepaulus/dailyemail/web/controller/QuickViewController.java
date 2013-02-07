package com.natepaulus.dailyemail.web.controller;

import java.util.HashMap;
import java.util.Map;

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
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pageTitle", "Daily Email Service - Quick View");
		EmailData data = emailService.generateQuickView(urlCode);
		model.put("data", data);
		return new ModelAndView("quickview", model);
	}
	
}
