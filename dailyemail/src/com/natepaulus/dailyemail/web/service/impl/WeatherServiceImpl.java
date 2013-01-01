package com.natepaulus.dailyemail.web.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.natepaulus.dailyemail.repository.Weather;
import com.natepaulus.dailyemail.repository.WeatherRepository;
import com.natepaulus.dailyemail.web.service.interfaces.WeatherService;
import com.natepaulus.nws.DataType;
import com.natepaulus.nws.Dwml;
import com.natepaulus.nws.LocationType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	WeatherRepository weatherRepository;

	@Override
	public Weather setInitialWeatherLocation(String zip) {

		Client zipCode = Client.create();
		zipCode.setFollowRedirects(true);
		WebResource r = zipCode
				.resource("http://graphical.weather.gov/xml/sample_products/browser_interface/ndfdXMLclient.php");
		MultivaluedMap queryParams = new MultivaluedMapImpl();
		queryParams.add("listZipCodeList", zip);
		r.accept(MediaType.APPLICATION_XML);

		Dwml dwml = r.queryParams(queryParams).get(Dwml.class);

		String result = "";
		if (!dwml.getLatLonList().equals(null)) {
			String[] locationCoordinates = dwml.getLatLonList().split("/s");
			String[] latLong = locationCoordinates[0].split(",");

			System.out.println("Lat: " + latLong[0]);
			System.out.println("Long: " + latLong[1]);
			Client locName = Client.create();
			WebResource locationName = locName
					.resource("http://forecast.weather.gov/MapClick.php");
			MultivaluedMap locationNameParams = new MultivaluedMapImpl();
			locationNameParams.add("lat", latLong[0]);
			locationNameParams.add("lon", latLong[1]);
			locationNameParams.add("unit", "0");
			locationNameParams.add("lg", "english");
			locationNameParams.add("FcstType", "dwml");
			locationName.accept(MediaType.APPLICATION_XML);
			Dwml areaName = locationName.queryParams(locationNameParams).get(
					Dwml.class);

			List<DataType> list = areaName.getData();
			Iterator<DataType> dataTypeIterator = list.iterator();

			while (dataTypeIterator.hasNext()) {
				DataType temp = dataTypeIterator.next();
				if (temp.getType().equals("forecast")) {
					List<LocationType> locTypeList = temp.getLocation();
					Iterator<LocationType> locTypeIterator = locTypeList
							.iterator();
					while (locTypeIterator.hasNext()) {
						LocationType temp1 = locTypeIterator.next();
						if (temp1.getLocationKey().equals("point1")) {
							if (temp1.getAreaDescription() != null) {
								result = temp1.getAreaDescription();
							} else if (temp1.getDescription() != null) {
								result = temp1.getDescription();
							}
						}
					}
				}
			}
						
			Weather weather = new Weather();
			weather.setDeliver_pref(0);
			weather.setLocation_name(result);
			weather.setLatitude(latLong[0]);
			weather.setLongitude(latLong[1]);
			
			return weather;
		} else {
			Weather weather = new Weather();
			weather.setDeliver_pref(0);
			weather.setLocation_name("");
			weather.setLatitude("0.0");
			weather.setLongitude("0.0");
			
			return weather;
		}
	}
}
