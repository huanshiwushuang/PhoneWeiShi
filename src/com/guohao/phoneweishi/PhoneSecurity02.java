package com.guohao.phoneweishi;

import com.guohao.Util.Data;
import com.guohao.Util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PhoneSecurity02 extends BaseActivityPhoneSecuritySetting {
	private CheckBox cBox;
	private SharedPreferences p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity02);
		
		initView();
		initShareData();
	}
	private void initShareData() {
		String string = p.getString(Data.K_Phone_SIM_Serial, Data.V_Phone_SIM_Serial);
		if (!string.equals(Data.V_Phone_SIM_Serial)) {
			cBox.setChecked(true);
		}
	}
	private void initView() {
		cBox = (CheckBox) findViewById(R.id.id_checkbox_bd_phone);
		p = Util.getPreferences(PhoneSecurity02.this);
		
	}
	
	public void next(View view) {
		saveShareData();
		PhoneSecurity03.actionStart(PhoneSecurity02.this);
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	public void last(View view) {
		saveShareData();
		PhoneSecurity01.actionStart(PhoneSecurity02.this);
		finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	private void saveShareData() {
		Editor editor = p.edit();
		if (cBox.isChecked()) {
			TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String string = manager.getSimSerialNumber();
			editor.putString(Data.K_Phone_SIM_Serial, string);
		}else {
			editor.putString(Data.K_Phone_SIM_Serial, Data.V_Phone_SIM_Serial);
		}
		editor.commit();
	}
	
	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity02.class);
		c.startActivity(intent);
	}
	
}
