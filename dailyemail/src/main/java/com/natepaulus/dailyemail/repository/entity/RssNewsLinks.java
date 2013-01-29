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

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;


@SuppressWarnings("serial")
@Entity
@Table(name = "rss_newslinks")
public class RssNewsLinks implements Serializable, Comparable<RssNewsLinks> {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "feed_id", insertable = false, updatable = false)
	private Long feedId;
	
	@Column
	private String title;
	
	@Column
	private String link;
	
	@Column
	private String description;
	
	@Column
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime pubDate;
	
	@ManyToOne
	@JoinColumn(name = "feed_id")
	private RssFeeds rssFeed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFeedId() {
		return feedId;
	}

	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateTime getPubDate() {
		return pubDate;
	}

	public void setPubDate(DateTime pubDate) {
		this.pubDate = pubDate;
	}

	public RssFeeds getRssFeed() {
		return rssFeed;
	}

	public void setRssFeed(RssFeeds rssFeed) {
		this.rssFeed = rssFeed;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RssNewsLinks other = (RssNewsLinks) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (link == null) {
			if (other.link != null) {
				return false;
			}
		} else if (!link.equals(other.link)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(RssNewsLinks r) {
		int dateCompare = pubDate.compareTo(r.getPubDate());
		return dateCompare;
	}

	@Override
	public String toString() {
		return "RssNewsLinks [id=" + id + ", feedId=" + feedId + ", title="
				+ title + ", link=" + link + ", description=" + description
				+ ", pubDate=" + pubDate + ", rssFeed=" + rssFeed + "]";
	}
	
}
