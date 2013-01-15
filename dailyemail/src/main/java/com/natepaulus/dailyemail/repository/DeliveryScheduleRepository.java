package com.natepaulus.dailyemail.repository;

import java.util.List;

import org.joda.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryScheduleRepository extends
		JpaRepository<DeliverySchedule, Long> {

	List<DeliverySchedule> findByTimeAndDeliveryDay(LocalTime time,
			int deliveryDay);

}
