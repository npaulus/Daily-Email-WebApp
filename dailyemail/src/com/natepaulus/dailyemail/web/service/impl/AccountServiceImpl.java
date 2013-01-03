package com.natepaulus.dailyemail.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.UserRepository;
import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.repository.WeatherRepository;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Resource
	private UserRepository userRepository;
	
	@Resource
	private WeatherRepository weatherRepository;
	
	@Override
	public Weather updateWeatherDeliveryPreference(int weather, User user) {
		Weather wx = user.getWeather();
		wx.setDeliver_pref(weather);
		weatherRepository.save(wx);
		
		return wx;
	}

}
