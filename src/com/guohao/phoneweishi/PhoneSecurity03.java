package com.guohao.phoneweishi;

import com.guohao.Util.Data;
import com.guohao.Util.StringUtil;
import com.guohao.Util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PhoneSecurity03 extends Activity implements OnClickListener {
	private EditText inputPhoneNum;
	private Button selectPhoneNum;
	
	private SharedPreferences p;
	
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
		Log.d("guohao", "¹ùºÆ01");
		String serial = p.getString(Data.K_Phone_Security_Num, Data.V_Phone_Security_Num);
		Log.d("guohao", "¹ùºÆ02");
		if (!serial.equals(Data.V_Phone_Security_Num)) {
			inputPhoneNum.setText(serial);
		}
		Log.d("guohao", "¹ùºÆ03");
	}
	private void initView() {
		inputPhoneNum = (EditText) findViewById(R.id.id_edittext_input_phone_num);
		selectPhoneNum = (Button) findViewById(R.id.id_button_select_phone_num);
		p = Util.getPreferences(this);
	}
	@Override
	public void onClick(View v) {
		Util.showToast(PhoneSecurity03.this, "Ñ¡Ôñ");
	}
	
	//------------------------------------------------------------------------------
	public void next(View view) {
		saveShareData();
		PhoneSecurity04.actionStart(PhoneSecurity03.this);
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	public void last(View view) {
		saveShareData();
		PhoneSecurity02.actionStart(PhoneSecurity03.this);
		finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	private void saveShareData() {
		String securityNum = inputPhoneNum.getText().toString();
		Editor editor = p.edit();
		if (StringUtil.isEmpty(securityNum)) {
			editor.putString(Data.K_Phone_Security_Num, Data.V_Phone_Security_Num);
		}else {
			editor.putString(Data.K_Phone_Security_Num, securityNum);
		}
		editor.commit();
	}
	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity03.class);
		c.startActivity(intent);
	}
}
