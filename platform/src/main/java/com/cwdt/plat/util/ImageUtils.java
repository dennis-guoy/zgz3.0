package com.cwdt.plat.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;

/**
 * ImageUtils
 * <ul>
 * convert between Bitmap, byte array, Drawable
 * <li>{@link #bitmapToByte(Bitmap)}</li>
 * <li>{@link #bitmapToDrawable(Bitmap)}</li>
 * <li>{@link #byteToBitmap(byte[])}</li>
 * <li>{@link #byteToDrawable(byte[])}</li>
 * <li>{@link #drawableToBitmap(Drawable)}</li>
 * <li>{@link #drawableToByte(Drawable)}</li>
 * </ul>
 * <ul>
 * scale image
 * <li>{@link #scaleImageTo(Bitmap, int, int)}</li>
 * <li>{@link #scaleImage(Bitmap, float, float)}</li>
 * </ul>
 */
public class ImageUtils {

	private ImageUtils() {
		throw new AssertionError();
	}

	/**
	 * convert Bitmap to byte array
	 * 
	 * @param b
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap b) {
		if (b == null) {
			return null;
		}

		ByteArrayOutputStream o = new ByteArrayOutputStream();
		b.compress(CompressFormat.PNG, 100, o);
		return o.toByteArray();
	}

	/**
	 * convert byte array to Bitmap
	 * 
	 * @param b
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] b) {
		return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	/**
	 * convert Drawable to Bitmap
	 * 
	 * @param d
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable d) {
		return d == null ? null : ((BitmapDrawable) d).getBitmap();
	}

	/**
	 * convert Bitmap to Drawable
	 * 
	 * @param b
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap b) {
		return b == null ? null : new BitmapDrawable(b);
	}

	/**
	 * convert Drawable to byte array
	 * 
	 * @param d
	 * @return
	 */
	public static byte[] drawableToByte(Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}

	/**
	 * convert byte array to Drawable
	 * 
	 * @param b
	 * @return
	 */
	public static Drawable byteToDrawable(byte[] b) {
		return bitmapToDrawable(byteToBitmap(b));
	}

	/**
	 * scale image
	 * 
	 * @param org
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
		return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
	}

	/**
	 * scale image
	 * 
	 * @param org
	 * @param scaleWidth
	 *            sacle of width
	 * @param scaleHeight
	 *            scale of height
	 * @return
	 */
	public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
		if (org == null) {
			return null;
		}

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
	}

	/**
	 * close inputStream
	 * 
	 * @param s
	 */
	private static void closeInputStream(InputStream s) {
		if (s == null) {
			return;
		}

		try {
			s.close();
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		}
	}

	/**
	 * 图片圆角
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundpx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = roundpx;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 压缩图片文件
	 * 
	 * @param src
	 * @param des
	 * @param quality
	 *            压缩比例
	 * @return
	 */
	public static boolean compressBmpFile(File src, File des, int quality) {

		try {
			Bitmap bitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
			bitmap.compress(CompressFormat.JPEG, quality, bos);
			bos.flush();
			bos.close();
			bitmap.recycle();
			System.gc();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Uri转File
	 * 
	 */
	public static File getfileFromURI(Context context, Uri contentUri) {
		File file = null;
		String[] proj = { MediaColumns.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		if (cursor.moveToFirst()) {
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			String res = cursor.getString(column_index);
			file = new File(res);
		}
		cursor.close();
		return file;
	}
	/**
	 * 转换图片成圆形
	 *
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);// 设置画笔无锯齿

		canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
		paint.setColor(color);

		// 以下有两种方法画圆,drawRounRect和drawCircle
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

		return output;
	}

	/**
	 * 从File取源文件压缩
	 * 
	 */
	public static Bitmap compressBmpbyFile(File src, int targetwidth, int targetheight) {
		try {
			Bitmap bitmap = null;
			if (src.exists()) {

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(src.getAbsolutePath(), options);
				int imageHeight = options.outHeight;
				int imageWidth = options.outWidth;

				int inSampleSize = 1;
				int reqHeight = targetheight;
				int reqWidth = targetwidth;

				if (imageHeight > reqHeight || imageWidth > reqWidth) {
					// 计算出实际宽高和目标宽高的比率
					final int heightRatio = Math.round((float) imageHeight / (float) reqHeight);
					final int widthRatio = Math.round((float) imageWidth / (float) reqWidth);
					// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
					// 一定都会大于等于目标的宽和高。
					inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
				}
				options.inSampleSize = inSampleSize;
				// 使用获取到的inSampleSize值再次解析图片
				options.inJustDecodeBounds = false;
				Bitmap bmp_result = BitmapFactory.decodeFile(src.getAbsolutePath(), options);
				bitmap = bmp_result;
			}
			return bitmap;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	/**
	 * 获取位图缩略图
	 * 
	 * @param bmp
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBmpThumb(Bitmap bmp, int width, int height) {
		try {
			return ThumbnailUtils.extractThumbnail(bmp, width, height);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取视频文件缩略图
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getVideoThumb(String filePath) {
		try {
			return ThumbnailUtils.createVideoThumbnail(filePath, Images.Thumbnails.MINI_KIND);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将Bitmap保存为文件
	 * 
	 * @param bmp
	 * @param filename
	 *            文件绝对路径
	 * @return
	 */
	public static boolean saveBitmapTofile(Bitmap bmp, String filename) {
		CompressFormat format = CompressFormat.JPEG;
		int quality = 100;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bmp.compress(format, quality, stream);
	}

}
