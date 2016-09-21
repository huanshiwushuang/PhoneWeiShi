package com.guohao.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class StringUtil {
	public static String getFileNameFromUrl(String url) {
		int i = url.lastIndexOf("/");
		return url.substring(i+1, url.length());
	}
	public static Boolean isEmpty(String string) {
		if (string == null || string.equals("") || string == "") {
			return true;
		}
		return false;
	}
	public static String encodeMD5(String string) {
		MessageDigest digest = null;
		StringBuilder builder = null;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] bs = digest.digest(string.getBytes());
			builder = new StringBuilder();
			for (int i = 0; i < bs.length; i++) {
				int j = bs[i]&0xff;
				String hex = Integer.toHexString(j);
				if (hex.length() < 2) {
					hex = "0"+hex;
				}
				builder.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
