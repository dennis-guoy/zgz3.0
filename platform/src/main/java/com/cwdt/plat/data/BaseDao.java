package com.cwdt.plat.data;

import java.io.File;

import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;

public class BaseDao {
	public static SQLiteDatabase gRSqliteDB = null;
	public static SQLiteDatabase gWSqliteDB = null;

	/**
	 * 获取基础可读写数据库
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getDataBase(Context context) {
		try {
			DataBaseHelper dbHelper = new DataBaseHelper(context);
			if (gRSqliteDB == null) {
				gRSqliteDB = dbHelper.getReadableDatabase();

			}
			if (gWSqliteDB == null) {
				gWSqliteDB = dbHelper.getWritableDatabase();

			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean getDataBase(Context context, File dbFile) {
		boolean bRet=false;
		try {
			// DataBaseHelper dbHelper = new DataBaseHelper(context);
			if (dbFile.exists()) {
				if (gRSqliteDB == null) {
					gRSqliteDB = SQLiteDatabase.openOrCreateDatabase(
							dbFile.getAbsoluteFile(), null);
				}
				if (gWSqliteDB == null) {
					gWSqliteDB = SQLiteDatabase.openOrCreateDatabase(
							dbFile.getAbsoluteFile(), null);

				}
				bRet=true;
			}
			else {
				Log.e("BaseDao", "扩展数据文件不存在！");
			}
		} catch (Exception e) {
			Log.e("BaseDao", e.getMessage());
			bRet=false;
//			return false;
		}
		return bRet;
	}

	// ----数据库关闭
	public static void CloseDataBase() {
		if (gWSqliteDB != null) {
			gWSqliteDB.close();
		}

		if (gRSqliteDB != null) {
			gRSqliteDB.close();
		}
	}
}
