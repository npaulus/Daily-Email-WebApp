package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.repository.Weather;

public interface WeatherService {
	
	public Weather setInitialWeatherLocation(String zipCode);
	
}
