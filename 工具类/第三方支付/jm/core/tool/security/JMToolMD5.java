package com.cn.jm.core.tool.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JMToolMD5 {

	private static MessageDigest md5 = null;
	static {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static String getMD5(String str) {
		byte[] data = getMD5(str.getBytes());
		BigInteger bi = new BigInteger(data).abs();
		String result = bi.toString(36);
		return result;
	}

	public static byte[] getMD5(byte[] data) {
		MessageDigest digest;
		try {
			digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(data);
			byte[] hash = digest.digest();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	public static final String MD5_32bit(String readyEncryptStr) throws NoSuchAlgorithmException {
		if (readyEncryptStr != null) {
			md5.update(readyEncryptStr.getBytes());
			// Get cipher text
			byte[] b = md5.digest();
			StringBuilder su = new StringBuilder();
			for (int offset = 0, bLen = b.length; offset < bLen; offset++) {
				String haxHex = Integer.toHexString(b[offset] & 0xFF);
				if (haxHex.length() < 2) {
					su.append("0");
				}
				su.append(haxHex);
			}
			return su.toString();
		} else {
			return null;
		}
	}

}
