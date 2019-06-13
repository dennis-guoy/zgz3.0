package com.cwdt.plat.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cwdt.plat.util.ResourceHelper;
import com.cwdt.plat.util.Tools;
import com.gyf.barlibrary.ImmersionBar;
import com.jngs.library.platfrom.R;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static com.cwdt.plat.data.Const.TopBarColor;


/**
 * 引用top_bar布局的窗口基类
 *
 * @author Administrator
 */
public abstract class AbstractCwdtActivity extends AppCompatActivity {
    public static String APP_TITLE = "cntitle";
    public Bundle baseBundle;
    public String LogTag = this.getClass().getSimpleName();
    public Dialog progressDialog = null;
    public Button btn_TopLeftButton;
    public ImageView btn_TopLeftImg;
    public Button btn_TopRightButton;
    public TextView tv_apptitle;
    public String strCurrentPage = "1";
    protected ImmersionBar mImmersionBar;

    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baseBundle = getIntent().getExtras();
        if (baseBundle == null) {
            baseBundle = new Bundle();
        }
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
    }

    public void showProgressDialog(String title, String message) {
        progressDialog = Tools.createLoadingDialog(AbstractCwdtActivity.this,
                message);
        progressDialog.show();
    }

    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void setProgressMessage(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            TextView tvmsg = (TextView) progressDialog
                    .findViewById(ResourceHelper.getId(this, "tipTextView"));
            tvmsg.setText(msg);
        }
    }

    public void SetAppTitle(String title) {
        try {
            tv_apptitle = getView(ResourceHelper.getId(this,
                    "apptitle"));
            tv_apptitle.setText(title);
        } catch (Exception e) {
//            Log.e(LogTag, e.getMessage());
        }
    }

    public void PrepareComponents() {
		btn_TopLeftButton = getView(R.id.quxiaobutton);
		btn_TopLeftButton.setVisibility(View.VISIBLE);

		btn_TopLeftButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

        btn_TopLeftImg = getView(R.id.quxiaoimg);
        btn_TopLeftImg.setVisibility(View.VISIBLE);

        btn_TopLeftImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_TopRightButton = getView(R.id.editbutton);
        if (baseBundle.containsKey(APP_TITLE)) {
            SetAppTitle(baseBundle.getString(APP_TITLE));
        }

    }

    /**
     * 根据资源ID获取资源控件实例
     *
     * @param id
     * @return
     */
    public <T extends View> T getView(int id) {
        try {
            return (T) this.findViewById(id);
        } catch (Exception e) {
            Log.e(LogTag, e.getMessage());
            return null;
        }
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        ImmersionBar.with(this)
                .statusBarColor(TopBarColor)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }

    protected <T> T initFromJson(String jsonString) {
        boolean var2 = false;

        try {
            return (T) JSON.parseObject(jsonString, this.getClass());
        } catch (Exception var4) {
            Log.e("BaseSerializableData", var4.getMessage());
            return null;
        }
    }

}
