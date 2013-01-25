package com.natepaulus.dailyemail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.natepaulus.dailyemail.repository.entity.Weather;

/**
 * The Interface WeatherRepository that extends JpaRepository for using Spring Data
 * JPA to make database queries easier.
 */
@Transactional(readOnly = false)
public interface WeatherRepository extends JpaRepository<Weather, Long>{

}
