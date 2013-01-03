package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.Weather;

public interface AccountService {
	
	public Weather updateWeatherDeliveryPreference(int weather, User user);
	
}
