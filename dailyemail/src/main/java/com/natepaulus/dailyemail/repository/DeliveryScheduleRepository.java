package com.natepaulus.dailyemail.repository;

import java.util.List;

import org.joda.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface DeliveryScheduleRepository extends JpaRepository to use Spring
 * Data JPA for easy query building.
 */
public interface DeliveryScheduleRepository extends
		JpaRepository<DeliverySchedule, Long> {

	/**
	 * Find by time and delivery day.
	 * 
	 * @param time
	 *            the time
	 * @param deliveryDay
	 *            the delivery day
	 * @return the list
	 */
	List<DeliverySchedule> findByTimeAndDeliveryDay(LocalTime time,
			int deliveryDay);

}
