package com.itheima.mobilesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.mobilesafe.db.BlackNumberDBOpenHelper;
import com.itheima.mobilesafe.domain.BlackNumberInfo;

/**
 * ���������ݿ����ɾ�Ĳ�
 * @author Administrator
 *
 */
public class BlackNumberDao {
	
	private BlackNumberDBOpenHelper helper;
	public BlackNumberDao(Context context) {
		helper = new BlackNumberDBOpenHelper(context);
	}
	
	/**
	 * ���Һ������������
	 * @return true �����ѯ���˺���������
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
	 * ����һ�������������ģʽ
	 * @param number
	 * @return ���null �����Ǻ���������
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
	 * ��Ӻ���������
	 * @param number ����������
	 * @param mode ����ģʽ
	 */
	public void add(String number,String mode){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into blacknumber (number,mode) values (?,?)", new Object[]{number,mode});
		db.close();
	}
	/**
	 * ���º��������������ģʽ
	 * @param number Ҫ���µĺ���������
	 * @param newmode �µ�����ģʽ
	 */
	public void update(String number,String newmode){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("update blacknumber set mode =? where number=?", new Object[]{newmode,number});
		db.close();
	}
	/**
	 * ɾ������������
	 * @param number
	 */
	public void delete(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from blacknumber where number = ?", new Object[]{number});
		db.close();
	}
	/**
	 * �������еĺ�����������Ϣ.
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
	 * ������ȡ���ݿ��������������Ϣ
	 * @param startIndex ��ʼ��ȡ���ݵ�λ��.
	 * @param maxNumber ����ȡ�����ݵ���Ŀ.
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
	 * �������ݿ�����һ���ж�������¼
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
