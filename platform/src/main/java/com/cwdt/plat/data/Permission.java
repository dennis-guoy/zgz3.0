package com.cwdt.plat.data;

import android.os.Build.VERSION;

public final class Permission {
    public static final String[] CALENDAR;
    public static final String[] CAMERA;
    public static final String[] CONTACTS;
    public static final String[] LOCATION;
    public static final String[] MICROPHONE;
    public static final String[] PHONE;
    public static final String[] SENSORS;
    public static final String[] SMS;
    public static final String[] STORAGE;
    public static final String[] PHONEANDSMS;
    static {
        if (VERSION.SDK_INT < 23) {
            CALENDAR = new String[0];
            CAMERA = new String[0];
            CONTACTS = new String[0];
            LOCATION = new String[0];
            MICROPHONE = new String[0];
            PHONE = new String[0];
            SENSORS = new String[0];
            SMS = new String[0];
            STORAGE = new String[0];
            PHONEANDSMS = new String[0];
        } else {
            CALENDAR = new String[]{"android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR"};
            CAMERA = new String[]{"android.permission.CAMERA"};
            CONTACTS = new String[]{"android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS"};
            LOCATION = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
            MICROPHONE = new String[]{"android.permission.RECORD_AUDIO"};
            PHONE = new String[]{"android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.USE_SIP", "android.permission.PROCESS_OUTGOING_CALLS"};
            SENSORS = new String[]{"android.permission.BODY_SENSORS"};
            SMS = new String[]{"android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS"};
            STORAGE = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            PHONEANDSMS = new String[]{"android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.USE_SIP", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS"};

        }

    }
}
