package com.guohao.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {
	public static String inputstreamToString(InputStream in, String encode) {
		String line = null;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, encode));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return builder.toString();
	}
	
	//获取本地文件大小
	public static long getLocalFileSize(String path) {
		long fileSize = -1;
		File file = new File(path);
		if (file.exists()) {
			fileSize = file.length();
		}
		return fileSize;
	}
}
