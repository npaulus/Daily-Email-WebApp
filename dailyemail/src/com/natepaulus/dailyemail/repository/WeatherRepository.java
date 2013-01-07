package com.natepaulus.dailyemail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = false)
public interface WeatherRepository extends JpaRepository<Weather, Long>{

}
