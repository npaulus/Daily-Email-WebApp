package com.natepaulus.dailyemail.web.exceptions;

@SuppressWarnings("serial")
public class RssFeedException extends Exception {
	
	/**
	 * Instantiates a new RSS Feed exception.
	 *
	 * @param message the message
	 */
	public RssFeedException (String message)
	{
		super(message);
	}
}
