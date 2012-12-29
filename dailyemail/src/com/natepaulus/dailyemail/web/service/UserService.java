package com.natepaulus.dailyemail.web.service;

import com.natepaulus.dailyemail.web.domain.AccountSignUp;

public interface UserService {
	
	
	
	/*public String findByEmail(String email);
	
	public String findByPassword(String password);*/
	
	public void addNewUser(AccountSignUp acctSignUp);
}
