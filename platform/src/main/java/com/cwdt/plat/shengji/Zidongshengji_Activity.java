package com.cwdt.plat.shengji;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import com.cwdt.plat.util.OpenFiles;
import com.cwdt.plat.util.StringUtils;
import com.cwdt.plat.util.Tools;
import com.jngs.library.platfrom.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Zidongshengji_Activity extends Activity {
	private TextView resultView;
	private ProgressBar xiazaijindu;
	private String downURL = "";
	private TextView tishi_text, queding;
	private LinearLayout fanhui_l;
	//下载文件
	public File file;
	 int filelength;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_shengji);
		fanhui_l = (LinearLayout) findViewById(R.id.fanhui_l);
		tishi_text = (TextView) findViewById(R.id.tishi_text);
		queding = (TextView) findViewById(R.id.queding);
		xiazaijindu = (ProgressBar) findViewById(R.id.xiazaijindu);
		xiazaijindu.setMax(100);
		resultView = (TextView) findViewById(R.id.resultView);
		downURL = getIntent().getStringExtra("downURL");
		String path = StringUtils.utf8Encode(downURL);
		if (path != null) {
			downfiles(path);
		} else {
			tishi_text.setText("更新失败，请返回重试!");
			fanhui_l.setVisibility(View.VISIBLE);
		}
		queding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(RESULT_OK);
				finish();

			}
		});
	}

	public boolean fileIsExists(String filename) {
		try {
			File f = Tools.getImageFileByName(filename);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	/**
	 * OkHttpClient  下载文件
	 *
	 */
	private void downfiles( final  String path) {
		// TODO Auto-generated method stub
//		downfile = new DownLoadfileWithProgress_shengji(Zidongshengji_Activity.this, path, null, xiazaijindu,
//				resultView);
//		downfile.handler = downloadHandler;
//		downfile.execute(); new  Thread
		new  Thread(new Runnable() {
			   @Override
			   public void run() {
				   getfile(path,xiazaijindu,resultView,new  Zidongshengji_Activity.OnDownloadListener() {
					   //下载成功
					   public void onDownloadSuccess() {
						   String path = StringUtils.utf8Encode(downURL);
						   String filename = path.substring(path.lastIndexOf('/') + 1);
					if (fileIsExists(filename)) {
						String fileurl = Tools.getImageFileByName(filename).getAbsolutePath();
						Intent intent = OpenFiles.openFile(fileurl);
						startActivity(intent);
						setResult(RESULT_OK);
						finish();
					} else {
						tishi_text.setText("更新信息失败，请返回重试!");
						fanhui_l.setVisibility(View.VISIBLE);
					}
					         }
					   //下载进度
					   public void onDownloading( int progress) {
						   xiazaijindu.setMax(filelength);
						   xiazaijindu.setProgress(progress);
						   float size = (float) progress / filelength;
						   DecimalFormat df = new DecimalFormat("0");// 格式化小数，不足的补0
						   final String filesize = df.format(size * 100);// 返回的是String类型的
						   runOnUiThread(new Runnable() {
							   @Override
							   public void run() {
								   resultView.setText(filesize + "%");
							   }
						   });

					                            }
					   //下载失败
					   public void onDownloadFailed() {
						   runOnUiThread(new Runnable() {
							   @Override
							   public void run() {
								   Toast.makeText(Zidongshengji_Activity.this,"下载失败",Toast.LENGTH_SHORT).show();
							   }
						   });

					   }
				   });
			   }
		   }).start();


	}

	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			return  false;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void getfile(String path, ProgressBar xiazaijindu, TextView resultView, final OnDownloadListener listener)
	{
		if (path == null)
		{	return ;}
		String fileName = "";
		if (path != null && path.length() != 0) {
			fileName = path.substring(path.lastIndexOf("/") + 1);
		}
		// 根据的名称创建文件（不存在：创建）
		file = Tools.getImageFileByName(fileName);
		if (file == null) {
			return ;
		}
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(path)
				.build();
		client.newCall(request).enqueue(new Callback() {
			public void onFailure(Call call, IOException e)
			{
				listener.onDownloadFailed();
			}
			public void onResponse(Call call, Response response) throws IOException {
				if (response.isSuccessful()) {
					byte[] buf = new byte[1024];
					int len = 0;
					int sum=0;
					int i=0;
					try {
					    InputStream is = response.body().byteStream();
				         long total= response.body().contentLength();
						DecimalFormat df = new DecimalFormat("0");
				 		String length=  df.format(total);
						Log.e("TISHI",length);
				        filelength =Integer.valueOf(length);
						Log.e("TISHI2",filelength+"");
					FileOutputStream fos = new FileOutputStream(file);
					while ((len=is.read(buf))!=-1)
					{
						fos.write(buf,0,len);
						sum =sum+len;
						Log.e("进度",sum+"");
						listener.onDownloading(sum);
					}
						fos.flush();
						fos.close();
						is.close();

						listener.onDownloadSuccess();
					}catch (Exception e)
					{
						if(file.exists())
						{
							file.delete();
						}
						listener.onDownloadFailed();

					}
				}
			}
		});








	}
	public interface OnDownloadListener {
		/**
		 * 下载成功
		 */
		void onDownloadSuccess();

		/**
		 * @param progress
		 * 下载进度
		 */
		void onDownloading(int progress);

		/**
		 * 下载失败
		 */
		void onDownloadFailed();
	}

}