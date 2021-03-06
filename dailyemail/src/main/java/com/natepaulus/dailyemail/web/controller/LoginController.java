package com.natepaulus.dailyemail.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public ModelAndView displayLoginForm() {
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("pageTitle", "Daily Email Service - Login");
		return new ModelAndView("login", model);
	}

	/**
	 * Process login.
	 *
	 * @param emailAddress the user email address
	 * @param pwd the user password
	 * @param session the session
	 * @param redirect the redirect to add flash map attributes to
	 * @return the string which is the view to display
	 * @throws AuthenticationException the authentication exception for invalid login attempts
	 */
	@RequestMapping(value = "/login/*", method = RequestMethod.POST)
	public String processLogin(@RequestParam final String emailAddress, @RequestParam final String pwd, final HttpSession session,
			final RedirectAttributes redirect) throws AuthenticationException {
		final long userId = this.userService.login(emailAddress, pwd);
		session.setAttribute("user", userId);
		session.removeAttribute(REQUESTED_URL);

		redirect.addFlashAttribute("user", userId);
		return "redirect:/account";

	}

}
