package com.guohao.phoneweishi;

import com.guohao.Util.Data;
import com.guohao.Util.Util;
import com.guohao.custom.SettingItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SettingActivity extends Activity {
	private com.guohao.custom.SettingItem settingItem;
	private SharedPreferences p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
		initLocalData();
	}
	
	private void initLocalData() {
		settingItem.getCheckBox().setChecked(p.getBoolean(Data.K_Auto_Update, Data.V_Auto_Updtae_Yes));
	}

	private void initView() {
		p = Util.getPreferences(SettingActivity.this);
		settingItem = (SettingItem) findViewById(R.id.id_setting_item);
	}

	
	public static void actionStart(Context c) {
		Intent intent = new Intent(c, SettingActivity.class);
		c.startActivity(intent);
	}
}
