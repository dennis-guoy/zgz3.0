package com.cwdt.plat.adapter;

import java.util.HashMap;

import com.cwdt.plat.util.ResourceHelper;
import com.cwdt.plat.util.ResourceUtils;
import com.cwdt.plat.util.Tools;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 全局基础ListViewAdatpter
 */
public class CustomListViewAdatpter extends BaseAdapter {
	protected LayoutInflater inflater;
	protected Context context;
	public HashMap<String, View> viewItemList;
	public Dialog progressDialog = null;

	public CustomListViewAdatpter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		viewItemList = new HashMap<String, View>();
	}

	public void clearCacheViews() {
		viewItemList.clear();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		addToCache(position, convertView);
		return convertView;
	}

	public View getCacheView(int position) {
		View itemview = null;
		if (viewItemList.containsKey(String.valueOf(position))) {
			itemview = viewItemList.get(String.valueOf(position));
		}
		return itemview;
	}

	public void addToCache(int position, View view) {
		try {
			viewItemList.put(String.valueOf(position), view);
		} catch (Exception e) {

		}
	}
	public void showProgressDialog(String title, String message) {
		progressDialog = Tools.createLoadingDialog(context, message);
		progressDialog.show();
	}

	public void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	public void setProgressMessage(String msg) {
		if (progressDialog != null && progressDialog.isShowing()) {
			TextView tvmsg = (TextView) progressDialog.findViewById(ResourceUtils.getId(context, "tipTextView"));
			tvmsg.setText(msg);
		}
	}


}
