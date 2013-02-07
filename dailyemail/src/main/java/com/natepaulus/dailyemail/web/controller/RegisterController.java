package com.natepaulus.dailyemail.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.natepaulus.dailyemail.repository.entity.Weather;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;
import com.natepaulus.dailyemail.web.service.interfaces.UserService;
import com.natepaulus.dailyemail.web.service.interfaces.WeatherService;

/**
 * The Class RegisterController handles new user sign up requests.
 */
@Controller
public class RegisterController {

	/** The user service. */
	@Autowired
	UserService userService;
	
	/** The weather service. */
	@Autowired
	WeatherService weatherService;

	/**
	 * Display register page.
	 *
	 * @param model the model
	 * @return the view to display
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String displayRegisterPage(Model model) {
		model.addAttribute("accountSignUp", new AccountSignUp());
		model.addAttribute("pageTitle", "Daily Email Service - Register");
		return "register";
	}

	/**
	 * Process sign up and validate the user entered valid inputs.
	 *
	 * @param accountSignUp the account sign up object that matches the register form on the web page
	 * @param result the result of binding the account sign up object to the submitted register form
	 * @param redirect the redirect to add flashmap attributes too
	 * @param model the model 
	 * @return the string
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processSignUp  (
			@Valid @ModelAttribute("accountSignUp") AccountSignUp accountSignUp,
			BindingResult result, RedirectAttributes redirect, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("pageTitle", "Daily Email - Register Error"); 
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
