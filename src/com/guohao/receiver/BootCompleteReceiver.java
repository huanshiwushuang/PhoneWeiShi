package com.guohao.receiver;

import com.guohao.Util.Data;
import com.guohao.Util.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Log.d("guohao", "接收到了开机广播");
			SharedPreferences p = Util.getPreferences(context);
			String serial = p.getString(Data.K_Phone_SIM_Serial, Data.V_Phone_SIM_Serial);
			if (serial.equals(Data.V_Phone_SIM_Serial)) {
				Log.d("guohao", "SIM卡更换，发送报警短信");
			}else {
				Log.d("guohao", "SIM卡未更换，安全");
			}
		}
	}
}
