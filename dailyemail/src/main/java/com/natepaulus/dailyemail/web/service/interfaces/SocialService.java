package com.natepaulus.dailyemail.web.service.interfaces;

import java.util.ArrayList;
import java.util.Map;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.web.domain.NewsFeed;

public interface SocialService {

	public String connectToFacebook();
	
	public User saveFacebookInformation(String authorizationCode, User user);
	
	public Map getDataForDisplay(User user);

	public ArrayList<NewsFeed> getRssNewsForReader(User user);
	
}
