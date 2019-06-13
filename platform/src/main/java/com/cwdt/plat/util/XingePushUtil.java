//package com.cwdt.plat.util;
//
//
//import android.content.Context;
//
//import com.tencent.android.tpush.XGPushManager;
//
//import java.util.List;
//
//public class XingePushUtil {
//	public static String LogTag = "XingePushUtil";
//
//
//	/**
//	 * 启动信鸽推送组件
//	 */
//	public static void startXGPush(Context context) {
//		XGPushManager.registerPush(context);
//		/*Intent service = new Intent(context, XGPushService.class);
//		 context.startService(service);*/
//	}
//	/**
//	 * 停止接收推送
//	 * @param context
//	 */
//	public static void stopWork(Context context) {
//		XGPushManager.unregisterPush(context);
//	}
//
//	/**
//	 * 设定信鸽推送监听频道
//	 *
//	 * @param context
//	 * @param tags
//	 */
//	public static void setTags(Context context, List<String> tags) {
//		for(String strtag :tags)
//		{
//			if (!strtag.equals("")) {
//				XGPushManager.setTag(context, strtag);
//			}
//		}
//	}
//
//	/**
//	 * 清除信鸽推送标签
//	 *
//	 */
//	public static void delTags(Context context, List<String> tags) {
//		for(String strtag :tags)
//		{
//			if (!strtag.equals("")) {
//				XGPushManager.deleteTag(context, strtag);
//			}
//		}
//	}
//
//
//}
