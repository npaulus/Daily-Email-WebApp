package com.natepaulus.dailyemail.web.domain;

public class WeatherCurrentConditions {

	private String latestObDateTime;
	private String weatherStation;
	private String cityState;
	private String currentTemp;
	private String humidity;
	private String curWx;
	private String windSpeed;
	private String windGust;
	private String sunRise;
	private String sunSet;
	
	public String getLatestObDateTime() {
		return latestObDateTime;
	}
	public void setLatestObDateTime(String latestObDateTime) {
		this.latestObDateTime = latestObDateTime;
	}
	public String getWeatherStation() {
		return weatherStation;
	}
	public void setWeatherStation(String weatherStation) {
		this.weatherStation = weatherStation;
	}
	public String getCityState() {
		return cityState;
	}
	public void setCityState(String cityState) {
		this.cityState = cityState;
	}
	public String getCurrentTemp() {
		return currentTemp;
	}
	public void setCurrentTemp(String currentTemp) {
		this.currentTemp = currentTemp;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getCurWx() {
		return curWx;
	}
	public void setCurWx(String curWx) {
		this.curWx = curWx;
	}
	public String getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
	public String getWindGust() {
		return windGust;
	}
	public void setWindGust(String windGust) {
		this.windGust = windGust;
	}
	public String getSunRise() {
		return sunRise;
	}
	public void setSunRise(String sunRise) {
		this.sunRise = sunRise;
	}
	public String getSunSet() {
		return sunSet;
	}
	public void setSunSet(String sunSet) {
		this.sunSet = sunSet;
	}
	@Override
	public String toString() {
		return "WeatherCurrentConditions [latestObDateTime=" + latestObDateTime
				+ ", weatherStation=" + weatherStation + ", cityState="
				+ cityState + ", currentTemp=" + currentTemp + ", humidity="
				+ humidity + ", curWx=" + curWx + ", windSpeed=" + windSpeed
				+ ", windGust=" + windGust + ", sunRise=" + sunRise
				+ ", sunSet=" + sunSet + "]";
	}
	
	
}
