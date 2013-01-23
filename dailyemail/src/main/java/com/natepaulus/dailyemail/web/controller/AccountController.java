package com.natepaulus.dailyemail.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.natepaulus.dailyemail.repository.DeliverySchedule;
import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;

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
	public ModelAndView displayAccountPage(@ModelAttribute("user") User user, @ModelAttribute("delTimeEntry") DeliveryTimeEntryForm delTimeEntry,
			HttpSession session, HttpServletRequest request) {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		Map<String, Object> model = new HashMap<String, Object>();
		
		
		if (map != null) {
			user = accountService.calculateUserDisplayTime(user);
			
			for (DeliverySchedule ds : user.getDeliveryTimes()) {
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
			
			session.setAttribute("user", user);
			session.setAttribute("deliveryTimeEntry", delTimeEntry);
			
			model.put("user", user);
			model.put("deliveryTimeEntry", delTimeEntry);			
			
			return new ModelAndView("account", model);
		} else {
			User loggedInUser = (User) session.getAttribute("user");
			loggedInUser = accountService
					.calculateUserDisplayTime(loggedInUser);
			

			for (DeliverySchedule ds : loggedInUser.getDeliveryTimes()) {
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
			model.put("user", loggedInUser);
			model.put("deliveryTimeEntry", delTimeEntry);

			return new ModelAndView("account", model);
		}

	}

	/**
	 * Adds the news link the user just submitted to the database.
	 *
	 * @param name the name of the rss feed 
	 * @param url the url for the rss feed
	 * @param session the session
	 * @param redirect to add flashmap attributes
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/account/addNews", method = RequestMethod.POST)
	public String addNewsLink(@RequestParam String name,
			@RequestParam String url, HttpSession session,
			RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");

		user = accountService.addNewsLink(url, name, user);

		redirect.addFlashAttribute("user", user);
		return "redirect:/account";
	}

	/**
	 * Update weather delivery preference for a user
	 *
	 * @param deliver_pref the deliver_pref the user selected
	 * @param redirect to add flashmap attributes
	 * @param session the session
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/account/weather", method = RequestMethod.POST)
	public String updateWeatherDeliveryPreference(
			@RequestParam int deliver_pref, RedirectAttributes redirect,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		accountService.updateWeatherDeliveryPreference(deliver_pref, user);

		redirect.addFlashAttribute("user", user);
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
	public String updateWeatherZipCode(@RequestParam String zipCode,
			HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		accountService.updateUserZipCode(user, zipCode);
		redirect.addFlashAttribute("user", user);
		return "redirect:/account";
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
	public String setIncludedNewsInformation(
			@RequestParam(required = false) String[] news, HttpSession session,
			RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if (news == null) {
			news = new String[1];
			news[0] = "0";
		}
		user = accountService.setIncludedNewsInformation(news, user);

		redirect.addFlashAttribute("user", user);
		return "redirect:/account";
	}

	/**
	 * Delete a news link the user no longer wishes to have
	 *
	 * @param id the id of the news link
	 * @param session the session
	 * @param redirect the redirect to add flashmap attributes
	 * @return the string which is the view to display
	 */
	@RequestMapping(value = "/account/deleteNewsLink/{id}", method = RequestMethod.GET)
	public String deleteNewsLink(@PathVariable int id, HttpSession session,
			RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		user = accountService.deleteNewsLink(id, user);

		redirect.addFlashAttribute("user", user);
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
	public String updateDeliverySchedule(
			@Valid @ModelAttribute("deliveryTimeEntry") DeliveryTimeEntryForm deliveryTimeEntry,
			BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if(result.hasErrors()){
			session.setAttribute("user", user);
			model.addAttribute("user", user);			
			return "account";
		}
		
		
		user = accountService.updateDeliverySchedule(deliveryTimeEntry, user);
		
		redirect.addFlashAttribute("user", user);
		redirect.addFlashAttribute("deliveryTimeEntry", deliveryTimeEntry);
		return "redirect:/account";
	}

}
