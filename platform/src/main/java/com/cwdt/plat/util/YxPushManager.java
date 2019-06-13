package com.cwdt.plat.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cwdt.plat.data.Const;
import com.cwdt.plat.service.YxSocketService;

import java.util.List;

/**
 * 新的推送管理器，之前Tools中的推送方法将逐步淘汰，后续使用时请使用本静态类的方法
 *
 * @author Administrator
 */
public class YxPushManager {
    public static String LogTag = "YxPushManager";

    /**
     * 启动百度推送组件
     */
    public static void startPush(Context context) {
        String push_type = Tools.getAppMetaData("push_type");
        if (push_type == null) {
            // 为了兼容之前没有设置过meta信息的系统，设置默认值为1
            push_type = "1";
        }

        if (push_type.equals("1")) {
        } else if (push_type.equals("2")) {
            // 使用个推推送
//			GeTuiUtil.startGetuiPush(context);
        } else if (push_type.equals("4")) {
            // 信鸽推送
//            XingePushUtil.startXGPush(context);
        }
        if (push_type.equals("3")) {
            // 开启个推推送
//			GeTuiUtil.startGetuiPush(context);
            // 开启YxSocket推送
            YxSocketPushUtil.startYxSocketPush(context);
        }

    }

    /**
     * 设定百度云推送监听频道
     *
     * @param context
     * @param tags
     */
    public static void setTags(Context context, List<String> tags) {
        String push_type = Tools.getAppMetaData("push_type");
        if (push_type == null) {
            // 为了兼容之前没有设置过meta信息的系统，设置默认值为1
            push_type = "1";
        }
        for (int i = 0; i < tags.size(); i++) {
            if (!Const.TS_Listend_Channels.contains(tags.get(i))) {
                Const.TS_Listend_Channels.add(tags.get(i));
            }
        }
        if (push_type.equals("1")) {
        } else if (push_type.equals("2")) {
            // 使用个推推送
//			GeTuiUtil.setTags(context, tags);
        } else if (push_type.equals("3")) {

//			GeTuiUtil.setTags(context, tags);
//			BaiduPushUtil.setTags(context, tags);
//			YxSocketPushUtil.setTags(context, tags);
        } else if (push_type.equals("4")) {
            // 使用信鸽推送
//            XingePushUtil.setTags(context, tags);
        }
    }

    /**
     * 清除百度云推送
     */
    public static void delTags(Context context, List<String> tags) {
        String push_type = Tools.getAppMetaData("push_type");
        if (push_type == null) {
            // 为了兼容之前没有设置过meta信息的系统，设置默认值为1
            push_type = "1";
        }
        if (push_type.equals("1")) {
        } else if (push_type.equals("2")) {
            // 使用个推推送 个推没有删除
            // GeTuiUtil.setTags(context, tags);
        } else if (push_type.equals("3")) {
//			YxSocketPushUtil.delTags(context, tags);
        } else if (push_type.equals("4")) {
//            XingePushUtil.delTags(context, tags);
        }
    }

    public static void stopPush(Context context) {
        String push_type = Tools.getAppMetaData("push_type");
        if (push_type == null) {
            // 为了兼容之前没有设置过meta信息的系统，设置默认值为1
            push_type = "1";
        }
        if (push_type.equals("1")) {

        } else if (push_type.equals("2")) {
            // 使用个推推送
//			GeTuiUtil.stopGetuiPush(context);
        } else if (push_type.equals("3")) {
            // 使用个推推送
//			GeTuiUtil.stopGetuiPush(context);
//			YxSocketPushUtil.stopWork(context);
        } else if (push_type.equals("4")) {
            // 使用个推推送
//            XingePushUtil.stopWork(context);
        }
    }

    /**
     * 数据接收
     */
    public static void DataReceived(String tag, String strRecData) {
        if (Const.isDebug) {
            Log.d(LogTag, "收到数据来自" + tag + " 内容：" + strRecData);
        }
        Context context = CwdtApplication.getInstance().getContext();
        String push_type = Tools.getAppMetaData("push_type");
        if (push_type == null) {
            // 为了兼容之前没有设置过meta信息的系统，设置默认值为1
            push_type = "1";
        }
        if (push_type.equals("1") || push_type.equals("2") || push_type.equals("4")) {

            Bundle bundle = new Bundle();
            bundle.putString("data", strRecData);
            bundle.putSerializable(YxSocketService.EXT_DTA_SOCKET_COMMAND,
                    SocketCmdInfo.parseSocketDataInfo(strRecData));
            Tools.SendBroadCast(context,
                    YxSocketService.BROADCAST_NETWORK_DATA_RECEIVED, bundle);

        } else if (push_type.equals("3")) {
            if (tag.equals("YXPushReceiver")) {
                Bundle bundle = new Bundle();
                bundle.putString("data", strRecData);
                bundle.putSerializable(YxSocketService.EXT_DTA_SOCKET_COMMAND,
                        SocketCmdInfo.parseSocketDataInfo(strRecData));
                Tools.SendBroadCast(context,
                        YxSocketService.BROADCAST_NETWORK_DATA_RECEIVED, bundle);
            } else {
                Intent intentSocket = new Intent(
                        context, YxSocketService.class);
                intentSocket.setAction(YxSocketService.ACTION_KEEP_ALIVE);
                context.startService(intentSocket);
            }
        }
    }

}
