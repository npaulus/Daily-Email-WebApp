package com.natepaulus.dailyemail.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;

/**
 * @author Nate
 *
 */
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
	public String addNewsLink(@RequestParam String name, @RequestParam String url, HttpSession session, RedirectAttributes redirect){
		User user = (User) session.getAttribute("user");
		
		user = accountService.addNewsLink(url, name, user);
		
		redirect.addFlashAttribute("user", user);
		return "redirect:/account";
	}
	
	@RequestMapping(value = "/account/weather", method = RequestMethod.POST)
	public String updateWeatherDeliveryPreference(@RequestParam int deliver_pref, RedirectAttributes redirect, HttpSession session){
		User user = (User) session.getAttribute("user");
		
		accountService.updateWeatherDeliveryPreference(deliver_pref, user);
				
		redirect.addFlashAttribute("user", user);
		return "redirect:/account";
	}
	
	@RequestMapping(value = "/account/changezip", method = RequestMethod.POST)
	public String updateWeatherZipCode(@RequestParam String zipCode, HttpSession session, RedirectAttributes redirect){
		User user = (User) session.getAttribute("user");
		accountService.updateUserZipCode(user, zipCode);
		redirect.addFlashAttribute("user", user);
		return "redirect:/account";
	}
	
	@RequestMapping(value= "/account/news", method= RequestMethod.POST)
	public String setIncludedNewsInformation(@RequestParam(required = false) String[] news, HttpSession session, RedirectAttributes redirect){
		User user = (User) session.getAttribute("user");
		if(news == null){
			news = new String[1];
			news[0] = "0";
		}
		user = accountService.setIncludedNewsInformation(news, user);
		
		redirect.addFlashAttribute("user", user);
		return "redirect:/account";
	}
	
	@RequestMapping(value="/account/deleteNewsLink/{id}", method = RequestMethod.GET)
	public String deleteNewsLink(@PathVariable int id, HttpSession session, RedirectAttributes redirect){
		User user = (User) session.getAttribute("user");
		user = accountService.deleteNewsLink(id, user);
		
		redirect.addFlashAttribute("user", user);
		return "redirect:/account";
	}
	
}
