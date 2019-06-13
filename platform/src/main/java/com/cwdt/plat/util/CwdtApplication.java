package com.cwdt.plat.util;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.Utils;
import com.cwdt.plat.data.BaseToken;
import com.cwdt.plat.data.BaseUserInfo;
import com.cwdt.plat.data.Const;
import com.cwdt.plat.data.GlobalData;

public class CwdtApplication extends MultiDexApplication {
	private static CwdtApplication mInstance = null;
	public boolean m_bKeyRight = true;

	public Context getContext()
	{
		return mInstance.getApplicationContext();
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		Utils.init(mInstance);
		initGlobalData();
		// initEngineManager(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public void initGlobalData() {
		try
		{
			Const.baseToken = JSON.parseObject(GlobalData.getSharedData(Const.TOKEN_STORE_NAME).toString(), BaseToken.class);
			Const.curUserInfo = JSON.parseObject(GlobalData.getSharedData(Const.USER_STORE_NAME).toString(), BaseUserInfo.class);
		}
		catch (Exception e)
		{
			Log.e("CwdtApplication",e.getMessage());
		}
	}

	public static CwdtApplication getInstance() {
		return mInstance;
	}

	/**
	 * 百度云推送引擎初始化，在需要使用百度云推送的情况下需要在子类创建时调用本函数
	 */
	public void initBaiduPush() {
		// FrontiaApplication.initFrontiaApplication(this);
	}

	private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

	public WindowManager.LayoutParams getWindowParams() {
		return windowParams;
	}
	
}
