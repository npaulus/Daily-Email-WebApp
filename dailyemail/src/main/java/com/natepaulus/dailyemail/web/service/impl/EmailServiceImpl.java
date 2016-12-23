package com.natepaulus.dailyemail.web.service.impl;

import com.google.gson.*;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import com.natepaulus.dailyemail.repository.*;
import com.natepaulus.dailyemail.repository.entity.*;
import com.natepaulus.dailyemail.web.domain.EmailData;
import com.natepaulus.dailyemail.web.domain.NewsFeed;
import com.natepaulus.dailyemail.web.domain.NewsStory;
import com.natepaulus.dailyemail.web.service.interfaces.EmailService;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.velocity.app.VelocityEngine;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailServiceImpl checks every minute to see if any users have requested their email at that time. If it finds any users it
 * then builds and sends them an email with their requested data.
 */
@Service
@PropertySource("classpath:emailConfig.properties")
public class EmailServiceImpl implements EmailService {

    public static final String N_A = "N/A";
    public static final double METERS_IN_MILE = 1609.344;
    public static final double METERS_SECOND_TO_MPH = 0.44704;
    @Resource
	private Environment environment;

	private final static String[] COMPASS_DIRECTIONS =  {"N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW","NW","NNW","N"};

	/** The email addresses to use: */
	private final static String ADMINISTRATOR = "administrator.email";
	private final static String DAILY_EMAIL_ADDRESS = "dailyemail.from.email";

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	/** The delivery schedule repository. */
	@Resource
	DeliveryScheduleRepository deliveryScheduleRepository;

	/** The Rss Feeds Repository. */
	@Resource
	RssFeedsRepository rssFeedsRepository;

	/** The User Rss Feeds Repository. */
	@Resource
	RssNewsLinksRepository rssNewsLinksRepository;

	@Resource
	UserRssFeedsRepository userRssFeedsRepository;

	/** The user repository. */
	@Resource
	UserRepository userRepository;

	/** The failed messages repository. */
	@Resource
	FailedMessagesRepository failedMessagesRepository;

	/** The java mail sender for sending email. */
	@Autowired
	private JavaMailSender sender;

	/** The velocity engine. */
	@Autowired
	private VelocityEngine velocityEngine;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.EmailService# generateQuickView(java.lang.String)
	 */
	@Override
	public EmailData generateQuickView(final String code) {

		final User user = this.userRepository.findByUrlCode(code);

		EmailData data = new EmailData();
		data = this.getWeatherConditions(data, user);
		this.getRssLinksForEmail(data, user);

		return data;
	}

	/**
	 * Returns weather forecast based on GPS coordinates using new weather.gov api.
	 *
	 * @param data
	 * @param user
     * @return
     */
	private EmailData getWeatherForecast(final EmailData data, final User user){

		final String uri = "https://api.weather.gov/points/"
				+ user.getWeather().getLatitude() + "," + user.getWeather().getLongitude() + "/forecast";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(result);

		JsonObject forecastData  = jsonElement.getAsJsonObject();
		JsonObject forecastProperties = forecastData.getAsJsonObject("properties");
		JsonArray forecastPeriods = forecastProperties.getAsJsonArray("periods");

		for(int index = 0; index < 3; index++){
			JsonObject forecast = forecastPeriods.get(index).getAsJsonObject();
			data.getWeatherForecast().getPeriodForecast().put(forecast.get("name").getAsString(),
					forecast.get("detailedForecast").getAsString());
		}

		return data;
	}

	private EmailData getCurrentWeatherConditions(final EmailData data, final User user){

	    final String uriPointForCityState = "http://api.weather.gov/points/"
                + user.getWeather().getLatitude() + "," + user.getWeather().getLongitude();

		final String uriStations = "http://api.weather.gov/points/"
				+ user.getWeather().getLatitude() + "," + user.getWeather().getLongitude() + "/stations";

		final RestTemplate restTemplate = new RestTemplate();
		final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		final HttpClient httpClient = HttpClientBuilder.create()
				.setRedirectStrategy(new LaxRedirectStrategy())
				.build();
		factory.setHttpClient(httpClient);
		restTemplate.setRequestFactory(factory);

		final String stationListResult = restTemplate.getForObject(uriStations, String.class);
		final JsonParser jsonParser = new JsonParser();
		final JsonElement stationList = jsonParser.parse(stationListResult);
		final JsonObject stationsJson = stationList.getAsJsonObject();
		final JsonArray stations = stationsJson.getAsJsonArray("observationStations");

		final String uriWeatherStation = stations.get(0).getAsString();

		final String stationDetails = restTemplate.getForObject(uriWeatherStation, String.class);
		final JsonElement stationInformation = jsonParser.parse(stationDetails);
		final JsonElement stationProperties = stationInformation.getAsJsonObject().get("properties");

		data.getWxCurCond().setWeatherStation(stationProperties.getAsJsonObject().get("name").getAsString());

        final String pointInformation = restTemplate.getForObject(uriPointForCityState, String.class);

        final JsonElement cityStateResponse = jsonParser.parse(pointInformation);
        final JsonObject cityState = cityStateResponse.getAsJsonObject().get("properties").getAsJsonObject().get("relativeLocation").getAsJsonObject().get("properties").getAsJsonObject();
        data.getWxCurCond().setCityState(cityState.get("city").getAsString() + ", " + cityState.get("state").getAsString());
		final String currentWxResponse = restTemplate.getForObject(uriWeatherStation + "/observations", String.class);

		final JsonObject allWxObservationsResponse = jsonParser.parse(currentWxResponse).getAsJsonObject();
		final JsonArray allWxObservations = allWxObservationsResponse.getAsJsonArray("features");
		final JsonObject currentWxObservation = allWxObservations.get(0).getAsJsonObject().get("properties").getAsJsonObject();

		final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
		final DateTime obsUTCTime = fmt.withOffsetParsed().parseDateTime(currentWxObservation.get("timestamp").getAsString());

		final DateTime localTime = new DateTime(obsUTCTime).withZone(DateTimeZone.forID(user.getDeliveryTimes().iterator().next().getTz()));
		final DateTimeFormatter outFmt = DateTimeFormat.forPattern("MMM dd',' yyyy h:mm a");
		this.logger.info("Printing Local Time with printer: " + outFmt.print(localTime));
		data.getWxCurCond().setLatestObDateTime(outFmt.print(localTime));

        data.getWxCurCond().setCurWx(currentWxObservation.get("textDescription").getAsString());

        if(!currentWxObservation.get("temperature").getAsJsonObject().get("value").isJsonNull()){
            data.getWxCurCond().setCurrentTemp(String.valueOf(
                    Math.round(currentWxObservation.get("temperature").getAsJsonObject().get("value").getAsDouble() * 9 / 5 + 32)));
        } else {
            data.getWxCurCond().setCurrentTemp(N_A);
        }

        if(!currentWxObservation.get("dewpoint").getAsJsonObject().get("value").isJsonNull()){
            data.getWxCurCond().setDewPoint(String.valueOf(
                    Math.round(currentWxObservation.get("dewpoint").getAsJsonObject().get("value").getAsDouble() * 9 / 5 + 32)));
        } else {
            data.getWxCurCond().setDewPoint(N_A);
        }

        if(!currentWxObservation.get("relativeHumidity").getAsJsonObject().get("value").isJsonNull()){
            data.getWxCurCond().setHumidity(String.valueOf(Math.round(currentWxObservation.get("relativeHumidity").getAsJsonObject().get("value").getAsDouble())));
        } else {
            data.getWxCurCond().setHumidity(N_A);
        }

        if(!currentWxObservation.get("visibility").getAsJsonObject().get("value").isJsonNull()) {
            data.getWxCurCond().setVisibility(String.valueOf(Math.round(currentWxObservation.get("visibility").getAsJsonObject().get("value").getAsDouble() / METERS_IN_MILE)));
        } else {
            data.getWxCurCond().setVisibility(N_A);
        }
        if(!currentWxObservation.get("windSpeed").getAsJsonObject().get("value").isJsonNull()){
            data.getWxCurCond().setWindSpeed(String.valueOf(Math.round(currentWxObservation.get("windSpeed").getAsJsonObject().get("value").getAsDouble() / METERS_SECOND_TO_MPH)));
        } else {
            data.getWxCurCond().setWindSpeed("0");
        }

        if(!currentWxObservation.get("windGust").getAsJsonObject().get("value").isJsonNull()){
            data.getWxCurCond().setWindSpeed(String.valueOf(Math.round(currentWxObservation.get("windGust").getAsJsonObject().get("value").getAsDouble() / METERS_SECOND_TO_MPH)));
        } else {
            data.getWxCurCond().setWindGust(N_A);
        }
		setWindDirection(data, currentWxObservation.get("windDirection").getAsJsonObject().get("value").getAsDouble());

        final DateTimeZone dtz = localTime.getZone();
        this.logger.info("TimeZone: " + dtz.toString());
        final Location location = new Location(user.getWeather().getLatitude(), user.getWeather().getLongitude());
        final SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, dtz.toString());
        final Calendar officialSunrise = calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance());
        final Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());
        final DateTimeFormatter dtfSunriseset = DateTimeFormat.forPattern("h:mm a");
        final DateTime dtSunrise = new DateTime(officialSunrise);
        final DateTime dtSunset = new DateTime(officialSunset);
        final DateTime dtSunriseLocal = dtSunrise.withZone(DateTimeZone.forID(dtz.toString()));
        final DateTime dtSunsetLocal = dtSunset.withZone(DateTimeZone.forID(dtz.toString()));

        data.getWxCurCond().setSunRise(dtfSunriseset.print(dtSunriseLocal));
        data.getWxCurCond().setSunSet(dtfSunriseset.print(dtSunsetLocal));

		return data;
	}

	/**
	 * Gets the weather conditions from the National Weather Service experimental forecast feed.
	 *
	 * @param data the EmailData object to add the weather conditions to
	 * @param user the user
	 * @return the weather conditions
	 */
	private EmailData getWeatherConditions(final EmailData data, final User user) {

		try {

			final URL curCond_ForecastURL =
					new URL("http://forecast.weather.gov/MapClick.php?lat=" + user.getWeather().getLatitude() + "&lon="
							+ user.getWeather().getLongitude() + "&unit=0&lg=english&FcstType=dwml");
			final InputStream inCC_Fx = curCond_ForecastURL.openStream();
			final DocumentBuilderFactory factoryCurCond_Fx = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builderCCFX = factoryCurCond_Fx.newDocumentBuilder();
			final org.w3c.dom.Document docCFX = builderCCFX.parse(inCC_Fx);

			final XPathFactory xPathfactoryCCFX = XPathFactory.newInstance();
			final XPath xpathCCFX = xPathfactoryCCFX.newXPath();
			XPathExpression exprCCFX = null;

			if (user.getWeather().getDeliver_pref() == 1 || user.getWeather().getDeliver_pref() == 3) {
				exprCCFX =
						xpathCCFX
						.compile("/dwml/data[@type = 'current observations']/time-layout/start-valid-time[@period-name = 'current']/text()");
				final String temp = (String) exprCCFX.evaluate(docCFX, XPathConstants.STRING);

				final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
				final DateTime xmlTime = fmt.withOffsetParsed().parseDateTime(temp);

				final DateTime localTime = xmlTime;

//				this.logger.info("Localtime: " + localTime.toString());
				final DateTimeFormatter outFmt = DateTimeFormat.forPattern("MMM dd',' yyyy h:mm a");
//				this.logger.info("Printing Local Time with printer: " + outFmt.print(xmlTime));
				data.getWxCurCond().setLatestObDateTime(outFmt.print(xmlTime));
				data.getWxCurCond().setCityState(user.getWeather().getLocation_name());

				exprCCFX = xpathCCFX.compile("/dwml/data[@type = 'current observations']/location/area-description/text()");
				data.getWxCurCond().setWeatherStation((String) exprCCFX.evaluate(docCFX, XPathConstants.STRING));
				exprCCFX =
						xpathCCFX
						.compile("/dwml/data[@type = 'current observations']/parameters/wind-speed[@type = 'sustained']/value/text()");
				double mphWindSpeed = 0;
				boolean isNAWindSpeed = false;
				try {
					mphWindSpeed = (Double) exprCCFX.evaluate(docCFX, XPathConstants.NUMBER);
					mphWindSpeed = 1.15155 * mphWindSpeed;
				} catch (final NumberFormatException e) {
					isNAWindSpeed = true;
				}
				if (isNAWindSpeed) {
					data.getWxCurCond().setWindSpeed("NA");
				} else {
					final int wSpeed = (int) Math.round(mphWindSpeed);
					data.getWxCurCond().setWindSpeed(Integer.toString(wSpeed));
				}
				exprCCFX =
						xpathCCFX
						.compile("/dwml/data[@type = 'current observations']/parameters/wind-speed[@type = 'gust']/value/text()");
				double mphWindGust = 0;
				boolean isNAWindGust = false;
				try {
					mphWindGust = (Double) exprCCFX.evaluate(docCFX, XPathConstants.NUMBER);
					mphWindGust = 1.15155 * mphWindSpeed;
				} catch (final NumberFormatException e) {
					isNAWindGust = true;
				}
				if (isNAWindGust) {
					data.getWxCurCond().setWindGust("NA");
				} else {
					final int wGust = (int) Math.round(mphWindGust);
					data.getWxCurCond().setWindGust(Integer.toString(wGust));
				}

				exprCCFX =
						xpathCCFX
								.compile("/dwml/data[@type = 'current observations']/parameters/direction[@type = 'wind']/value/text()");
				Double windDirection = (Double) exprCCFX.evaluate(docCFX, XPathConstants.NUMBER);

				setWindDirection(data, windDirection);

				exprCCFX =
						xpathCCFX
								.compile("/dwml/data[@type = 'current observations']/parameters/weather[@time-layout = 'k-p1h-n1-1']/weather-conditions/value/visibility/text()");

				data.getWxCurCond().setVisibility((String) exprCCFX.evaluate(docCFX, XPathConstants.STRING));

				exprCCFX =
						xpathCCFX
						.compile("/dwml/data[@type = 'current observations']/parameters/humidity[@type = 'relative']/value/text()");
				data.getWxCurCond().setHumidity((String) exprCCFX.evaluate(docCFX, XPathConstants.STRING));
				exprCCFX =
						xpathCCFX
						.compile("/dwml/data[@type = 'current observations']/parameters/temperature[@type = 'apparent']/value/text()");
				data.getWxCurCond().setCurrentTemp((String) exprCCFX.evaluate(docCFX, XPathConstants.STRING));
				exprCCFX =
						xpathCCFX
						.compile("/dwml/data[@type = 'current observations']/parameters/temperature[@type = 'dew point']/value/text()");
				data.getWxCurCond().setDewPoint((String) exprCCFX.evaluate(docCFX, XPathConstants.STRING));
				exprCCFX =
						xpathCCFX
						.compile("/dwml/data[@type = 'current observations']/parameters/weather[@time-layout = 'k-p1h-n1-1']/weather-conditions/@weather-summary");
				data.getWxCurCond().setCurWx((String) exprCCFX.evaluate(docCFX, XPathConstants.STRING));
				final DateTimeZone dtz = xmlTime.getZone();
				this.logger.info("TimeZone: " + dtz.toString());
				final Location location = new Location(user.getWeather().getLatitude(), user.getWeather().getLongitude());
				final SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, dtz.toString());
				final Calendar officialSunrise = calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance());
				final Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());
				final DateTimeFormatter dtfSunriseset = DateTimeFormat.forPattern("h:mm a");
				final DateTime dtSunrise = new DateTime(officialSunrise);
				final DateTime dtSunset = new DateTime(officialSunset);
				final DateTime dtSunriseLocal = dtSunrise.withZone(DateTimeZone.forID(dtz.toString()));
				final DateTime dtSunsetLocal = dtSunset.withZone(DateTimeZone.forID(dtz.toString()));

				data.getWxCurCond().setSunRise(dtfSunriseset.print(dtSunriseLocal));
				data.getWxCurCond().setSunSet(dtfSunriseset.print(dtSunsetLocal));

			}
			if (user.getWeather().getDeliver_pref() == 2 || user.getWeather().getDeliver_pref() == 3) {
				exprCCFX =
						xpathCCFX
						.compile("/dwml/data[@type='forecast']/time-layout[layout-key/text()=/dwml/data[@type='forecast']/parameters[@applicable-location='point1']/wordedForecast/@time-layout]/start-valid-time");
				final NodeList days = (NodeList) exprCCFX.evaluate(docCFX, XPathConstants.NODESET);
				exprCCFX =
						xpathCCFX
						.compile("/dwml/data[@type='forecast']/parameters[@applicable-location='point1']/wordedForecast/text");
				final NodeList forecastText = (NodeList) exprCCFX.evaluate(docCFX, XPathConstants.NODESET);

				for (int i = 0; i < 3; i++) {
					final Element e = (Element) days.item(i);
					final Element e2 = (Element) forecastText.item(i);
					data.getWeatherForecast().getPeriodForecast().put(e.getAttribute("period-name"), e2.getFirstChild().getNodeValue());

				}
			}
			inCC_Fx.close();
		} catch (final Exception ex) {
			this.logger.error("Get Weather Data had an issue.", ex);
		}

		return data;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.EmailService# retrieveUserListForEmails()
	 */
	@Override
	@Scheduled(cron = "0 0/1 * * * ?")
	public void retrieveUserListForEmails() {
		// logger.info("Retrieving List of users to email");
		int dayOfWeek = -1;
		final DateTime currentTime = new DateTime();

		final List<DeliverySchedule> allDeliverySchedules = this.deliveryScheduleRepository.findAll();
		final List<User> users = new ArrayList<User>();

		for (final DeliverySchedule d : allDeliverySchedules) {
			// get current time in user's local date & time
			final DateTime currentLocalTime =
					currentTime.withSecondOfMinute(0).withMillisOfSecond(0).withZone(DateTimeZone.forID(d.getTz()));

			final int currentDayOfWeek = currentLocalTime.getDayOfWeek();

			if (currentDayOfWeek == 7 || currentDayOfWeek == 6) {
				dayOfWeek = 1;
			} else {
				dayOfWeek = 0;
			}

			final LocalTime userSetTime = d.getTime(); // user's set time

			// convert local time to today's time & date and user's local time &
			// date
			final DateTime userSetTimeDateTime = userSetTime.toDateTimeToday().withZone(DateTimeZone.UTC);
			DateTime userLocalSetTime = userSetTimeDateTime.withZone(DateTimeZone.forID(d.getTz()));

			// Adjust for daylight savings since times saved to db remove the DST offset
			final DateTimeZone dayLightSavingsCheck = DateTimeZone.forID(d.getTz());
			if(!dayLightSavingsCheck.isStandardOffset(System.currentTimeMillis())){
				userLocalSetTime = userLocalSetTime.minusHours(1);
			}

			/*
			 * logger.info("before IF userLocalSetTime: " + userLocalSetTime.toString()); logger.info("before IF currentLocalTime: " +
			 * currentLocalTime.toString());
			 */

			// check if current time equals user set time for today's date
			if (userLocalSetTime.equals(currentLocalTime)) {
				// logger.info("User's local set time is equal to current local time.");
				// if the delivery day (weekend or weekday) equals the delivery
				// day in the schedule add user to list
				if (d.getDeliveryDay() == dayOfWeek) {
					users.add(d.getUser());
					// logger.info("Added user: " + d.getUser().getEmail());
				}
			}
		}

		final Iterator<User> userIterator = users.iterator();
		while (userIterator.hasNext()) {
			final User user = userIterator.next();
			this.sendEmail(user);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.natepaulus.dailyemail.web.service.interfaces.EmailService# retrySendingFailedMessages()
	 */
	@Override
	@Scheduled(cron = "0 0/5 * * * ?")
	public void retrySendingFailedMessages() {
		// logger.info("retry sending failed messages");
		final List<FailedMessages> failedMessages = this.failedMessagesRepository.findAll();

		for (final FailedMessages failedMsg : failedMessages) {
			final MimeMessagePreparator preparator = new MimeMessagePreparator() {

				@Override
				public void prepare(final MimeMessage mimeMessage) throws Exception {
					final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
					message.setTo(failedMsg.getToAddress());
					message.setFrom(EmailServiceImpl.this.environment.getRequiredProperty(DAILY_EMAIL_ADDRESS));
					message.setSubject("Daily News & Weather");

					message.setText(failedMsg.getMessage(), true);
				}
			};
			try {
				this.sender.send(preparator);
				this.failedMessagesRepository.delete(failedMsg);
				// logger.info("Successfully resent");
			} catch (final MailException e) {
				final int failedAttempts = failedMsg.getNumberFailedAttempts() + 1;
				failedMsg.setNumberFailedAttempts(failedAttempts);
				this.failedMessagesRepository.save(failedMsg);
				// logger.info("Failed again and incremented counter");
			}
		}

	}

	/**
	 * Send email to the user with the data they have requested.
	 *
	 * @param user the user
	 */
	private void sendEmail(final User user) {

		EmailData emailData = new EmailData();
		emailData.setToAddress(user.getEmail());
		emailData.setToName(user.getFirstName() + " " + user.getLastName());

		emailData = this.getWeatherConditions(emailData, user);
		this.getWeatherForecast(new EmailData(), user);
		this.getCurrentWeatherConditions(new EmailData(), user);
		this.getRssLinksForEmail(emailData, user);
		final EmailData data = emailData;
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("data", data);
		@SuppressWarnings("deprecation")
		final String messageText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, "email.vm", model);
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare(final MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				message.setTo(data.getToAddress());
				message.setFrom(EmailServiceImpl.this.environment.getRequiredProperty(DAILY_EMAIL_ADDRESS));
				message.setSubject("Daily News & Weather");
				message.setText(messageText, true);
			}
		};
		try {
			this.sender.send(preparator);
			this.logger.info("Message sent to: " + data.getToAddress());
		} catch (final MailException e) {
			final FailedMessages fm = new FailedMessages();
			fm.setToAddress(data.getToAddress());
			fm.setToName(data.getToName());
			fm.setMessage(messageText);
			fm.setErrorMessage(e.getMessage());
			fm.setNumberFailedAttempts(1);
			this.failedMessagesRepository.save(fm);
			this.logger.info("Email errored occured. Saved to Database.");
		}
	}

	/**
	 * Send error email.
	 *
	 * @param errorMessage the error message
	 */
	private void sendErrorEmail(final String errorMessage) {

		final MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare(final MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				message.setTo(EmailServiceImpl.this.environment.getRequiredProperty(ADMINISTRATOR));
				message.setFrom(EmailServiceImpl.this.environment.getRequiredProperty(DAILY_EMAIL_ADDRESS));
				message.setSubject("Error - Daily News & Weather");

				message.setText(errorMessage, true);
			}
		};
		this.sender.send(preparator);
		this.logger.info("Error Message sent!");
	}

	private void getRssLinksForEmail(EmailData data, User user){

		// grab first 5 news items from rss feeds and add to email data
		for(UserRssFeeds userFeed : user.getUserRssFeeds()){
			if(userFeed.getDeliver() == 1) {
				RssFeeds rssFeed = rssFeedsRepository.findOne(userFeed.getFeedId());

				try {
					final URLConnection connection = new URL(rssFeed.getUrl()).openConnection();
					connection
							.setRequestProperty("User-Agent",
									"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
					connection.connect();

					final InputStream is = connection.getInputStream();
					final InputSource source = new InputSource(is);
					final SyndFeedInput input = new SyndFeedInput();
					final SyndFeed feed = input.build(source);

					@SuppressWarnings("rawtypes")
					final Iterator iFeed = feed.getEntries().iterator();
					final int numLinks = 5;
					int counter = 0;
					final NewsFeed newsFeed = new NewsFeed();
					newsFeed.setFeedTitle(userFeed.getFeedName());
					while (iFeed.hasNext() && counter < numLinks) {

						SyndEntry entry = (SyndEntry) iFeed.next();
						if(null == entry.getDescription()){
							entry.setDescription(new SyndContentImpl());
							entry.getDescription().setValue(new String());
						}
						final NewsStory newsStory = new NewsStory(entry.getTitle(), entry.getLink(),
								entry.getDescription().getValue().replaceAll("\\<.*?>", ""));
						newsFeed.getNewsStories().add(newsStory);
						counter++;

					}
					data.getNewsFeeds().add(newsFeed);

				} catch (MalformedURLException e) {
					logger.error("URL is bad: " + e.getStackTrace());
				} catch (IOException e) {
					logger.error("IO Exception Occurred: " + e.getStackTrace());
				} catch (FeedException e) {
					logger.error("Soething is wrong with the feed: " + e.getStackTrace());
				}
			}
		}

	}

	private void setWindDirection(EmailData data, Double windDirectionInDegrees){
		if(windDirectionInDegrees == 999){
			data.getWxCurCond().setWindDirection("Variable");
		} else if(windDirectionInDegrees == 0 && data.getWxCurCond().getWindSpeed().equals("NA")){
			data.getWxCurCond().setWindDirection("Calm");
		} else {
			final int sector = (int) Math.round((windDirectionInDegrees % 360) / 22.5);
			data.getWxCurCond().setWindDirection(COMPASS_DIRECTIONS[sector]);
		}
	}

	private String convertCelsiusToFahrenheit(final String celsiusValue){
	    if(null == celsiusValue || "null".equals(celsiusValue)){
	        return N_A;
        } else {
            try {
                double tempInCelsius = Double.parseDouble(celsiusValue);
                final Long tempInFahrenheit = Math.round(tempInCelsius * 9 / 5 + 32);
                return tempInFahrenheit.toString();
            } catch (NumberFormatException e){
                logger.error("Bad temp value: ", e);
                return N_A;
            }
        }
    }

}
