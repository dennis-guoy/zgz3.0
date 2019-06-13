package com.cwdt.plat.view;

import java.io.File;

import com.cwdt.plat.util.ImageUtils;
import com.cwdt.plat.util.Tools;
import com.jngs.library.platfrom.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class DownLoadPicTaskForListView extends DownLoadRollingPicTask {

	public DownLoadPicTaskForListView(Context parentActivity,
			String picUrlString, ImageView ivImageView) {
		super(parentActivity, picUrlString, ivImageView);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 判断文件
	 */
	@Override
	protected void onPostExecute(File result) {
		if (result != null) {
			if (result.exists()) {
				if (Tools.isTxtFile(result.getName())) {
					imView.setImageResource(R.drawable.img_zanwu);
					return;
				}
				
				if (Tools.isAudioFile(result.getName())) {
					imView.setImageResource(R.drawable.img_zanwu);
					return;
				}
				if (Tools.isVideoFile(result.getName())) {
					Bitmap vBitmap = ImageUtils.getVideoThumb(result.getAbsolutePath());
//					imView.setImageResource(R.drawable.no_image);
					imView.setImageBitmap(vBitmap);
					
					return;
				}
				if (Tools.isGraphFile(result.getName())) {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap bmp = BitmapFactory.decodeFile(result.getAbsolutePath(),
							options);
					
					if (bmp == null) {
						result.delete();
						imView.setImageResource(R.drawable.img_zanwu);
					} else {
//						bmp = Tools.CompressImage(bmp, 100, 100);
						imView.setImageBitmap(bmp);
						imView.clearAnimation();
					}
					return;
				}
				
			} else {
				imView.setImageResource(R.drawable.img_zanwu);
			}
			// imView.setImageURI(result);
		} else {
			imView.setImageResource(R.drawable.img_zanwu);
		}
	}

}
