package com.natepaulus.dailyemail.web.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

/**
 * The Class SocialConfig configures spring social settings.
 */
@Configuration
public class SocialConfig {

	@Resource
    private Environment environment;
	
	/** The properties for adding connection factories*/
	private final static String FB_CLIENTID = "facebook.clientId";
	private final static String FB_CLIENT_SECRET = "facebook.clientSecret";
	
	
	/**
	 * Connection factory locator is used to add multiple connection factories
	 * that can be looked up depending on the social api that is needed
	 * (Facebook, Twitter, LinkedIn, etc).
	 * 
	 * @return the connection factory locator
	 */
	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();

		registry.addConnectionFactory(new FacebookConnectionFactory(
				 environment.getRequiredProperty(FB_CLIENTID), environment.getRequiredProperty(FB_CLIENT_SECRET)));

		return registry;
	}

}
