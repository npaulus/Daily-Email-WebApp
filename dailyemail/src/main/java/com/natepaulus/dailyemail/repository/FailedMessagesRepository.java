package com.natepaulus.dailyemail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natepaulus.dailyemail.repository.entity.FailedMessages;

public interface FailedMessagesRepository extends
		JpaRepository<FailedMessages, Long> {

}
