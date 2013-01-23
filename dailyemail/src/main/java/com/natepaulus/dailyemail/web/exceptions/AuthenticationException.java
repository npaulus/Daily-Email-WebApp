package com.natepaulus.dailyemail.web.exceptions;

/**
 * The Class AuthenticationException represents an invalid login attempt.
 */
@SuppressWarnings("serial")
public class AuthenticationException extends Exception {
	
	/**
	 * Instantiates a new authentication exception.
	 *
	 * @param message the message
	 */
	public AuthenticationException (String message)
	{
		super(message);
	}
	
}
