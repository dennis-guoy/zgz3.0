package com.cwdt.plat.view;



import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jngs.library.platfrom.R;

/**
 * 下拉刷新控件
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class PullToRefreshListView_two extends ListView implements OnScrollListener {

	protected final static String TAG = "PullToRefreshListView";

	// 下拉刷新标志
	protected final static int PULL_To_REFRESH = 0;
	// 松开刷新标志
	protected final static int RELEASE_To_REFRESH = 1;
	// 正在刷新标志
	protected final static int REFRESHING = 2;
	// 刷新完成标志
	protected final static int DONE = 3;

	protected LayoutInflater inflater;

	protected LinearLayout headView;
	protected TextView tipsTextview;
	protected TextView lastUpdatedTextView;
	protected ImageView arrowImageView;
	protected ProgressBar progressBar;
	// 用来设置箭头图标动画效果
	protected RotateAnimation animation;
	protected RotateAnimation reverseAnimation;

	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	protected boolean isRecored;

	protected int headContentWidth;
	protected int headContentHeight;
	protected int headContentOriginalTopPadding;

	protected int startY;
	protected int firstItemIndex;
	protected int currentScrollState;
	public int state;
	protected boolean isBack;

	protected final static int iPageSize = 20;
	protected View footerView;
	protected TextView foot_more;
	protected ProgressBar foot_progress;

	protected final int LISTVIEW_DATA_MORE = 1;
	protected final int LISTVIEW_DATA_LOADING = 2;
	protected final int LISTVIEW_DATA_FULL = 3;
	protected final int LISTVIEW_DATA_EMPTY = 4;
	// 获取下页数据事件
	protected OnGetNextPage onGetNextPage = null;
	protected int iDataCount = 0;
	protected int lastvisible = 1;
	protected Handler mHandler = new Handler();

	public OnRefreshListener refreshListener;

	public PullToRefreshListView_two(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PullToRefreshListView_two(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	protected void init(Context context) {
		// 设置滑动效果
		animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(100);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(100);
		reverseAnimation.setFillAfter(true);

		inflater = LayoutInflater.from(context);
		headView = (LinearLayout) inflater.inflate(R.layout.pull_to_refresh_head, null);

		arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(50);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

		headContentOriginalTopPadding = headView.getPaddingTop();

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();

		headView.setPadding(headView.getPaddingLeft(), -1 * headContentHeight, headView.getPaddingRight(),
				headView.getPaddingBottom());
		headView.invalidate();

		// System.out.println("初始高度："+headContentHeight);
		// System.out.println("初始TopPad："+headContentOriginalTopPadding);

		addHeaderView(headView);
		setOnScrollListener(this);

		// 改进版本，增加footer

		footerView = inflater.inflate(R.layout.listview_footer, null);
		foot_more = (TextView) footerView.findViewById(R.id.listview_foot_more);
		foot_progress = (ProgressBar) footerView.findViewById(R.id.listview_foot_progress);

		addFooterView(footerView);

	}

	@Override
	public void onScroll(AbsListView view, int firstVisiableItem, int visibleItemCount, int totalItemCount) {
		firstItemIndex = firstVisiableItem;
		iDataCount = totalItemCount;
		// lastvisible = firstVisiableItem + visibleItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		currentScrollState = scrollState;
		// 修改集成footer
		if (getAdapter().isEmpty()) {
			return;
		}
		// 判断是否滚动到底部
		boolean scrollEnd = false;
		try {
			if (view.getPositionForView(footerView) == view.getLastVisiblePosition()) {
				scrollEnd = true;
				lastvisible = view.getLastVisiblePosition();
			}
		} catch (Exception e) {
			scrollEnd = false;
		}
		Object tagObject = this.getTag();
		if (tagObject == null) {
			tagObject = 0;
		}
		int lvDataState = Integer.valueOf(tagObject.toString());
		if (scrollEnd && lvDataState == LISTVIEW_DATA_MORE) {
			this.setTag(LISTVIEW_DATA_LOADING);
			foot_more.setText(R.string.pull_to_refresh_refreshing_label);
			foot_progress.setVisibility(View.VISIBLE);
			// 当前pageIndex
			int pageIndex = iDataCount / iPageSize;
			if (onGetNextPage != null) {
				// 保存当前最后一条信息位置
				// lastvisible = getAdapter().getCount()-1;
				onGetNextPage.OnGetNextPage(pageIndex, pageIndex + 1, iPageSize, iDataCount);

			}
		}

	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (refreshListener == null) {
			return super.onTouchEvent(event);
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (firstItemIndex == 0 && !isRecored) {
				startY = (int) event.getY();
				isRecored = true;
				// System.out.println("当前-按下高度-ACTION_DOWN-Y："+startY);
			}
			break;

		case MotionEvent.ACTION_CANCEL:// 失去焦点&取消动作
		case MotionEvent.ACTION_UP:

			if (state != REFRESHING) {
				if (state == DONE) {
					// System.out.println("当前-抬起-ACTION_UP：DONE什么都不做");
				} else if (state == PULL_To_REFRESH) {
					state = DONE;
					changeHeaderViewByState();
					// System.out.println("当前-抬起-ACTION_UP：PULL_To_REFRESH-->DONE-由下拉刷新状态到刷新完成状态");
				} else if (state == RELEASE_To_REFRESH) {
					state = REFRESHING;
					changeHeaderViewByState();
					onRefresh();
					// System.out.println("当前-抬起-ACTION_UP：RELEASE_To_REFRESH-->REFRESHING-由松开刷新状态，到刷新完成状态");
				}
			}

			isRecored = false;
			isBack = false;

			break;

		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();
			// System.out.println("当前-滑动-ACTION_MOVE Y："+tempY);
			if (!isRecored && firstItemIndex == 0) {
				// System.out.println("当前-滑动-记录拖拽时的位置 Y："+tempY);
				isRecored = true;
				startY = tempY;
			}
			if (state != REFRESHING && isRecored) {
				// 可以松开刷新了
				if (state == RELEASE_To_REFRESH) {
					// 往上推，推到屏幕足够掩盖head的程度，但还没有全部掩盖
					if ((tempY - startY < headContentHeight + 20) && (tempY - startY) > 0) {
						state = PULL_To_REFRESH;
						changeHeaderViewByState();
						// System.out.println("当前-滑动-ACTION_MOVE：RELEASE_To_REFRESH--》PULL_To_REFRESH-由松开刷新状态转变到下拉刷新状态");
					}
					// 一下子推到顶
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();
						// System.out.println("当前-滑动-ACTION_MOVE：RELEASE_To_REFRESH--》DONE-由松开刷新状态转变到done状态");
					}
					// 往下拉，或者还没有上推到屏幕顶部掩盖head
					else {
						// 不用进行特别的操作，只用更新paddingTop的值就行了
					}
				}
				// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
				else if (state == PULL_To_REFRESH) {
					// 下拉到可以进入RELEASE_TO_REFRESH的状态
					if (tempY - startY >= headContentHeight + 20 && currentScrollState == SCROLL_STATE_TOUCH_SCROLL) {
						state = RELEASE_To_REFRESH;
						isBack = true;
						changeHeaderViewByState();
						// System.out.println("当前-滑动-PULL_To_REFRESH--》RELEASE_To_REFRESH-由done或者下拉刷新状态转变到松开刷新");
					}
					// 上推到顶了
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();
						// System.out.println("当前-滑动-PULL_To_REFRESH--》DONE-由Done或者下拉刷新状态转变到done状态");
					}
				}
				// done状态下
				else if (state == DONE) {
					if (tempY - startY > 0) {
						state = PULL_To_REFRESH;
						changeHeaderViewByState();
						// System.out.println("当前-滑动-DONE--》PULL_To_REFRESH-由done状态转变到下拉刷新状态");
					}
				}

				// 更新headView的size
				if (state == PULL_To_REFRESH) {
					int topPadding = (tempY - startY - headContentHeight);
					headView.setPadding(headView.getPaddingLeft(), topPadding, headView.getPaddingRight(),
							headView.getPaddingBottom());
					headView.invalidate();
					// System.out.println("当前-下拉刷新PULL_To_REFRESH-TopPad："+topPadding);
				}

				// 更新headView的paddingTop
				if (state == RELEASE_To_REFRESH) {
					int topPadding = (tempY - startY - headContentHeight);
					headView.setPadding(headView.getPaddingLeft(), topPadding / 3, headView.getPaddingRight(),
							headView.getPaddingBottom());
					headView.invalidate();
					// System.out.println("当前-释放刷新RELEASE_To_REFRESH-TopPad："+topPadding);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	// 当状态改变时候，调用该方法，以更新界面
	public void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:

			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText(R.string.pull_to_refresh_release_label);

			// Log.v(TAG, "当前状态，松开刷新");
			break;
		case PULL_To_REFRESH:

			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);
			}
			tipsTextview.setText(R.string.pull_to_refresh_pull_label);

			// Log.v(TAG, "当前状态，下拉刷新");
			break;

		case REFRESHING:
			// System.out.println("刷新REFRESHING-TopPad："+headContentOriginalTopPadding);
			headView.setPadding(headView.getPaddingLeft(), headContentOriginalTopPadding, headView.getPaddingRight(),
					headView.getPaddingBottom());
			headView.invalidate();

			progressBar.setVisibility(View.VISIBLE);

			// foot_progress.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText(R.string.pull_to_refresh_refreshing_label);
			lastUpdatedTextView.setVisibility(View.GONE);

			// Log.v(TAG, "当前状态,正在刷新...");
			break;
		case DONE:
			// System.out.println("完成DONE-TopPad："+(-1 * headContentHeight));

			int top = 0;
			int duration = 0;

			while (top < headContentHeight) {

				top += 10;
				duration += 10;
				final int t = top;

				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {

						headView.setPadding(headView.getPaddingLeft(), -1 * t, headView.getPaddingRight(),
								headView.getPaddingBottom());
					}
				}, duration);
			}

			headView.invalidate();

			progressBar.setVisibility(View.GONE);

			foot_progress.setVisibility(View.GONE);

			arrowImageView.clearAnimation();
			// 此处更换图标
			arrowImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);

			tipsTextview.setText(R.string.pull_to_refresh_pull_label);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			// try {
			//
			// setSelection(lastvisible);
			// } catch (Exception e) {
			// // TODO: handle exception
			// }

			// Log.v(TAG, "当前状态，done");
			break;
		}
	}

	// 点击刷新
	public void clickRefresh() {
		setSelection(0);
		state = REFRESHING;
		changeHeaderViewByState();
		onRefresh();
	}

	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void onRefreshComplete(String update) {
		lastUpdatedTextView.setText(update);
		onRefreshComplete();
	}

	public void onRefreshComplete() {
		state = DONE;
		changeHeaderViewByState();
	}

	private void onRefresh() {
		if (refreshListener != null) {
			// lastvisible = 1;
			refreshListener.onRefresh();
		}
	}

	// 计算headView的width及height值
	protected void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setOnGetNextPage(OnGetNextPage l) {
		this.onGetNextPage = l;
	}

	public void dataComplate(int iLastCount, int iOpt) {
		// 通过getAdapter().getCount() 获得的数字是包含头部和尾部的，所以需要减掉
		int dataCount = getAdapter().getCount() - 3;
		if (iLastCount >= 0) {

			if (iLastCount < iPageSize) {
				setTag(LISTVIEW_DATA_FULL);
				foot_more.setText(R.string.load_full);
			} else if (iLastCount == iPageSize) {
				setTag(LISTVIEW_DATA_MORE);
				foot_more.setText(R.string.load_more);
			}

		} else if (iLastCount == -1) {
			// 有异常--显示加载出错 & 弹出错误消息
			setTag(LISTVIEW_DATA_MORE);
			foot_more.setText(R.string.load_error);
		}
		if (dataCount == 0) {
			setTag(LISTVIEW_DATA_EMPTY);
			foot_more.setText(R.string.load_empty);
		}

		foot_progress.setVisibility(View.GONE);
		onRefreshComplete();
		try {
			// setSelection(dataCount +2- iLastCount - iOpt);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean isHeaderOrFooter(View view) {
		boolean bRet = false;
		try {
			if (view == footerView) {
				bRet = true;
				return bRet;
			}
			if (view == headView) {
				bRet = true;
				return bRet;
			}
			if (view.getId() == 1024) {
				bRet = true;
				return bRet;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bRet;
	}

}
