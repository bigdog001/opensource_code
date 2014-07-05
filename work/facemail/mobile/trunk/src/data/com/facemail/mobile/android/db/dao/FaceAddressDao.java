package com.facemail.mobile.android.db.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.facemail.mobile.android.db.FaceMainHelper;
import com.facemail.mobile.android.db.bean.FaceAddress;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.impl.FaceAddressInter;
import com.facemail.mobile.android.util.SystemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/4/13
 * Time: 6:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class FaceAddressDao implements FaceAddressInter{
    private Context mContext;
    private static FaceAddressDao faceAddressDao;

    private FaceAddressDao(Context mContext) {
        this.mContext = mContext;
    }
    public static FaceAddressDao getFaceAddressDao(Context context) {
        if (faceAddressDao==null) {
            faceAddressDao=new FaceAddressDao(context);
        }
        return faceAddressDao;
    }

    @Override
    public long[] saveAddress(List<FaceAddress> faceAddresses) {
        if (faceAddresses==null||faceAddresses.size()==0) {
            return null;
        }
        long[]result=new long[faceAddresses.size()];
        int index=0;
        SystemUtil.log("facemailsaveAddress,开始插入Addresses:" + faceAddresses.size()+" 个");
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        db.beginTransaction();
        for (FaceAddress faceAddress: faceAddresses) {
            long newRowId = 0;
            ContentValues values = new ContentValues();
            values.put(Config.USER_FACEADDRESS.EMAIL_ADDRESS, faceAddress.getEmailAddress());
            values.put(Config.USER_FACEADDRESS.EMAIL_DESC, faceAddress.getEmailDesc());
            values.put(Config.USER_FACEADDRESS.EMAIL_ADRESS_SOURCE_EMAIL, faceAddress.getAddress_source_email());
            values.put(Config.USER_FACEADDRESS.EMAIL_ADRESS_MESSAGE_NUMBER_EMAIL, faceAddress.getMessagenumber());
            values.put(Config.USER_FACEADDRESS.EMAIL_ADRESS_TYPE_EMAIL, faceAddress.getType());
            newRowId = db.insert(Config.USER_FACEADDRESS.EMAIL_ADDRESS_TABLE_NAME, null, values);
            result[index++]=newRowId;
        }

        db.setTransactionSuccessful();
        db.endTransaction();
//        db.close();
        SystemUtil.log("facemailsaveAddress,成功插入Addresses:" +  faceAddresses.size()+" 个");
        return result;
    }

    @Override
    public long saveAddres(FaceAddress faceAddress) {
        if ("".equals(faceAddress)||faceAddress==null) {
            return 0L ;
        }
        long newRowId = 0;
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Config.USER_FACEADDRESS.EMAIL_ADDRESS, faceAddress.getEmailAddress());
        values.put(Config.USER_FACEADDRESS.EMAIL_DESC, faceAddress.getEmailDesc());
        values.put(Config.USER_FACEADDRESS.EMAIL_ADRESS_SOURCE_EMAIL, faceAddress.getAddress_source_email());
        values.put(Config.USER_FACEADDRESS.EMAIL_ADRESS_MESSAGE_NUMBER_EMAIL, faceAddress.getMessagenumber());
        values.put(Config.USER_FACEADDRESS.EMAIL_ADRESS_TYPE_EMAIL, faceAddress.getType());
        newRowId = db.insert(Config.USER_FACEADDRESS.EMAIL_ADDRESS_TABLE_NAME, null, values);

        SystemUtil.log("成功插入地址:" + faceAddress+",地址编号："+newRowId);
        return newRowId;

    }

    @Override
    public Map<String,List<FaceAddress>> getFaceAddress(FaceMailMessage faceMailMessage) {
        if (faceMailMessage==null) {
            return null;
        }
        Map <String,List<FaceAddress>> mailAddresses=null;
        List<FaceAddress> faceAddresses_from;
        List<FaceAddress> faceAddresses_to;
        List<FaceAddress> faceAddresses_cc;
        List<FaceAddress> faceAddresses_bcc;
        List<FaceAddress> faceAddresses_reply;
        String sql = "select * from " + Config.USER_FACEADDRESS.EMAIL_ADDRESS_TABLE_NAME + " where "+
                Config.USER_FACEADDRESS.EMAIL_ADRESS_SOURCE_EMAIL+"='"+faceMailMessage.getMail_user_name()+"' and "+Config.USER_FACEADDRESS.EMAIL_ADRESS_MESSAGE_NUMBER_EMAIL
                +"='"+faceMailMessage.getMessageNumber()+"'";
        SystemUtil.log("get message addresses:"+sql);
        SQLiteDatabase db =FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        Cursor c =db.rawQuery(sql,null);
        if (c!=null&&c.moveToNext()) {
            mailAddresses=new HashMap<String, List<FaceAddress>>();
            faceAddresses_from=new ArrayList<FaceAddress>();
            faceAddresses_to=new ArrayList<FaceAddress>();
            faceAddresses_cc=new ArrayList<FaceAddress>();
            faceAddresses_bcc=new ArrayList<FaceAddress>();
            faceAddresses_reply=new ArrayList<FaceAddress>();
            do {
                FaceAddress fa=new FaceAddress();
                fa.set_id(c.getInt(c.getColumnIndexOrThrow(Config.USER_FACEADDRESS._ID)));
                fa.setEmailAddress(c.getString(c.getColumnIndexOrThrow(Config.USER_FACEADDRESS.EMAIL_ADDRESS)));
                fa.setEmailDesc(c.getString(c.getColumnIndexOrThrow(Config.USER_FACEADDRESS.EMAIL_DESC)));
                fa.setType(c.getString(c.getColumnIndexOrThrow(Config.USER_FACEADDRESS.EMAIL_ADRESS_TYPE_EMAIL)));
                if (!"".equals(fa.getType())&&fa.getType()!=null) {
                    if (Config.ADRESS_TYPE.FROM.equals(fa.getType())) {
                        faceAddresses_from.add(fa);
                    } else if (Config.ADRESS_TYPE.TO.equals(fa.getType())) {
                        faceAddresses_to.add(fa);
                    }else if (Config.ADRESS_TYPE.CC.equals(fa.getType())) {
                        faceAddresses_cc.add(fa);
                    }else if (Config.ADRESS_TYPE.BCC.equals(fa.getType())) {
                        faceAddresses_bcc.add(fa);
                    }else if (Config.ADRESS_TYPE.REPLY_TO.equals(fa.getType())) {
                        faceAddresses_reply.add(fa);
                    }
                }
            } while (c.moveToNext());
            if (faceAddresses_from.size()>0) {
                mailAddresses.put(Config.ADRESS_TYPE.FROM,faceAddresses_from);
            } if (faceAddresses_to.size()>0) {
                mailAddresses.put(Config.ADRESS_TYPE.TO,faceAddresses_to);
            } if (faceAddresses_cc.size()>0) {
                mailAddresses.put(Config.ADRESS_TYPE.CC,faceAddresses_cc);
            } if (faceAddresses_bcc.size()>0) {
                mailAddresses.put(Config.ADRESS_TYPE.BCC,faceAddresses_bcc);
            } if (faceAddresses_reply.size()>0) {
                mailAddresses.put(Config.ADRESS_TYPE.REPLY_TO,faceAddresses_reply);
            }
            c.close();
//            db.close();
        }
        return mailAddresses;
    }
}
