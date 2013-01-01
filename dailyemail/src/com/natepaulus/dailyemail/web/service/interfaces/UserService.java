package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;

public interface UserService {
	
	
	
	/*public String findByEmail(String email);
	
	public String findByPassword(String password);*/
	
	public void addNewUser(AccountSignUp acctSignUp);
	
	public User login(String email, String password) throws AuthenticationException;
}
