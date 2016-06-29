package com.natepaulus.dailyemail.web.domain;


/**
 * The Class WeatherCurrentConditions represents the current weather conditions
 * for a specific weather station the user has set.
 */
public class WeatherCurrentConditions {

	/** The latest observation date time. */
	private String latestObDateTime;

	/** The weather station. */
	private String weatherStation;

	/** The city, state. */
	private String cityState;

	/** The current temperature. */
	private String currentTemp;

	/** The humidity. */
	private String humidity;
	
	/** The dew point. */
	private String dewPoint;

	/** The current weather description. (Cloudy, Sunny, etc) */
	private String curWx;

	/** The wind speed speed in MPH. */
	private String windSpeed;

	/** The wind gust speed in MPH. */
	private String windGust;

	/** The wind direction in degrees of true north */
	private String windDirection;

	/** The visibility in statue miles */
	private String visibility;

	/** The sun rise time. */
	private String sunRise;

	/** The sun set time. */
	private String sunSet;

	/**
	 * Gets the latest ob date time.
	 * 
	 * @return the latest ob date time
	 */
	public String getLatestObDateTime() {
		return latestObDateTime;
	}

	/**
	 * Sets the latest ob date time.
	 * 
	 * @param latestObDateTime
	 *            the new latest ob date time
	 */
	public void setLatestObDateTime(String latestObDateTime) {
		this.latestObDateTime = latestObDateTime;
	}

	/**
	 * Gets the weather station.
	 * 
	 * @return the weather station
	 */
	public String getWeatherStation() {
		return weatherStation;
	}

	/**
	 * Sets the weather station.
	 * 
	 * @param weatherStation
	 *            the new weather station
	 */
	public void setWeatherStation(String weatherStation) {
		this.weatherStation = weatherStation;
	}

	/**
	 * Gets the city state.
	 * 
	 * @return the city state
	 */
	public String getCityState() {
		return cityState;
	}

	/**
	 * Sets the city state.
	 * 
	 * @param cityState
	 *            the new city state
	 */
	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	/**
	 * Gets the current temp.
	 * 
	 * @return the current temp
	 */
	public String getCurrentTemp() {
		return currentTemp;
	}

	/**
	 * Sets the current temp.
	 * 
	 * @param currentTemp
	 *            the new current temp
	 */
	public void setCurrentTemp(String currentTemp) {
		this.currentTemp = currentTemp;
	}

	/**
	 * Gets the humidity.
	 * 
	 * @return the humidity
	 */
	public String getHumidity() {
		return humidity;
	}

	/**
	 * Sets the humidity.
	 * 
	 * @param humidity
	 *            the new humidity
	 */
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getDewPoint() {
		return dewPoint;
	}

	public void setDewPoint(String dewPoint) {
		this.dewPoint = dewPoint;
	}

	/**
	 * Gets the cur wx.
	 * 
	 * @return the cur wx
	 */
	public String getCurWx() {
		return curWx;
	}

	/**
	 * Sets the cur wx.
	 * 
	 * @param curWx
	 *            the new cur wx
	 */
	public void setCurWx(String curWx) {
		this.curWx = curWx;
	}

	/**
	 * Gets the wind speed.
	 * 
	 * @return the wind speed
	 */
	public String getWindSpeed() {
		return windSpeed;
	}

	/**
	 * Sets the wind speed.
	 * 
	 * @param windSpeed
	 *            the new wind speed
	 */
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	/**
	 * Gets the wind gust.
	 * 
	 * @return the wind gust
	 */
	public String getWindGust() {
		return windGust;
	}

	/**
	 * Sets the wind gust.
	 * 
	 * @param windGust
	 *            the new wind gust
	 */
	public void setWindGust(String windGust) {
		this.windGust = windGust;
	}

	/**
	 * Gets the wind direction.
	 *
	 * @return windDirection the wind direction in degrees
     */
	public String getWindDirection() {
		return windDirection;
	}

	/**
	 * Sets the wind direction.
	 *
	 * @param windDirection the new wind direction in degrees
     */
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	/**
	 * Gets the visibility in statue miles.
	 *
	 * @return visibility in statue miles
     */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * Sets the visibility in statue miles.
	 *
	 * @param visibility new visiblity in statue miles
     */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	/**
	 * Gets the sun rise.
	 * 
	 * @return the sun rise
	 */
	public String getSunRise() {
		return sunRise;
	}

	/**
	 * Sets the sun rise.
	 * 
	 * @param sunRise
	 *            the new sun rise
	 */
	public void setSunRise(String sunRise) {
		this.sunRise = sunRise;
	}

	/**
	 * Gets the sun set.
	 * 
	 * @return the sun set
	 */
	public String getSunSet() {
		return sunSet;
	}

	/**
	 * Sets the sun set.
	 * 
	 * @param sunSet
	 *            the new sun set
	 */
	public void setSunSet(String sunSet) {
		this.sunSet = sunSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
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
