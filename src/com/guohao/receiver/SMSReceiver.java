package com.guohao.receiver;

import com.guohao.Util.Util;
import com.guohao.phoneweishi.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage[] messages = null;
		String num = "",content = "";
		if (bundle != null) {
			Object[] objects = (Object[]) bundle.get("pdus");
			messages = new SmsMessage[objects.length];
			for (int i = 0; i < messages.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[])objects[i]);
				num += messages[i].getOriginatingAddress();
				content += messages[i].getMessageBody();
			}
			
		}
		Log.d("guohao", "ºÅÂë£º"+num+"----ÄÚÈÝ£º"+content);
		
		Util.showToast(context, "ºÅÂë£º"+num+"----ÄÚÈÝ£º"+content);
		
		if ("#*alarm*#".equals(content));{
			MediaPlayer player = MediaPlayer.create(context, R.raw.baojing);
			player.setVolume(1f,1f);
			player.setLooping(true);
			player.start();
			abortBroadcast();//ÖÕÖ¹µô¹ã²¥£¬ ÈÃ¶ÌÐÅ²»ÔÙ¼ÌÐøÍùÏÂ´«²¥
		}
	}

}
