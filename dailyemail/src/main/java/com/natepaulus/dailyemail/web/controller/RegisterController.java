package com.natepaulus.dailyemail.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;
import com.natepaulus.dailyemail.web.service.interfaces.UserService;
import com.natepaulus.dailyemail.web.service.interfaces.WeatherService;

@Controller
public class RegisterController {

	@Autowired
	UserService userService;
	
	@Autowired
	WeatherService weatherService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String displayRegisterPage(Model model) {
		model.addAttribute("accountSignUp", new AccountSignUp());

		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processSignUp  (
			@Valid @ModelAttribute("accountSignUp") AccountSignUp accountSignUp,
			BindingResult result, RedirectAttributes redirect, Model model) {

		if (result.hasErrors()) {
			return "register";
		}
		
		Weather weather = weatherService.setInitialWeatherLocation(accountSignUp.getZipcode());		
		
		if(userService.addNewUser(accountSignUp, weather)){
			redirect.addFlashAttribute("accountSignUp", accountSignUp);
			return "redirect:/registerSuccess";
		} else {
			model.addAttribute("userExists", "Email already exists. Try again.");			
			return "register";
		}
	}

}
