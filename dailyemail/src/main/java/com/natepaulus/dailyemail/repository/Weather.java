package com.natepaulus.dailyemail.repository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * The Class Weather represents the location data and the user's preference for
 * receiving weather information.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "weather")
public class Weather implements Serializable {

	/** The user id. */
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idusers", unique = true, nullable = false)
	private Long idusers;

	/**
	 * The deliver_pref. 0 = no weather in email 1 = Current Conditions in email
	 * 2 = Weather Forecast in email 3 = Both Current Conditions and Weather
	 * forecast in email
	 */
	@Column
	private int deliver_pref;

	/** The location_name. */
	@Column
	private String location_name;

	/** The latitude. */
	@Column
	private String latitude;

	/** The longitude. */
	@Column
	private String longitude;

	/** The user. */
	@OneToOne
	@PrimaryKeyJoinColumn
	private User user;

	/**
	 * Gets the idusers.
	 * 
	 * @return the idusers
	 */
	public Long getIdusers() {
		return idusers;
	}

	/**
	 * Sets the idusers.
	 * 
	 * @param idusers
	 *            the new idusers
	 */
	public void setIdusers(Long idusers) {
		this.idusers = idusers;
	}

	/**
	 * Gets the deliver_pref.
	 * 
	 * @return the deliver_pref
	 */
	public int getDeliver_pref() {
		return deliver_pref;
	}

	/**
	 * Sets the deliver_pref.
	 * 
	 * @param deliver_pref
	 *            the new deliver_pref
	 */
	public void setDeliver_pref(int deliver_pref) {
		this.deliver_pref = deliver_pref;
	}

	/**
	 * Gets the location_name.
	 * 
	 * @return the location_name
	 */
	public String getLocation_name() {
		return location_name;
	}

	/**
	 * Sets the location_name.
	 * 
	 * @param location_name
	 *            the new location_name
	 */
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	/**
	 * Gets the latitude.
	 * 
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 * 
	 * @param latitude
	 *            the new latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 * 
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 * 
	 * @param longitude
	 *            the new longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
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
