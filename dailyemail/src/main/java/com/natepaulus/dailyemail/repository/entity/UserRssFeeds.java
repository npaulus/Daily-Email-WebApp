package com.natepaulus.dailyemail.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "user_rss_feeds")
public class UserRssFeeds implements Serializable, Comparable<UserRssFeeds> {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;
	
	@Column(name = "feed_id", insertable = false, updatable = false)
	private Long feedId;
	
	@Column(name = "feed_name")
	private String feedName;
	
	@Column(name = "deliver")
	private int deliver;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "feed_id")
	private RssFeeds rssFeed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFeedId() {
		return feedId;
	}

	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}

	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RssFeeds getRssFeed() {
		return rssFeed;
	}

	public void setRssFeed(RssFeeds rssFeed) {
		this.rssFeed = rssFeed;
	}

	public int getDeliver() {
		return deliver;
	}

	public void setDeliver(int deliver) {
		this.deliver = deliver;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((feedName == null) ? 0 : feedName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRssFeeds other = (UserRssFeeds) obj;
		if (feedName == null) {
			if (other.feedName != null)
				return false;
		} else if (!feedName.equals(other.feedName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public int compareTo(UserRssFeeds u) {
		int feedNameCompare = feedName.compareTo(u.getFeedName());
		return feedNameCompare;
	}

	@Override
	public String toString() {
		return "UserRssFeeds{" +
				"id=" + id +
				", userId=" + userId +
				", feedId=" + feedId +
				", feedName='" + feedName + '\'' +
				", deliver=" + deliver +
				", user=" + user +
				", rssFeed=" + rssFeed +
				'}';
	}
}
