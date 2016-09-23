package com.guohao.phoneweishi;

import com.guohao.Util.Data;
import com.guohao.Util.StringUtil;
import com.guohao.Util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PhoneSecurity02 extends Activity implements OnCheckedChangeListener {
	private CheckBox cBox;
	private String serial;
	private String securityNum;
	private Boolean securityStart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity02);
		
		initView();
		initShareData();
		initIntentData();
		setListener();
	}
	
	private void initIntentData() {
		Intent intent = getIntent();
		serial = intent.getStringExtra(Data.K_Phone_SIM_Serial);
		securityNum = intent.getStringExtra(Data.K_Phone_Security_Num);
		securityStart = intent.getBooleanExtra(Data.K_Phone_Security_Start, Data.V_Phone_Security_Start);
		if (StringUtil.isEmpty(serial)) {
			cBox.setChecked(false);
		}else {
			cBox.setChecked(true);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			serial = manager.getSimSerialNumber();
		}else {
			serial = Data.V_Phone_SIM_Serial;
		}
	}
	private void setListener() {
		cBox.setOnCheckedChangeListener(this);
	}
	private void initShareData() {
		SharedPreferences p = Util.getPreferences(this);
		String serial = p.getString(Data.K_Phone_SIM_Serial, Data.V_Phone_SIM_Serial);
		if (serial.equals(Data.V_Phone_SIM_Serial)) {
			cBox.setChecked(false);
		}else {
			Util.showToast(PhoneSecurity02.this, "붙붙1："+serial);
			cBox.setChecked(true);
		}
		Util.showToast(PhoneSecurity02.this, "붙붙2："+serial);
	}
	private void initView() {
		cBox = (CheckBox) findViewById(R.id.id_checkbox_bd_phone);
	}

	
	
	public void next(View view) {
		PhoneSecurity03.actionStart(PhoneSecurity02.this,serial,securityNum,securityStart);
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void last(View view) {
		PhoneSecurity01.actionStart(PhoneSecurity02.this,serial,securityNum,securityStart);
		finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}

	public static void actionStart(Context c, String serial, String securityNum, Boolean securityStart) {
		Intent intent = new Intent(c, PhoneSecurity02.class);
		intent.putExtra(Data.K_Phone_SIM_Serial, serial);
		intent.putExtra(Data.K_Phone_Security_Num, securityNum);
		intent.putExtra(Data.K_Phone_Security_Start, securityStart);
		c.startActivity(intent);
	}

}
