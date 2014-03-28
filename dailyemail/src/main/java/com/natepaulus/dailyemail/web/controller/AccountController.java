package com.natepaulus.dailyemail.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.natepaulus.dailyemail.repository.entity.DeliverySchedule;
import com.natepaulus.dailyemail.repository.entity.User;
import com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm;
import com.natepaulus.dailyemail.web.exceptions.RssFeedException;
import com.natepaulus.dailyemail.web.exceptions.ZipCodeException;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountController.
 *
 * @author Nate
 */
@Controller
public class AccountController {

	/** The account service. */
	@Autowired
	AccountService accountService;

	private final Logger logger = LoggerFactory.getLogger(AccountController.class);

	/**
	 * Adds the news link to the database.
	 *
	 * @param name the name
	 * @param url the url to add
	 * @param session the session
	 * @param redirect the redirect
	 * @return the string which is the view to display
	 * @throws RssFeedException the rss feed exception (feed url invalid or user already has this link in their list)
	 */
	@RequestMapping(value = "/account/addNews", method = RequestMethod.POST)
	public String addNewsLink(@RequestParam final String name, @RequestParam final String url, final HttpSession session,
			final RedirectAttributes redirect) throws RssFeedException {
		final long userId = (long) session.getAttribute("user");

		this.accountService.addNewsLink(url, name, userId);

		redirect.addFlashAttribute("confirmSave", "rssLinkSaved");
		redirect.addFlashAttribute("user", userId);
		return "redirect:/account";
	}

	/**
	 * Delete a news link the user no longer wishes to have.
	 *
	 * @param id the id of the news link
	 * @param session the session
	 * @param redirect the redirect to add flashmap attributes
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/account/deleteNewsLink/{id}", method = RequestMethod.GET)
	public String deleteNewsLink(@PathVariable final int id, final HttpSession session, final RedirectAttributes redirect) {
		final long userId = (long) session.getAttribute("user");
		this.accountService.deleteNewsLink(id, userId);

		redirect.addFlashAttribute("user", userId);
		redirect.addFlashAttribute("confirmSave", "rssLinkDeleted");
		return "redirect:/account";
	}

	@RequestMapping(value = "/account/deleteUrlCode", method = RequestMethod.POST)
	public String deleteUrlCode(final HttpSession session, final RedirectAttributes redirect) {
		final long userId = (long) session.getAttribute("user");
		this.accountService.deleteUrlCode(userId);
		redirect.addFlashAttribute("user", userId);
		redirect.addFlashAttribute("confirmSave", "urlDeleted");
		return "redirect:/account";
	}

	/**
	 * Display account page.
	 *
	 * @param user the user
	 * @param delTimeEntry the del time entry
	 * @param session the session
	 * @param request the request
	 * @return the model and view
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView displayAccountPage(@ModelAttribute("delTimeEntry") final DeliveryTimeEntryForm delTimeEntry,
			final HttpSession session, final HttpServletRequest request) {
		final Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		final Map<String, Object> model = new HashMap<String, Object>();

		final long userId = (long) session.getAttribute("user");

		if (map != null) {
			User user = this.accountService.findUserById(userId);
			user = this.accountService.calculateUserDisplayTime(user);
			final String confirmSave = (String) map.get("confirmSave");

			for (final DeliverySchedule ds : user.getDeliveryTimes()) {
				if (ds.getDeliveryDay() == 0) {
					delTimeEntry.setTimezone(ds.getTz());
					delTimeEntry.setWeekDayDisabled(ds.isDisabled());
					delTimeEntry.setWeekDayTime(ds.getDisplayTime());
				} else {
					delTimeEntry.setTimezone(ds.getTz());
					delTimeEntry.setWeekEndDisabled(ds.isDisabled());
					delTimeEntry.setWeekEndTime(ds.getDisplayTime());
				}
			}

			session.setAttribute("user", userId);
			session.setAttribute("deliveryTimeEntry", delTimeEntry);

			model.put("pageTitle", "Daily Email - Account Page");
			model.put("confirmSave", confirmSave);
			model.put("weather", user.getWeather());
			model.put("zipcode", user.getZipcode());
			model.put("userRssFeeds", user.getUserRssFeeds());
			model.put("urlCode", user.getUrlCode());
			model.put("deliveryTimeEntry", delTimeEntry);

			return new ModelAndView("account", model);
		} else {
			final long loggedInUserId = (long) session.getAttribute("user");
			User loggedInUser = this.accountService.findUserById(loggedInUserId);
			loggedInUser = this.accountService.calculateUserDisplayTime(loggedInUser);

			for (final DeliverySchedule ds : loggedInUser.getDeliveryTimes()) {
				if (ds.getDeliveryDay() == 0) {
					delTimeEntry.setTimezone(ds.getTz());
					delTimeEntry.setWeekDayDisabled(ds.isDisabled());
					delTimeEntry.setWeekDayTime(ds.getDisplayTime());
				} else {
					delTimeEntry.setTimezone(ds.getTz());
					delTimeEntry.setWeekEndDisabled(ds.isDisabled());
					delTimeEntry.setWeekEndTime(ds.getDisplayTime());
				}
			}
			model.put("pageTitle", "Daily Email - Account Page");
			model.put("user", loggedInUser);
			model.put("weather", loggedInUser.getWeather());
			model.put("zipcode", loggedInUser.getZipcode());
			model.put("userRssFeeds", loggedInUser.getUserRssFeeds());
			model.put("urlCode", loggedInUser.getUrlCode());
			model.put("deliveryTimeEntry", delTimeEntry);

			return new ModelAndView("account", model);
		}

	}

	@RequestMapping(value = "/account/generateUrlCode", method = RequestMethod.POST)
	public String generateUrlCode(final HttpSession session, final RedirectAttributes redirect) {
		final long userId = (long) session.getAttribute("user");
		this.accountService.generateUrlCode(userId);
		redirect.addFlashAttribute("user", userId);
		redirect.addFlashAttribute("confirmSave", "urlGenerated");
		return "redirect:/account";
	}

	/**
	 * Handle rss feed exception.
	 *
	 * @param ex the exception
	 * @param request the http request
	 * @return the model and view
	 */
	@ExceptionHandler(RssFeedException.class)
	public ModelAndView handleRssFeedException(final RssFeedException ex, final HttpServletRequest request) {

		final HttpSession session = request.getSession();
		final long userId = (long) session.getAttribute("user");
		final DeliveryTimeEntryForm delTimeEntry = (DeliveryTimeEntryForm) session.getAttribute("deliveryTimeEntry");

		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("pageTitle", "Daily Email - Account Page");
		model.put("user", userId);
		model.put("deliveryTimeEntry", delTimeEntry);
		model.put("rssException", ex);

		return new ModelAndView("account", model);
	}

	/**
	 * Handle zip code exception.
	 *
	 * @param ex the ex
	 * @param request the http request
	 * @return the model and view
	 */
	@ExceptionHandler(ZipCodeException.class)
	public ModelAndView handleZipCodeException(final ZipCodeException ex, final HttpServletRequest request) {
		final HttpSession session = request.getSession();
		final long userId = (long) session.getAttribute("user");
		final DeliveryTimeEntryForm delTimeEntry = (DeliveryTimeEntryForm) session.getAttribute("deliveryTimeEntry");

		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("pageTitle", "Daily Email - Account Page");
		model.put("user", userId);
		model.put("deliveryTimeEntry", delTimeEntry);
		model.put("zipCodeException", ex);

		return new ModelAndView("account", model);
	}

	/**
	 * Sets the included news information for the daily email.
	 *
	 * @param news the news feeds the user selected
	 * @param session the session
	 * @param redirect the redirect to add flashmap attributes
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/account/news", method = RequestMethod.POST)
	public String setIncludedNewsInformation(@RequestParam(required = false) String[] news, final HttpSession session,
			final RedirectAttributes redirect) {
		final long userId = (long) session.getAttribute("user");
		if (news == null) {
			news = new String[1];
			news[0] = "0";
		}
		this.accountService.setIncludedNewsInformation(news, userId);

		redirect.addFlashAttribute("user", userId);
		redirect.addFlashAttribute("confirmSave", "newsDeliverySaved");
		return "redirect:/account";
	}

	/**
	 * Update daily email delivery schedule.
	 *
	 * @param deliveryTimeEntry the delivery time the user entered in HH:MM A format
	 * @param result the result of the web binding of the form
	 * @param redirect the redirect to add flashmap attributes
	 * @param session the session
	 * @param model the model
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/account/delivery", method = RequestMethod.POST)
	public String updateDeliverySchedule(@Valid @ModelAttribute("deliveryTimeEntry") final DeliveryTimeEntryForm deliveryTimeEntry,
			final BindingResult result, final RedirectAttributes redirect, final HttpSession session, final Model model) {
		long userId = (long) session.getAttribute("user");
		if (result.hasErrors()) {
			session.setAttribute("user", userId);
			model.addAttribute("user", userId);
			return "account";
		}

		userId = this.accountService.updateDeliverySchedule(deliveryTimeEntry, userId);

		redirect.addFlashAttribute("user", userId);
		redirect.addFlashAttribute("deliveryTimeEntry", deliveryTimeEntry);
		redirect.addFlashAttribute("confirmSave", "deliveryTimesSaved");
		return "redirect:/account";
	}

	/**
	 * Update weather delivery preference for a user.
	 *
	 * @param deliver_pref the deliver_pref the user selected
	 * @param redirect to add flashmap attributes
	 * @param session the session
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/account/weather", method = RequestMethod.POST)
	public String updateWeatherDeliveryPreference(@RequestParam final int deliver_pref, final RedirectAttributes redirect,
			final HttpSession session) {
		final long userId = (long) session.getAttribute("user");

		this.accountService.updateWeatherDeliveryPreference(deliver_pref, userId);

		redirect.addFlashAttribute("user", userId);
		redirect.addFlashAttribute("confirmSave", "weatherDeliveryPreferenceSaved");
		return "redirect:/account";
	}

	/**
	 * Update weather zip code for a user.
	 *
	 * @param zipCode the zip code
	 * @param session the session
	 * @param redirect the redirect to add flashmap attributes
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/account/changezip", method = RequestMethod.POST)
	public String
	updateWeatherZipCode(@RequestParam final String zipCode, final HttpSession session, final RedirectAttributes redirect)
			throws ZipCodeException {
		final long userId = (long) session.getAttribute("user");
		this.accountService.updateUserZipCode(userId, zipCode);
		redirect.addFlashAttribute("user", userId);
		redirect.addFlashAttribute("confirmSave", "zipCodeSaved");
		return "redirect:/account";
	}

}
