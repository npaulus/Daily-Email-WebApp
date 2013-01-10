package com.natepaulus.dailyemail.web.domain;

public class NewsStory {
	
	private String storyTitle;
	private String storyLink;
	private String storyDesc;
	
	public NewsStory(String storyTitle, String storyLink, String storyDesc){
		this.storyTitle = storyTitle;
		this.storyLink = storyLink;
		this.storyDesc = storyDesc;
	}
	
	public String getStoryTitle() {
		return storyTitle;
	}
	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}
	public String getStoryLink() {
		return storyLink;
	}
	public void setStoryLink(String storyLink) {
		this.storyLink = storyLink;
	}
	public String getStoryDesc() {
		return storyDesc;
	}
	public void setStoryDesc(String storyDesc) {
		this.storyDesc = storyDesc;
	}
	
}
