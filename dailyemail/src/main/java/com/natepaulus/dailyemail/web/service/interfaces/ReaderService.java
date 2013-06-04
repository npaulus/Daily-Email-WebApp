package com.natepaulus.dailyemail.web.service.interfaces;

import java.util.List;
import java.util.Map;

import com.natepaulus.dailyemail.repository.entity.RssNewsLinks;
import com.natepaulus.dailyemail.repository.entity.User;
import com.natepaulus.dailyemail.web.domain.JSONRssNewsLinks;


/**
 * The Interface ReaderService.
 */
public interface ReaderService {
	
	public Map<String, List<RssNewsLinks>> getNewsForReaderDisplay(User user);

	List<JSONRssNewsLinks> loadAdditionalNewsLinks(User user, long feedId,
			int pageNumber);
	
}
