package com.natepaulus.dailyemail.web.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.natepaulus.dailyemail.repository.entity.Weather;
import com.natepaulus.dailyemail.web.service.interfaces.WeatherService;
import com.natepaulus.nws.DataType;
import com.natepaulus.nws.Dwml;
import com.natepaulus.nws.LocationType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The Class WeatherServiceImpl.
 */
@Service
@PropertySource("classpath:google.properties")
public class WeatherServiceImpl implements WeatherService {
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

	@Resource
	private Environment environment;

	final private static String GOOGLE_API_KEY = "api";

	/* (non-Javadoc)
	 * @see com.natepaulus.dailyemail.web.service.interfaces.WeatherService#setInitialWeatherLocation(java.lang.String)
	 */
	@Override
	public Weather setInitialWeatherLocation(String zip) {

		Map<String, String> map = new HashMap<String, String>();
		map = getWeatherLocationData(zip, "0");

		Weather weather = new Weather();
		weather.setDeliver_pref(Integer.parseInt(map.get("DeliveryPref")));
		weather.setLocation_name(map.get("LocationName"));
		weather.setLatitude(map.get("Lat"));
		weather.setLongitude(map.get("Long"));

		return weather;
	}

	/* (non-Javadoc)
	 * @see com.natepaulus.dailyemail.web.service.interfaces.WeatherService#updateWeatherLocation(java.lang.String, com.natepaulus.dailyemail.repository.Weather)
	 */
	public Weather updateWeatherLocation(String zipCode, Weather weather){
		Map<String, String> map = new HashMap<String, String>();
		String del = weather.getDeliver_pref() + "";
		map = getWeatherLocationData(zipCode, del);
		
		weather.setDeliver_pref(Integer.parseInt(map.get("DeliveryPref")));
		weather.setLocation_name(map.get("LocationName"));
		weather.setLatitude(map.get("Lat"));
		weather.setLongitude(map.get("Long"));
		
		return weather;
	}
	
	/**
	 * Gets the weather location data based on a zip code and stores persists it in the database.
	 *
	 * @param zip the zipcode
	 * @param deliveryPref the delivery preference the user selected
	 * @return the weather location data
	 */
	private Map<String, String> getWeatherLocationData(final String zip,
			final String deliveryPref) {

		final GeoApiContext context = new GeoApiContext().setApiKey(environment.getProperty(GOOGLE_API_KEY));
		GeocodingResult[] results = new GeocodingResult[0];
		try {
			results = GeocodingApi.geocode(context,
					zip).await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null != results && results.length >= 1 && results[0].addressComponents != null
				&& results[0].addressComponents.length > 3 && null != results[0].geometry) {

			String city = StringUtils.EMPTY;
			String state = StringUtils.EMPTY;

			for(AddressComponent addressComponent : results[0].addressComponents){
				for(AddressComponentType addressComponentType : addressComponent.types){
					switch (addressComponentType.toString()) {
						case "locality":
							city = addressComponent.longName;
							break;
						case "administrative_area_level_1":
							state = addressComponent.shortName;
							break;
					}
				}
			}
			final String latitude = String.valueOf(results[0].geometry.location.lat);
			final String longitude = String.valueOf(results[0].geometry.location.lng);

			Map<String, String> map = new HashMap<String, String>();
			map.put("LocationName", city + ", " + state);
			map.put("Lat", latitude);
			map.put("Long", longitude);
			map.put("DeliveryPref", deliveryPref);
			return map;

		} else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("LocationName", "");
			map.put("Lat", "0.0");
			map.put("Long", "0.0");
			map.put("DeliveryPref", "0");
			return map;
		}
	}
}
