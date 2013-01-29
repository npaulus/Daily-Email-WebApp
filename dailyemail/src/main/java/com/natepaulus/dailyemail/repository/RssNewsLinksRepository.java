package com.natepaulus.dailyemail.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.natepaulus.dailyemail.repository.entity.RssNewsLinks;

public interface RssNewsLinksRepository extends JpaRepository<RssNewsLinks, Long>{
	
	List<RssNewsLinks> findByFeedIdOrderByPubDateDesc(Long id, Pageable pageable);
	
}
