package com.guohao.phoneweishi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PhoneSecurity02 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity02);
		
	}
	
	
	public void next(View view) {
		PhoneSecurity03.actionStart(this);
	}

	public void last(View view) {
		finish();
	}

	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity02.class);
		c.startActivity(intent);
	}
}
