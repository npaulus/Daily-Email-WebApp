package com.natepaulus.dailyemail.web.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.natepaulus.dailyemail.repository.DeliverySchedule;
import com.natepaulus.dailyemail.repository.NewsLink;
import com.natepaulus.dailyemail.repository.NewsRepository;
import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.repository.UserRepository;
import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.repository.WeatherRepository;
import com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;
import com.natepaulus.dailyemail.web.service.interfaces.WeatherService;

@Service
public class AccountServiceImpl implements AccountService {

	@Resource
	private UserRepository userRepository;

	@Resource
	private WeatherRepository weatherRepository;

	@Resource
	private NewsRepository newsRepository;

	@Autowired
	private WeatherService weatherService;

	@Override
	@Transactional
	public Weather updateWeatherDeliveryPreference(int weather, User user) {
		if (weather < 0 || weather > 3) {
			weather = 0;
		}
		Weather wx = user.getWeather();
		wx.setDeliver_pref(weather);
		weatherRepository.save(wx);

		return wx;
	}

	@Override
	@Transactional
	public User updateUserZipCode(User user, String zipCode) {
		String zipCodeExp = "\\d{5}(-\\d{4})?";
		boolean zipIsGood = zipCode.matches(zipCodeExp);

		if (zipIsGood) {
			user.setZipcode(zipCode);
			Weather weather = weatherService.updateWeatherLocation(zipCode,
					user.getWeather());
			user.setWeather(weather);
			userRepository.save(user);

		} else {
			// handle error here
		}

		return user;
	}

	@Override
	@Transactional
	public User addNewsLink(String url, String name, User user) {

		NewsLink newLink = new NewsLink();
		newLink.setSource_name(name);
		newLink.setUrl(url);
		newLink.setDeliver(0);
		newLink.setUser(user);
		user.getNewsLink().add(newLink);
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User setIncludedNewsInformation(String[] news, User user) {
		Iterator<NewsLink> links = user.getNewsLink().iterator();
		while (links.hasNext()) {
			NewsLink link = links.next();
			link.setDeliver(0);
			for (String s : news) {
				if (link.getId() == Integer.parseInt(s)) {
					link.setDeliver(1);
				}
			}
		}

		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User deleteNewsLink(int id, User user) {

		Iterator<NewsLink> links = user.getNewsLink().iterator();

		while (links.hasNext()) {
			NewsLink link = links.next();

			if (link.getId() == id) {
				links.remove();
			}

		}

		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User updateDeliverySchedule(DeliveryTimeEntryForm delTimeEntry, User user) {

		Set<DeliverySchedule> ds = new HashSet<DeliverySchedule>();

		ds.add(createNewDeliverySchedule(delTimeEntry.getWeekDayTime(), delTimeEntry.getTimezone(), 0, user, delTimeEntry.isWeekDayDisabled()));
		ds.add(createNewDeliverySchedule(delTimeEntry.getWeekEndTime(), delTimeEntry.getTimezone(), 1, user, delTimeEntry.isWeekEndDisabled()));

		user.setDeliveryTimes(ds);

		return userRepository.save(user);
	}
	@Override
	public User calculateUserDisplayTime(User user) {
		
		for(DeliverySchedule ds : user.getDeliveryTimes()){
			
			DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");
			
			DateTime utc = ds.getTime().toDateTimeToday(DateTimeZone.UTC);
			DateTime userTime = utc.withZone(DateTimeZone.forID(ds.getTz()));
			
			ds.setDisplayTime(fmt.print(userTime));
			
		}
		
		return user;
	}
	
	private DeliverySchedule createNewDeliverySchedule(String time,
			String tz, int deliveryDay, User user, boolean disabled) {
		DeliverySchedule deliverySchedule = new DeliverySchedule();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");
		
		LocalDateTime userEnteredTime = fmt.parseLocalDateTime(time);
		LocalTime userLocalTime = userEnteredTime.toLocalTime();
		
		DateTime dateTimeZone = new DateTime(userLocalTime.toDateTimeToday(DateTimeZone
				.forID(tz)));
		DateTime utc = dateTimeZone.withZone(DateTimeZone.UTC);
		LocalTime lt = utc.toLocalTime();
		deliverySchedule.setDeliveryDay(deliveryDay);
		deliverySchedule.setDisabled(disabled);
		deliverySchedule.setIdusers(user.getId());
		deliverySchedule.setTime(lt);
		deliverySchedule.setUser(user);
		deliverySchedule.setTz(tz);

		return deliverySchedule;
	}
}
