package com.natepaulus.dailyemail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natepaulus.dailyemail.repository.entity.RssFeeds;

public interface RssFeedsRepository extends JpaRepository<RssFeeds, Long>{

	RssFeeds findByUrl(String url);
	
	List<RssFeeds> findByDisabled(boolean disabled);
	
	
	
}
