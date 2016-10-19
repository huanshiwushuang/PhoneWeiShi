package com.guohao.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAdmin extends DeviceAdminReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		
		Log.d("guohao", "获取到管理员");
		
	}
}
