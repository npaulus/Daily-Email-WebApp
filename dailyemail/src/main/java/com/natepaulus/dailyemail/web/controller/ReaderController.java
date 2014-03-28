package com.natepaulus.dailyemail.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.natepaulus.dailyemail.repository.entity.RssNewsLinks;
import com.natepaulus.dailyemail.repository.entity.User;
import com.natepaulus.dailyemail.web.service.interfaces.EmailService;
import com.natepaulus.dailyemail.web.service.interfaces.ReaderService;

/**
 * The Class ReaderController handles getting user RSS feeds and social media to display.
 */
@Controller
public class ReaderController {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(ReaderController.class);

	/** The reader service */
	@Autowired
	ReaderService readerService;

	/** The email service. */
	@Autowired
	EmailService emailService;

	/**
	 * This handles the callback from facebook that contains the code to use for getting the current user's facebook information
	 *
	 * @param request the http request
	 * @param response the http response
	 * @param redirect the redirect to add flashmap attributes too
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/social/facebook/connect", method = RequestMethod.GET)
	public String completeConnectionToFacebook(final HttpServletRequest request, final HttpServletResponse response,
			final RedirectAttributes redirect) {
		this.logger.info("Saving connection to Facebook method: ");
		final HttpSession session = request.getSession();

		final User user = (User) session.getAttribute("user");

		request.getParameter("code");
		this.logger.info("User Email in Callback Controller: " + user.getEmail());

		session.setAttribute("user", user);
		redirect.addFlashAttribute("user", user);
		return "redirect:/reader";
	}

	/**
	 * This is the first phase of connecting a user from this app to their facebook account.
	 *
	 * @param request the http request
	 * @param response the http response
	 * @return the string which is the view to display
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/connect/facebook", method = RequestMethod.POST)
	public String connectToFacebook(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

		final User user = (User) request.getSession().getAttribute("user");
		this.logger.info("Connecting to Facebook method: ");
		this.logger.info("User ID in connectToFacebook is:" + user.getId());

		return "";

	}

	/**
	 * Display reader.
	 *
	 * @param user the user
	 * @param session the session
	 * @param request the http request
	 * @return the model and view
	 */
	@RequestMapping(value = "reader", method = RequestMethod.GET)
	public ModelAndView displayReader(final HttpSession session, final HttpServletRequest request) {
		final Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		final Map<String, Object> model = new HashMap<String, Object>();

		final long user = (long) session.getAttribute("user");

		if (map != null) {

			model.put("user", user);

			final Map<String, List<RssNewsLinks>> userNewsData = this.readerService.getNewsForReaderDisplay(user);
			model.put("pageTitle", "Daily Email Service - RSS Reader");
			model.put("userNewsData", userNewsData);
			// model.putAll(socialService.getDataForDisplay(user));
			// model.put("news", socialService.getRssNewsForReader(user));
			// logger.info("Model Data (if): " + model.toString());

			return new ModelAndView("reader", model);
		} else {
			final long loggedInUser = (long) session.getAttribute("user");
			model.put("user", loggedInUser);
			final Map<String, List<RssNewsLinks>> userNewsData = this.readerService.getNewsForReaderDisplay(loggedInUser);
			model.put("pageTitle", "Daily Email Service - RSS Reader");
			model.put("userNewsData", userNewsData);
			// model.putAll(socialService.getDataForDisplay(loggedInUser));
			// model.put("news",
			// socialService.getRssNewsForReader(loggedInUser));
			// logger.info("Model Data (else): " + model.toString());
			return new ModelAndView("reader", model);
		}

	}

}
