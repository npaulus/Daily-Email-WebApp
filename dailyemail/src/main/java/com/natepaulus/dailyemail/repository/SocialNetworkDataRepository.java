package com.natepaulus.dailyemail.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialNetworkDataRepository extends JpaRepository<SocialNetworkData, Long> {

	public ArrayList<SocialNetworkData> findByIdusers(Long idusers);
	
}
