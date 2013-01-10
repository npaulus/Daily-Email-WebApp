package com.natepaulus.dailyemail.repository;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements Serializable{
	
	@Id	
	@Column(name = "idusers")
	@GeneratedValue(strategy = GenerationType.AUTO)		
	private Long id;
		
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column
	private String zipcode;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade=CascadeType.ALL)
	private Weather weather;		
	
	@OneToMany(mappedBy="user", fetch= FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval= true)
	private Set<NewsLink> newsLink;
	
	@OneToMany(mappedBy="user", fetch= FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval= true)
	private Set<DeliverySchedule> deliveryTimes;
	
	public Set<DeliverySchedule> getDeliveryTimes() {
		return deliveryTimes;
	}
	public void setDeliveryTimes(Set<DeliverySchedule> deliveryTimes) {
		this.deliveryTimes = deliveryTimes;
	}
	public Set<NewsLink> getNewsLink() {
		return newsLink;
	}
	public void setNewsLink(Set<NewsLink> newsLink) {
		this.newsLink = newsLink;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	
	public Weather getWeather() {
		return weather;
	}
	public void setWeather(Weather weather) {
		this.weather = weather;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + "]";
	}
	
	
}
