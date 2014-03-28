package com.natepaulus.dailyemail.web.service.interfaces;

import java.util.List;
import java.util.Map;

import com.natepaulus.dailyemail.repository.entity.RssNewsLinks;

/**
 * The Interface ReaderService.
 */
public interface ReaderService {

	public Map<String, List<RssNewsLinks>> getNewsForReaderDisplay(long user);

}
