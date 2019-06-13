package com.cwdt.plat.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	public final static String DBBase_Name = "LocalTaxData0";// ----数据库名称
	public final static int DataBase_Ver = 1;
	private SharedPreferences pre;
	public DataBaseHelper(Context context) {
		super(context, DBBase_Name, null, DataBase_Ver);
	}
	@Override
	public void onCreate(SQLiteDatabase myDB) {
    //t通讯录表
		String  strSql = "CREATE TABLE [Tongxunlu_info] ("
				+ "[id] VARCHAR(50)  NOT NULL,"
				+ "[name] VARCHAR(50)  NULL,"
				+ "[keshi_id] VARCHAR(50)  NULL,"
				+ "[keshiname] VARCHAR(50)  NULL,"
				+ "[officename] VARCHAR(50)  NULL,"
				+ "[office_id] VARCHAR(50)  NULL,"
				+ "[leftviewcolor] VARCHAR(50)  NULL,"
				+ "[name_pinyin] VARCHAR(50)  NULL,"
				+ "[phone1] VARCHAR(50)  NULL,"
				+ "[phone2] VARCHAR(50)  NULL,"
				+ "[phone3] VARCHAR(50)  NULL"
				+ ")";
		try {

			myDB.execSQL(strSql);
		} catch (Exception e) {
			Log.e("error", e.toString());
			// TODO: handle exception
		}
//通知表
		strSql = "CREATE TABLE [localnotifys] ([id] INTEGER  NULL,"
				+ " [navtitle] TEXT  NULL," + " [postdate] TEXT  NULL,"
				+ " [status] INTEGER DEFAULT '0' NULL,"
				+ " [name] VARCHAR(20)  NULL,"
				+ " [readcount] INTEGER DEFAULT '0' NULL,"
				+ " [hasread] INTEGER DEFAULT '0' NULL)";
		myDB.execSQL(strSql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
		String strSql = "";
		
		switch (oldVersion) {
		

		default:/*
			strSql = "DROP TABLE \"userinfo\"";
			try {
			myDB.execSQL(strSql);
			} catch (SQLException e) {
				Log.e("error", "DROP failed");

			}
			strSql = "DROP TABLE \"keshiinfo\"";
			try {
				myDB.execSQL(strSql);
			} catch (SQLException e) {
				Log.e("error", "DROP failed");

			}
			
			
			strSql = "CREATE TABLE \"keshiinfo\" (\"id\" TEXT,\"name\" TEXT,\"tel_num\" TEXT,\"ordergrade\" INT )";
			myDB.execSQL(strSql);
			strSql = "CREATE TABLE \"userinfo\" (\"id\" TEXT,\"name\" TEXT,\"phone\" TEXT,\"action_number\" TEXT,"
				+ "\"shortphone\" TEXT,\"thirdphone\" TEXT,\"email\" TEXT,\"department_id\" TEXT,\"ordergrade\" INT,\"orderuser\" TEXT default 0)";
		try {
			myDB.execSQL(strSql);

		} catch (SQLException e) {
			Log.e("error", "CREATE failed");

		}*/
			break;
		}
	}

}
