package com.cwdt.plat.adapter;

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewPagersAdapter extends PagerAdapter {
	public ArrayList<View> viewsList; // 滑动的View集合

	public CustomViewPagersAdapter() {

		viewsList = new ArrayList<View>();
	}

	public CustomViewPagersAdapter(ArrayList<View> picsViews) {
		viewsList = picsViews;
	}

	public void setListViews(ArrayList<View> picsViews) {
		viewsList = picsViews;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return viewsList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub

		try {
			container.removeView(viewsList.get(position));
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("sss", position + "");
		}
		// super.destroyItem(container, position, object);
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		// 将每个图片加入到ViewPager里

		((ViewPager) container).addView(viewsList.get(position));
		return viewsList.get(position);

		// return super.instantiateItem(container, position);
	}

}
