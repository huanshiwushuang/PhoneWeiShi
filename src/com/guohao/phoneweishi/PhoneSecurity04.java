package com.guohao.phoneweishi;

import com.guohao.Util.Data;
import com.guohao.Util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class PhoneSecurity04 extends Activity implements OnCheckedChangeListener {
	private String serial;
	private String securityNum;
	private Boolean securityStart;
	
	private CheckBox startBH;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity04);
		
		initView();
		initIntentData();
		setListener();
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		securityStart = isChecked;
	}
	private void setListener() {
		startBH.setOnCheckedChangeListener(this);
	}
	private void initIntentData() {
		Intent intent = getIntent();
		serial = intent.getStringExtra(Data.K_Phone_Security_Num);
		securityNum = intent.getStringExtra(Data.K_Phone_Security_Num);
		securityStart = intent.getBooleanExtra(Data.K_Phone_Security_Start, Data.V_Phone_Security_Start);
		startBH.setChecked(securityStart);
	}
	private void initView() {
		startBH = (CheckBox) findViewById(R.id.id_checkbox_start_bh);
	}
	public void next(View view) {
		if (securityStart) {
			Editor editor = Util.getPreferences(PhoneSecurity04.this).edit();
			editor.putString(Data.K_Phone_SIM_Serial, serial);
			editor.putString(Data.K_Phone_Security_Num, securityNum);
			editor.putBoolean(Data.K_Phone_Security_Start, securityStart);
			editor.commit();
		}
		Util.showToast(this, "…Ë÷√ÕÍ≥…");
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void last(View view) {
		PhoneSecurity03.actionStart(PhoneSecurity04.this,serial,securityNum,securityStart);
		finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}

	public static void actionStart(Context c, String serial, String securityNum, Boolean securityStart) {
		Intent intent = new Intent(c, PhoneSecurity04.class);
		intent.putExtra(Data.K_Phone_SIM_Serial, serial);
		intent.putExtra(Data.K_Phone_Security_Num, securityNum);
		intent.putExtra(Data.K_Phone_Security_Start, securityStart);
		c.startActivity(intent);
	}
}
