package com.cwdt.plat.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 网络连接状态检测类
 * 
 * @author Administrator
 * 
 */
public class ConnectionDetector {

	private Context _context;

	public ConnectionDetector(Context context) {
		this._context = context;
	}

	public boolean isConnectingToInternet() {
		boolean bRet = false;
		try {
			bRet = isNetworkAvailable(_context);
		} catch (Exception e) {

			Log.e("ConnectionDetector", e.getMessage());
			bRet = false;
		}
		return bRet;
	}

	// ---判断网络是否连接
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connect = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connect == null) {
			return false;
		} else// get all network info
		{
			NetworkInfo[] info = connect.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}