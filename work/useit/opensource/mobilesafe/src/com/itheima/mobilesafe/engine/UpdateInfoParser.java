package com.itheima.mobilesafe.engine;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.itheima.mobilesafe.domain.UpdateInfo;

/**
 * ������Ϣ�Ľ�����
 * @author Administrator
 *
 */
public class UpdateInfoParser {
	/**
	 * ���ط������ϵĸ�����Ϣ
	 * @param is ���������ص���
	 * @return ������Ϣ null�������ʧ��
	 */
	public static UpdateInfo getUpdateInfo(InputStream is){
		XmlPullParser parser = Xml.newPullParser();
		//��ʼ��������.
		try {
			parser.setInput(is, "utf-8");
			int type = parser.getEventType();
			UpdateInfo info = new UpdateInfo();
			while(type!=XmlPullParser.END_DOCUMENT){
				switch (type) {
				case XmlPullParser.START_TAG:
					if("version".equals(parser.getName())){
						String version = parser.nextText();
						info.setVersion(version);
					}else if("description".equals(parser.getName())){
						String description = parser.nextText();
						info.setDescription(description);
					}else if("apkurl".equals(parser.getName())){
						String apkurl = parser.nextText();
						info.setApkurl(apkurl);
					}
					break;


				}
				
				
				
				type = parser.next();
			}
			
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}









