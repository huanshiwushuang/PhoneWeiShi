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
			Log.d("guohao", i+" ���ṩ��"+list.get(i));
		}
		
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				Util.showToast(LocationActivity.this, "״̬�ı�");
			}
			@Override
			public void onProviderEnabled(String provider) {
				Util.showToast(LocationActivity.this, "GPS�ѿ���");
			}
			@Override
			public void onProviderDisabled(String provider) {
				Util.showToast(LocationActivity.this, "GPS�ѹر�");
			} 
			@Override
			public void onLocationChanged(Location location) {
				if (location == null) {
					Util.showToast(LocationActivity.this, "location Ϊnull");
				}else {
					content = "���ȣ�"+location.getLongitude()+"\n"+"γ�ȣ�"+location.getLatitude()+"\n"+
							"���Σ�"+location.getAltitude()+"��ȷ�ȣ�"+location.getAccuracy();
					Log.d("guohao", "���ȣ�"+location.getLongitude());
					Log.d("guohao", "γ�ȣ�"+location.getLatitude());
					Log.d("guohao", "���Σ�"+location.getAltitude());
					Log.d("guohao", "��ȷ�ȣ�"+location.getAccuracy());
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
