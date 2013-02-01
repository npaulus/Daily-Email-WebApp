package com.natepaulus.dailyemail.web.service;

import java.util.Iterator;
import java.util.Set;

import com.natepaulus.dailyemail.repository.entity.DeliverySchedule;
import com.natepaulus.dailyemail.repository.entity.User;

public class UserTimeZone {

	public static String getUserTimeZone(User user){
		
		Set<DeliverySchedule> ds = user.getDeliveryTimes();
		Iterator<DeliverySchedule> dsI = ds.iterator();
		String tz = dsI.next().getTz();
		
		return tz;
		
	}
	
}
