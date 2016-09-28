package com.guohao.phoneweishi;

import com.guohao.Util.Data;
import com.guohao.Util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PhoneSecurityEnd extends Activity implements OnClickListener {
	private RelativeLayout securityNum,securityIsStart;
	private TextView restartSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity_end);
		
		initView();
		initShareData();
	}
	
	private void initShareData() {
		SharedPreferences p = Util.getPreferences(PhoneSecurityEnd.this);
		String num = p.getString(Data.K_Phone_Security_Num, Data.V_Phone_Security_Num);
		Boolean isStart = p.getBoolean(Data.K_Phone_Security_Start, Data.V_Phone_Security_Start);
		((TextView) securityNum.getChildAt(1)).setText(num);
		if (isStart) {
			((ImageView) securityIsStart.getChildAt(1)).setImageResource(R.drawable.lock);
		}
	}

	private void initView() {
		securityNum = (RelativeLayout) findViewById(R.id.id_relativelayout_phone_security_num);
		securityIsStart = (RelativeLayout) findViewById(R.id.id_relativelayout_phone_security_is_start);
		restartSetting = (TextView) findViewById(R.id.id_textview_restart_setting);
		restartSetting.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		PhoneSecurity01.actionStart(PhoneSecurityEnd.this);
		finish();
	}

	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurityEnd.class);
		c.startActivity(intent);
	}
}
