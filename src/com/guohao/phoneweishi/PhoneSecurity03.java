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
import android.widget.Button;
import android.widget.EditText;

public class PhoneSecurity03 extends Activity implements OnClickListener {
	private String serial;
	private String securityNum;
	private Boolean securityStart;
	
	private EditText inputPhoneNum;
	private Button selectPhoneNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity03);
		
		initView();
		initShareData();
		setListener();
	}
	private void setListener() {
		selectPhoneNum.setOnClickListener(this);
	}
	private void initShareData() {
		SharedPreferences p = Util.getPreferences(this);
		String serial = p.getString(Data.K_Phone_Security_Num, Data.V_Phone_Security_Num);
		if (!serial.equals(Data.V_Phone_Security_Num)) {
			inputPhoneNum.setText(serial);
		}
	}
	private void initView() {
		inputPhoneNum = (EditText) findViewById(R.id.id_edittext_input_phone_num);
		selectPhoneNum = (Button) findViewById(R.id.id_button_select_phone_num);
	}
	@Override
	public void onClick(View v) {
		Util.showToast(PhoneSecurity03.this, "Ñ¡Ôñ");
	}
	
	//------------------------------------------------------------------------------
	public void next(View view) {
		securityNum = inputPhoneNum.getText().toString();
		PhoneSecurity04.actionStart(PhoneSecurity03.this,serial,securityNum,securityStart);
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void last(View view) {
		securityNum = inputPhoneNum.getText().toString();
		PhoneSecurity02.actionStart(PhoneSecurity03.this);
		finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity03.class);
		c.startActivity(intent);
	}
}
