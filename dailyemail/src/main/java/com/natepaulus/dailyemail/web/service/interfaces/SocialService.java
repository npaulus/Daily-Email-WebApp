package com.natepaulus.dailyemail.web.service.interfaces;

import java.util.ArrayList;
import java.util.Map;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.web.domain.NewsFeed;

/**
 * The Interface SocialService.
 */
public interface SocialService {

	/**
	 * Connect to facebook is the first part of establishing a connection
	 * between a user of this app and facebook.
	 * 
	 * @return the string
	 */
	public String connectToFacebook();

	/**
	 * This is the callback from facebook that sends the completed authorization
	 * code to this app to sync up the two accounts.
	 * 
	 * @param authorizationCode
	 *            the authorization code
	 * @param user
	 *            the user
	 * @return the user
	 */
	public User saveFacebookInformation(String authorizationCode, User user);

	/**
	 * This connects to facebook or any social media provider and retrieves the
	 * user's current news feed for display
	 * 
	 * @param user
	 *            the user
	 * @return the data for display
	 */
	public Map getDataForDisplay(User user);

	/**
	 * Gets news items from the users RSS feeds and returns them for display
	 * 
	 * @param user
	 *            the user
	 * @return the rss news for the user
	 */
	public ArrayList<NewsFeed> getRssNewsForReader(User user);

}
