package com.natepaulus.dailyemail.web.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.natepaulus.dailyemail.repository.RssFeedsRepository;
import com.natepaulus.dailyemail.repository.RssNewsLinksRepository;
import com.natepaulus.dailyemail.repository.entity.RssNewsLinks;
import com.natepaulus.dailyemail.repository.entity.User;
import com.natepaulus.dailyemail.repository.entity.UserRssFeeds;
import com.natepaulus.dailyemail.web.controller.ReaderController;
import com.natepaulus.dailyemail.web.service.UserTimeZone;
import com.natepaulus.dailyemail.web.service.interfaces.ReaderService;


/**
 * The Class ReaderServiceImpl.
 */
@Service
public class ReaderServiceImpl implements ReaderService {
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(ReaderController.class);
	
	@Resource
	RssFeedsRepository rssFeedsRepository;
	
	@Resource
	RssNewsLinksRepository rssNewsLinksRepository;
	
	@Override
	public Map<String, List<RssNewsLinks>> getNewsForReaderDisplay(User user) {
		DateTimeFormatter outFmt = DateTimeFormat
				.forPattern("MMM dd',' yyyy h:mm a");
		String userTz = UserTimeZone.getUserTimeZone(user);
		Set<UserRssFeeds> userRssFeeds = user.getUserRssFeeds();
		Pageable page = new PageRequest(0, 15);
		
		Map<String, List<RssNewsLinks>> userNewsData = new LinkedHashMap<String, List<RssNewsLinks>>();
		for(UserRssFeeds urf: userRssFeeds){
			
			List<RssNewsLinks> convertTime = rssNewsLinksRepository.findByFeedIdOrderByPubDateDesc(urf.getFeedId(), page);
			for(RssNewsLinks r : convertTime){
				DateTime utcPubDate = r.getPubDate();
				r.setPubDateToDisplay(outFmt.print(utcPubDate.withZone(DateTimeZone.forID(userTz))));
			}
				
			userNewsData.put(urf.getFeedName(),convertTime);
		}
		
		
		return userNewsData;
	}

	

}
