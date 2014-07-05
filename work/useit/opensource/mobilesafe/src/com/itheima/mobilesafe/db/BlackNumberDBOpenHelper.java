package com.itheima.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumberDBOpenHelper extends SQLiteOpenHelper {

	public BlackNumberDBOpenHelper(Context context) {
		super(context, "blacknumber.db", null, 1);
	}

	/**
	 * 数据库第一次被创建的时候 调用. 方便数据库表结构的初始化.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table blacknumber (id integer primary key autoincrement, number varchar(20), mode varchar(2)) ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
