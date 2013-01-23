package com.natepaulus.dailyemail.repository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The Class NewsLink is an entity class that represents the news_link table in
 * the database. It is used for tracking the RSS feed a user has entered in the
 * database.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "news_links")
public class NewsLink implements Serializable {

	/** The id. */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The user id. */
	@Column(insertable = false, updatable = false)
	private Long idusers;

	/** The user assigned RSS feed name. */
	@Column
	private String source_name;

	/** The url. */
	@Column
	private String url;

	/**
	 * Used to know if newslink should be included in user email. 0 means
	 * include and 1 means exclude from email.
	 */
	@Column
	private int deliver;

	/** The user. */
	@ManyToOne
	@JoinColumn(name = "idusers")
	private User user;

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public Long getIdusers() {
		return idusers;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param idusers the new user id
	 */
	public void setIdusers(Long idusers) {
		this.idusers = idusers;
	}

	/**
	 * Gets the user assigned RSS feed name.
	 * 
	 * @return the user assigned RSS feed name.
	 */
	public String getSource_name() {
		return source_name;
	}

	/**
	 * Sets the user assigned RSS feed name.
	 * 
	 * @param source_name the user assigned RSS feed name.
	 */
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the delivery preference.
	 * 
	 * @return the delivery preference
	 */
	public int getDeliver() {
		return deliver;
	}

	/**
	 * Sets the delivery preference.
	 * 
	 * @param deliver
	 *            the new delivery preference
	 */
	public void setDeliver(int deliver) {
		this.deliver = deliver;
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
