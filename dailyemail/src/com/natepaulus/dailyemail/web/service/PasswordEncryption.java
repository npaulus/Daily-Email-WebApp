package com.natepaulus.dailyemail.web.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {
	
	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	
	public static String toSHA1(String password) {
		byte[] passInBytes = null;
		try{
			passInBytes = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e){
			passInBytes = password.getBytes();
		}
		MessageDigest md = null;
		String hexPassword = "";
		try {
			md = MessageDigest.getInstance("SHA-1");
			hexPassword = PasswordEncryption.asHex(md.digest(passInBytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		

		return hexPassword;
	}

	private static String asHex(byte[] buf) {

		char[] chars = new char[2 * buf.length];
		for (int i = 0; i < buf.length; ++i) {
			chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
			chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
		}
		return new String(chars);
	}

}
