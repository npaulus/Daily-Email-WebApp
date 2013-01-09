package com.natepaulus.dailyemail.web.domain;

import java.util.ArrayList;
import java.util.List;

public class EmailData {
	
	private String toAddress;
	
	private String toName;
	
	private List<NewsFeed> newsFeeds;
	
	private WeatherCurrentConditions wxCurCond;
	
	private WeatherForecast weatherForecast;

	public EmailData(){
		this.weatherForecast = new WeatherForecast();
		this.wxCurCond = new WeatherCurrentConditions();
		this.newsFeeds = new ArrayList<NewsFeed>();
	}
	
	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public List<NewsFeed> getNewsFeeds() {
		return newsFeeds;
	}

	public void setNewsFeeds(List<NewsFeed> newsFeeds) {
		this.newsFeeds = newsFeeds;
	}

	public WeatherCurrentConditions getWxCurCond() {
		return wxCurCond;
	}

	public void setWxCurCond(WeatherCurrentConditions wxCurCond) {
		this.wxCurCond = wxCurCond;
	}

	public WeatherForecast getWeatherForecast() {
		return weatherForecast;
	}

	public void setWeatherForecast(WeatherForecast weatherForecast) {
		this.weatherForecast = weatherForecast;
	}
	
}
