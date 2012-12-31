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

@SuppressWarnings("serial")
@Entity
@Table(name = "weather")
public class Weather implements Serializable {
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "idusers", unique = true, nullable = false)
	private Long idusers;
	
	@Column
	private int deliver_pref;
	
	@Column
	private String location_name;
	
	@Column
	private String latitude;
	
	@Column
	private String longitude;	
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private User user;
		
	public Long getIdusers() {
		return idusers;
	}
	public void setIdusers(Long idusers) {
		this.idusers = idusers;
	}
	
	public int getDeliver_pref() {
		return deliver_pref;
	}
	public void setDeliver_pref(int deliver_pref) {
		this.deliver_pref = deliver_pref;
	}
	
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}		
	
}
