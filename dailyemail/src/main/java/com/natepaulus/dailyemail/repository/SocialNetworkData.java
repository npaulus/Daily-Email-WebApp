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
 * The Class SocialNetworkData is used for persisting the connection data for a
 * given user to facebook or twitter.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "social")
public class SocialNetworkData implements Serializable {

	/** The id. */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The user id. */
	@Column(insertable = false, updatable = false)
	private Long idusers;

	/** The provider id. */
	@Column(name = "provider_id")
	private String providerId;

	/** The provider user id. */
	@Column(name = "provider_user_id")
	private String providerUserId;

	/** The display name. */
	@Column(name = "display_name")
	private String displayName;

	/** The profile url. */
	@Column(name = "profile_url")
	private String profileUrl;

	/** The image url. */
	@Column(name = "image_url")
	private String imageUrl;

	/** The access token. */
	@Column(name = "access_token")
	private String accessToken;

	/** The secret. */
	@Column
	private String secret;

	/** The refresh token. */
	@Column(name = "refresh_token")
	private String refreshToken;

	/** The expire time. */
	@Column(name = "expire_time")
	private Long expireTime;

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
	 * @param idusers
	 *            the new user id
	 */
	public void setIdusers(Long idusers) {
		this.idusers = idusers;
	}

	/**
	 * Gets the provider id.
	 * 
	 * @return the provider id
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * Sets the provider id.
	 * 
	 * @param providerId
	 *            the new provider id
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * Gets the provider user id.
	 * 
	 * @return the provider user id
	 */
	public String getProviderUserId() {
		return providerUserId;
	}

	/**
	 * Sets the provider user id.
	 * 
	 * @param providerUserId
	 *            the new provider user id
	 */
	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	/**
	 * Gets the display name.
	 * 
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 * 
	 * @param displayName
	 *            the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the profile url.
	 * 
	 * @return the profile url
	 */
	public String getProfileUrl() {
		return profileUrl;
	}

	/**
	 * Sets the profile url.
	 * 
	 * @param profileUrl
	 *            the new profile url
	 */
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	/**
	 * Gets the image url.
	 * 
	 * @return the image url
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Sets the image url.
	 * 
	 * @param imageUrl
	 *            the new image url
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Gets the access token.
	 * 
	 * @return the access token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the access token.
	 * 
	 * @param accessToken
	 *            the new access token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Gets the secret.
	 * 
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * Sets the secret.
	 * 
	 * @param secret
	 *            the new secret
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * Gets the refresh token.
	 * 
	 * @return the refresh token
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * Sets the refresh token.
	 * 
	 * @param refreshToken
	 *            the new refresh token
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * Gets the expire time.
	 * 
	 * @return the expire time
	 */
	public Long getExpireTime() {
		return expireTime;
	}

	/**
	 * Sets the expire time.
	 * 
	 * @param expireTime
	 *            the new expire time
	 */
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
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
