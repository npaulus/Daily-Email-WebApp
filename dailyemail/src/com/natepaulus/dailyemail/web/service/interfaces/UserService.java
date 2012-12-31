package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.web.domain.AccountSignUp;

public interface UserService {
	
	
	
	/*public String findByEmail(String email);
	
	public String findByPassword(String password);*/
	
	public void addNewUser(AccountSignUp acctSignUp);
}
