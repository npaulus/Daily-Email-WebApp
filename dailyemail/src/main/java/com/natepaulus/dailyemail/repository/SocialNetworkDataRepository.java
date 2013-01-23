package com.natepaulus.dailyemail.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The Interface SocialNetworkDataRepository that extends JpaRepository for using Spring Data
 * JPA to make database queries easier.
 */
public interface SocialNetworkDataRepository extends JpaRepository<SocialNetworkData, Long> {

	/**
	 * Find by user id.
	 *
	 * @param idusers the user id
	 * @return the array list
	 */
	public ArrayList<SocialNetworkData> findByIdusers(Long idusers);
	
}
