package com.cwdt.plat.dataopt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.Utils;
import com.cwdt.plat.data.Const;
import com.cwdt.plat.data.GlobalData;
import com.cwdt.plat.data.PersistentCookieStore;
import com.cwdt.plat.util.CwdtApplication;
import com.cwdt.plat.util.Tools;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.blankj.utilcode.util.DeviceUtils.getMacAddress;
import static com.blankj.utilcode.util.DeviceUtils.getModel;

public abstract class JsonBase {
//    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();
private final PersistentCookieStore  cookieStore = new PersistentCookieStore(Utils.getApp());
    private OkHttpClient client = new OkHttpClient();
    public MediaType JSONs
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
//    public String strNetError = "";
//    public static Boolean isUseEncode = true;
    /**
     * 日志标志
     */
    public String LogTAG = "JSONBASE";
    /**
     * 传入JSONObject
     */
    public JSONObject inJsonObject;
    /**
     * 输出JSONObject
     */
    public JSONObject outJsonObject;
    /**
     * 程序标识
     */
    // public String strAppId = "A00002";
    /**
     * 当前用户ID
     */

    public String strUserId;
    /*
     * 发送数据字段、内容
     */
    public HashMap<String, String> sendArrList;
    /**
     * 操作符
     */
//    public static String optString = "";
    /**
     * 操作参数数据
     */
    public JSONObject optData;
    /**
     * 网络返回JSON字符串
     */
    public String recvString = "";
    /**
     * 接口地址定义
     */
    public String interfaceUrl = "";
    /**
     * 当前分页
     */
//    public String currentPage = "1";

    public Integer offset=0;

    public Integer limit=10;

    /**
     * 数据传递Handler
     */
    public Handler dataHandler = null;
    /**
     * 如果是线程模式下， 通过dataHandler发送返回数据,dataMessage中包含了返回数据
     */
    public Message dataMessage = null;

    public JsonBase() {
        inJsonObject = new JSONObject();
        optData = new JSONObject();
        prepareCustomData();
        packBaseData();

    }
    private void packBaseData()
    {
//        optData.put("deviceid", DeviceUtils.getAndroidID());
        try
        {
            if (Const.curUserInfo!=null )
            {
                optData.put("userId", Const.curUserInfo.getUserId());
            }
            if (Const.baseToken!=null )
            {
                optData.put("authtoken", Const.baseToken.getAuthtoken());
            }
        }
        catch (Exception e)
        {
            Log.e(LogTAG,e.getMessage());
        }

    }
    /**
     * 执行获取数据操作
     *
     * @param strHttp 需要访问的Json数据接口地址
     * @return boolean 是否获取成功
     */
    public boolean RunData(String strHttp) {
        boolean ret = false;
        try {
            PacketData();

            String strData = optData.toString();
            if (Const.isDebug) {
                Log.d(LogTAG, "待发数据：" + strData);
                Log.d(LogTAG, "接口路径：" + strHttp);
            }
//            strData = "FE0X" + encodeData(strData);
            recvString = SendRequest(strHttp, strData);
            if (Const.isDebug) {
                Log.d(LogTAG, "返回数据：" + recvString);
            }
            if (recvString.indexOf("error:") >= 0) {
                dataMessage = new Message();
                dataMessage.arg1 = 1;
                dataMessage.obj = recvString;
                // 如果是线程模式下，通过dataHandler发送返回数据,这时需要ParsReturnData中为dataMessage赋值
                if (dataMessage != null && dataHandler != null) {
                    dataHandler.sendMessage(dataMessage);
                }
                if (Const.isDebug) {
                    Log.e(LogTAG, recvString);
                }
                return false;
            } else {

                outJsonObject = JSONObject.parseObject(recvString);
                //  new JSONObject(recvString);
                if (Const.isDebug) {
                    Log.d(LogTAG, recvString);
                }

                if(Const.mcontext!=null){
                    try{

                        if(recvString!=null){
                            JSONObject jsonObject = JSON.parseObject(recvString);
                            if(jsonObject!=null){
                                String code =jsonObject.getString("statuscode") ;
                                if("9".equals(code)){
                                    if(Const.n==0){
                                        Intent intent = new Intent(Const.mcontext, Class.forName("com.workapp.wode.denglu.DengLuActivity"));
//                                        Tools.SendBroadCast(Const.mcontext,"TOKENEND"); //判断token是否失效发广播处理
                                        Const.mcontext.startActivity(intent);
                                        Const.n=1;
                                    }

                                }
                            }
                        }

                    }catch (Exception e){

                    }


                }

                ret = true;
            }
            if (ret) {
                ret = ParsReturnData();
            }
            // 如果是线程模式下，通过dataHandler发送返回数据,这时需要ParsReturnData中为dataMessage赋值
            if (dataMessage != null && dataHandler != null) {
                dataHandler.sendMessage(dataMessage);
            }
        } catch (Exception e) {
            // TODO: handle exception
            if (Const.isDebug) {
                Log.e(LogTAG, e.getMessage());
            }
        }
        return ret;
    }

    /**
     * 执行获取数据操作，使用 <br>
     * Const.JSON_INTERFACE_URL <br>
     * 作为数据访问路径
     *
     * @return
     */
    public void RunDataAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RunData(interfaceUrl);
            }
        }).start();
    }

    /**
     * 执行获取数据操作，使用 <br>
     * Const.JSON_INTERFACE_URL <br>
     * 作为数据访问路径
     *
     * @return 是否获取成功
     */
    public boolean RunData() {
        return RunData(interfaceUrl);
    }

    /**
     * 解析返回数据 如果是线程模式下， 通过dataHandler发送返回数据, 这时需要ParsReturnData中为dataMessage赋值
     *
     * @return
     * @throws FileNotFoundException
     */
    public abstract boolean ParsReturnData() throws FileNotFoundException;

    /**
     * 包装待发送数据包
     */
    public abstract void PacketData();

    /**
     * 重组自定义的HTTP访问路径及默认数据
     */
    public abstract void prepareCustomData();

    /**
     * 加密需要提交给接口的数据
     *
     * @param data
     * @return
     */
    public String encodeData(String data) {
        String strRet = data;
        try {
//            strRet = AESHelper.encode(strRet);


        } catch (Exception e) {
            // TODO: handle exception
        }
        return strRet;
    }

    /**
     * 解密数据
     *
     * @param data
     * @return
     */
    public String decodeData(String data) {
        String strRet = data;
        try {
//            strRet = AESHelper.decode(strRet);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return strRet;
    }

    /**
     * 发送数据到接口路径
     *
     * @param strurl
     * @param strJson
     * @return
     */
    protected String SendRequest(String strurl, String strJson) {
        String result = "";
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //设置读取超时90S
            builder.readTimeout(90, TimeUnit.SECONDS);
            builder.cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                    if (cookies != null && cookies.size() > 0) {
                        for (Cookie item : cookies) {
                            Log.e(LogTAG,item.value());
                            cookieStore.add(url, item);
                        }
                    }
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url);
                    return cookies;
                }
            });
            client = builder.build();

//            RequestBody body = RequestBody.create(JSONs, strJson);
            FormBody formBody = new FormBody.Builder()
                    .add("params", strJson)
                    .build();

            Request request = new Request.Builder()
                    .url(strurl)
                    .post(formBody)
                    .addHeader("Content-type","application/x-www-form-urlencoded")
                    .addHeader("userToken", (String)GlobalData.getSharedData("token"))
                    .addHeader("Accept","*/*")
                    .addHeader("appVersion", AppUtils.getAppVersionName())
                    .addHeader("deviceModel",getModel())
                    .addHeader("deviceType","Android")
                    .addHeader("systemVersion",""+DeviceUtils.getSDKVersion())
                    .addHeader("ip",getMacAddress())
                    .build();
            Response response = client.newCall(request).execute();


            if (response.request().url().toString().equals(strurl))
            {
                result = response.body().string();
                int nCode = response.code();
                if (nCode != 200) {
                    result = "error:无法获取后台资源！";
                }
            }
            else {
                if(response.request().url().toString().indexOf("/login")>0)
                {
                    Tools.SendBroadCast(CwdtApplication.getInstance(),Const.BROADCAST_NEED_LOGIN);
                }
                else {
                    result = "error:访问后台资源出错！";
                }
            }

        } catch (Exception ex) {
            result = "error:访问后台资源出错！";
            Log.e(LogTAG, ex.getMessage());
        }
        return result;
    }
}

