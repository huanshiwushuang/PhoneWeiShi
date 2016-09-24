package com.guohao.phoneweishi;

import com.guohao.Util.Data;
import com.guohao.Util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class PhoneSecurity04 extends Activity {
	private CheckBox startBH;
	private SharedPreferences p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity04);
		
		initView();
		initShareData();
	}
	private void initShareData() {
		Boolean status = p.getBoolean(Data.K_Phone_Security_Start, Data.V_Phone_Security_Start);
		startBH.setChecked(status);
	}
	private void initView() {
		startBH = (CheckBox) findViewById(R.id.id_checkbox_start_bh);
		p = Util.getPreferences(PhoneSecurity04.this);
	}
	public void next(View view) {
		saveShareData();
		Util.showToast(this, "…Ë÷√ÕÍ≥…");
		PhoneSecurityEnd.actionStart(PhoneSecurity04.this);
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	public void last(View view) {
		saveShareData();
		PhoneSecurity03.actionStart(PhoneSecurity04.this);
		finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	private void saveShareData() {
		Editor editor = p.edit();
		if (startBH.isChecked()) {
			editor.putBoolean(Data.K_Phone_Security_Start, true);
		}else {
			editor.putBoolean(Data.K_Phone_Security_Start, Data.V_Phone_Security_Start);
		}
		editor.commit();
	}
	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity04.class);
		c.startActivity(intent);
	}
}
