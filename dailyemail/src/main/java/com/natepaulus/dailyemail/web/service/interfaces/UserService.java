package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;


/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Adds the new user to the application
	 * 
	 * @param acctSignUp
	 *            the account sign up object that backs the register form on the
	 *            register page for easy validation.
	 * @param weather
	 *            the weather information needed to get forecast and current
	 *            conditions for the user's email.
	 * @return true, if successful
	 */
	public boolean addNewUser(AccountSignUp acctSignUp, Weather weather);

	/**
	 * Logs a user into the application.
	 * 
	 * @param email
	 *            the email of the user
	 * @param password
	 *            the password of the user
	 * @return the user
	 * @throws AuthenticationException
	 *             thrown when a login is invalid
	 */
	public User login(String email, String password)
			throws AuthenticationException;
}
