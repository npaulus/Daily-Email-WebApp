package com.natepaulus.dailyemail.web.domain;

import java.util.ArrayList;
import java.util.List;

public class NewsFeed {
	
	private String feedTitle;
	
	List<NewsStory> newsStories;

	public NewsFeed(){
		this.newsStories = new ArrayList<NewsStory>();
	}
	
	public String getFeedTitle() {
		return feedTitle;
	}

	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}

	public List<NewsStory> getNewsStories() {
		return newsStories;
	}

	public void setNewsStories(List<NewsStory> newsStories) {
		this.newsStories = newsStories;
	}
	
}
