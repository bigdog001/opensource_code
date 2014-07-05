package com.itheima.mobilesafe.test;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class TestAddressDB extends AndroidTestCase {
	public void testReadDB() throws Exception {
		SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.itheima.mobilesafe/files/address.db", null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = db.rawQuery("select location from data2 where id =(select outkey from data1 where id = ?)", new String[]{"1371234"});
		if(cursor.moveToNext()){
			String address = cursor.getString(0);
			System.out.println(address);
		}
	}
}
