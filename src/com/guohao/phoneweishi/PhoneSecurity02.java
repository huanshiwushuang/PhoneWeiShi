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
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PhoneSecurity02 extends Activity implements OnCheckedChangeListener {
	private CheckBox cBox;
	private SharedPreferences p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity02);
		
		initView();
		initShareData();
		setListener();
	}
	private void setListener() {
		cBox.setOnCheckedChangeListener(this);
	}
	private void initShareData() {
		String string = p.getString(Data.K_Phone_SIM_Serial, Data.V_Phone_SIM_Serial);
		if (string.equals(Data.V_Phone_SIM_Serial)) {
			cBox.setChecked(false);
		}else {
			cBox.setChecked(true);
		}
	}
	private void initView() {
		cBox = (CheckBox) findViewById(R.id.id_checkbox_bd_phone);
		p = Util.getPreferences(PhoneSecurity02.this);
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Editor editor = p.edit();
		if (isChecked) {
			TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String string = manager.getSimSerialNumber();
			editor.putString(Data.K_Phone_SIM_Serial, string);
		}else {
			editor.putString(Data.K_Phone_SIM_Serial, Data.V_Phone_SIM_Serial);
		}
		editor.commit();
	}
	
	
	public void next(View view) {
		PhoneSecurity03.actionStart(PhoneSecurity02.this);
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	public void last(View view) {
		PhoneSecurity01.actionStart(PhoneSecurity02.this);
		finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity02.class);
		c.startActivity(intent);
	}
}
