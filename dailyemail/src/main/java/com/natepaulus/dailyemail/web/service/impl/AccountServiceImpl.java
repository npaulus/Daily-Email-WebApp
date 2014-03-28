package com.natepaulus.dailyemail.web.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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

import com.natepaulus.dailyemail.repository.DeliveryScheduleRepository;
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
import com.natepaulus.dailyemail.web.service.UrlCodeGenerator;
import com.natepaulus.dailyemail.web.service.interfaces.AccountService;
import com.natepaulus.dailyemail.web.service.interfaces.WeatherService;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

/**
 * The Class AccountServiceImpl processes user requests to update their delivery times, rss feeds, weather preferences, and weather
 * location. These updates are persisted in the database.
 */
@Service
public class AccountServiceImpl implements AccountService {

	/*
	 * private final Logger logger = LoggerFactory .getLogger(AccountServiceImpl.class);
	 */

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

	/** The weather service. */
	@Autowired
	private WeatherService weatherService;

	@Autowired
	private DeliveryScheduleRepository deliveryScheduleRepository;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#addNewsLink (java.lang.String, java.lang.String,
	 * com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	public long addNewsLink(final String url, final String name, final long userId) throws RssFeedException {

		// add link to rss feeds table if it doesn't already exist
		final long rssFeedId = this.addRssFeedToDatabase(url);

		// associate feed with the user
		this.setUserRssFeed(rssFeedId, userId, name);

		return userId;
	}

	@Transactional
	private long addRssFeedToDatabase(final String url) throws RssFeedException {
		RssFeeds rssFeed = this.rssFeedsRepository.findByUrl(url);
		final DateTime dt = new DateTime().withZone(DateTimeZone.UTC);
		if (rssFeed != null) {
			return rssFeed.getId();
		} else {
			rssFeed = new RssFeeds();
			rssFeed.setDateAdded(dt);
			rssFeed.setDisabled(false);
			rssFeed.setRssNewsLinks(new HashSet<RssNewsLinks>());
			rssFeed.setUserRssFeeds(new TreeSet<UserRssFeeds>());
			rssFeed.setUrl(url);

			rssFeed = this.getRssNewsLinkForNewFeed(url, rssFeed);
		}

		return this.rssFeedsRepository.save(rssFeed).getId();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#
	 * calculateUserDisplayTime(com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	public User calculateUserDisplayTime(final User user) {

		for (final DeliverySchedule ds : user.getDeliveryTimes()) {

			final DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");

			final DateTime utc = ds.getTime().toDateTimeToday(DateTimeZone.UTC);
			final DateTime userTime = utc.withZone(DateTimeZone.forID(ds.getTz()));

			ds.setDisplayTime(fmt.print(userTime));

		}

		return user;
	}

	/**
	 * Creates the new delivery schedule for the user to receive their daily email.
	 *
	 * @param time the delivery time the user wants
	 * @param tz the timezone selected by the user
	 * @param deliveryDay the delivery day (weekend 1 or weekday 0)
	 * @param user the user
	 * @param disabled the disabled flag, if set don't send anything for the day it is checked
	 * @return the new delivery schedule
	 */
	private DeliverySchedule createNewDeliverySchedule(final String time, final String tz, final int deliveryDay, final User user,
			final boolean disabled) {
		final DeliverySchedule deliverySchedule = new DeliverySchedule();
		final DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");

		final LocalDateTime userEnteredTime = fmt.parseLocalDateTime(time);
		final LocalTime userLocalTime = userEnteredTime.toLocalTime();

		final DateTime dateTimeZone = new DateTime(userLocalTime.toDateTimeToday(DateTimeZone.forID(tz)));
		final DateTime utc = dateTimeZone.withZone(DateTimeZone.UTC);
		final LocalTime lt = utc.toLocalTime();
		deliverySchedule.setDeliveryDay(deliveryDay);
		deliverySchedule.setDisabled(disabled);
		deliverySchedule.setIdusers(user.getId());
		deliverySchedule.setTime(lt);
		deliverySchedule.setUser(user);
		deliverySchedule.setTz(tz);

		return deliverySchedule;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService# deleteNewsLink(int,
	 * com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	@Transactional
	public long deleteNewsLink(final int id, final long userId) {

		final User user = this.userRepository.findOne(userId);

		final Iterator<UserRssFeeds> links = user.getUserRssFeeds().iterator();

		while (links.hasNext()) {
			final UserRssFeeds link = links.next();

			if (link.getFeedId() == id) {
				links.remove();
			}

		}

		this.userRepository.save(user);

		return userId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#deleteUrlCode
	 * (com.natepaulus.dailyemail.repository.entity.User)
	 */
	@Override
	@Transactional
	public long deleteUrlCode(final long userId) {
		final User user = this.userRepository.findOne(userId);
		user.setUrlCode(null);
		this.userRepository.save(user);
		return userId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#findUserById(long)
	 */
	@Override
	public User findUserById(final long userId) {
		return this.userRepository.findOne(userId);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#
	 * generateUrlCode(com.natepaulus.dailyemail.repository.entity.User)
	 */
	@Override
	@Transactional
	public long generateUrlCode(final long userId) {

		final User user = this.userRepository.findOne(userId);

		final UrlCodeGenerator ucg = new UrlCodeGenerator();
		user.setUrlCode(ucg.nextSessionId());
		this.userRepository.save(user);
		return userId;
	}

	private RssFeeds getRssNewsLinkForNewFeed(final String url, final RssFeeds rssFeed) throws RssFeedException {
		final Set<RssNewsLinks> rssLinks = new HashSet<RssNewsLinks>();
		try {

			final URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			final InputStream is = connection.getInputStream();
			final InputSource source = new InputSource(is);
			final SyndFeedInput input = new SyndFeedInput();
			final SyndFeed feed = input.build(source);

			@SuppressWarnings("rawtypes")
			final Iterator iFeed = feed.getEntries().iterator();

			while (iFeed.hasNext()) {
				final RssNewsLinks link = new RssNewsLinks();
				link.setFeedId(rssFeed.getId());
				link.setRssFeed(rssFeed);
				final SyndEntry entry = (SyndEntry) iFeed.next();
				link.setTitle(entry.getTitle());
				link.setLink(entry.getLink());
				link.setDescription(entry.getDescription().getValue().replaceAll("\\<.*?>", ""));

				final Date publicationDate = entry.getPublishedDate();
				final DateTime publishedDate = new DateTime(publicationDate);
				link.setPubDate(publishedDate);

				rssLinks.add(link);

			}
			rssFeed.setRssNewsLinks(rssLinks);

		} catch (final Exception ex) {
			throw new RssFeedException("The feed appears to be invalid");
		}

		return rssFeed;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService# setIncludedNewsInformation(java.lang.String[],
	 * com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	@Transactional
	public long setIncludedNewsInformation(final String[] news, final long userId) {

		final User user = this.userRepository.findOne(userId);

		final Iterator<UserRssFeeds> links = user.getUserRssFeeds().iterator();
		while (links.hasNext()) {
			final UserRssFeeds link = links.next();
			link.setDeliver(0);
			for (final String s : news) {
				if (link.getFeedId() == Integer.parseInt(s)) {
					link.setDeliver(1);
				}
			}
		}

		return userId;
	}

	@Transactional
	private void setUserRssFeed(final long rssFeedId, final long userId, final String name) throws RssFeedException {

		final User user = this.userRepository.findOne(userId);
		final RssFeeds rssFeed = this.rssFeedsRepository.findOne(rssFeedId);
		UserRssFeeds userRssFeed = this.userRssFeedsRepository.findByUserIdAndFeedId(userId, rssFeedId);

		if (userRssFeed == null) {
			// user doesn't have this feed
			userRssFeed = new UserRssFeeds();
			userRssFeed.setFeedId(rssFeedId);
			userRssFeed.setFeedName(name);
			userRssFeed.setDeliver(1);
			userRssFeed.setUserId(userId);

			userRssFeed.setRssFeed(rssFeed);
			userRssFeed.setUser(user);

			rssFeed.getUserRssFeeds().add(userRssFeed);
			user.getUserRssFeeds().add(userRssFeed);
			this.userRssFeedsRepository.save(userRssFeed);

		} else {
			throw new RssFeedException("You've already added this feed to your list.");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService# updateDeliverySchedule
	 * (com.natepaulus.dailyemail.web.domain.DeliveryTimeEntryForm, com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	@Transactional
	public long updateDeliverySchedule(final DeliveryTimeEntryForm delTimeEntry, final long userId) {

		final User user = this.userRepository.findOne(userId);

		final Set<DeliverySchedule> ds = user.getDeliveryTimes();
		if (!ds.isEmpty()) {
			ds.clear();
		}
		ds.add(this.createNewDeliverySchedule(delTimeEntry.getWeekDayTime(), delTimeEntry.getTimezone(), 0, user,
				delTimeEntry.isWeekDayDisabled()));
		ds.add(this.createNewDeliverySchedule(delTimeEntry.getWeekEndTime(), delTimeEntry.getTimezone(), 1, user,
				delTimeEntry.isWeekEndDisabled()));

		this.userRepository.save(user);

		return userId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService#
	 * updateUserZipCode(com.natepaulus.dailyemail.repository.User, java.lang.String)
	 */
	@Override
	@Transactional
	public long updateUserZipCode(final long userId, final String zipCode) throws ZipCodeException {
		final String zipCodeExp = "\\d{5}(-\\d{4})?";
		final boolean zipIsGood = zipCode.matches(zipCodeExp);

		if (zipIsGood) {
			final User user = this.userRepository.findOne(userId);
			user.setZipcode(zipCode);
			final Weather weather = this.weatherService.updateWeatherLocation(zipCode, user.getWeather());
			user.setWeather(weather);
			this.userRepository.save(user);

		} else {
			throw new ZipCodeException("Zip Code is Invalid");
		}

		return userId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.AccountService# updateWeatherDeliveryPreference(int,
	 * com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	@Transactional
	public Weather updateWeatherDeliveryPreference(int weather, final long userId) {
		if (weather < 0 || weather > 3) {
			weather = 0;
		}

		final Weather wx = this.weatherRepository.findOne(userId);
		wx.setDeliver_pref(weather);
		this.weatherRepository.save(wx);

		return wx;
	}
}
