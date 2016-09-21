package com.guohao.Util;


import com.guohao.phoneweishi.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class Util {
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	public static NetworkInfo getActiveNetworkInfo(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return info;
	}
	public static DisplayMetrics getScreenInfo(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
	public static void postHandler(Handler handler, Object obj, int what) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		msg.obj = obj;
		handler.sendMessage(msg);
	}
	
	public static SharedPreferences getPreferences(Context c) {
		return PreferenceManager.getDefaultSharedPreferences(c);
	}
}
