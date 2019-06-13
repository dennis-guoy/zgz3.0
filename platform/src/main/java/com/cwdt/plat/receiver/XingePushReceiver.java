//package com.cwdt.plat.receiver;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.cwdt.plat.data.Const;
//import com.cwdt.plat.util.XingePushUtil;
//import com.cwdt.plat.util.YxPushManager;
//import com.tencent.android.tpush.XGPushBaseReceiver;
//import com.tencent.android.tpush.XGPushClickedResult;
//import com.tencent.android.tpush.XGPushRegisterResult;
//import com.tencent.android.tpush.XGPushShowedResult;
//import com.tencent.android.tpush.XGPushTextMessage;
//
//public class XingePushReceiver extends XGPushBaseReceiver{
//	public static final String TAG = XingePushReceiver.class.getSimpleName();
//	@Override
//	public void onDeleteTagResult(Context arg0, int arg1, String arg2) {
//		// TODO Auto-generated method stub
//		String responseString = "onDelTags errorCode=" + arg1 + " sucessTags=" + arg2 ;
//		Log.d(TAG, responseString);
//		int iLen=Const.TS_Listend_Channels.size()-1;
//		for(int i=iLen;i>=0;i--)
//		{
//			if (Const.TS_Listend_Channels.get(i).equals(arg2)) {
//				Const.TS_Listend_Channels.remove(i);
//			}
//		}
////		Const.BD_Listend_channels.clear();
//	}
//
//	@Override
//	public void onNotifactionClickedResult(Context arg0, XGPushClickedResult arg1) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onNotifactionShowedResult(Context arg0, XGPushShowedResult arg1) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onRegisterResult(Context arg0, int errorCode, XGPushRegisterResult message) {
//		// TODO Auto-generated method stub
//
//		String text = "";
//		if (errorCode == XGPushBaseReceiver.SUCCESS) {
//			text = message + "注册成功";
//			// 在这里拿token
//			String token = message.getToken();
//			XingePushUtil.setTags(arg0, Const.TS_Listend_Channels);
//		} else {
//			text = message + "注册失败，错误码：" + errorCode;
//		}
//		Log.d(TAG, text);
//	}
//
//	@Override
//	public void onSetTagResult(Context arg0, int arg1, String arg2) {
//		// TODO Auto-generated method stub
//		String responseString = "onSetTags errorCode=" + arg1 + " sucessTags=" + arg2;
//		Log.d(TAG, responseString);
//	}
//
//	@Override
//	public void onTextMessage(Context arg0, XGPushTextMessage arg1) {
//		String messageString = "透传消息 message=\"" + arg1.getTitle() + "\" customContentString=" + arg1.getContent();
//		Log.d(TAG, messageString);
//		YxPushManager.DataReceived("XingePush", arg1.getContent());
//
//	}
//
//	@Override
//	public void onUnregisterResult(Context arg0, int arg1) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
