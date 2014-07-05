package com.itheima.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ApplockDBOpenHelper extends SQLiteOpenHelper {

	public ApplockDBOpenHelper(Context context) {
		super(context, "applock.db", null, 1);
	}

	/**
	 * 数据库第一次被创建的时候 调用. 方便数据库表结构的初始化.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table applock (id integer primary key autoincrement, packname varchar(20)) ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
