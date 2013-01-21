package com.natepaulus.dailyemail.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.web.service.interfaces.SocialService;

@Controller
public class ReaderController {

	final Logger logger = LoggerFactory.getLogger(ReaderController.class);

	@Autowired
	SocialService socialService;

	@RequestMapping(value = "reader", method = RequestMethod.GET)
	public ModelAndView displayReader(@ModelAttribute("user") User user,
			HttpSession session, HttpServletRequest request) {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		Map<String, Object> model = new HashMap<String, Object>();

		if (map != null) {

			model.put("user", user);
			
			model.putAll(socialService.getDataForDisplay(user));
			model.put("news", socialService.getRssNewsForReader(user));
			logger.info("Model Data (if): " + model.toString());
			
			return new ModelAndView("reader", model);
		} else {
			User loggedInUser = (User) session.getAttribute("user");
			model.put("user", loggedInUser);
			model.putAll(socialService.getDataForDisplay(loggedInUser));
			model.put("news", socialService.getRssNewsForReader(loggedInUser));
			logger.info("Model Data (else): " + model.toString());
			return new ModelAndView("reader", model);
		}

	}

	@RequestMapping(value = "/connect/facebook", method = RequestMethod.POST)
	public String connectToFacebook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		User user = (User) request.getSession().getAttribute("user");
		logger.info("Connecting to Facebook method: ");
		logger.info("User ID in connectToFacebook is:" + user.getId());

		return "redirect:" + socialService.connectToFacebook();

	}

	@RequestMapping(value = "/social/facebook/connect", method = RequestMethod.GET)
	public String completeConnectionToFacebook(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirect) {
		logger.info("Saving connection to Facebook method: ");
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		String authorizationCode = request.getParameter("code");
		logger.info("User Email in Callback Controller: " + user.getEmail());

		user = socialService.saveFacebookInformation(authorizationCode, user);
		session.setAttribute("user", user);
		redirect.addFlashAttribute("user", user);
		return "redirect:/reader";
	}

}
