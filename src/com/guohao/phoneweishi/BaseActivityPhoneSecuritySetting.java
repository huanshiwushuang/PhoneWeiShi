package com.guohao.phoneweishi;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class BaseActivityPhoneSecuritySetting extends Activity {
	private GestureDetector detector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		detector = new GestureDetector(BaseActivityPhoneSecuritySetting.this, new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				if ((e1.getRawX()-e2.getRawX()) > 200) {
					next(null);
				}else if ((e2.getRawX()-e1.getRawX()) > 200) {
					last(null);
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	public abstract void next(View view);
	public abstract void last(View view);
}
