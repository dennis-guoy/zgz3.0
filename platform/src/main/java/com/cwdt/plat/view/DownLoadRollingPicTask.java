package com.cwdt.plat.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cwdt.plat.util.Tools;
import com.jngs.library.platfrom.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * @author Administrator
 * 
 */
public class DownLoadRollingPicTask extends AsyncTask<String, View, File> {

	protected String LogTag = "DownLoadRollingPicTask";
	protected String urlString;
	protected ImageView imView;
	protected Context context;
	public boolean downruning = true;

	public DownLoadRollingPicTask(Context parentActivity, String picUrlString, ImageView ivImageView) {
		urlString = picUrlString;
		imView = ivImageView;
		context = parentActivity;
	}

	@Override
	protected File doInBackground(String... params) {
		File uRetUri = null;
		if (urlString == null)
			return null;
		String fileName = "";
		// 获取url中图片的文件名与后缀
		if (urlString != null && urlString.length() != 0) {
			fileName = urlString.substring(urlString.lastIndexOf("/") + 1);
		}
		// 根据图片的名称创建文件（不存在：创建）
		// Tools.getImageFileByName(fileName);
		// File file = new File(Tools.getCacheDir(), fileName);
		File file = Tools.getImageFileByName(fileName);
		if (file == null) {
			return null;
		}
		// 如果在缓存中找不到指定图片则下载
		if (!file.exists() && !file.isDirectory()) {
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				if (conn.getResponseCode() == 200) {

					InputStream is = conn.getInputStream();
					FileOutputStream fos = new FileOutputStream(file);
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1 && downruning) {
						fos.write(buffer, 0, len);
					}
					is.close();
					fos.close();
					// 返回一个URI对象
					uRetUri = file;
				}
			} catch (Exception e) {
				if (file.exists()) {
					file.delete();
				}
				Log.e(LogTag, e.getMessage());
			}
		}
		// 如果缓存中有则直接使用缓存中的图片
		else {
			// uRetUri = Uri.fromFile(file);
			uRetUri = file;
		}
		return uRetUri;
	}

	@Override
	protected void onPostExecute(File result) {
		if (result != null) {
			if (result.exists()) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bmp = BitmapFactory.decodeFile(result.getAbsolutePath(), options);
				// int iWidth = imView.getWidth();
				// int iHeight= imView.getHeight();
				// if (iWidth==0) {
				// bmp = Tools.CompressImage(bmp, 100, 100);
				// }
				// else {
				// bmp = Tools.CompressImage(bmp, iWidth, iHeight);
				// }
				if (bmp == null) {
					result.delete();
					imView.setImageResource(R.drawable.img_zanwu);
				} else {
					imView.setImageBitmap(bmp);
					imView.clearAnimation();
					imView.setTag(result.getAbsolutePath());
				}
			} else {
				imView.setImageResource(R.drawable.img_zanwu);
			}
			// imView.setImageURI(result);
		} else {
			imView.setImageResource(R.drawable.img_zanwu);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(View... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
