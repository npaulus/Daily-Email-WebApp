package com.natepaulus.dailyemail.web.domain;

import java.util.HashMap;
import java.util.Map;

public class WeatherForecast {
	
	private String weatherStationName;
	
	private Map<String, String> periodForecast;

	public WeatherForecast(){
		this.periodForecast = new HashMap<String, String>();
	}
	
	public String getWeatherStationName() {
		return weatherStationName;
	}

	public void setWeatherStationName(String weatherStationName) {
		this.weatherStationName = weatherStationName;
	}

	public Map<String, String> getPeriodForecast() {
		return periodForecast;
	}

	public void setPeriodForecast(Map<String, String> periodForecast) {
		this.periodForecast = periodForecast;
	}

	@Override
	public String toString() {
		return "WeatherForecast [weatherStationName=" + weatherStationName
				+ ", periodForecast=" + periodForecast + "]";
	}
	
	
}
