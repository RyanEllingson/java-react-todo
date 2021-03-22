package com.ryan.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashGenerator {
	
	public static String HashPassword(String inputPass) {
//		generate random salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String result = generateHash(inputPass, salt);
		result += "/";
		for (int i=0; i<salt.length; i++) {
			result += salt[i];
			if (i < salt.length - 1) {
				result += ",";
			}
		}
		return result;
	}

	private static String generateHash(String inputStr, byte[] salt) {
		String result = "";
//		generate hash from input String
		KeySpec spec = new PBEKeySpec(inputStr.toCharArray(), salt, 65536, 128);
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = factory.generateSecret(spec).getEncoded();
			for (int i=0; i<hash.length; i++) {
				result += hash[i];
				if (i < hash.length - 1) {
					result += ",";
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean comparePasswords(String hashedPass, String inputPass, String salt) {
		String[] saltArr = salt.split(",");
		byte[] saltBytes = new byte[saltArr.length];
		for (int i=0; i<saltArr.length; i++) {
			saltBytes[i] = Byte.parseByte(saltArr[i]);
		}
		String hash = generateHash(inputPass, saltBytes);
		return hash.equals(hashedPass);
	}
}
