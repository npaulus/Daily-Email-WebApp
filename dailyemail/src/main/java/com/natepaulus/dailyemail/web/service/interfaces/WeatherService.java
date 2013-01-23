package com.natepaulus.dailyemail.web.service.interfaces;

import com.natepaulus.dailyemail.repository.Weather;

/**
 * The Interface WeatherService.
 */
public interface WeatherService {
	
	/**
	 * Sets the initial weather location data for a user when they first register with the application.
	 *
	 * @param zipCode the zip code
	 * @return the weather location information
	 */
	public Weather setInitialWeatherLocation(String zipCode);
	
	/**
	 * Update weather location for a user based on a new zip code
	 *
	 * @param zipCode the zip code
	 * @param weather the weather location information
	 * @return the weather location information
	 */
	public Weather updateWeatherLocation(String zipCode, Weather weather);
}
