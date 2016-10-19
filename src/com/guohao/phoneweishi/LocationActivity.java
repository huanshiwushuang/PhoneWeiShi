package com.guohao.phoneweishi;

import java.util.List;

import com.guohao.Util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationActivity extends Activity {
	private TextView textview;
	private String content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		
		initView(); 
		getLocation();
	}

	private void getLocation() {
		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		List<String> list = manager.getProviders(true);
		for (int i = 0; i < list.size(); i++) {
			Log.d("guohao", i+" 可提供："+list.get(i));
		}
		
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				Util.showToast(LocationActivity.this, "状态改变");
			}
			@Override
			public void onProviderEnabled(String provider) {
				Util.showToast(LocationActivity.this, "GPS已开启");
			}
			@Override
			public void onProviderDisabled(String provider) {
				Util.showToast(LocationActivity.this, "GPS已关闭");
			} 
			@Override
			public void onLocationChanged(Location location) {
				if (location == null) {
					Util.showToast(LocationActivity.this, "location 为null");
				}else {
					content = "经度："+location.getLongitude()+"\n"+"纬度："+location.getLatitude()+"\n"+
							"海拔："+location.getAltitude()+"精确度："+location.getAccuracy();
					Log.d("guohao", "经度："+location.getLongitude());
					Log.d("guohao", "纬度："+location.getLatitude());
					Log.d("guohao", "海拔："+location.getAltitude());
					Log.d("guohao", "精确度："+location.getAccuracy());
					textview.setText(content);
				}
			}
		});
	}

	private void initView() {
		textview = (TextView) findViewById(R.id.id_textview_location);
	}
	public static void actionStart(Context context) {
		Intent intent = new Intent(context, LocationActivity.class);
		context.startActivity(intent);
	}
}
