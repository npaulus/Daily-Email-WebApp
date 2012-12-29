package com.natepaulus.dailyemail.web.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.UserRepository;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;

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
		User newUser = new User();
		newUser.setFirstName(acctSignUp.getFirst_name());
		newUser.setLastName(acctSignUp.getLast_name());
		newUser.setEmail(acctSignUp.getEmail());
		newUser.setPassword(acctSignUp.getPassword());
		
		userRepository.save(newUser);
	}

}
