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
import com.natepaulus.dailyemail.repository.UserRepository;
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

	@Resource
	UserRepository userRepository;

	@Override
	public Map<String, List<RssNewsLinks>> getNewsForReaderDisplay(final long userId) {
		final User user = this.userRepository.findOne(userId);

		final DateTimeFormatter outFmt = DateTimeFormat.forPattern("MMM dd',' yyyy h:mm a");
		final String userTz = UserTimeZone.getUserTimeZone(user);
		final Set<UserRssFeeds> userRssFeeds = user.getUserRssFeeds();
		final Pageable page = new PageRequest(0, 15);

		final Map<String, List<RssNewsLinks>> userNewsData = new LinkedHashMap<String, List<RssNewsLinks>>();
		for (final UserRssFeeds urf : userRssFeeds) {

			final List<RssNewsLinks> convertTime = this.rssNewsLinksRepository.findByFeedIdOrderByPubDateDesc(urf.getFeedId(), page);
			for (final RssNewsLinks r : convertTime) {
				final DateTime utcPubDate = r.getPubDate();
				r.setPubDateToDisplay(outFmt.print(utcPubDate.withZone(DateTimeZone.forID(userTz))));
			}

			userNewsData.put(urf.getFeedName(), convertTime);
		}

		return userNewsData;
	}

}
