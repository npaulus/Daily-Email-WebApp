package com.natepaulus.dailyemail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface NewsRepository that extends JpaRepository for using Spring Data
 * JPA to make database queries easier.
 */
public interface NewsRepository extends JpaRepository<NewsLink, Long> {

}
