package com.itheima.mobilesafe.test;

import android.content.ContentValues;
import android.net.Uri;
import android.test.AndroidTestCase;

public class TestInsertSms extends AndroidTestCase {

	
	public void testInsertSms() throws Exception{
		//ǰ��: ��ʾ�û�ɾ�����еĶ���.
		//1.����xml�ļ�. 
		//2.��ȡ����ÿһ�����ŵ�����.
		//3.��������Ĵ��� ���뵽ϵͳ�Ķ���Ӧ������.
		Uri uri = Uri.parse("content://sms/");
		ContentValues values = new ContentValues();
		values.put("address", 342342);
		values.put("date", 1357801503371l);
		values.put("type", 2);
		values.put("body", "���Ľ��п�ת��1000000000000");
		getContext().getContentResolver().insert(uri, values);
		// �������  
		// ����绰. 
	}
}
