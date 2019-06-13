package com.cwdt.plat.receiver;

import com.cwdt.plat.data.Const;
import com.cwdt.plat.service.YxSocketService;
import com.cwdt.plat.util.YxPushManager;
import com.cwdt.plat.util.YxSocketPushUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class YXPushReceiver extends BroadcastReceiver {
	public static final String TAG = YXPushReceiver.class
			.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		String strAct = intent.getAction();
		if (strAct.equals(YxSocketService.BROADCAST_YX_SOCKET_DATA_RECEIVED)) {
			Bundle bundle = intent.getExtras();
			if (bundle!=null) {
				String strData=bundle.getString("data");
				if (Const.isDebug) {
					Log.d(TAG, strData);
				}
				YxPushManager.DataReceived("YXPushReceiver", strData);
			}
		}
		else {
			YxSocketPushUtil.startYxSocketPush(context);
		}
	}
}
