package com.cwdt.plat.util;

import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.cwdt.plat.data.GlobalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.blankj.utilcode.util.DeviceUtils.getMacAddress;
import static com.blankj.utilcode.util.DeviceUtils.getModel;

/**
 * Created by Administrator on 2018/1/8.
 */

public class HttpHelper {
    /**
     * 发送POST请求到指定的URL
     * @param strurl  请求的链接地址
     * @param strData 提交的数据内容
     * @return
     */
    public static String SendPostRequest(String strurl, String strData) {
        String result = "";
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient client = builder.build();
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), strData);
            Request request = new Request.Builder()
                    .url(strurl)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            result = response.body().string();
            int nCode = response.code();
            if (nCode != 200) {
                result = "error:无法获取后台资源！";
            }

        } catch (Exception ex) {
            result = "error:访问后台资源出错！";
            Log.e("HttpHelper", ex.getMessage());
        }
        return result;
    }

    /**
     * 图文上传 上传一个文件和多个文字
     * 返回结果 jsonObject为空时为不成功
     *
     * @param httpUrl 地址
     * @param Params
     * @param
     * @return
     */
    public static JSONObject UploadAttach(String httpUrl, HashMap<String, String> Params, File file) throws IOException {
        JSONObject rJobject = null;
        OkHttpClient client = new OkHttpClient();
        okhttp3.MultipartBody.Builder builder = (new okhttp3.MultipartBody.Builder()).setType(MultipartBody.FORM);
        if(file != null) {

            builder.addFormDataPart("file", Params.get("file"), RequestBody.create(MediaType.parse("image/*"), file));
            builder.addFormDataPart("flag", Params.get("flag"));
        }

        MultipartBody body = builder.build();
        Request request = (new okhttp3.Request.Builder())
                .url(httpUrl)
                .post(body)
                .addHeader("X-AUTH-TOKEN", (String) GlobalData.getSharedData("token"))
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()) {
            String temp = response.body().string();
            if(temp != null && !temp.isEmpty()) {
                try {
                    rJobject = new JSONObject(temp);
                } catch (JSONException var11) {
                    var11.printStackTrace();
                }
            }
        }

        return rJobject;
    }

    /**
     * 通过Multipart方式上传文件或参数
     *
     * @param url
     * @param Params
     * @param files
     * @return
     */
    public static String HttpUploadRes(String url, HashMap<String, String> Params, ArrayList<File> files) throws IOException {
        String value = "";
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (Params != null) {
            for (String key : Params.keySet()) {
                builder.addFormDataPart(key, Params.get(key));
            }
        }
        if (files != null && !files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                //Files
                builder.addFormDataPart("image" + i, files.get(i).getName(), RequestBody.create(MediaType.parse("image/jpg"), files.get(i)));
            }
        }
        MultipartBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful() == true) {
            //上传成功
            value = response.body().string();
        } else {
            //上传失败
        }
        return value;
    }



}
