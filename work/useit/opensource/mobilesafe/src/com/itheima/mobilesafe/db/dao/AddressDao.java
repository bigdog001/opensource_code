package com.itheima.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AddressDao {

	private static final String path = "/data/data/com.itheima.mobilesafe/files/address.db";

	/**
	 * 获取号码的归属地信息.
	 * 
	 * @param number
	 * @return
	 */
	public static String getAddress(String number) {
		String address = number;// 如果查询不到号码归属地 就返回当前号码
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);

		// 判断号码是否是手机号码.
		// 13 15 14 18 + 9位的数字.
		if (number.matches("^1[3458]\\d{9}$")) {// 手机号码
			Cursor cursor = db
					.rawQuery(
							"select location from data2 where id =(select outkey from data1 where id = ?)",
							new String[] { number.substring(0, 7) });
			if (cursor.moveToNext()) {
				address = cursor.getString(0);
			}
			cursor.close();
		} else {// 其他号码
			switch (number.length()) {
			case 3:
				address = "特殊号码";
				break;

			case 4:
				address = "模拟器";
				break;
			case 7:
				address = "本地号码";
				break;
			case 8:
				address = "本地号码";
				break;
			
			default:
				if(number.length()>=10){
					Cursor curosr = null;
					curosr = db.rawQuery("select location from data2 where area = ? ", new String[]{number.substring(1, 3)});//查询前三位
					if(curosr.moveToNext()){
						String info  = curosr.getString(0);
						address = info.substring(0, info.length()-2);
					}
					curosr.close();
					curosr = db.rawQuery("select location from data2 where area = ? ", new String[]{number.substring(1, 4)});//查询前四位
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
