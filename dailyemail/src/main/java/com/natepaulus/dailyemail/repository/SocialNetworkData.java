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

@SuppressWarnings("serial")
@Entity
@Table(name = "social")
public class SocialNetworkData implements Serializable {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(insertable = false, updatable = false)
	private Long idusers;
	
	@Column(name = "provider_id")
	private String providerId;
	
	@Column(name = "provider_user_id")
	private String providerUserId;
	
	@Column(name = "display_name")
	private String displayName;
	
	@Column(name = "profile_url")
	private String profileUrl;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "access_token")
	private String accessToken;
	
	@Column
	private String secret;
	
	@Column(name = "refresh_token")
	private String refreshToken;
	
	@Column(name = "expire_time")
	private Long expireTime;
	
	@ManyToOne
	@JoinColumn(name = "idusers")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdusers() {
		return idusers;
	}

	public void setIdusers(Long idusers) {
		this.idusers = idusers;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "SocialNetworkData [id=" + id + ", idusers=" + idusers
				+ ", providerId=" + providerId + ", providerUserId="
				+ providerUserId + ", displayName=" + displayName
				+ ", profileUrl=" + profileUrl + ", imageUrl=" + imageUrl
				+ ", accessToken=" + accessToken + ", secret=" + secret
				+ ", refreshToken=" + refreshToken + ", expireTime="
				+ expireTime + ", user=" + user + "]";
	}
}
