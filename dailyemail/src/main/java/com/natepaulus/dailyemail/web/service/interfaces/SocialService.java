package com.natepaulus.dailyemail.web.service.interfaces;

import java.util.Map;

import com.natepaulus.dailyemail.repository.entity.User;

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
	public Map<String, Object> getDataForDisplay(User user);


}
