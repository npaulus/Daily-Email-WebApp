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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;


// TODO: Auto-generated Javadoc
/**
 * The Class User represents a user of the daily email web application. This is
 * used to persist the user data to the database
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements Serializable {

	/** The id. */
	@Id
	@Column(name = "idusers")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The first name. */
	@Column(name = "first_name")
	private String firstName;

	/** The last name. */
	@Column(name = "last_name")
	private String lastName;

	/** The email. */
	@Column(name = "email")
	private String email;

	/** The password. */
	@Column(name = "password")
	private String password;
 
	/** The zipcode. */
	@Column
	private String zipcode;
	
	/** The url code for quick email access. */
	@Column(name = "url_code")
	private String urlCode;

	/** The weather preferences. */
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Weather weather;

	/** The delivery times a user has set to receive their email. */
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<DeliverySchedule> deliveryTimes;

	/** The social network data the user has set for facebook and/or twitter. */
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SocialNetworkData> socialNetworkData;
	
	/** The user's rss feeds. */
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@Sort(type=SortType.NATURAL)
	private SortedSet<UserRssFeeds> userRssFeeds;
	

	/**
	 * Gets the delivery times.
	 * 
	 * @return the delivery times
	 */
	public Set<DeliverySchedule> getDeliveryTimes() {
		return deliveryTimes;
	}

	/**
	 * Sets the delivery times.
	 * 
	 * @param deliveryTimes
	 *            the new delivery times
	 */
	public void setDeliveryTimes(Set<DeliverySchedule> deliveryTimes) {
		this.deliveryTimes = deliveryTimes;
	}

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
	 * Gets the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the zipcode.
	 * 
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * Sets the zipcode.
	 * 
	 * @param zipcode
	 *            the new zipcode
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * Gets the weather preferences.
	 * 
	 * @return the weather
	 */
	public Weather getWeather() {
		return weather;
	}

	/**
	 * Sets the weather preferences.
	 * 
	 * @param weather
	 *            the new weather
	 */
	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	/**
	 * Gets the social network data.
	 * 
	 * @return the social network data
	 */
	public Set<SocialNetworkData> getSocialNetworkData() {
		return socialNetworkData;
	}

	/**
	 * Sets the social network data.
	 * 
	 * @param socialNetworkData
	 *            the new social network data
	 */
	public void setSocialNetworkData(Set<SocialNetworkData> socialNetworkData) {
		this.socialNetworkData = socialNetworkData;
	}
	
	
	/**
	 * Gets the user's rss feeds.
	 *
	 * @return the user rss feeds
	 */
	public SortedSet<UserRssFeeds> getUserRssFeeds() {
		return userRssFeeds;
	}

	/**
	 * Sets the user's rss feeds.
	 *
	 * @param userRssFeeds the new user rss feeds
	 */
	public void setUserRssFeeds(SortedSet<UserRssFeeds> userRssFeeds) {
		this.userRssFeeds = userRssFeeds;
	}
	
	
	/**
	 * Gets the url code.
	 *
	 * @return the url code
	 */
	public String getUrlCode() {
		return urlCode;
	}

	/**
	 * Sets the url code.
	 *
	 * @param urlCode the new url code
	 */
	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", password=" + password
				+ "]";
	}

}
