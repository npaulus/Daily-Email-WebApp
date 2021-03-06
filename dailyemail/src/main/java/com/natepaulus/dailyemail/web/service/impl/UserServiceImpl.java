package com.natepaulus.dailyemail.web.service.impl;

import java.util.HashSet;

import javax.annotation.Resource;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.natepaulus.dailyemail.repository.UserRepository;
import com.natepaulus.dailyemail.repository.entity.DeliverySchedule;
import com.natepaulus.dailyemail.repository.entity.User;
import com.natepaulus.dailyemail.repository.entity.Weather;
import com.natepaulus.dailyemail.web.domain.AccountSignUp;
import com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;
import com.natepaulus.dailyemail.web.service.PasswordEncryption;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;
import com.natepaulus.dailyemail.web.service.interfaces.UserService;

/**
 * The Class UserServiceImpl.
 */
@Service
public class UserServiceImpl implements UserService {

	/** The user repository. */
	@Resource
	private UserRepository userRepository;

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.UserService#addNewUser
	 * (com.natepaulus.dailyemail.web.domain.AccountSignUp, com.natepaulus.dailyemail.repository.Weather)
	 */
	@Override
	@Transactional
	public boolean addNewUser(final AccountSignUp acctSignUp, final Weather weather) {

		final User existingUser = this.userRepository.findByEmail(acctSignUp.getEmail());
		if (existingUser != null) {
			return false;
		}

		final User newUser = new User();
		newUser.setDeliveryTimes(new HashSet<DeliverySchedule>());
		newUser.setFirstName(acctSignUp.getFirst_name());
		newUser.setLastName(acctSignUp.getLast_name());
		newUser.setEmail(acctSignUp.getEmail());
		newUser.setPassword(PasswordEncryption.toSHA1(acctSignUp.getPassword()));
		newUser.setZipcode(acctSignUp.getZipcode());

		newUser.setWeather(weather);
		weather.setUser(newUser);

		this.userRepository.save(newUser);

		LocalTime day = new LocalTime();
		day = LocalTime.MIDNIGHT;

		final DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");
		final String initialDeliveryTime = fmt.print(day);

		final DeliveryTimeEntryForm dtef = new DeliveryTimeEntryForm();
		dtef.setTimezone("America/New_York");
		dtef.setWeekDayDisabled(true);
		dtef.setWeekEndDisabled(true);
		dtef.setWeekDayTime(initialDeliveryTime);
		dtef.setWeekEndTime(initialDeliveryTime);

		this.accountService.updateDeliverySchedule(dtef, newUser.getId());

		return true;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.UserService#login(java .lang.String, java.lang.String)
	 */
	@Override
	public long login(final String email, final String password) throws AuthenticationException {
		final User user = this.userRepository.findByEmail(email);
		if (user == null || !user.getPassword().equals(PasswordEncryption.toSHA1(password))) {
			throw new AuthenticationException("Invalid Login. Please try again.");
		}
		return user.getId();
	}
}
