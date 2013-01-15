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
@Table(name = "news_links")
public class NewsLink implements Serializable {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(insertable = false, updatable = false)
	private Long idusers;

	@Column
	private String source_name;

	@Column
	private String url;

	@Column
	private int deliver;

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

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDeliver() {
		return deliver;
	}

	public void setDeliver(int deliver) {
		this.deliver = deliver;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
