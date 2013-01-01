package com.natepaulus.dailyemail.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;
import com.natepaulus.dailyemail.web.service.PasswordEncryption;
import com.natepaulus.dailyemail.web.service.interfaces.UserService;

@Controller
public class LoginController {

	public static final String REQUESTED_URL = "REQUESTED_URL";
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String displayLoginForm() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processLogin(@RequestParam String emailAddress,
			@RequestParam String pwd, HttpSession session, RedirectAttributes redirect) throws AuthenticationException {
		User user = this.userService.login(emailAddress, PasswordEncryption.toSHA1(pwd));
		session.setAttribute("user", user);
		String url = (String) session.getAttribute(REQUESTED_URL);
		session.removeAttribute(REQUESTED_URL);
		if(StringUtils.hasText(url) && !url.contains("login")){
			redirect.addFlashAttribute("user", user);
			return "redirect:" + url;			
		} else {
			redirect.addFlashAttribute("user", user);
			return "redirect:/index";
		}
		
		
	}

}
