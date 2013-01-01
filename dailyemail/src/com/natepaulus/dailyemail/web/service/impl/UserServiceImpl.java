package com.natepaulus.dailyemail.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.UserRepository;
import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;
import com.natepaulus.dailyemail.web.service.PasswordEncryption;
import com.natepaulus.dailyemail.web.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;

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
	public void addNewUser(AccountSignUp acctSignUp, Weather weather) {

		User newUser = new User();
		newUser.setFirstName(acctSignUp.getFirst_name());
		newUser.setLastName(acctSignUp.getLast_name());
		newUser.setEmail(acctSignUp.getEmail());
		newUser.setPassword(PasswordEncryption.toSHA1(acctSignUp.getPassword()));
		newUser.setZipcode(acctSignUp.getZipcode());

		newUser.setWeather(weather);
		weather.setUser(newUser);

		userRepository.save(newUser);

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
