package com.itheima.mobilesafe.engine;

import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Xml;

public class SmsUtils {
	
	
	public interface BackUpStatusListener{
	  /**
	   * �ڱ��ݶ���֮ǰ max �ܵĽ���
	   * @param max
	   */
	   void	beforeBackup(int max);
	   /**
	    * ���ڱ��ݵĽ���.
	    * @param process
	    */
	   void onBackUpProcess(int process);
	}
	
	
	/**
	 * ���ݶ��ŵĹ��߷���
	 * @param context ������
	 * @param fos ���ݵ��ĸ��ļ����������.
	 * @throws Exception
	 */
	public static void backUp(Context context,FileOutputStream fos,BackUpStatusListener listener) throws Exception {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.parse("content://sms/");
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(fos, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "smss");
		Cursor cursor = resolver.query(uri, new String[]{"address","date","type","body"}, null, null, null);
		//pd.setMax(cursor.getCount());//���ó�ʼ�����ܳ���
		listener.beforeBackup(cursor.getCount());
		int total = 0;
		while(cursor.moveToNext()){
			serializer.startTag(null, "sms");
			serializer.startTag(null, "address");
			String address = cursor.getString(0);
			serializer.text(address);
			serializer.endTag(null, "address");
			
			serializer.startTag(null, "date");
			String date = cursor.getString(1);
			serializer.text(date);
			serializer.endTag(null, "date");
			
			
			serializer.startTag(null, "type");
			String type = cursor.getString(2);
			serializer.text(type);
			serializer.endTag(null, "type");
			
			
			serializer.startTag(null, "body");
			String body = cursor.getString(3);
			serializer.text(body);
			serializer.endTag(null, "body");
			serializer.endTag(null, "sms");
			fos.flush();
			total++;
			//pd.setProgress(total);
			listener.onBackUpProcess(total);
			Thread.sleep(1000);
		}
		
		
		serializer.endTag(null, "smss");
		serializer.endDocument();
		
		fos.flush();
		fos.close();
	}
}
