package com.natepaulus.dailyemail.repository.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@SuppressWarnings("serial")
@Entity
@Table(name = "rss_feeds")
public class RssFeeds implements Serializable {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String url;
	
	@Column(name = "connect_failures")
	private int connectFailures;
	
	@Column
	private boolean disabled;
	
	@Column(name = "date_added")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime dateAdded;
	
	@OneToMany(mappedBy = "rssFeed", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RssNewsLinks> rssNewsLinks;
	
	@OneToMany(mappedBy = "rssFeed", fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
	private Set<UserRssFeeds> userRssFeeds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getConnectFailures() {
		return connectFailures;
	}

	public void setConnectFailures(int connectFailures) {
		this.connectFailures = connectFailures;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public DateTime getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(DateTime dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Set<RssNewsLinks> getRssNewsLinks() {
		return rssNewsLinks;
	}

	public void setRssNewsLinks(Set<RssNewsLinks> rssNewsLinks) {
		this.rssNewsLinks = rssNewsLinks;
	}

	public Set<UserRssFeeds> getUserRssFeeds() {
		return userRssFeeds;
	}

	public void setUserRssFeeds(Set<UserRssFeeds> userRssFeeds) {
		this.userRssFeeds = userRssFeeds;
	}

	@Override
	public String toString() {
		return "RssFeeds [id=" + id + ", url=" + url + ", connectFailures="
				+ connectFailures + ", disabled=" + disabled + ", dateAdded="
				+ dateAdded + ", rssNewsLinks=" + rssNewsLinks
				+ ", userRssFeeds=" + userRssFeeds + "]";
	}
	
	
	
}
