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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@SuppressWarnings("serial")
@Entity
@Table(name = "delivery_schedule")
public class DeliverySchedule implements Serializable {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	@Column(insertable=false, updatable=false)
	private Long idusers;
	
	@Column
	private int disabled;
	
	@Column
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime time;
	
	@Column
	private int delivery_day;
	
	@Column
	private String tz;
	
	@Transient
	private String displayTime;
	
	@ManyToOne
	@JoinColumn(name="idusers")
	private User user;

	public String getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public int getDelivery_day() {
		return delivery_day;
	}

	public void setDelivery_day(int delivery_day) {
		this.delivery_day = delivery_day;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getIdusers() {
		return idusers;
	}

	public void setIdusers(Long idusers) {
		this.idusers = idusers;
	}
	
	
}
