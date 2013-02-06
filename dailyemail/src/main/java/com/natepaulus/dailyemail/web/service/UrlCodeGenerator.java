package com.natepaulus.dailyemail.web.service;

import java.security.SecureRandom;
import java.math.BigInteger;

public class UrlCodeGenerator {

	 private SecureRandom random = new SecureRandom();

	  public String nextSessionId()
	  {
	    return new BigInteger(130, random).toString(32);
	  }

}
