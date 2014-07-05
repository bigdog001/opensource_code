package com.itheima.mobilesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.mobilesafe.db.BlackNumberDBOpenHelper;
import com.itheima.mobilesafe.domain.BlackNumberInfo;

/**
 * 黑名单数据库的增删改查
 * @author Administrator
 *
 */
public class BlackNumberDao {
	
	private BlackNumberDBOpenHelper helper;
	public BlackNumberDao(Context context) {
		helper = new BlackNumberDBOpenHelper(context);
	}
	
	/**
	 * 查找黑名单号码号码
	 * @return true 代表查询到了黑名单号码
	 */
	public boolean find (String number){
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from blacknumber where number=?", new String[]{number});
		if(cursor.moveToNext()){
			result = true;
		}
		cursor.close();
		db.close();
		return result;
	}
	/**
	 * 返回一个黑名单号码的模式
	 * @param number
	 * @return 如果null 代表不是黑名单号码
	 */
	public String findMode(String number){
		String mode = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select mode from blacknumber where number=?", new String[]{number});
		if(cursor.moveToNext()){
			mode = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return mode;
	}
	
	
	/**
	 * 添加黑名单号码
	 * @param number 黑名单号码
	 * @param mode 拦截模式
	 */
	public void add(String number,String mode){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into blacknumber (number,mode) values (?,?)", new Object[]{number,mode});
		db.close();
	}
	/**
	 * 更新黑名单号码的拦截模式
	 * @param number 要更新的黑名单号码
	 * @param newmode 新的拦截模式
	 */
	public void update(String number,String newmode){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("update blacknumber set mode =? where number=?", new Object[]{newmode,number});
		db.close();
	}
	/**
	 * 删除黑名单号码
	 * @param number
	 */
	public void delete(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from blacknumber where number = ?", new Object[]{number});
		db.close();
	}
	/**
	 * 返回所有的黑名单号码信息.
	 * @return
	 */
	public List<BlackNumberInfo> findAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select number,mode from blacknumber", null);
		 List<BlackNumberInfo> blackNumberInfos = new ArrayList<BlackNumberInfo>();
		 while(cursor.moveToNext()){
			String number = cursor.getString(0);
			String mode = cursor.getString(1);
			BlackNumberInfo info = new BlackNumberInfo();
			info.setMode(mode);
			info.setNumber(number);
			blackNumberInfos.add(info);
		 }
		 try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 cursor.close();
		 db.close();
		 return blackNumberInfos;
	}
	/**
	 * 分批获取数据库里面黑名单的信息
	 * @param startIndex 开始获取数据的位置.
	 * @param maxNumber 最多获取的数据的条目.
	 */
	public List<BlackNumberInfo> findByPage(int startIndex ,int maxNumber){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select number,mode from blacknumber order by id desc limit ? offset ?", new String[]{maxNumber+"",startIndex+""});
		 List<BlackNumberInfo> blackNumberInfos = new ArrayList<BlackNumberInfo>();
		 while(cursor.moveToNext()){
			String number = cursor.getString(0);
			String mode = cursor.getString(1);
			BlackNumberInfo info = new BlackNumberInfo();
			info.setMode(mode);
			info.setNumber(number);
			blackNumberInfos.add(info);
		 }
		 cursor.close();
		 db.close();
		 return blackNumberInfos;
	}
	
	/**
	 * 返回数据库里面一共有多少条记录
	 */
	public int getTotalCount(){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from blacknumber ",null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
}
