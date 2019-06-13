package com.cwdt.plat.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cwdt.plat.service.YxSocketService;

import java.util.ArrayList;
import java.util.List;

public class YxSocketPushUtil {
	public static String LogTag="YxSocketPushUtil";
	
	
	/**
	 * 启动YxSocket推送组件
	 */
	public static void startYxSocketPush(Context context) {
		Log.d(LogTag, "启动YxSocket推送服务");
		Intent intentSocket = new Intent(
				context, YxSocketService.class);
		context.startService(intentSocket);
	}
	/**
	 * 设定YxSocket推送监听频道
	 * 
	 * @param context
	 * @param tags
	 */
	public static void setTags(Context context, List<String> tags) {
		Log.d(LogTag, "设置YxSocket推送监听频道");
		Intent intentSocket = new Intent(
				context, YxSocketService.class);
		intentSocket.putStringArrayListExtra(YxSocketService.EXT_DTA_TAGS, (ArrayList<String>) tags);
		intentSocket.setAction(YxSocketService.ACTION_SET_TAGS);
		context.startService(intentSocket);
	}
	/**
	 * 清除YxSocket推送监听频道
	 * 
	 */
	public static void delTags(Context context, List<String> tags) {
		Log.d(LogTag, "删除YxSocket推送监听频道");
		Intent intentSocket = new Intent(
				context, YxSocketService.class);
		intentSocket.putStringArrayListExtra(YxSocketService.EXT_DTA_TAGS, (ArrayList<String>) tags);
		intentSocket.setAction(YxSocketService.ACTION_DEL_TAGS);
		context.startService(intentSocket);
		
	}


	/**
	 *
	 * 停止YxSocket服务
	 */
	public static void stopWork(Context context) {
		Log.d(LogTag, "停止YxSocket服务");
		Intent intentSocket = new Intent(
				context, YxSocketService.class);
		context.stopService(intentSocket);
	}
	
}
