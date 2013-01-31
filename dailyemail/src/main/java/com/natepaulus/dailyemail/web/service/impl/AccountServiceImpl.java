package com.natepaulus.dailyemail.web.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
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
import org.xml.sax.InputSource;

import com.natepaulus.dailyemail.repository.NewsRepository;
import com.natepaulus.dailyemail.repository.RssFeedsRepository;
import com.natepaulus.dailyemail.repository.UserRepository;
import com.natepaulus.dailyemail.repository.UserRssFeedsRepository;
import com.natepaulus.dailyemail.repository.WeatherRepository;
import com.natepaulus.dailyemail.repository.entity.DeliverySchedule;
import com.natepaulus.dailyemail.repository.entity.RssFeeds;
import com.natepaulus.dailyemail.repository.entity.RssNewsLinks;
import com.natepaulus.dailyemail.repository.entity.User;
import com.natepaulus.dailyemail.repository.entity.UserRssFeeds;
import com.natepaulus.dailyemail.repository.entity.Weather;
import com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm;
import com.natepaulus.dailyemail.web.exceptions.RssFeedException;
import com.natepaulus.dailyemail.web.exceptions.ZipCodeException;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;
import com.natepaulus.dailyemail.web.service.interfaces.WeatherService;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

/**
 * The Class AccountServiceImpl processes user requests to update their delivery
 * times, rss feeds, weather preferences, and weather location. These updates
 * are persisted in the database.
 */
@Service
public class AccountServiceImpl implements AccountService {

	/*private final Logger logger = LoggerFactory
			.getLogger(AccountServiceImpl.class);*/

	/** The user repository. */
	@Resource
	private UserRepository userRepository;

	/** The weather repository. */
	@Resource
	private WeatherRepository weatherRepository;
	
	/** The Rss Feeds Repository */
	@Resource
	private RssFeedsRepository rssFeedsRepository;
	
	/** The User Rss Feeds repository */
	@Resource
	private UserRssFeedsRepository userRssFeedsRepository;

	/** The news repository. */
	@Resource
	private NewsRepository newsRepository;

	/** The weather service. */
	@Autowired
	private WeatherService weatherService;

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#
	 * updateWeatherDeliveryPreference(int,
	 * com.natepaulus.dailyemail.repository.User)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#
	 * updateUserZipCode(com.natepaulus.dailyemail.repository.User,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public User updateUserZipCode(User user, String zipCode) throws ZipCodeException {
		String zipCodeExp = "\\d{5}(-\\d{4})?";
		boolean zipIsGood = zipCode.matches(zipCodeExp);

		if (zipIsGood) {
			user.setZipcode(zipCode);
			Weather weather = weatherService.updateWeatherLocation(zipCode,
					user.getWeather());
			user.setWeather(weather);
			userRepository.save(user);

		} else {
			throw new ZipCodeException("Zip Code is Invalid");
		}

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.natepaulus.dailyemail.web.service.interfaces.AccountService#addNewsLink
	 * (java.lang.String, java.lang.String,
	 * com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	@Transactional
	public User addNewsLink(String url, String name, User user) throws RssFeedException {

		RssFeeds rssFeed = rssFeedsRepository.findByUrl(url);
		DateTime dt = new DateTime().withZone(DateTimeZone.UTC);
		if (rssFeed == null) {
			rssFeed = new RssFeeds();
			rssFeed.setDateAdded(dt);
			rssFeed.setDisabled(false);
			rssFeed.setRssNewsLinks(new HashSet<RssNewsLinks>());
			rssFeed.setUserRssFeeds(new HashSet<UserRssFeeds>());
			rssFeed.setUrl(url);
						
			Set<RssNewsLinks> rssLinks = new HashSet<RssNewsLinks>();
							
			try {

				URLConnection connection = new URL(url).openConnection();
				connection
						.setRequestProperty(
								"User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				connection.connect();
				InputStream is = connection.getInputStream();
				InputSource source = new InputSource(is);
				SyndFeedInput input = new SyndFeedInput();
				SyndFeed feed = input.build(source);

				@SuppressWarnings("rawtypes")
				Iterator iFeed = feed.getEntries().iterator();

				while (iFeed.hasNext()) {
					RssNewsLinks link = new RssNewsLinks();
					link.setFeedId(rssFeed.getId());
					link.setRssFeed(rssFeed);
					SyndEntry entry = (SyndEntry) iFeed.next();
					link.setTitle(entry.getTitle());
					link.setLink(entry.getLink());
					link.setDescription(entry.getDescription().getValue()
							.replaceAll("\\<.*?>", ""));
					
					Date publicationDate = entry.getPublishedDate();
					DateTime publishedDate = new DateTime(publicationDate);
					link.setPubDate(publishedDate);

					rssLinks.add(link);

				}
				rssFeed.setRssNewsLinks(rssLinks);
				rssFeedsRepository.save(rssFeed);
				
			} catch (Exception ex) {
				throw new RssFeedException("The feed appears to be invalid");				
			}
			
			UserRssFeeds userRssFeed = new UserRssFeeds();
			userRssFeed.setFeedId(rssFeed.getId());
			userRssFeed.setFeedName(name);
			userRssFeed.setDeliver(1);
			userRssFeed.setUserId(user.getId());
			userRssFeed.setRssFeed(rssFeed);
			userRssFeed.setUser(user);
			
			rssFeed.getUserRssFeeds().add(userRssFeed);		
			user.getUserRssFeeds().add(userRssFeed);
			
		} else {
			
			//check for existing association
			UserRssFeeds userNewFeed = userRssFeedsRepository.findByUserIdAndFeedId(user.getId(), rssFeed.getId());
			if(userNewFeed == null){
				//user doesn't have this feed
				userNewFeed = new UserRssFeeds();
				userNewFeed.setFeedName(name);
				userNewFeed.setFeedId(rssFeed.getId());
				userNewFeed.setRssFeed(rssFeed);
				userNewFeed.setDeliver(1);
				userNewFeed.setUserId(user.getId());
				userNewFeed.setUser(user);
				
				rssFeed.getUserRssFeeds().add(userNewFeed);
				user.getUserRssFeeds().add(userNewFeed);
				
				userRssFeedsRepository.save(userNewFeed);
			} else {
				throw new RssFeedException("You've already added this feed to your list.");
			}
			
		}
		
		return userRepository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#
	 * setIncludedNewsInformation(java.lang.String[],
	 * com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	@Transactional
	public User setIncludedNewsInformation(String[] news, User user) {
		Iterator<UserRssFeeds> links = user.getUserRssFeeds().iterator();
		while (links.hasNext()) {
			UserRssFeeds link = links.next();
			link.setDeliver(0);
			for (String s : news) {
				if (link.getFeedId() == Integer.parseInt(s)) {
					link.setDeliver(1);
				}
			}
		}

		return userRepository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#
	 * deleteNewsLink(int, com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	@Transactional
	public User deleteNewsLink(int id, User user) {
		
		Iterator<UserRssFeeds> links = user.getUserRssFeeds().iterator();

		while (links.hasNext()) {
			UserRssFeeds link = links.next();

			if (link.getFeedId() == id) {
				links.remove();
			}

		}

		return userRepository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#
	 * updateDeliverySchedule
	 * (com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm,
	 * com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	@Transactional
	public User updateDeliverySchedule(DeliveryTimeEntryForm delTimeEntry,
			User user) {

		Set<DeliverySchedule> ds = new HashSet<DeliverySchedule>();

		ds.add(createNewDeliverySchedule(delTimeEntry.getWeekDayTime(),
				delTimeEntry.getTimezone(), 0, user,
				delTimeEntry.isWeekDayDisabled()));
		ds.add(createNewDeliverySchedule(delTimeEntry.getWeekEndTime(),
				delTimeEntry.getTimezone(), 1, user,
				delTimeEntry.isWeekEndDisabled()));

		user.setDeliveryTimes(ds);

		return userRepository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#
	 * calculateUserDisplayTime(com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	public User calculateUserDisplayTime(User user) {

		for (DeliverySchedule ds : user.getDeliveryTimes()) {

			DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");

			DateTime utc = ds.getTime().toDateTimeToday(DateTimeZone.UTC);
			DateTime userTime = utc.withZone(DateTimeZone.forID(ds.getTz()));

			ds.setDisplayTime(fmt.print(userTime));

		}

		return user;
	}

	/**
	 * Creates the new delivery schedule for the user to receive their daily
	 * email.
	 * 
	 * @param time
	 *            the delivery time the user wants
	 * @param tz
	 *            the timezone selected by the user
	 * @param deliveryDay
	 *            the delivery day (weekend 1 or weekday 0)
	 * @param user
	 *            the user
	 * @param disabled
	 *            the disabled flag, if set don't send anything for the day it
	 *            is checked
	 * @return the new delivery schedule
	 */
	private DeliverySchedule createNewDeliverySchedule(String time, String tz,
			int deliveryDay, User user, boolean disabled) {
		DeliverySchedule deliverySchedule = new DeliverySchedule();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");

		LocalDateTime userEnteredTime = fmt.parseLocalDateTime(time);
		LocalTime userLocalTime = userEnteredTime.toLocalTime();

		DateTime dateTimeZone = new DateTime(
				userLocalTime.toDateTimeToday(DateTimeZone.forID(tz)));
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
