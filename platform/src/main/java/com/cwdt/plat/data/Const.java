package com.cwdt.plat.data;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.jngs.library.platfrom.R;

import java.util.ArrayList;
import java.util.List;

public class Const {
    public static String BROADCAST_NEED_LOGIN = Utils.getApp().getPackageName()+ "BROADCAST_NEED_LOGIN";
    public static String BROADCAST_LOGIN_OK = Utils.getApp().getPackageName()+ "BROADCAST_LOGIN_OK";
    public static String BROADCAST_LOGOUT_OK = Utils.getApp().getPackageName()+ "BROADCAST_LOGOUT_OK";
    public static String ZITIDAXIAO = "zhong";
    public  static String version="1.0";
    public static int screenwidth = 0;
    public static int screenheight = 0;
    public static  Context mcontext;
    public static  int n=0;
    /**
     * Token存放名称
     */
    public static final String TOKEN_STORE_NAME="TOKEN_STORE_APP";
    /**
     * 用户信息存放名称
     */
    public static final String USER_STORE_NAME="USER_STORE_APP";
    /**
     * 服务器地址
     */
//    public static String BASE_URL = "http://47.98.57.203:8080/yuxinweb/";
    public static String BASE_URL = "http://app.zhugongedu.com/index.php/mobile/index.php?act=entrancePost";
    // 个人信息
    public static BaseUserInfo curUserInfo = new BaseUserInfo();
    public static BaseToken baseToken = new BaseToken();

    public static int iTimeWuCha = 6000;
    /**
     * 通用接口地址
     */
    public static String JSON_DATA_INTERFACE_URL = BASE_URL ;
//    public static String LOCAL_NOTIFY_READ_URL = BASE_URL + "/interface/readlocalnotify.aspx";
//    public static String urlnoticeAttach_Url = BASE_URL + "/interface/postnotifyfile.aspx";
//    public static String LOCAL_NOTIFY_READ_Datail = BASE_URL + "/interface/readnotifyresult.aspx";
    public static String   Fileupload_URL=BASE_URL+"/common/sysFile/upload";
    public static int TopBarColor= R.color.statusbar_bg;
    /**
     * 附件上传socket缓存大小
     */
//    public static int iSocketBufferSize = 8196;
    // 附件上传服务器地址
//    public static String UPLOAD_SERVER_IP = "60.208.91.35";
    // 附件上传服务器端口
//    public static Integer UPLOAD_SERVER_PORT = 9033;
    // 当前项目是否处于调试状态，此处不要修改，需要通过Application进行设置
    public static boolean isDebug = true;
    /**
     * 全局TAG订阅列表
     */
    public static List<String> TS_Listend_Channels = new ArrayList<String>();

    public static void setBaseUrl(String URL) {
         BASE_URL = URL;
        JSON_DATA_INTERFACE_URL=BASE_URL;
        Fileupload_URL=BASE_URL+"/common/sysFile/upload";
    }
    public static void setTopBarColor(int color){
        TopBarColor=color;
    }
    public static void setContext(Context context){
        mcontext=context;
    };
}
