
package com.cwdt.tongxunlu.sortlistview;
import java.util.HashMap;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jngs.library.platfrom.R;


/**
 * @author  huangxin
 */
public class QuickAlphabeticBar extends android.support.v7.widget.AppCompatImageButton {
	
	public static final String TAG = "QuickAlphabeticBar";
	
	public static final int DEFAULT_SCREEN_HEIGHT = 800;
	
	public static final int DEFAULT_TEXT_SIZE = 20;
	
	private TextView mDialogText;
	private Handler mHandler;
	private ListView mList;
	private float mHight;
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	private String[] letters = new String[] { 
			"A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "#"};
	
	private HashMap<String, Integer> alphaIndexer=new HashMap<String, Integer>();
	
	private float centerXY[] = null;
	
	private int singleHeight;
	
//	private int singleHeight;
	
	private int height;
	
	private int width;
	
	private float arc = 0;
	
	private float radius;
	
	private float normalWidth;
	private float selectedWidth;
	private boolean isselectedState = false;
	
	private int textSize;
	
	private int startPos = -1;
	private int endPos = -1; 
	
	private Context context;
	
	private float measureTextSize = -1;
	
	private int screenWidth;
	
	private int screenHeight;
//	private TextView mTextDialog;
	
	public QuickAlphabeticBar(Context context) {
		super(context);
		 init(context);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		 init(context);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		 init(context);
	}

/*	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}*/

	private void init(Context context){
		this.context = context;
		for(int i=0;i<letters.length;i++){
			
			if(i!=letters.length-1){
			alphaIndexer.put(letters[i], i);}
			else {
				alphaIndexer.put("#", i);
			}
		}
		
		//textSize = getTextSizeFormRatio();
	
		normalWidth =50;//getResources().getDimensionPixelSize(R.dimen.azbar_normal_width);
//		selectedWidth = height / letters.length;// getResources().getDimensionPixelSize(R.dimen.azbar_selected_width);
		setBackgroundDrawable(new ColorDrawable(0x00000000));
		
	}
	
	public void init(TextView  ctx) {
		mDialogText = ctx;
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler = new Handler();
		
	}
	
	public void setListView(ListView mList) {
		this.mList = mList;
	}

	public void setAlphaIndexer(HashMap alphaIndexer) {
		this.alphaIndexer = alphaIndexer;
	}

	public void setHight(float mHight) {
		this.mHight = mHight;
	}

	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) 	{
		
		int act = event.getAction();
		float y = event.getY();
		float x = event.getX();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		singleHeight = height / letters.length;
		radius = 8 * singleHeight / 2;

	//	int selectIndex = (int) (y / (mHight / letters.length));	
		final int selectIndex = (int) (y / getHeight() * letters.length);	
		if (selectIndex > -1 && selectIndex < letters.length) {
			String key = letters[selectIndex];
			if (alphaIndexer.containsKey(key)) {
	//			int pos = (Integer) alphaIndexer.get(key);
				/*if (mList.getHeaderViewsCount() > 0) {
					this.mList.setSelectionFromTop(
							pos + mList.getHeaderViewsCount(), 0);
				} else {
					this.mList.setSelectionFromTop(pos, 0);
				}*/
			}

			mDialogText.setText(letters[selectIndex]);
			arc = 0;

			if(!isselectedState){
				isselectedState = true;
				//int selected = (int) (centerXY[0] - (radius + paint.measureText(letters[selectIndex])));
				setLayoutParams((int) selectedWidth);
				
			}
		}
		
		switch (act) {
		case MotionEvent.ACTION_DOWN:
			setBackgroundResource(R.drawable.sidebar_background);
			showBkg = true;
			if (listener != null) {
				listener.onTouchingLetterChanged(letters[selectIndex]);
			}
			if (oldChoose != selectIndex) {
				if (selectIndex >= 0 && selectIndex < letters.length) {
					choose = selectIndex;
					mDialogText.setText(letters[selectIndex]);
					invalidate();
				}
			}

			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.INVISIBLE) {
							mDialogText.setVisibility(VISIBLE);
							
						}
					}
				});
			}
			break;
		case MotionEvent.ACTION_MOVE:
			isselectedState=true;
			if(x < selectedWidth * 0.5 && isselectedState){
				reseAlphabeticBar();
			//	return super.onTouchEvent(event);
			}
			if (oldChoose != selectIndex) {
				if (selectIndex >= 0 && selectIndex < letters.length) {
					choose = selectIndex;
					if (listener != null) {
						listener.onTouchingLetterChanged(letters[choose]);
						mDialogText.setText(letters[selectIndex]);
					}
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			setBackgroundResource(R.drawable.sidebar_background);
			//setBackgroundDrawable(new ColorDrawable(0x00000000));
			reseAlphabeticBar();
			break;
		case MotionEvent.ACTION_CANCEL:
			setBackgroundResource(R.drawable.sidebar_background);
			reseAlphabeticBar();
			break;
		default:
			break;
		}

		return super.onTouchEvent(event);
	}
	
	
	private void reseAlphabeticBar(){
		centerXY = null;
		showBkg = false;
		choose = -1;
		isselectedState = false;
		arc = 0;
		setLayoutParams((int) normalWidth);
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (mDialogText != null
							&& mDialogText.getVisibility() == View.VISIBLE) {
						mDialogText.setVisibility(INVISIBLE);
						invalidate();
					}
				}
			});
		}
	}

	
	private void setLayoutParams(int width){
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, 
				LayoutParams.MATCH_PARENT);
		lp.gravity=Gravity.RIGHT;
	//	lp.addRule(FrameLayout.BELOW, -1);
	//	lp.addRule(RelativeLayout.BELOW, R.id.acbuwa_topbar);
		setLayoutParams(lp);
	}
	
	//TODO:
	Paint paint = new Paint();
	boolean showBkg = false;
	int choose = -1;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/*if (showBkg) {//???ñ????????
			canvas.drawColor(Color.parseColor(#b20264));
		}*/

		height = getHeight();
		width = getWidth();
		
		getStartAndEndPosFromArg();
		
		boolean flag = false;
		textSize =  height / letters.length-2;
		selectedWidth =  radius+50;
		for (int i = 0; i < letters.length; i++) {
			paint.setColor(getResources().getColor(android.R.color.black));
			paint.setTextSize(textSize);
			Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
			paint.setTypeface(font);
			paint.setAntiAlias(true); 
			
			measureTextSize = paint.measureText(letters[i]);
			
			float xPos, yPos;
			if(isselectedState){
				xPos = selectedWidth - normalWidth / 2 -  measureTextSize / 2;
			}else{
				xPos = normalWidth / 2 - measureTextSize / 2;
			}
			singleHeight = height / letters.length;
			yPos = singleHeight * i + singleHeight;
			
			if (i == choose) {
				paint.setColor(Color.parseColor("#00BFFF"));
				paint.setFakeBoldText(true);
			}
			
			if((i >= startPos && i <= endPos) && choose != -1 && isselectedState){
				
				if(centerXY == null){
					centerXY = new float[2];
					centerXY[0] = selectedWidth - normalWidth / 2 - measureTextSize / 2;
					centerXY[1] = singleHeight * choose + singleHeight;
				}

				if (!flag) {
					getStartPosFromArg(startPos);
					flag = true;
				}
				
				float[] arcXY = getXYFormArg();
				xPos  =  arcXY[0];
				yPos  =  arcXY[1];

				arc = (float) (arc - 22.5);
				int size = getArgLetterTextSize(i);
				paint.setTextSize(size);
			}
			canvas.drawText(letters[i], xPos, yPos, paint);
			paint.reset();
		}
		centerXY = null;
	}
	
	private void getStartAndEndPosFromArg(){
		if(choose != -1){
			if(choose <= 3){
				startPos = 0;
			}else{
				startPos = choose - 3;
			}

			if(choose - letters.length + 4 <= 0){
				endPos = choose + 3;
			}else{
				endPos = letters.length -1;
			}
		}
	}
	
	private void getStartPosFromArg(int startPos) {

		if (startPos == choose) {
			arc = 180;
		} else if (startPos + 1 == choose) {
			arc = (float) 202.5;
		} else if (startPos + 2 == choose) {
			arc = 225;
		} else if (startPos + 3 == choose) {
			arc = (float) 247.5;
		}
	}

	private int getArgLetterTextSize(int i){
		if(i == choose){
			return textSize + 8;
		}else if(i + 1 == choose || choose + 1 == i){
			return textSize + 6;
		}else if(i + 2== choose || choose + 2 == i){
			return textSize + 4;
		}else if(i + 3== choose || choose + 3 == i){
			return textSize + 4;
		}
		return textSize;
	}
	
	private float[] getXYFormArg(){
		float[] xy = new float[2];	
		xy[0] = (float) (centerXY[0] + radius * Math.cos(arc * Math.PI / 180));
		xy[1] = (float) (centerXY[1] + radius * Math.sin(arc * Math.PI / 180));
		return xy;
	}

		public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}
		public interface OnTouchingLetterChangedListener {
			public void onTouchingLetterChanged(String s);
		}
		
	
}
