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
public class NewsLink implements Serializable{
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	@Column(insertable=false, updatable=false)
	private Long idusers;
	
	@Column
	private String source_name;
	
	@Column
	private String url;
	
	@Column
	private int deliver;
	
	@ManyToOne
	@JoinColumn(name="idusers")
	private User user;
}
