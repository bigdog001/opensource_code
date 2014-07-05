package com.itheima.mobilesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CommonNumDao {
	private static final String path = "/data/data/com.itheima.mobilesafe/files/commonnum.db";

	/**
	 * 返回分组的数量
	 * 
	 * @return
	 */
	public static int getGroupCount() {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		int count = 0;
		Cursor cursor = db.rawQuery("select count(*) from classlist", null);
		if (cursor.moveToNext()) {
			count = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		return count;
	}
	/**
	 * 返回所有的分组的信息
	 */
	public static List<String> getGroupInfos(){
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		List<String> groupInfos = new ArrayList<String>();
		Cursor cursor = db.rawQuery("select name from classlist", null);
		while (cursor.moveToNext()) {
			groupInfos.add( cursor.getString(0));
		}
		cursor.close();
		db.close();
		return groupInfos;
	}
	
	
	/**
	 * 获取某个分组的名称
	 * @param groupPosition
	 * @return
	 */
	public static String getGroupName(int groupPosition) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		int newPostition = groupPosition + 1;
		String name = "";
		Cursor cursor = db.rawQuery("select name from classlist where idx=?",
				new String[] { String.valueOf(newPostition) });
		if (cursor.moveToNext()) {
			name = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return name;

	}

	/**
	 * 返回某个分组里面有多个孩子
	 */
	public static int getChildCountByPosition(int groupPosition) {
		int newposition = groupPosition + 1;
		String sql = "select count(*) from table" + newposition;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		int count = 0;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToNext()) {
			count = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		return count;

	}
	/**
	 * 返回某个分组里面孩子的信息
	 * @param groupPosition
	 * @param childPosition
	 * @return
	 */
	public static String getChildInfoByPosition(int groupPosition, int childPosition){
		int newGroupPosition = groupPosition + 1;
		int newChildPosition = childPosition + 1;
		//select name,number from table1 where _id='2'
		String result="";
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor =  db.rawQuery("select name,number from table"+newGroupPosition+" where _id=?", new String[]{String.valueOf(newChildPosition)});
		if(cursor.moveToNext()){
			String name = cursor.getString(0);
			String number = cursor.getString(1);
			result = name+"\n"+number;
		}
		cursor.close();
		db.close();
		return result;
	}
	/**
	 * 返回某个分组里面的所有的孩子信息.
	 */
	public static List<String> getChildrenInfosByGroupPostion(int groupPosition){
		int newGroupPosition = groupPosition + 1;
		List<String> childrenInfos = new ArrayList<String>();
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor =  db.rawQuery("select name,number from table"+newGroupPosition, null);
		while(cursor.moveToNext()){
			String name = cursor.getString(0);
			String number = cursor.getString(1);
			String result = name+"\n"+number;
			childrenInfos.add(result);
		}
		cursor.close();
		db.close();
		return childrenInfos;
	}
}
