package com.cwdt.plat.adapter;

import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class CustomSpinnerAdatpter extends CustomListViewAdatpter {
	public HashMap<String, View> popViewItemList;
	protected int dropdownResource=-1;
	
	public CustomSpinnerAdatpter(Context context) {
		super(context);
		popViewItemList = new HashMap<String, View>();
	}

	@Override
	public void clearCacheViews() {
		super.clearCacheViews();
		popViewItemList.clear();
	}
	public void setDropdownResource(int res) {
		dropdownResource=res;
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
	/**
	 * 专门用于Spinner的Adapter功能，使用时务必继承并在重写的getDropDownView结尾使用return super.getDropDownView(.....)
	 */
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if (convertView!=null) {
			addToCache(position, convertView,parent);
	        return convertView;
		}
		if (dropdownResource!=-1) {
			inflater.inflate(dropdownResource, null);
			addToCache(position, convertView,parent);
		}
		else
		{
			return super.getView(position, convertView, parent);
		}
        return convertView;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		addToCache(position, convertView, parent);
		return convertView;
	}
	/**
	 * 专门用于Spinner的Adapter功能，使用两个缓存对象分别保存Spinner收起状态和弹出状态下的每条View信息
	 * @param position
	 * @param parent getView 或者 getDropDownView 传递的ViewGroup变量
	 * @return
	 */
	public View getCacheView(int position,ViewGroup parent) {
		View itemview = null;
		if (parent.getClass().getName().toString().equals("android.widget.Spinner")) {
			if (viewItemList.containsKey(String.valueOf(position))) {
				itemview = viewItemList.get(String.valueOf(position));
			}
		}
		else
		{
			if (popViewItemList.containsKey(String.valueOf(position))) {
				itemview = popViewItemList.get(String.valueOf(position));
			}			
		}
		
		return itemview;
	}
	/**
	 * 专门用于Spinner的Adapter功能，使用两个缓存对象分别保存Spinner收起状态和弹出状态下的每条View信息
	 * @param position
	 * @param parent getView 或者 getDropDownView 传递的ViewGroup变量
	 * @return
	 */
	public void addToCache(int position, View view,ViewGroup parent) {
		try {
			if (parent.getClass().getName().toString().equals("android.widget.Spinner")) {
				viewItemList.put(String.valueOf(position), view);
			}
			else
			{
				popViewItemList.put(String.valueOf(position), view);
			}
		} catch (Exception e) {

		}
	}
}
