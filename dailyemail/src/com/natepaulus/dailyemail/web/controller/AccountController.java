package com.natepaulus.dailyemail.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;

@Controller
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView displayAccountPage(@ModelAttribute("user") User user, HttpSession session, HttpServletRequest request) {
		Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
		if(map!=null){
			session.setAttribute("user", user);
			return new ModelAndView("account", "user", user);
		} else {
			User loggedInUser = (User) session.getAttribute("user");
			return new ModelAndView("account", "user", loggedInUser);
		}
		
	}
	
	@RequestMapping(value = "/account/addNews", method = RequestMethod.POST)
	public String addNewsLink(@RequestParam String name, @RequestParam String url){
		System.out.println("Name: " + name);
		System.out.println("URL: " + url);
		return "account";
	}
	
	@RequestMapping(value = "/account/weather", method = RequestMethod.POST)
	public String addNewsLink(@RequestParam int weather, HttpSession session){
		User user = (User) session.getAttribute("user");
		Weather wx = user.getWeather();				
		wx = accountService.updateWeatherDeliveryPreference(weather, user);
		user.setWeather(wx);
		session.setAttribute("user", user);
		return "account";
	}
	
}
