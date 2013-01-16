package com.natepaulus.dailyemail.web.service.impl;

import javax.annotation.Resource;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.UserRepository;
import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;
import com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;
import com.natepaulus.dailyemail.web.service.PasswordEncryption;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;
import com.natepaulus.dailyemail.web.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;
	
	@Autowired
	private AccountService accountService;

	/*
	 * @Transactional(readOnly = true) public List<User> findAll() { List<User>
	 * allUsers = (List<User>) userRepository.findAll(); return allUsers; }
	 */

	/*
	 * @Transactional(readOnly = true) public String findByEmail(String email) {
	 * return userRepository.findByEmail(email); }
	 * 
	 * @Transactional(readOnly = true) public String findByPassword(String
	 * password) { return userRepository.findByPassword(password); }
	 */

	@Transactional
	public boolean addNewUser(AccountSignUp acctSignUp, Weather weather) {
		
		User existingUser = userRepository.findByEmail(acctSignUp.getEmail());
		if(existingUser != null){
			return false;
		}
		
		User newUser = new User();
		newUser.setFirstName(acctSignUp.getFirst_name());
		newUser.setLastName(acctSignUp.getLast_name());
		newUser.setEmail(acctSignUp.getEmail());
		newUser.setPassword(PasswordEncryption.toSHA1(acctSignUp.getPassword()));
		newUser.setZipcode(acctSignUp.getZipcode());

		newUser.setWeather(weather);
		weather.setUser(newUser);
		
		userRepository.save(newUser);
		
		LocalTime day = new LocalTime();
		day = LocalTime.MIDNIGHT;
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");
		String initialDeliveryTime = fmt.print(day);
		
		DeliveryTimeEntryForm dtef = new DeliveryTimeEntryForm();
		dtef.setTimezone("America/New_York");
		dtef.setWeekDayDisabled(true);
		dtef.setWeekEndDisabled(true);
		dtef.setWeekDayTime(initialDeliveryTime);
		dtef.setWeekEndTime(initialDeliveryTime);
		
		accountService.updateDeliverySchedule(dtef, newUser);
		
		return true;
		
	}

	@Override
	public User login(String email, String password) throws AuthenticationException{
		User user = userRepository.findByEmail(email);		
		if(user == null || !user.getPassword().equals(PasswordEncryption.toSHA1(password))){
			throw new AuthenticationException("Invalid Login. Please try again.");
		}
		return user;
	}
}
