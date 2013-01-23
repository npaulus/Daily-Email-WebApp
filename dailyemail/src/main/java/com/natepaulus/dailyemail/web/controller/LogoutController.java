package com.natepaulus.dailyemail.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class LogoutController handles a user logout.
 */
@Controller
public class LogoutController {
	
	/**
	 * Logout user.
	 *
	 * @param session the session
	 * @return the string which is the view to display
	 */
	@RequestMapping(value="/logout")
	public String logoutUser(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}
	
}
