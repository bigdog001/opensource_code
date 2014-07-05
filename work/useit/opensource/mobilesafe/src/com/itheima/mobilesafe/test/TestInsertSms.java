package com.itheima.mobilesafe.test;

import android.content.ContentValues;
import android.net.Uri;
import android.test.AndroidTestCase;

public class TestInsertSms extends AndroidTestCase {

	
	public void testInsertSms() throws Exception{
		//前提: 提示用户删除所有的短信.
		//1.解析xml文件. 
		//2.获取里面每一条短信的内容.
		//3.利用下面的代码 插入到系统的短信应用里面.
		Uri uri = Uri.parse("content://sms/");
		ContentValues values = new ContentValues();
		values.put("address", 342342);
		values.put("date", 1357801503371l);
		values.put("type", 2);
		values.put("body", "您的建行卡转入1000000000000");
		getContext().getContentResolver().insert(uri, values);
		// 虚拟短信  
		// 虚拟电话. 
	}
}
