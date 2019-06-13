package com.cwdt.plat.data;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class RollingPicDao {
	public String LogTag = "RollingPicDao";

	public BaseDao dbDao;

	public RollingPicDao() {
	}

	/**
	 * 从本地数据中获取最新的四条滚动图片数据
	 * 
	 * @return
	 */
	public ArrayList<BaseRollingpic> getNewestRollingPicItems() {
		ArrayList<BaseRollingpic> tmPicItems = new ArrayList<BaseRollingpic>();
		String strSql = "select * from rollingpics order by id desc limit 0,4;";
		try {
			Cursor cursor = BaseDao.gRSqliteDB.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				BaseRollingpic sdiInfo = new BaseRollingpic();

				sdiInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
				sdiInfo.setSoftid(cursor.getInt(cursor.getColumnIndex("softid")));
				sdiInfo.setPicurl(cursor.getString(cursor
						.getColumnIndex("picpath")));
				sdiInfo.setNavtype(cursor.getInt(cursor.getColumnIndex("navtype")));
				sdiInfo.setNavurl(cursor.getString(cursor
						.getColumnIndex("navurl")));
				sdiInfo.setNavtitle(cursor.getString(cursor
						.getColumnIndex("navtitle")));
				sdiInfo.setNavcontent(cursor.getString(cursor
						.getColumnIndex("navcontent")));
				sdiInfo.setCt(new Date(cursor.getString(cursor
						.getColumnIndex("postdate"))));
				tmPicItems.add(sdiInfo);
			}
			cursor.close();
		} catch (Exception e) {
			Log.e(LogTag, e.getMessage());
		}
		return tmPicItems;
	}

	/**
	 * 向滚动图表中插入一条数据
	 * 
	 * @param sdi
	 *            singleRollingPicItem
	 * @return
	 */
	public Boolean InsertRollingPicItem(BaseRollingpic sdi) {
		Boolean bRet = false;
		try {
			ContentValues cv = new ContentValues();
			cv.put("id", sdi.getId());
			cv.put("areaid", sdi.getOrgid());
			cv.put("softid", sdi.getSoftid());
			cv.put("picpath", sdi.getPicurl());
			cv.put("navtype", sdi.getNavtype());
			cv.put("navurl", sdi.getNavurl());
			cv.put("navtitle", sdi.getNavtitle());
			cv.put("navcontent", sdi.getNavcontent());
			cv.put("postdate", sdi.getCt().toString());
			Long iRet = BaseDao.gWSqliteDB.insert("rollingpics", null, cv);
			bRet = iRet > 0;
		} catch (Exception e) {
			Log.e(LogTag, e.getMessage());
		}
		return bRet;
	}

	//更新数据库
	public Boolean UpdateRollingPicItem(BaseRollingpic sdi) {
		Boolean bRet=false;
		try {
			ContentValues cv = new ContentValues();
			cv.put("id", sdi.getId());
			cv.put("areaid", sdi.getOrgid());
			cv.put("softid", sdi.getSoftid());
			cv.put("picpath", sdi.getPicurl());
			cv.put("navtype", sdi.getNavtype());
			cv.put("navurl", sdi.getNavurl());
			cv.put("navtitle", sdi.getNavtitle());
			cv.put("navcontent", sdi.getNavcontent());
			cv.put("postdate", sdi.getCt().toString());
			int iRet = BaseDao.gWSqliteDB.update("rollingpics", cv, " id=?",new String[]{sdi.getId().toString()});
			bRet = iRet > 0;
		} catch (Exception e) {
			Log.e(LogTag, e.getMessage());
		}
		return bRet;
		
	}
	/**
	 * 获取指定ID的图片数据是否存在
	 * @param dataID
	 * @return
	 */
	public Boolean PicDataExists(String dataID) {
		Boolean bRet = false;
		String strSql = " select id from rollingpics where id=" + dataID;
		try {
			Cursor cursor = BaseDao.gRSqliteDB.rawQuery(strSql, null);
			bRet = cursor.getCount() > 0;
		} catch (Exception e) {
			Log.e(LogTag, e.getMessage());
		}
		return bRet;
	}
	
	/**
	 * 清除旧的滚动图片数据
	 * @param
	 * @return
	 */
	public Boolean ClearPicDatas() {
		Boolean bRet = false;
		String strSql = " delete from rollingpics ";
		try {
			BaseDao.gWSqliteDB.execSQL(strSql);
			bRet=true;
//			Cursor cursor = dbDao.gRSqliteDB.rawQuery(strSql, null);
//			bRet = cursor.getCount() > 0;
		} catch (Exception e) {
			Log.e(LogTag, e.getMessage());
		}
		return bRet;
	}
}
