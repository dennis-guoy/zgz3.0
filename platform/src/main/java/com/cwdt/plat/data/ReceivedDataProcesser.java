package com.cwdt.plat.data;

import com.cwdt.plat.dataopt.SocketDataInfo;
import com.cwdt.plat.util.SocketCmdInfo;

import android.content.Context;
import android.util.Log;

public abstract class ReceivedDataProcesser {
	private String  LOG_TAG="ReceivedDataProcesser";
	protected SocketDataInfo curCommandInfo;
	public Context curContext;
	public ReceivedDataProcesser(String recString) {
		try {
			SocketDataInfo sdi=SocketCmdInfo.parseSocketDataInfo(recString);
//			recvData = recString;
//			strSplitArr = recString.split(SocketCommandInfo.CMD_SPLIT);
//			strCommand=strSplitArr[0];
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		}
	}
	public abstract void dataProcess();
}
