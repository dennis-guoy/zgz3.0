package com.cwdt.plat.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.jngs.library.platfrom.R;


/**
 * 自定义dialog
 */
public class MyDialog extends Dialog implements
		View.OnClickListener {
	private DialogClickListener listener;
	Context context;
	private TextView tv_restinfo_pop_tel_content;
	private TextView dialog_textViewID;
	private TextView dialog_textViewID1;
	private TextView shu;
	private String leftBtnText;
	private String rightBtnText;
	private String content;
	private ImageView guanbi;

	public MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/**
	 * 自定义dialog
	 * 
	 * @param context
	 * @param theme
	 *            主题
	 * @param content
	 *            主体文字
	 * @param leftBtnText
	 *            左按钮文字，若为""则隐藏
	 * @param rightBtnText
	 *            右按钮文字，若为""则隐藏
	 * @param listener
	 *            回调接口
	 */
	public MyDialog(Context context, int theme, String content,
			String leftBtnText, String rightBtnText,
			DialogClickListener listener) {
		super(context, theme);
		this.context = context;
		this.content = content;
		this.leftBtnText = leftBtnText;
		this.rightBtnText = rightBtnText;
		this.listener = listener;
	}

	public void setTextSize(int size) {
		dialog_textViewID.setTextSize(size);
		dialog_textViewID1.setTextSize(size);
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_trunk);
		tv_restinfo_pop_tel_content = (TextView) findViewById(R.id.tv_restinfo_pop_tel_content);
		dialog_textViewID1 = (TextView) findViewById(R.id.dialog_textViewID1);
		dialog_textViewID = (TextView) findViewById(R.id.dialog_textViewID);
		guanbi = (ImageView) findViewById(R.id.guanbi);
		shu = (TextView) findViewById(R.id.shu);
		dialog_textViewID.setOnClickListener(this);
		dialog_textViewID1.setOnClickListener(this);
		guanbi.setOnClickListener(this);
		initView();
		initDialog(context);
	}

	/**
	 * 设置dialog的宽为屏幕的3分之1
	 * 
	 * @param context
	 */
	private void initDialog(Context context) {
//		setCanceledOnTouchOutside(false);
		setCancelable(false);
//		setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(DialogInterface dialog, int keyCode,
//					KeyEvent event) {
//				if (keyCode == KeyEvent.KEYCODE_BACK
//						&& event.getRepeatCount() == 0) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		});
		WindowManager windowManager = this.getWindow().getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = display.getWidth() / 6 * 5; // // 设置宽度
		this.getWindow().setAttributes(lp);
	}


	private void initView() {
		tv_restinfo_pop_tel_content.setText(content);
		if (leftBtnText.equals("")) {
			shu.setVisibility(View.GONE);
			dialog_textViewID.setVisibility(View.GONE);
		} else
			dialog_textViewID.setText(leftBtnText);
		if (rightBtnText.equals("")) {
			shu.setVisibility(View.GONE);
			dialog_textViewID1.setVisibility(View.GONE);
		} else
			dialog_textViewID1.setText(rightBtnText);
	}

	public interface DialogClickListener {
		void onLeftBtnClick(Dialog dialog);

		void onRightBtnClick(Dialog dialog);

		void onguanbiBtnClick(Dialog dialog);
	}

	/**
	 * 提示框点击事件
	 *
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.dialog_textViewID) {
			listener.onLeftBtnClick(this);
			return;
		} else if (v.getId() == R.id.dialog_textViewID1) {
			listener.onRightBtnClick(this);
			return;
		} else if (v.getId() == R.id.guanbi) {
			listener.onguanbiBtnClick(this);
			return;
		}
	}
}