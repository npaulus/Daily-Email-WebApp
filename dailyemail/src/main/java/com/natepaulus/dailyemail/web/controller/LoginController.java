package com.natepaulus.dailyemail.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.natepaulus.dailyemail.repository.entity.User;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;
import com.natepaulus.dailyemail.web.service.interfaces.UserService;

/**
 * The Class LoginController to handle login requests.
 */
@Controller
public class LoginController {

	/** The Constant REQUESTED_URL. */
	public static final String REQUESTED_URL = "REQUESTED_URL";

	/** The user service. */
	@Autowired
	UserService userService;

	/**
	 * Display login form.
	 * 
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String displayLoginForm() {
		return "login";
	}

	/**
	 * Process login.
	 * 
	 * @param emailAddress
	 *            the user email address
	 * @param pwd
	 *            the user password
	 * @param session
	 *            the session
	 * @param redirect
	 *            the redirect to add flash map attributes to
	 * @return the string which is the view to display
	 * @throws AuthenticationException
	 *             the authentication exception for invalid login attempts
	 */
	@RequestMapping(value = "/login/*", method = RequestMethod.POST)
	public String processLogin(@RequestParam String emailAddress,
			@RequestParam String pwd, HttpSession session,
			RedirectAttributes redirect) throws AuthenticationException {
		User user = this.userService.login(emailAddress, pwd);
		session.setAttribute("user", user);
		session.removeAttribute(REQUESTED_URL);

		redirect.addFlashAttribute("user", user);
		return "redirect:/account";

	}

}
