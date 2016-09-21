package com.guohao.phoneweishi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PhoneSecurity01 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity01);
		
	}
	
	public void next(View view) {
		PhoneSecurity02.actionStart(this);
	}

	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity01.class);
		c.startActivity(intent);
	}
}
