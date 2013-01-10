package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;

public interface UserService {	
	
	public void addNewUser(AccountSignUp acctSignUp, Weather weather);
	
	public User login(String email, String password) throws AuthenticationException;
}
