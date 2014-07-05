package com.itheima.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VirusDao {
	private static final String path = "/data/data/com.itheima.mobilesafe/files/antivirus.db";

	/**
	 * ����Ӧ�ó����ǩ�� ����Ƿ��ǲ���
	 * @param md5
	 * @return null����ȫ, desc ���ֲ���.
	 */
	public static String findVirsu(String md5) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		String result = null;

		Cursor cursor = db.rawQuery("select desc from datable where md5=?",
				new String[] { md5 });
		if (cursor.moveToFirst()) {
			result = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return result;

	}
}
