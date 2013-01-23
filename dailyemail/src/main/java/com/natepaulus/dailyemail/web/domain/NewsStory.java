package com.natepaulus.dailyemail.web.domain;

/**
 * The Class NewsStory represents one news story in a news feed.
 */
public class NewsStory {
	
	/** The story title. */
	private String storyTitle;
	
	/** The story link. */
	private String storyLink;
	
	/** The story description. */
	private String storyDesc;
	
	/**
	 * Instantiates a new news story.
	 *
	 * @param storyTitle the story title
	 * @param storyLink the story link
	 * @param storyDesc the story description
	 */
	public NewsStory(String storyTitle, String storyLink, String storyDesc){
		this.storyTitle = storyTitle;
		this.storyLink = storyLink;
		this.storyDesc = storyDesc;
	}
	
	/**
	 * Gets the story title.
	 *
	 * @return the story title
	 */
	public String getStoryTitle() {
		return storyTitle;
	}
	
	/**
	 * Sets the story title.
	 *
	 * @param storyTitle the new story title
	 */
	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}
	
	/**
	 * Gets the story link.
	 *
	 * @return the story link
	 */
	public String getStoryLink() {
		return storyLink;
	}
	
	/**
	 * Sets the story link.
	 *
	 * @param storyLink the new story link
	 */
	public void setStoryLink(String storyLink) {
		this.storyLink = storyLink;
	}
	
	/**
	 * Gets the story description.
	 *
	 * @return the story description
	 */
	public String getStoryDesc() {
		return storyDesc;
	}
	
	/**
	 * Sets the story description.
	 *
	 * @param storyDesc the new story description
	 */
	public void setStoryDesc(String storyDesc) {
		this.storyDesc = storyDesc;
	}
	
}
