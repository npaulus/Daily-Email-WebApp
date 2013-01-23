package com.natepaulus.dailyemail.web.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.natepaulus.dailyemail.web.controller.ReaderController;

/**
 * The Class PasswordEncryption encrypts a user password to SHA1 so it isn't stored plain text anywhere.
 */
public class PasswordEncryption {
	
	/** The logger. */
	final static Logger logger = LoggerFactory.getLogger(ReaderController.class);
	
	/** The Constant HEX_CHARS. */
	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	
	/**
	 * To sha1 converts a string to an SHA1 representation of that string
	 *
	 * @param password the password
	 * @return the SHA1 string generated from the password
	 */
	public static String toSHA1(String password) {
		byte[] passInBytes = null;
		
		
		passInBytes = password.getBytes();
		
		MessageDigest md = null;
		String hexPassword = "";
		try {
			md = MessageDigest.getInstance("SHA-1");
			hexPassword = PasswordEncryption.asHex(md.digest(passInBytes));
		} catch (NoSuchAlgorithmException e) {
			logger.error("There is an algorithm error: ", e);
		}

		

		return hexPassword;
	}

	/**
	 * Converts bytes to hex characters
	 *
	 * @param buf the byte[] buf
	 * @return the string of hex characters
	 */
	private static String asHex(byte[] buf) {

		char[] chars = new char[2 * buf.length];
		for (int i = 0; i < buf.length; ++i) {
			chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
			chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
		}
		return new String(chars);
	}

}
