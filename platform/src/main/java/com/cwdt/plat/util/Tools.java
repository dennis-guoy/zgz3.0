package com.cwdt.plat.util;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.Utils;
import com.jngs.library.platfrom.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Tools {
    /**
     * 发送系统广播
     *
     * @param context
     * @param act
     */
    public static void SendBroadCast(Context context, String act) {
        HashMap<String, String> dictHashMap = new HashMap<String, String>();
        SendBroadCast(context, act, dictHashMap);
    }

    /**
     * 发送系统广播
     *
     * @param act
     * @param ExtraData
     */
    public static void SendBroadCast(Context context, String act, HashMap<String, String> ExtraData) {
        Intent biIntent = new Intent(act);
        if (ExtraData != null) {
            Iterator<Entry<String, String>> iter = ExtraData.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, String> entry = iter.next();
                String key = entry.getKey();
                String val = entry.getValue();
                biIntent.putExtra(key, val);
            }
        }
        context.sendBroadcast(biIntent);
    }

    /**
     * 发送系统广播
     *
     * @param act
     * @param ExtraData
     */
    public static void SendBroadCast(Context context, String act, Bundle ExtraData) {
        Intent biIntent = new Intent(act);
        biIntent.putExtras(ExtraData);
        context.sendBroadcast(biIntent);
    }

    /**
     * 以缩略图形式进行图片压缩
     *
     * @param bmp
     * @param width
     * @param height
     */
    public static Bitmap CompressImage(Bitmap bmp, int width, int height) {
        try {
            return ThumbnailUtils.extractThumbnail(bmp, width, height);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 创建文件目录
     *
     * @return
     */
    public static boolean createSDCardDir() {

        if (SDCardUtils.isSDCardEnable()) {
            // 创建一个文件夹对象，赋值为外部存储器的目录
            File sdcardDir = Environment.getExternalStorageDirectory();
            // 得到一个路径，内容是sdcard的文件夹路径和名字
            String path = sdcardDir.getPath() + "/ZSTTaxImages";
            File path1 = new File(path);
            if (!path1.exists()) {
                // 若不存在，创建目录，可以在应用启动的时候创建
                path1.mkdirs();
            } else if (path1.isDirectory()) {
                File[] childFiles = path1.listFiles();
                if (childFiles == null || childFiles.length == 0) {

                }
            }
            return true;

        } else {
            return false;
        }
    }

    /**
     * @param bmp
     * @param file
     */
    public static void compressDZBmpToFile(Bitmap bmp, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 60;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);

        /*
         * while (baos.toByteArray().length / 1024 > 100) { baos.reset();
         * options -= 10; bmp.compress(Bitmap.CompressFormat.JPEG, options,
         * baos); }
         */
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在浏览器中打开指定连接地址
     *
     * @param urlString
     */
    public static void openUrl(Context context, String urlString) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(urlString));
            context.startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * 获取系统公共文件存储目录
     *
     * @return
     */
    public static File getApplicationWorkDirectory() {
        if (SDCardUtils.isSDCardEnable()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            String path = sdcardDir.getPath() + "/ZSTTaxImages";
            File path1 = new File(path);
            if (!path1.exists()) {
                path1.mkdirs();
            }

            return path1;
        } else {
            return null;
        }
    }

    /**
     * 根据一个图片文件名称获取程序中该文件（系统公共文件存储目录）的文件对象
     *
     * @param strFileName
     * @return
     */
    public static File getImageFileByName(String strFileName) {

        File appdir = getApplicationWorkDirectory();
        if (appdir == null) {
            return null;
        }
        File imagefile = new File(appdir.getAbsolutePath(), strFileName);
        return imagefile;
    }


    /**
     * 根据图片文件名称获取图片的缩略图，如果缩略图文件不存在则自动生成 并保存在 sdcardDir.getPath() +
     * "/ZSTTaxImages/thumb";
     *
     * @param strFileName
     * @return
     */
    public static File getThumbImageFileByName(String strFileName) {
        return getThumbImageFileByName(strFileName, 100, 100);
    }

    /**
     * 根据图片文件名称获取图片的缩略图，如果缩略图不存在则自动创建
     *
     * @param strFileName
     * @param width
     * @param height
     * @return
     */
    public static File getThumbImageFileByName(String strFileName, int width, int height) {
        File bFile = getImageFileByName(strFileName);
        if (bFile == null) {
            return null;
        }
        if (!bFile.exists()) {
            return null;
        }
        try {
            File sdcardDir = Environment.getExternalStorageDirectory();
            String path = sdcardDir.getPath() + "/ZSTTaxImages/thumb";
            File path1 = new File(path);
            if (!path1.exists()) {
                path1.mkdirs();
            }

            File imagefile = new File(path, strFileName);
            if (imagefile.exists()) {
                return imagefile;
            } else {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(bFile.getAbsolutePath(), opts);
                // Bitmap bitmap = getBitmapFromFile(bFile.getPath());
                bitmap = Tools.CompressImage(bitmap, width, height);
                ImageUtils.save(bitmap, imagefile.getAbsolutePath(), Bitmap.CompressFormat.JPEG);
            }
            return imagefile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bmp
     * @param file
     */
    public static void compressBmpToFile(Bitmap bmp, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 60;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);

        /*
         * while (baos.toByteArray().length / 1024 > 100) { baos.reset();
         * options -= 10; bmp.compress(Bitmap.CompressFormat.JPEG, options,
         * baos); }
         */
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据时间生成适合于做文件名称的字符串
     * 自动生成文件名
     *
     * @return
     */
    public static String getFileCurrDateStr() {
        String strRet = "";
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        strRet = dateFormat.format(now);
        return strRet;
    }


    /**
     * 保存BitMap到文件，文件名使用当前时间
     *
     * @param bitmap
     * @return
     */
    public static String createImageFile(Bitmap bitmap) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            String path = sdcardDir.getPath() + "/ZSTTaxImages";
            File path1 = new File(path);
            if (!path1.exists()) {
                path1.mkdirs();
            }
            String fileName = getFileCurrDateStr() + ".jpg";
            File imagefile = new File(path, fileName);
            Tools.compressBmpToFile(bitmap, imagefile);
            return fileName;
        }
        return "";
    }

    /**
     * 根据Meta标签获取标签内容
     *
     * @param metaKey
     * @return
     */
    public static String getAppMetaData(String metaKey) {
        Bundle metaData = null;
        String metaValue = "";
        try {
            ApplicationInfo ai = Utils.getApp().getPackageManager().getApplicationInfo(Utils.getApp().getPackageName(),
                    PackageManager.GET_META_DATA);
            ;
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                if (metaData.containsKey(metaKey)) {
                    Object o = metaData.get(metaKey);
                    metaValue = o.toString();
                }
            }
        } catch (Exception ex) {

        }
        return metaValue;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * toast显示
     */
    public static void ShowToast(Context context, String strContent) {
        try {
//            Toast toast = Toast.makeText(context, strContent, Toast.LENGTH_SHORT);
            ToastUtil toast = new ToastUtil(context, R.layout.toast_center, strContent);
//            toast.setGravity(Gravity.CENTER, 10, 10);
            toast.show();
        } catch (Exception e) {
            Log.e("ShowToast", e.getMessage());
        }
    }

    public static void ShowToast(String strContent) {
        try {
            ShowToast(CwdtApplication.getInstance().getContext(), strContent);
        } catch (Exception e) {
            Log.e("ShowToast", e.getMessage());
        }

    }

    public static boolean isTxtFile(String filename) {
        boolean bRet = false;
        String end = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        if (end.equals("txt")) {
            bRet = true;
        }
        return bRet;
    }

    /**
     * 文件是否音乐文件
     *
     * @param filename
     * @return
     */
    public static boolean isAudioFile(String filename) {
        boolean bRet = false;
        String end = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg")
                || end.equals("wav") || end.equals("amr")) {
            bRet = true;
        }
        return bRet;
    }

    /**
     * 文件是否视频文件
     *
     * @param filename
     * @return
     */
    public static boolean isVideoFile(String filename) {
        boolean bRet = false;
        String end = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        if (end.equals("3gp") || end.equals("mp4") || end.equals("mpg")) {
            bRet = true;
        }
        return bRet;
    }

    /**
     * 文件是否图片文件
     *
     * @param filename
     * @return
     */
    public static boolean isGraphFile(String filename) {
        boolean bRet = false;
        String end = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            bRet = true;
        }
        return bRet;
    }

    public static Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(
                R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context,
                R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        if (TextUtils.isEmpty(msg)) {
            tipTextView.setVisibility(View.GONE);
        } else {
            tipTextView.setVisibility(View.VISIBLE);
            tipTextView.setText(msg);// 设置加载信息
          }

            Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

            loadingDialog.setCancelable(true);// 不可以用“返回键”取消
            loadingDialog.setContentView(layout,
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
            return loadingDialog;

        }

        /**
         * 设定百度云推送监听频道
         *
         * @param context
         * @param tags
         */
        public static void setBaiduTags (Context context, List < String > tags){
            YxPushManager.setTags(context, tags);
            // String push_type=PackageUtils.getMetaValue(context, "push_type");
            // if (push_type==null) {
            // // 为了兼容之前没有设置过meta信息的系统，设置默认值为1
            // push_type="1";
            // }
            // if (push_type.equals("1")) {
            // // 使用百度推送
            // BaiduPushUtil.setTags(context, tags);
            // }
            // else if (push_type.equals("2")) {
            // // 使用个推推送
            // GeTuiUtil.setTags(context, tags);
            // }
            // else if (push_type.equals("3")) {
            //
            // GeTuiUtil.setTags(context, tags);
            // BaiduPushUtil.setTags(context, tags);
            // YxSocketPushUtil.setTags(context, tags);
            // }
        }

        public static void setjiaodian (View view){
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }

        /**
         * 创建.nomedia文件到缓存文件夹
         *
         * @return
         */
        public static void Creatnomediafile () {
            File nomedia = new File(getApplicationWorkDirectory().getPath(), ".nomedia");
            try {
                if (!nomedia.exists())
                    nomedia.createNewFile();
                FileOutputStream nomediaFos = new FileOutputStream(nomedia);
                nomediaFos.flush();
                nomediaFos.close();
            } catch (IOException e) {
                Log.e("IOException", "exception in createNewFile() method");
                return;
            }
        }

        /**
         * 删除指定ID的任务栏通知信息
         *
         * @param notifyid
         */
        public static void deleteNotifyInfo (Context context,int notifyid){
            NotificationManager mNotifyManager = (NotificationManager) context
                    .getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            mNotifyManager.cancel(notifyid);
        }

        /**
         * 清除百度云推送,当前函数调用YxPushManager中的 delTags方法删除已经订阅的推送频道
         */
        public static void delBaiduTags (Context context, List < String > tags){
            YxPushManager.delTags(context, tags);
            // PushManager.delTags(context, tags);
            // Const.BD_Listend_channels.clear();

        }

        /**
         * 启动百度推送组件
         */
        public static void startBaiduPush (Context context){
            YxPushManager.startPush(context);
            // String push_type=PackageUtils.getMetaValue(context, "push_type");
            // if (push_type==null) {
            // // 为了兼容之前没有设置过meta信息的系统，设置默认值为1
            // push_type="1";
            // }
            //
            // if (push_type.equals("1")) {
            // BaiduPushUtil.startBaiduPush(context);
            //
            // // 使用百度推送
            // // Push: 无账号初始化，用api key绑定
            //// PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY,
            //// PackageUtils.getMetaValue(context, "api_key"));
            // }
            // else if (push_type.equals("2")) {
            // // 使用个推推送
            // GeTuiUtil.startGetuiPush(context);
            // } if (push_type.equals("3")) {
            // // 开启个推推送
            // GeTuiUtil.startGetuiPush(context);
            // // 开启百度推送
            // BaiduPushUtil.startBaiduPush(context);
            // // 开启YxSocket推送
            // YxSocketPushUtil.startYxSocketPush(context);
            // }
        }


    }
