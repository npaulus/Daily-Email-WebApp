package com.natepaulus.dailyemail.web.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class NewsFeed models one RSS feed. It includes a title and list of news
 * stories for this feed.
 */
public class NewsFeed {

	/** The feed title. */
	private String feedTitle;

	/** The news stories. */
	List<NewsStory> newsStories;

	/**
	 * Instantiates a new news feed and List of news stories.
	 */
	public NewsFeed() {
		this.newsStories = new ArrayList<NewsStory>();
	}

	/**
	 * Gets the feed title.
	 * 
	 * @return the feed title
	 */
	public String getFeedTitle() {
		return feedTitle;
	}

	/**
	 * Sets the feed title.
	 * 
	 * @param feedTitle
	 *            the new feed title
	 */
	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}

	/**
	 * Gets the news stories.
	 * 
	 * @return the news stories
	 */
	public List<NewsStory> getNewsStories() {
		return newsStories;
	}

	/**
	 * Sets the news stories.
	 * 
	 * @param newsStories
	 *            the new news stories
	 */
	public void setNewsStories(List<NewsStory> newsStories) {
		this.newsStories = newsStories;
	}

}
