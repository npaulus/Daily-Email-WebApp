package com.natepaulus.dailyemail.web.exceptions;

@SuppressWarnings("serial")
public class ZipCodeException extends Exception {
	/**
	 * Instantiates a new Zip Code exception.
	 *
	 * @param message the message
	 */
	public ZipCodeException (String message)
	{
		super(message);
	}
}
