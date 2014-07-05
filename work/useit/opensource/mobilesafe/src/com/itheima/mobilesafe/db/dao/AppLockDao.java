package com.itheima.mobilesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.mobilesafe.db.ApplockDBOpenHelper;
import com.itheima.mobilesafe.db.BlackNumberDBOpenHelper;
import com.itheima.mobilesafe.domain.BlackNumberInfo;

/**
 * ���������ݿ����ɾ�Ĳ�
 * 
 * @author Administrator
 * 
 */
public class AppLockDao {

	private ApplockDBOpenHelper helper;

	public AppLockDao(Context context) {
		helper = new ApplockDBOpenHelper(context);
	}

	/**
	 * ���������ĳ���
	 * 
	 * @return true
	 */
	public boolean find(String packname) {
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from applock where packname=?",
				new String[] { packname });
		if (cursor.moveToNext()) {
			result = true;
		}
		cursor.close();
		db.close();
		return result;
	}

	/**
	 * ��������İ���
	 * 
	 * @param packname
	 */
	public void add(String packname) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into applock (packname) values (?)",
				new Object[] { packname });
		db.close();
	}

	/**
	 * ɾ������
	 * 
	 * @param packname
	 */
	public void delete(String packname) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from applock where packname = ?",
				new Object[] { packname });
		db.close();
	}

	/**
	 * �������е������İ���
	 * 
	 * @return
	 */
	public List<String> findAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select packname from applock", null);
		List<String> lockedPackNames = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String packname = cursor.getString(0);

			lockedPackNames.add(packname);
		}

		cursor.close();
		db.close();
		return lockedPackNames;
	}

}
