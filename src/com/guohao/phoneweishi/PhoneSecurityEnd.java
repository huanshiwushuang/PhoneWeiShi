package com.guohao.phoneweishi;

import com.guohao.Util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PhoneSecurityEnd extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Util.showToast(PhoneSecurityEnd.this, "���ý���");
		
	}
	
	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurityEnd.class);
		c.startActivity(intent);
	}
}
