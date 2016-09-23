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
		PhoneSecurity02.actionStart(PhoneSecurity01.this);
//		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		finish();
		overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
	}

	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity01.class);
		c.startActivity(intent);
	}
}
