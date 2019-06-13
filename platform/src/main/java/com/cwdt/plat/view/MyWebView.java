package com.cwdt.plat.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends WebView {

	private Activity mActivity;
	private Context mcontext;

	public MyWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyWebView(Context context, Activity activity) {
		super(context);
		mActivity = activity;
		mcontext = context;
		init(context);
	}

	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setWebViewClient(new MyWebViewClient());

	};

	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			view.loadUrl("file:///android_asset/erro.html");

		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && canGoBack() && !getUrl().equals("file:///android_asset/erro.html")) {
			goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
