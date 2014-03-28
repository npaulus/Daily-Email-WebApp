package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.repository.entity.User;
import com.natepaulus.dailyemail.repository.entity.Weather;
import com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm;
import com.natepaulus.dailyemail.web.exceptions.RssFeedException;
import com.natepaulus.dailyemail.web.exceptions.ZipCodeException;

// TODO: Auto-generated Javadoc
/**
 * The Interface AccountService.
 */
public interface AccountService {

	/**
	 * Adds the news link.
	 *
	 * @param url the url for the RSS feed
	 * @param name the name the user entered for the feed
	 * @param user the user
	 * @return the user
	 * @throws RssFeedException the rss feed exception
	 */
	public long addNewsLink(String url, String name, long userId) throws RssFeedException;

	/**
	 * Calculate the proper time to display to the user based on their time zone.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User calculateUserDisplayTime(User user);

	/**
	 * Delete RSS feed from the user's account.
	 *
	 * @param id the id
	 * @param user the user
	 * @return the user
	 */
	public long deleteNewsLink(int id, long userId);

	/**
	 * Delete url code.
	 *
	 * @param user the user
	 * @return the user
	 */
	public long deleteUrlCode(long userId);

	/**
	 * Find user by id.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User findUserById(long userId);

	/**
	 * Generate url code.
	 *
	 * @param user the user
	 * @return the user
	 */
	public long generateUrlCode(long userId);

	/**
	 * Sets the included news information for the users daily email.
	 *
	 * @param news the rss feeds to include in their daily email
	 * @param user the user
	 * @return the user
	 */
	public long setIncludedNewsInformation(String[] news, long userId);

	/**
	 * Update delivery times, timezone, and whether or not to disable weekend or weekday delivery.
	 *
	 * @param delEntryTime the delivery entry time object that backs the delivery time entry form on the account page for validation
	 * @param user the user
	 * @return the user
	 */
	public long updateDeliverySchedule(DeliveryTimeEntryForm delEntryTime, long userId);

	/**
	 * Update user zip code.
	 *
	 * @param user the user
	 * @param zipCode the zip code
	 * @return the user
	 * @throws ZipCodeException the zip code exception
	 */
	public long updateUserZipCode(long user, String zipCode) throws ZipCodeException;

	/**
	 * Update weather delivery preference.
	 *
	 * @param weather the weather preference selected (0,1,2, or 3)
	 * @param user the user
	 * @return the weather object that represents the delivery preference and location data
	 */
	public Weather updateWeatherDeliveryPreference(int weather, long userId);

}
