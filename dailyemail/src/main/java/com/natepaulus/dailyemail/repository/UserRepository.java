package com.natepaulus.dailyemail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natepaulus.dailyemail.repository.entity.User;


/**
 * The Interface UserRepository that extends JpaRepository for using Spring Data
 * JPA to make database queries easier.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the user
	 */
	User findByEmail(String email);	
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the user
	 */
	User findById(String id);
	
}
