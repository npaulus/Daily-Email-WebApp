package com.natepaulus.dailyemail.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.UserRepository;
import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;
import com.natepaulus.dailyemail.web.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;
		
	/*	
	@Transactional(readOnly = true)
	public List<User> findAll() {
		List<User> allUsers = (List<User>) userRepository.findAll();
		return allUsers;
	}*/

/*	@Transactional(readOnly = true)
	public String findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Transactional(readOnly = true)
	public String findByPassword(String password) {
		return userRepository.findByPassword(password);
	}*/
	
	@Transactional
	public void addNewUser(AccountSignUp acctSignUp){
		
		Weather weather = new Weather();
		weather.setDeliver_pref(0);
		weather.setLatitude("TestLat");
		weather.setLongitude("TestLong");
		weather.setLocation_name("Test Location Name");
		
		User newUser = new User();
		newUser.setFirstName(acctSignUp.getFirst_name());
		newUser.setLastName(acctSignUp.getLast_name());
		newUser.setEmail(acctSignUp.getEmail());
		newUser.setPassword(acctSignUp.getPassword());
		newUser.setZipcode(acctSignUp.getZipcode());
		
		newUser.setWeather(weather);
		weather.setUser(newUser);
				
		userRepository.save(newUser);
				
	}

}
