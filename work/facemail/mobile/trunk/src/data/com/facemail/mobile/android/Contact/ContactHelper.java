package com.facemail.mobile.android.Contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.facemail.mobile.android.Contact.ContactImpl.ContactInterface;
import android.provider.ContactsContract.Data;
import com.facemail.mobile.android.db.conf.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/21/13
 * Time: 2:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactHelper implements ContactInterface {
    private  ContactHelper mContactHlper;
    private Context mContext;
    private static final String[] PHONES_PROJECTION = new String[] { ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER, Data.PHOTO_ID,Data.CONTACT_ID };
    private ContactHelper(Context context){
     this.mContext=context;
    }
    @Override
    public List<Map<String,String>> getAllContacts() {
        List<Map<String,String>> contacts=null;
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri_contacts = Uri.parse(Config.FaceUri.CONTACTS);
        //取所有联系人的全字段
        Cursor cursor_contacts = contentResolver.query(uri_contacts, null, null, null, null);
        if (cursor_contacts!=null) {
            while (cursor_contacts.moveToNext()) {
                String id = cursor_contacts.getString(cursor_contacts
                        .getColumnIndex(ContactsContract.Contacts._ID)); // id
                String name = cursor_contacts.getString(cursor_contacts
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)); // 姓名

                Map<String, String> map = new HashMap<String, String>();
                map.put("id", id);
                map.put("name", name);
                contacts.add(map);
            }
            cursor_contacts.close(); // 关闭
        }


        return contacts;
    }

    public String lookupNameByPhoneNumber(String phoneNumber){
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = mContext.getContentResolver().query(uri,
                new String[] {ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        String name = null;
        if (cursor == null) {
            name = null;
        }
        try {
            if (cursor.moveToFirst()) {
                name = cursor.getColumnName(0);
            }
        } finally {
            cursor.close();
        }
        return name;
    }

    @Override
    public List<String> lookupEmailsByName(String name) {
        List<String> emails=null;
        if ("".equals(name)||name==null) {
            return emails;
        }

        ContentResolver contentResolver=mContext.getContentResolver();
        Uri uri_contacts = Uri.parse(Config.FaceUri.CONTACTS);
        Cursor cursor_contacts = contentResolver.query(uri_contacts, new String[]{ContactsContract.Contacts._ID},
                ContactsContract.Contacts.DISPLAY_NAME + " =? ", new String[]{name}, null);
        if (cursor_contacts!=null&&cursor_contacts.getCount()>0) {
            cursor_contacts.moveToFirst();
            int contactId = cursor_contacts.getInt (cursor_contacts.getColumnIndex(ContactsContract.Contacts._ID));
            //找出此联系人id对应的所有email
            Cursor email_cursor = contentResolver.query ( ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null );
            StringBuffer sb = new StringBuffer();
            int typePhone, resType;
            String email;
            if (email_cursor.getCount() > 0) {
                emails=new ArrayList<String>();
                email_cursor.moveToFirst();
                do {
                    email = email_cursor.getString ( email_cursor.getColumnIndex (ContactsContract.CommonDataKinds.Phone.NUMBER) );
                    sb.append( email +"\n");
                } while (email_cursor.moveToNext());


            } else {
                return null;
            }
        }

        return null;
    }


}
