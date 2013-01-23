package com.natepaulus.dailyemail.web.service.interfaces;

/**
 * The Interface EmailService.
 */
public interface EmailService {

	/**
	 * This method uses the spring task scheduler functionality to run once
	 * every minute and gets all the users that would like their email delivered
	 * at the current time in one minute increments.
	 */
	public void retrieveUserListForEmails();

}
