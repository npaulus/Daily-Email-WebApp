package com.natepaulus.dailyemail.repository.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;

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

	@OneToMany(mappedBy = "rssFeed", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RssNewsLinks> rssNewsLinks;

	@OneToMany(mappedBy = "rssFeed", fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
	private Set<UserRssFeeds> userRssFeeds;

	public int getConnectFailures() {
		return this.connectFailures;
	}

	public DateTime getDateAdded() {
		return this.dateAdded;
	}

	public Long getId() {
		return this.id;
	}

	public Set<RssNewsLinks> getRssNewsLinks() {
		return this.rssNewsLinks;
	}

	public String getUrl() {
		return this.url;
	}

	public Set<UserRssFeeds> getUserRssFeeds() {
		return this.userRssFeeds;
	}

	public boolean isDisabled() {
		return this.disabled;
	}

	public void setConnectFailures(final int connectFailures) {
		this.connectFailures = connectFailures;
	}

	public void setDateAdded(final DateTime dateAdded) {
		this.dateAdded = dateAdded;
	}

	public void setDisabled(final boolean disabled) {
		this.disabled = disabled;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setRssNewsLinks(final Set<RssNewsLinks> rssNewsLinks) {
		this.rssNewsLinks = rssNewsLinks;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUserRssFeeds(final SortedSet<UserRssFeeds> userRssFeeds) {
		this.userRssFeeds = userRssFeeds;
	}

	@Override
	public String toString() {
		return "RssFeeds [id=" + this.id + ", url=" + this.url + ", connectFailures=" + this.connectFailures + ", disabled="
				+ this.disabled + ", dateAdded=" + this.dateAdded + ", rssNewsLinks=" + this.rssNewsLinks + ", userRssFeeds="
				+ this.userRssFeeds + "]";
	}

}
