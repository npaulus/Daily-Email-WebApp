package com.natepaulus.dailyemail.web.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class EmailData is the main object used to store the information that
 * goes into a user's daily email. It is based to a velocity template for
 * displaying this information.
 */
public class EmailData {

	/** The to address. */
	private String toAddress;

	/** The to name. */
	private String toName;

	/** The news feeds. */
	private List<NewsFeed> newsFeeds;

	/** The weather current conditions. */
	private WeatherCurrentConditions wxCurCond;

	/** The weather forecast. */
	private WeatherForecast weatherForecast;

	/**
	 * Instantiates a new email data and calls the constructors for supporting
	 * objects.
	 */
	public EmailData() {
		this.weatherForecast = new WeatherForecast();
		this.wxCurCond = new WeatherCurrentConditions();
		this.newsFeeds = new ArrayList<NewsFeed>();
	}

	/**
	 * Gets the to address.
	 * 
	 * @return the to address
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * Sets the to address.
	 * 
	 * @param toAddress
	 *            the new to address
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**
	 * Gets the to name.
	 * 
	 * @return the to name
	 */
	public String getToName() {
		return toName;
	}

	/**
	 * Sets the to name.
	 * 
	 * @param toName
	 *            the new to name
	 */
	public void setToName(String toName) {
		this.toName = toName;
	}

	/**
	 * Gets the news feeds.
	 * 
	 * @return the news feeds
	 */
	public List<NewsFeed> getNewsFeeds() {
		return newsFeeds;
	}

	/**
	 * Sets the news feeds.
	 * 
	 * @param newsFeeds
	 *            the new news feeds
	 */
	public void setNewsFeeds(List<NewsFeed> newsFeeds) {
		this.newsFeeds = newsFeeds;
	}

	/**
	 * Gets the weather current conditions.
	 * 
	 * @return the current weather conditions
	 */
	public WeatherCurrentConditions getWxCurCond() {
		return wxCurCond;
	}

	/**
	 * Sets the current weather conditions
	 * 
	 * @param wxCurCond
	 *            the new current weather conditions
	 */
	public void setWxCurCond(WeatherCurrentConditions wxCurCond) {
		this.wxCurCond = wxCurCond;
	}

	/**
	 * Gets the weather forecast.
	 * 
	 * @return the weather forecast
	 */
	public WeatherForecast getWeatherForecast() {
		return weatherForecast;
	}

	/**
	 * Sets the weather forecast.
	 * 
	 * @param weatherForecast
	 *            the new weather forecast
	 */
	public void setWeatherForecast(WeatherForecast weatherForecast) {
		this.weatherForecast = weatherForecast;
	}

}
