package com.guohao.phoneweishi;

import com.guohao.Util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PhoneSecurity04 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity04);
		
	}
	
	
	public void next(View view) {
		Util.showToast(this, "…Ë÷√ÕÍ≥…");
	}

	public void last(View view) {
		finish();
	}

	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity04.class);
		c.startActivity(intent);
	}
}
