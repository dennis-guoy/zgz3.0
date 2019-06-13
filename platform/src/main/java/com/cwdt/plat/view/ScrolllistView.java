package com.cwdt.plat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @Description:解决在scrollview中只显示第一行数据的问题
 */
public class ScrolllistView extends ListView {
	public ScrolllistView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrolllistView(Context context) {
		super(context);
	}

	public ScrolllistView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
