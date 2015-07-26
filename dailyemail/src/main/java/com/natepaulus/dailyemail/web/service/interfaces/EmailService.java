package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.web.domain.EmailData;

// TODO: Auto-generated Javadoc
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
	
	
	/**
	 * Generate quick view page.
	 *
	 * @param code the code
	 * @return the data
	 */
	public EmailData generateQuickView(String code);
	
	/**
	 * Retry sending failed messages.
	 */
	public void retrySendingFailedMessages();

}
