package com.cwdt.plat.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.cwdt.plat.data.Const;
import com.cwdt.plat.data.ReceivedDataProcesser;
import com.cwdt.plat.data.SampleDataProcesser;
import com.cwdt.plat.dataopt.SocketDataInfo;
import com.cwdt.plat.util.CwdtApplication;
import com.cwdt.plat.util.SocketCmdInfo;
import com.cwdt.plat.util.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class YxSocketService extends Service {
    public static final String TAG = "YxSocketService";
    public static final String ACTION_LINK_START = "ACTION_LINK_START";
    public static final String ACTION_LINK_STOP = "ACTION_LINK_STOP";
    public static final String ACTION_KEEP_ALIVE = "ACTION_KEEP_ALIVE";
    public static final String ACTION_RECONNECT = "ACTION_RECONNECT";
    public static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    public static final String ACTION_SET_TAGS = "ACTION_SET_TAGS";
    public static final String ACTION_DEL_TAGS = "ACTION_DEL_TAGS";

    public static final String BROADCAST_SEND_MSG = "BROADCAST_SEND_MSG";
    public static final String BROADCAST_MSG_SEND_STATUS = "BROADCAST_MSG_SEND_STATUS";
    public static final String BROADCAST_LINK_START = "BROADCAST_LINK_START";
    public static final String BROADCAST_LINK_STOP = "BROADCAST_LINK_STOP";
    public static final String BROADCAST_KEEP_ALIVE = "BROADCAST_KEEP_ALIVE";
    public static final String BROADCAST_RECONNECT = "BROADCAST_RECONNECT";
    public static final String BROADCAST_NETWORK_DATA_RECEIVED = "BROADCAST_NETWORK_DATA_RECEIVED";
    public static final String BROADCAST_NETWORK_STATUS = "BROADCAST_NETWORK_STATUS";

    public static String BROADCAST_YX_SOCKET_DATA_RECEIVED = CwdtApplication
            .getInstance().getPackageName()
            + ".BROADCAST_YX_SOCKET_DATA_RECEIVED";

    public static final String EXT_DTA_SOCKET_COMMAND = "EXT_DTA_SOCKET_COMMAND";
    public static final String EXT_DTA_TAGS = "EXT_DTA_TAGS";
//    /**
//     * 数据发送最大重试次数
//     */
//    public static final int I_SEND_ERR_RETRY = 5;
//    /**
//     * 读取错误最大允许次数
//     */
//    public static final int I_MAX_READ_ERR = 10;
//
//    public static Boolean isConnected = false;
//    private boolean isBusy = true;
//    private boolean isInReconnecting = false;
//    private Object lockDataSend = new Object();
//    private AlarmManager alarmManager;
//    private ConnectivityManager connectivityManager;
//    private Socket m_socket;
//    private BufferedReader m_socketReader;
//    private PrintWriter m_socketWriter;
//
//    private Handler sockConnectHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            isInReconnecting = false;
//            switch (msg.what) {
//                case 0:
//                    // 连接成功
//                    Log.d(TAG, "连接成功");
//                    sendNetworkStatusBroadcast(0);
//                    createSocketReadThread();
//                    makeKeepAliveAlerm();
//
//                    // 创建并注册连接成功，则自动注册频道监听
//                    if (Const.isDebug) {
//                        Log.d(TAG, "创建并注册连接成功，则自动注册频道监听");
//                    }
//                    ArrayList<String> tagsArrayList = new ArrayList<String>();
//                    tagsArrayList.addAll(Const.TS_Listend_Channels);
//                    settags(tagsArrayList);
//                    break;
//                case 1:
//                    // 网络未连接造成的连接失败,启动定时器进行重新连接]
//                    Log.d(TAG, "网络未连接造成的连接失败，启动重新定时器");
//
//                    sendNetworkStatusBroadcast(2);
//                    makeReconnectAlarm(false);
//                    break;
//                case 2:
//                    // 其他原因造成的连接失败,可以重试连接
//                    Log.d(TAG, "其他原因造成的连接失败，初始化连接组件");
//
//                    sendNetworkStatusBroadcast(2);
//                    makeReconnectAlarm(false);
//                    break;
//
//                case 3:
//                    // 用户没有登录
//
//                    break;
//            }
//        }
//
//    };
//
//    private Handler dataReceiveHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            try {
//                switch (msg.what) {
//                    case 0:
//                        // 数据接收成功
//                        String strRev = msg.obj.toString();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("data", strRev);
//                        SocketDataInfo socketdta = SocketCmdInfo
//                                .parseSocketDataInfo(strRev);
//
//                        bundle.putSerializable(EXT_DTA_SOCKET_COMMAND, socketdta);
//                        Tools.SendBroadCast(YxSocketService.this,
//                                BROADCAST_YX_SOCKET_DATA_RECEIVED, bundle);
//
//                        break;
//
//                    case 1:
//                    case 2:
//                        // 其他原因造成的连接失败
//                        if (msg.arg1 >= I_MAX_READ_ERR) {
//                            // 关闭连接并启动定时重新连接程序
//                            makeReconnectAlarm(false);
//                        }
//                        break;
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//
//        }
//
//    };
//
//    /**
//     * 数据发送Handler
//     */
//    private Handler dataSendedHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            sendNetworkStatusBroadcast(4);
//            switch (msg.what) {
//                case 0:
//                    // 数据发送成功,发送广播给需要的窗口
//                    HashMap<String, String> ExtraData = new HashMap<String, String>();
//                    ExtraData.put("data", msg.obj.toString());
//                    ExtraData.put("sendstatus", "0");
//                    Tools.SendBroadCast(YxSocketService.this,
//                            BROADCAST_MSG_SEND_STATUS, ExtraData);
//                    Log.d(TAG, "数据发送成功：" + msg.obj.toString());
//                    break;
//                case 1:
//                    // 由于网络原因发送失败
//                    Log.d(TAG, "由于网络原因发送失败：" + msg.obj.toString());
//                    if (msg.arg1 <= I_SEND_ERR_RETRY) {
//                        // 失败重试次数小于全局发送重试次数，重新发送
//                        Log.d(TAG, "重新发送：" + msg.arg1 + "/" + I_SEND_ERR_RETRY
//                                + " " + msg.obj.toString());
//                        sendCmd(msg.obj.toString());
//                    }
//                    break;
//                case 2:
//                    // 由于未知原因发送失败
//                    Log.d(TAG, "由于未知原因发送失败,准备重建连接：" + msg.obj.toString());
//                    makeReconnectAlarm(false);
//                    break;
//
//                case 3:
//                    // 网络已断开
//                    Log.d(TAG, "网络已断开,准备重建连接：" + msg.obj.toString());
//
//                    makeReconnectAlarm(false);
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    };
//    /**
//     * 心跳包定时器广播接收
//     */
//    private BroadcastReceiver keepAliveSendReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            sendheartbeat();
//        }
//    };
//
//    /**
//     * 通过网络发送信息操作广播接收
//     */
//    private BroadcastReceiver messageSendReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//                String msgString = intent.getStringExtra("data");
//                if (msgString != null) {
//                    if (msgString.length() > 0) {
//
//                        Intent intent2 = new Intent(ACTION_SEND_MSG);
//                        intent2.putExtra("data", msgString);
//                        startService(intent2);
//                        // sendCmd(msgString);
//                    }
//                }
//            } catch (Exception e) {
//                Log.e(TAG, e.getMessage());
//            }
//        }
//    };
//
//    /**
//     * 重新连接定时器广播接收
//     */
//    private BroadcastReceiver reconnectSocketReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//
//                Log.d(TAG, "收到重新连接服务器广播,当前连接状态" + isConnected.toString());
//                if (!isConnected) {
//                    Intent intent1 = new Intent(context, YxSocketService.class);
//                    intent1.setAction(ACTION_LINK_START);
//                    startService(intent1);
//                    // initSocketCom();
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//        }
//    };
//
//    private BroadcastReceiver networkStatChangeReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//
//                String action = intent.getAction();
//                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//                    Log.d("mark", "网络状态已经改变");
//
//
////					connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
////					NetworkInfo info = connectivityManager
////							.getActiveNetworkInfo();
//                    if (NetworkUtils.isAvailableByPing()) {
//                        if ((!isConnected)) {
//                            Log.d(TAG, "开始连接服务器");
//                            makeReconnectAlarm(true);
//                        }
//                    } else {
//                        Log.d(TAG, "没有可用网络");
//                        releaseSocketCom();
//                    }
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//        }
//    };
//
    public YxSocketService() {

    }
//
//    protected void parsRecievedData(String strRev) {
//        try {
//
//            ReceivedDataProcesser rdp = new SampleDataProcesser(strRev);
//            rdp.curContext = YxSocketService.this;
//            rdp.dataProcess();
//
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
//    }

//    private void initSocketCom() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String strUserID = Const.curUserInfo.UserId;
//                    Log.d(TAG, "初始化网络组件,当前用户ID：" + strUserID);
//                    if (!TextUtils.isEmpty(strUserID)) {
//                        Log.d(TAG, "正在创建服务器连接");
//                        isBusy = true;
//                        m_socket = new Socket(Const.SERVER_ADDR,
//                                Const.SERVER_PORT);
//                        m_socket.setKeepAlive(true);
//
//                        // 连接创建完成后直接发送数据连接创建指令
//                        m_socketReader = new BufferedReader(
//                                new InputStreamReader(m_socket.getInputStream()));
//                        m_socketWriter = new PrintWriter(m_socket
//                                .getOutputStream());
//
//                        // CreateSocketData csData = new CreateSocketData();
//                        m_socketWriter.write(SocketCmdInfo
//                                .getCreateSocketStr(strUserID));
//                        m_socketWriter.flush();
//
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        String strRec = m_socketReader.readLine();
//
//                        if (strRec != null) {
//                            SocketDataInfo sdi = SocketCmdInfo
//                                    .parseSocketDataInfo(strRec);
//                            // 判断命令类型是否为连接指令，并且返回状态为正确
//                            // if
//                            // ((sdi.SocketCommand==SocketCmdInfo.CREATESOCKCOMMAND)&&(sdi.Cmdstatus.equals(SocketCmdInfo.COMMANDOK)))
//                            // {
//                            //
//                            // }
//                            // 判断命令类型是否为连接指令，并且返回状态为正确
//                            if ((sdi.SocketCommand == SocketCmdInfo.CREATESOCKCOMMAND)
//                                    && (sdi.Cmdstatus
//                                    .equals(SocketCmdInfo.COMMANDOK))) {
//
//                                // if (strRec
//                                // .startsWith(String.valueOf(SocketCmdInfo.CREATESOCKCOMMAND)))
//                                // {
//                                isConnected = true;
//                                isBusy = false;
//                                Message msg = sockConnectHandler
//                                        .obtainMessage();
//                                msg.what = 0;
//                                msg.sendToTarget();
//
//                            } else {
//                                Message msg = sockConnectHandler
//                                        .obtainMessage();
//                                msg.what = 2;
//                                msg.sendToTarget();
//                                Log.d(TAG, "创建连接指令返回发生错误");
//                            }
//                        } else {
//                            Message msg = sockConnectHandler.obtainMessage();
//                            msg.what = 2;
//                            msg.sendToTarget();
//                            Log.d(TAG, "创建连接指令返回发生错误");
//                        }
//                        // m_socketReader.close();
//                        // m_socketWriter.close();
//                    } else {
//                        // 用户可能没有登录，不再建立连接
//                        Message msg = sockConnectHandler.obtainMessage();
//                        msg.what = 3;
//                        msg.sendToTarget();
//                        Log.d(TAG, "用户没有登录");
//                    }
//
//                } catch (UnknownHostException e) {
//
//                    try {
//                        if (m_socketReader != null) {
//                            m_socketReader.close();
//                        }
//
//                    } catch (IOException e1) {
//                        // TODO Auto-generated catch block
//                        // e1.printStackTrace();
//                    }
//                    // m_socketWriter.close();
//                    Message msg = sockConnectHandler.obtainMessage();
//                    msg.what = 1;
//                    msg.sendToTarget();
//                    e.printStackTrace();
//                } catch (IOException e) {
//
//                    try {
//                        if (m_socketReader != null) {
//                            m_socketReader.close();
//                        }
//
//                    } catch (IOException e1) {
//                        // TODO Auto-generated catch block
//                        // e1.printStackTrace();
//                    }
//                    // m_socketWriter.close();
//                    Message msg = sockConnectHandler.obtainMessage();
//                    msg.what = 2;
//                    msg.obj = e;
//                    msg.sendToTarget();
//                    // TODO Auto-generated catch block
//                    // e.printStackTrace();
//                }
//            }
//        }).start();
//    }

//    private void releaseSocketCom() {
//        isConnected = false;
//        releaseKeepAliveAlerm();
//        releaseReconnectAlarm();
//        sendNetworkStatusBroadcast(1);
//        try {
//
//            m_socket.shutdownInput();
//            m_socket.shutdownOutput();
//            m_socket.close();
//            m_socketWriter.close();
//            m_socketReader.close();
//        } catch (Exception e) {
//            // Log.e(TAG, e.getMessage());
//        }
//    }

    /**
     * 创建心跳包定时器
     */
//    private void makeKeepAliveAlerm() {
//        try {
//
//            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//            Intent myIntent = new Intent();
//            myIntent.setAction(BROADCAST_KEEP_ALIVE);
//            PendingIntent pi = PendingIntent.getBroadcast(this, 0, myIntent, 0);
//            Calendar cNow = Calendar.getInstance();
//
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//                    cNow.getTimeInMillis() + 1000,
//                    Const.HEARTBEAT_TIME * 1000 * 60, pi);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//        // cNow.getTimeInMillis()+1000, 1000 * 5, pi);
//    }

    /*
     * 创建重新连接定时器
     */
//    private void makeReconnectAlarm(Boolean now) {
//        try {
//
//            Log.d(TAG, "创建重连接定时器：是否已经启动：" + isInReconnecting + " 是否立即启动：" + now);
//            if (!isInReconnecting || now) {
//                sendNetworkStatusBroadcast(5);
//                isInReconnecting = true;
//                releaseSocketCom();
//                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                Intent myIntent = new Intent();
//                myIntent.setAction(BROADCAST_LINK_START);
//                PendingIntent pi = PendingIntent.getBroadcast(this, 0,
//                        myIntent, 0);
//                if (now) {
//                    Log.d(TAG, "设定为立即重新连接");
//                    alarmManager.set(AlarmManager.RTC_WAKEUP,
//                            System.currentTimeMillis() + 100, pi);
//                } else {
//                    Log.d(TAG, "设定为" + Const.SOCKETRECONNECT_TIME + "分钟后重新连接");
//                    alarmManager.set(AlarmManager.RTC_WAKEUP,
//                            System.currentTimeMillis()
//                                    + Const.SOCKETRECONNECT_TIME * 1000 * 60,
//                            pi);
//                }
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }

    /*
     * 取消重新连接定时器
     */
//    private void releaseReconnectAlarm() {
//        // releaseSocketCom();
//        try {
//
//            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//            Intent myIntent = new Intent();
//            myIntent.setAction(BROADCAST_LINK_START);
//            PendingIntent pi = PendingIntent.getBroadcast(this, 0, myIntent, 0);
//            alarmManager.cancel(pi);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }

    /**
     * 释放心跳包定时器
     */
//    private void releaseKeepAliveAlerm() {
//        try {
//
//            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//            Intent myIntent = new Intent();
//            myIntent.setAction(BROADCAST_KEEP_ALIVE);
//            PendingIntent pi = PendingIntent.getBroadcast(this, 0, myIntent, 0);
//            alarmManager.cancel(pi);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
//        super.onCreate();
//        initSocketCom();
//        regReceiver();
    }

//    private void regReceiver() {
//        IntentFilter iFilter = new IntentFilter();
//        iFilter.addAction(BROADCAST_KEEP_ALIVE);
//        registerReceiver(keepAliveSendReceiver, iFilter);
//
//        IntentFilter iFilter2 = new IntentFilter();
//        iFilter2.addAction(BROADCAST_LINK_START);
//        iFilter2.addAction(BROADCAST_RECONNECT);
//        registerReceiver(reconnectSocketReceiver, iFilter2);
//
//        IntentFilter mFilter = new IntentFilter();
//        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(networkStatChangeReceiver, mFilter);
//
//        IntentFilter mFilter1 = new IntentFilter();
//        mFilter.addAction(BROADCAST_SEND_MSG);
//        registerReceiver(messageSendReceiver, mFilter1);
//
//    }
//
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        releaseSocketCom();
//        unregReceiver();
    }
//
//    private void unregReceiver() {
//        unregisterReceiver(keepAliveSendReceiver);
//        unregisterReceiver(reconnectSocketReceiver);
//        unregisterReceiver(networkStatChangeReceiver);
//        unregisterReceiver(messageSendReceiver);
//    }
//
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
//        try {
//            if (!isConnected) {
//                initSocketCom();
//            }
//
//            if (intent.getAction().equals(ACTION_KEEP_ALIVE)) {
//                sendheartbeat();
//            } else if (intent.getAction().equals(ACTION_LINK_START)) {
//                // if (!isConnected || !isInReconnecting) {
//                Log.d(TAG, "重新连接服务器,当前连接状态" + isConnected.toString());
//                initSocketCom();
//                // }
//            } else if (intent.getAction().equals(ACTION_LINK_STOP)) {
//                Log.d(TAG, "断开服务器连接");
//                disconnect();
//                // releaseSocketCom();
//            } else if (intent.getAction().equals(ACTION_RECONNECT)) {
//                Log.d(TAG, "立刻重新连接服务器");
//                boolean now = intent.getBooleanExtra("now", false);
//                makeReconnectAlarm(now);
//            } else if (intent.getAction().equals(ACTION_SEND_MSG)) {
//
//                String msgString = intent.getStringExtra("data");
//                Log.d(TAG, "发送信息：" + msgString);
//                if (msgString != null) {
//                    if (msgString.length() > 0) {
//                        sendCmd(msgString);
//                    }
//                }
//            } else if (intent.getAction().equals(ACTION_SET_TAGS)) {
//                ArrayList<String> arrayList = intent.getExtras()
//                        .getStringArrayList(EXT_DTA_TAGS);
//                settags(arrayList);
//            } else if (intent.getAction().equals(ACTION_DEL_TAGS)) {
//                ArrayList<String> arrayList = intent.getExtras()
//                        .getStringArrayList(EXT_DTA_TAGS);
//                deltags(arrayList);
//            }
//        } catch (Exception e) {
//            // Log.e(TAG, e.getMessage());
//        }

        return super.onStartCommand(intent, flags, startId);
    }
//
//    /**
//     * 初始化网络数据接收线程
//     */
//    private void createSocketReadThread() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int iErrCount = 0;
//                while (isConnected
//                        && (m_socket.isConnected() && (!m_socket
//                        .isInputShutdown()))) {
//                    try {
//                        m_socketReader = new BufferedReader(
//                                new InputStreamReader(m_socket.getInputStream()));
//                        String strRev = m_socketReader.readLine();
//                        Log.d(TAG, "收到数据：" + strRev);
//                        if (strRev != null) {
//                            iErrCount = 0;
//                            Message msgMessage = new Message();
//                            msgMessage.what = 0;
//                            msgMessage.obj = strRev;
//                            msgMessage.arg1 = iErrCount;
//                            dataReceiveHandler.sendMessage(msgMessage);
//                        } else {
//                            iErrCount++;
//                            Message msgMessage = new Message();
//                            msgMessage.what = 1;
//                            msgMessage.arg1 = iErrCount;
//                            dataReceiveHandler.sendMessage(msgMessage);
//                        }
//
//                    } catch (IOException e) {
//                        iErrCount++;
//                        Message msgMessage = new Message();
//                        msgMessage.what = 2;
//                        msgMessage.arg1 = iErrCount;
//                        dataReceiveHandler.sendMessage(msgMessage);
//                        Log.e(TAG, e.getMessage());
//                    }
//                }
//            }
//        }).start();
//    }
//
//    public void sendCmd(final String strmsg) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (lockDataSend) {
//
//                    try {
//                        // 如果网络读写处于使用状态，则等待
//                        while (isBusy) {
//                            try {
//                                Thread.sleep(100);
//                            } catch (Exception e) {
//                                Log.e(TAG, e.getMessage());
//                            }
//                        }
//
//                        sendNetworkStatusBroadcast(3);
//                        Message msg = new Message();
//                        isBusy = true;
//                        if (m_socket.isConnected() && isConnected) {
//                            try {
//                                Log.d(TAG, "发送数据：" + strmsg);
//
//                                m_socketWriter = new PrintWriter(
//                                        m_socket.getOutputStream());
//                                m_socketWriter.write(strmsg);
//                                m_socketWriter.flush();
//                                msg.what = m_socketWriter.checkError() ? 1 : 0;
//                                msg.obj = strmsg;
//
//                            } catch (Exception e) {
//                                msg.what = 1;
//                                msg.obj = strmsg;
//                                // m_socketWriter.close();
//                                Log.e(TAG, e.getMessage());
//                            }
//                        } else {
//                            msg.what = 3;
//                            msg.obj = strmsg;
//                        }
//                        isBusy = false;
//                        dataSendedHandler.sendMessage(msg);
//
//                    } catch (Exception e) {
//                        isBusy = false;
//                        Message msg = dataSendedHandler.obtainMessage();
//                        msg.what = 2;
//                        msg.obj = e;
//                        msg.sendToTarget();
//                        Log.e(TAG, e.getMessage());
//                    }
//                }
//            }
//        }).start();
//    }
//
//    private void disconnect() {
//        sendCmd(SocketCmdInfo.getCloseSocketStr(Const.curUserInfo.UserId));
//        try {
//            Thread.sleep(200);
//            releaseSocketCom();
//
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
//    }
//
//    /**
//     * 发送心跳包
//     */
//    private void sendheartbeat() {
//        try {
//            if (isConnected) {
//                Intent intent = new Intent(ACTION_SEND_MSG);
//                intent.setClass(YxSocketService.this, YxSocketService.class);
//                // HeartData heartData = new HeartData();
//                intent.putExtra("data",
//                        SocketCmdInfo.getHeartBeatStr(Const.curUserInfo.UserId));
//                startService(intent);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
//
//    /**
//     * 发送设置监听频道信息
//     *
//     * @param tagsList
//     */
//    private void settags(ArrayList<String> tagsList) {
//        try {
//            if (isConnected) {
//                Intent intent = new Intent(ACTION_SEND_MSG);
//                intent.setClass(YxSocketService.this, YxSocketService.class);
//                intent.putExtra("data", SocketCmdInfo.getSetTagsSocketStr(
//                        Const.curUserInfo.UserId, tagsList));
//                startService(intent);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
//
//    /**
//     * 发送设置监听频道信息
//     *
//     * @param tagsList
//     */
//    private void deltags(ArrayList<String> tagsList) {
//        try {
//            if (isConnected) {
//                Intent intent = new Intent(ACTION_SEND_MSG);
//                intent.setClass(YxSocketService.this, YxSocketService.class);
//                intent.putExtra("data", SocketCmdInfo.getDeleteTagsSocketStr(
//                        Const.curUserInfo.UserId, tagsList));
//                startService(intent);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
//
//    /**
//     * 通过广播发送当前网络状态信息
//     *
//     * @param status 当前网络状态：<br>
//     *               0:已连接 <br>
//     *               1:已断开 <br>
//     *               2:连接发生错误 <br>
//     *               3:数据发送中 <br>
//     *               4:数据发送完成 <br>
//     *               5:定时连接已启动<br>
//     */
//    private void sendNetworkStatusBroadcast(int status) {
//        try {
//            Bundle bundle = new Bundle();
//            bundle.putInt("netstatus", status);
//            bundle.putBoolean("isconnected", isConnected);
//            Tools.SendBroadCast(YxSocketService.this, BROADCAST_NETWORK_STATUS,
//                    bundle);
//
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
}
