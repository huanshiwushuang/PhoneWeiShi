package com.guohao.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;

public class HttpUtil {
	public static final int GET_VERSION_INFO_ERROR = -1;
	public static final int GET_VERSION_INFO_OK = 0; 
	
	public static final int GET_NETWORK_FILE_ERROR = -2;
	public static final int GET_NETWORK_FILE_OK = 1;
	public static final int GET_NETWORK_FILE_ALL_SIZE = 2;
	public static final int GET_NETWORK_FILE_CURRENT_SIZE = 3;
	
	public static void getVersionInfo(final String address, final Handler handler) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection = HttpUtil.getGetHttpUrlConnection(address);
				InputStream in = null;
				try {
					if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
						in = connection.getInputStream();
						String result = StreamUtil.inputstreamToString(in, "UTF-8");
						
						Util.postHandler(handler, result, GET_VERSION_INFO_OK);
					}else {
						Util.postHandler(handler, connection.getResponseCode(), GET_VERSION_INFO_ERROR);
					}
				} catch (IOException e) {
					Util.postHandler(handler, e.toString(), GET_VERSION_INFO_ERROR);
				} finally {
					connection.disconnect();
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
	
	public static void getNetworkFile(final String address, final String savePath, final Handler handler) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection = getGetHttpUrlConnection(address);
				InputStream in = null;
				FileOutputStream fileOut = null;
				try {
					if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
						//获取网络文件大小
						double size = connection.getContentLength()/1024.0;
						Util.postHandler(handler, size, GET_NETWORK_FILE_ALL_SIZE);
						
						//开始下载网络文件
						in = connection.getInputStream();
						fileOut = new FileOutputStream(savePath);
						byte[] b = new byte[30*1024];
						
						double k = 0;
						int len = 0;
						while ((len = in.read(b, 0, b.length)) != -1) {
							fileOut.write(b, 0, len);
							k += len;
							Util.postHandler(handler, k/1024.0, GET_NETWORK_FILE_CURRENT_SIZE);
						}
						fileOut.flush();
						Util.postHandler(handler, savePath, GET_NETWORK_FILE_OK);
					}else {
						Util.postHandler(handler, connection.getResponseCode(), GET_NETWORK_FILE_ERROR);
					}
				} catch (IOException e) {
					Util.postHandler(handler, e.toString(), GET_NETWORK_FILE_ERROR);
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fileOut != null) {
						try {
							fileOut.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		}).start();
	}
	
	public static HttpURLConnection getGetHttpUrlConnection(String address) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(address);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(10*1000);
			connection.setConnectTimeout(10*1000);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static HttpURLConnection getPOSTHttpUrlConnection(String address) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(address);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(true);
			connection.setUseCaches(false);
			connection.setReadTimeout(10*1000);
			connection.setConnectTimeout(10*1000);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
