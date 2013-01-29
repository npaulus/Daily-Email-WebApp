package com.natepaulus.dailyemail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natepaulus.dailyemail.repository.entity.UserRssFeeds;

public interface UserRssFeedsRepository extends JpaRepository<UserRssFeeds, Long>{
	
	UserRssFeeds findByUserIdAndFeedId(Long uid, Long fid);
}
