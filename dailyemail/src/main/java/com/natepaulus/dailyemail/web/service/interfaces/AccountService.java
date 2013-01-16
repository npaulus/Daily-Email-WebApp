package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm;

public interface AccountService {
	
	public Weather updateWeatherDeliveryPreference(int weather, User user);
	
	public User updateUserZipCode(User user, String zipCode);
	
	public User addNewsLink(String url, String name, User user);
	
	public User setIncludedNewsInformation(String[] news, User user);
	
	public User deleteNewsLink(int id, User user);
	
	public User updateDeliverySchedule(DeliveryTimeEntryForm delEntryTime, User user);
	
	public User calculateUserDisplayTime(User user);
		
}
