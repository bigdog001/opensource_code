package com.itheima.mobilesafe.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.itheima.mobilesafe.domain.ContactInfo;

public class ContactInfoProvider {

	/**
	 * 获取联系人
	 * 
	 * @return
	 */
	public static List<ContactInfo> getContactInfos(Context context) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
		Cursor cursor = resolver.query(uri, new String[] { "contact_id" },
				null, null, null);
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);
			if (id != null) {
				ContactInfo info = new ContactInfo();
				Cursor dataCursor = resolver.query(dataUri, new String[] {
						"mimetype", "data1" }, "raw_contact_id=?",
						new String[] { id }, null);
				while (dataCursor.moveToNext()) {
					if("vnd.android.cursor.item/name".equals( dataCursor.getString(0))){
						info.setName(dataCursor.getString(1));
					}else if("vnd.android.cursor.item/phone_v2".equals( dataCursor.getString(0))){
						info.setPhone(dataCursor.getString(1));
					}
				}
				contactInfos.add(info);
				dataCursor.close();
			}
		}
		cursor.close();
		return contactInfos;
	}
}
