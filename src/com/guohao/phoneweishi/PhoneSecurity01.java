package com.guohao.phoneweishi;

import com.guohao.Util.Data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PhoneSecurity01 extends Activity {
	private String serial;
	private String securityNum;
	private Boolean securityStart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity01);
		
		initIntentData();
	}
	
	private void initIntentData() {
		Intent intent = getIntent();
		serial = intent.getStringExtra(Data.K_Phone_SIM_Serial);
		securityNum = intent.getStringExtra(Data.K_Phone_Security_Num);
		securityStart = intent.getBooleanExtra(Data.K_Phone_Security_Start, Data.V_Phone_Security_Start);
	}

	public void next(View view) {
		PhoneSecurity02.actionStart(PhoneSecurity01.this,serial,securityNum,securityStart);
//		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		finish();
		overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
	}

	public static void actionStart(Context c, String serial, String securityNum, Boolean securityStart) {
		Intent intent = new Intent(c, PhoneSecurity01.class);
		intent.putExtra(Data.K_Phone_SIM_Serial, serial);
		intent.putExtra(Data.K_Phone_Security_Num, securityNum);
		intent.putExtra(Data.K_Phone_Security_Start, securityStart);
		c.startActivity(intent);
	}
}
