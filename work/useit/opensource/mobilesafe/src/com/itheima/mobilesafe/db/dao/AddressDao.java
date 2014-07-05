package com.itheima.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AddressDao {

	private static final String path = "/data/data/com.itheima.mobilesafe/files/address.db";

	/**
	 * ��ȡ����Ĺ�������Ϣ.
	 * 
	 * @param number
	 * @return
	 */
	public static String getAddress(String number) {
		String address = number;// �����ѯ������������� �ͷ��ص�ǰ����
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);

		// �жϺ����Ƿ����ֻ�����.
		// 13 15 14 18 + 9λ������.
		if (number.matches("^1[3458]\\d{9}$")) {// �ֻ�����
			Cursor cursor = db
					.rawQuery(
							"select location from data2 where id =(select outkey from data1 where id = ?)",
							new String[] { number.substring(0, 7) });
			if (cursor.moveToNext()) {
				address = cursor.getString(0);
			}
			cursor.close();
		} else {// ��������
			switch (number.length()) {
			case 3:
				address = "�������";
				break;

			case 4:
				address = "ģ����";
				break;
			case 7:
				address = "���غ���";
				break;
			case 8:
				address = "���غ���";
				break;
			
			default:
				if(number.length()>=10){
					Cursor curosr = null;
					curosr = db.rawQuery("select location from data2 where area = ? ", new String[]{number.substring(1, 3)});//��ѯǰ��λ
					if(curosr.moveToNext()){
						String info  = curosr.getString(0);
						address = info.substring(0, info.length()-2);
					}
					curosr.close();
					curosr = db.rawQuery("select location from data2 where area = ? ", new String[]{number.substring(1, 4)});//��ѯǰ��λ
					if(curosr.moveToNext()){
						String info  = curosr.getString(0);
						address = info.substring(0, info.length()-2);
					}
					curosr.close();
				}
				
			}

		}
		db.close();
		return address;
	}
}
