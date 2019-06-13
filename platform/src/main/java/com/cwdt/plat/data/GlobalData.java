package com.cwdt.plat.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.cwdt.plat.util.CwdtApplication;

/**
 * SharedPreferences数据存储类
 */
public class GlobalData {

    private static SharedPreferences myshared = null;

    public static Object getSharedData(Context context, String strName) {
        Object objRet = null;
        if (myshared == null) {

            myshared = context.getSharedPreferences("LoginInfo", 0);
        }
        objRet = myshared.getString(strName, "");
        return objRet;
    }

    public static void SetSharedData(Context context, String strName, Object objValue) {
        if (myshared == null) {
            myshared = context.getSharedPreferences("LoginInfo", 0);
        }
        SharedPreferences.Editor editor = myshared.edit();
        if (objValue.getClass() == String.class) {
            editor.putString(strName, (String) objValue);
            editor.commit();
        }
    }

    public static void SetSharedData(String strName, Object objValue) {
        Context context = CwdtApplication.getInstance().getContext();
        SetSharedData(context, strName, objValue);
    }

    public static Object getSharedData(String strName) {
        Context context = CwdtApplication.getInstance().getContext();
        return getSharedData(context, strName);
    }

    //-----字符转换，为整行的数据对齐的问题
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
