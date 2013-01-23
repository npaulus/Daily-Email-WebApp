package com.natepaulus.dailyemail.web.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class WeatherForecast represents the weather forecast for a weather station based on a user's location which is set by zip code.
 */
public class WeatherForecast {
	
	/** The weather station name. */
	private String weatherStationName;
	
	/** The period forecast. */
	private Map<String, String> periodForecast;

	/**
	 * Instantiates a new weather forecast.
	 */
	public WeatherForecast(){
		this.periodForecast = new LinkedHashMap<String, String>();
	}
	
	/**
	 * Gets the weather station name.
	 *
	 * @return the weather station name
	 */
	public String getWeatherStationName() {
		return weatherStationName;
	}

	/**
	 * Sets the weather station name.
	 *
	 * @param weatherStationName the new weather station name
	 */
	public void setWeatherStationName(String weatherStationName) {
		this.weatherStationName = weatherStationName;
	}

	/**
	 * Gets the period forecast.
	 *
	 * @return the period forecast
	 */
	public Map<String, String> getPeriodForecast() {
		return periodForecast;
	}

	/**
	 * Sets the period forecast.
	 *
	 * @param periodForecast the period forecast
	 */
	public void setPeriodForecast(Map<String, String> periodForecast) {
		this.periodForecast = periodForecast;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WeatherForecast [weatherStationName=" + weatherStationName
				+ ", periodForecast=" + periodForecast + "]";
	}
	
	
}
