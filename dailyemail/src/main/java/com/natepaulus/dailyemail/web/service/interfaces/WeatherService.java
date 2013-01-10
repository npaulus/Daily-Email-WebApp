package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.repository.Weather;

public interface WeatherService {
	
	public Weather setInitialWeatherLocation(String zipCode);
	
	public Weather updateWeatherLocation(String zipCode, Weather weather);
}
