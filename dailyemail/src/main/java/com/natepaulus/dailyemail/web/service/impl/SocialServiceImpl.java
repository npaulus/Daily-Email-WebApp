package com.natepaulus.dailyemail.web.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.natepaulus.dailyemail.repository.SocialNetworkDataRepository;
import com.natepaulus.dailyemail.repository.UserRepository;
import com.natepaulus.dailyemail.repository.entity.NewsLink;
import com.natepaulus.dailyemail.repository.entity.SocialNetworkData;
import com.natepaulus.dailyemail.repository.entity.User;
import com.natepaulus.dailyemail.web.domain.NewsFeed;
import com.natepaulus.dailyemail.web.domain.NewsStory;
import com.natepaulus.dailyemail.web.service.interfaces.SocialService;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

/**
 * The Class SocialServiceImpl.
 */
@Service
public class SocialServiceImpl implements SocialService {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(SocialServiceImpl.class);

	/** The user repository. */
	@Autowired
	UserRepository userRepository;

	/** The social data repository. */
	@Autowired
	SocialNetworkDataRepository socialDataRepository;

	/** The factory locator for finding an appropriate connection. */
	@Autowired
	ConnectionFactoryLocator locator;

	/* (non-Javadoc)
	 * @see com.natepaulus.dailyemail.web.service.interfaces.SocialService#connectToFacebook()
	 */
	@Override
	public String connectToFacebook() {

		FacebookConnectionFactory connectionFactory = (FacebookConnectionFactory) locator
				.getConnectionFactory(Facebook.class);
		OAuth2Operations oauthOperations = connectionFactory
				.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri("http://tempnate.dyndns-free.com:8080/dailyemail/social/facebook/connect");
		params.setScope("read_stream,user_activities,user_events,user_questions,user_videos,user_interests,user_notes,user_status," +
		"user_likes,user_photos,user_groups,friends_groups,friends_likes,friends_photos,friends_activities,friends_events,friends_status,status_update");
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(
				GrantType.AUTHORIZATION_CODE, params);

		return authorizeUrl;
	}

	/* (non-Javadoc)
	 * @see com.natepaulus.dailyemail.web.service.interfaces.SocialService#saveFacebookInformation(java.lang.String, com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	public User saveFacebookInformation(String authorizationCode, User user) {
		logger.info("User email in saveFaceBookInformation:" + user.getEmail());

		FacebookConnectionFactory connectionFactory = (FacebookConnectionFactory) locator
				.getConnectionFactory(Facebook.class);
		OAuth2Operations oauthOperations = connectionFactory
				.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations
				.exchangeForAccess(
						authorizationCode,
						"http://tempnate.dyndns-free.com:8080/dailyemail/social/facebook/connect",
						null);
		Connection<Facebook> connection = connectionFactory
				.createConnection(accessGrant);

		ConnectionData data = connection.createData();
		SocialNetworkData snd = new SocialNetworkData();

		snd.setAccessToken(data.getAccessToken());
		snd.setDisplayName(data.getDisplayName());
		snd.setExpireTime(data.getExpireTime());
		snd.setImageUrl(data.getImageUrl());
		snd.setProfileUrl(data.getProfileUrl());
		snd.setProviderId(data.getProviderId());
		snd.setProviderUserId(data.getProviderUserId());
		snd.setRefreshToken(data.getRefreshToken());
		snd.setSecret(data.getSecret());
		snd.setUser(user);

		logger.info(snd.toString());

		Set<SocialNetworkData> setSocialNetworkData;

		if (user.getSocialNetworkData() == null) {
			setSocialNetworkData = new HashSet<SocialNetworkData>();
		} else {
			setSocialNetworkData = user.getSocialNetworkData();
		}

		setSocialNetworkData.add(snd);
		user.setSocialNetworkData(setSocialNetworkData);

		return userRepository.save(user);

	}

	/* (non-Javadoc)
	 * @see com.natepaulus.dailyemail.web.service.interfaces.SocialService#getDataForDisplay(com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	public Map<String, Object> getDataForDisplay(User user) {
		logger.info("Inside SocialService getDataForDisplay");
		Map<String, Object> socialDataMap = new HashMap<String, Object>();
		ArrayList<SocialNetworkData> snd = socialDataRepository
				.findByIdusers(user.getId());
		logger.info("ArrayList Size: " + snd.size());
		logger.info("User ID: " + user.getId());
		for (SocialNetworkData s : snd) {
			logger.info("Provider id: " + s.getProviderId());
			
			switch(s.getProviderId()){
			case "facebook" :
				logger.info("Inside first switch case: ");
				FacebookTemplate facebookTemplate = new FacebookTemplate(s.getAccessToken());
				List<Post> userFeed = facebookTemplate.feedOperations().getHomeFeed(0, 10);
				socialDataMap.put("facebook", userFeed);
				logger.info("SocialDataMap: " + socialDataMap.toString());
				break;
			}
		}
		
		return socialDataMap;

	}

	/* (non-Javadoc)
	 * @see com.natepaulus.dailyemail.web.service.interfaces.SocialService#getRssNewsForReader(com.natepaulus.dailyemail.repository.User)
	 */
	@Override
	public ArrayList<NewsFeed> getRssNewsForReader(User user) {
		
		ArrayList<NewsFeed> newsFeeds = new ArrayList<NewsFeed>();
		for(NewsLink n : user.getNewsLink()){
			
				NewsFeed newsFeed = new NewsFeed();
				newsFeed.setFeedTitle(n.getSource_name());
				try {
					
	                /*URL xmlUrl = new URL(n.getUrl());
	                XmlReader reader = new XmlReader(xmlUrl);
	                SyndFeed feed = new SyndFeedInput().build(reader);*/
					
					String url = n.getUrl();
					InputStream is = new URL(url).openConnection().getInputStream();
					InputSource source = new InputSource(is);
					SyndFeedInput input = new SyndFeedInput();
					SyndFeed feed = input.build(source);
					
	                @SuppressWarnings("rawtypes")
					Iterator iFeed = feed.getEntries().iterator();
	                int count = 0;
	                int max = 5;
	                
	                while(iFeed.hasNext() && count < max) {
	                    
	                	SyndEntry entry = (SyndEntry) iFeed.next();
	                    String title = entry.getTitle();
	                    logger.info("Article Title: " + title);
	                    String link = entry.getLink();
	                    String desc = entry.getDescription().getValue().replaceAll("\\<.*?>", "");
	                    NewsStory newsStory = new NewsStory(title, link, desc);
	                    newsFeed.getNewsStories().add(newsStory);
	                    count++;
	                    
	                }              
	                
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
				
				newsFeeds.add(newsFeed);
			
		}
		
		
		return newsFeeds;
	}
}
