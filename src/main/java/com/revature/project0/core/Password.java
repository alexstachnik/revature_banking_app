package com.revature.project0.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.iharder.Base64;

public class Password {

	public static String hash(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes());
			return Base64.encodeBytes(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException();
		}
		
	}
	
}
