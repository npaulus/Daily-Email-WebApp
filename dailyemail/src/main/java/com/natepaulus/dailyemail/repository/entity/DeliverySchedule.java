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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

/**
 * The Class DeliverySchedule is used for capturing a user's desired
 * delivery scheduled for a particular day and if it is disabled or not.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "delivery_schedule")
public class DeliverySchedule implements Serializable {

	/** The id. */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The user id. */
	@Column(insertable = false, updatable = false)
	private Long idusers;

	/** The disabled flagged for this delivery schedule. */
	@Column
	private boolean disabled;

	/** The delivery time. */
	@Column
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	private LocalTime time;

	/** The delivery day weekend (1) or weekday (0). */
	@Column(name = "delivery_day")
	private int deliveryDay;

	/** The time zone value. */
	@Column
	private String tz;

	/** The display time. */
	@Transient
	private String displayTime;

	/** The user. */
	@ManyToOne
	@JoinColumn(name = "idusers")
	private User user;

	/**
	 * Gets the display time.
	 * 
	 * @return the display time
	 */
	public String getDisplayTime() {
		return displayTime;
	}

	/**
	 * Sets the display time.
	 * 
	 * @param displayTime
	 *            the new display time
	 */
	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}

	/**
	 * Gets the time zone.
	 * 
	 * @return the tz
	 */
	public String getTz() {
		return tz;
	}

	/**
	 * Sets the time zone.
	 * 
	 * @param tz
	 *            the new tz
	 */
	public void setTz(String tz) {
		this.tz = tz;
	}

	/**
	 * Gets the delivery day.
	 * 
	 * @return the delivery day
	 */
	public int getDeliveryDay() {
		return deliveryDay;
	}

	/**
	 * Sets the delivery day.
	 * 
	 * @param deliveryDay
	 *            the new delivery day
	 */
	public void setDeliveryDay(int deliveryDay) {
		this.deliveryDay = deliveryDay;
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
	 * Checks if is disabled.
	 * 
	 * @return true, if is disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Sets the disabled.
	 * 
	 * @param disabled
	 *            the new disabled
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * Gets the time.
	 * 
	 * @return the time
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * Sets the time.
	 * 
	 * @param time
	 *            the new time
	 */
	public void setTime(LocalTime time) {
		this.time = time;
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

	/**
	 * Gets the user id.
	 * 
	 * @return the idusers
	 */
	public Long getIdusers() {
		return idusers;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param idusers
	 *            the new idusers
	 */
	public void setIdusers(Long idusers) {
		this.idusers = idusers;
	}

}
