package com.facemail.mobile.android.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.db.FaceMainHelper;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.impl.FaceMailMessageImpl;
import com.facemail.mobile.android.util.SystemUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/26/13
 * Time: 11:29 AM.
 */
public class FaceMailMessageDao implements FaceMailMessageImpl {
    private Context mContext;
    private static FaceMailMessageDao faceMailMessageDao;

    private FaceMailMessageDao(Context mContext) {
        this.mContext = mContext;
    }

    public static FaceMailMessageDao getInstance(Context context) {
        if (faceMailMessageDao == null) {
            faceMailMessageDao = new FaceMailMessageDao(context);
        }
        return faceMailMessageDao;
    }

    @Override
    public long insertMail(FaceMailMessage mail) {
        if (mail == null) {
            return 0;
        }
        SystemUtil.log("facemail0030,开始插入email" + mail.getMail_title());
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        long newRowId = 0;
        ContentValues values = new ContentValues();
        values.put(Config.USER_FaceMailMessage.MAIL_TITLE, mail.getMail_title());
        values.put(Config.USER_FaceMailMessage.MAIL_CONTENT, mail.getMail_content());
        values.put(Config.USER_FaceMailMessage.MAIL_CONTENT_TYPE, mail.getMail_content_type());
        values.put(Config.USER_FaceMailMessage.MAIL_FROM, mail.getMail_from());
        values.put(Config.USER_FaceMailMessage.MAIL_FROM_DESC, mail.getMail_from_desc());
        values.put(Config.USER_FaceMailMessage.MAIL_IS_ATTACH, mail.getMail_is_attach());
        values.put(Config.USER_FaceMailMessage.MAIL_ATTACH_FILENAME, mail.getMail_attach_filename());
        values.put(Config.USER_FaceMailMessage.MAIL_TO, mail.getTo());
        values.put(Config.USER_FaceMailMessage.MAIL_TO_CC, mail.getTo_cc());
        values.put(Config.USER_FaceMailMessage.MAIL_TO_BCC, mail.getTo_bcc());
        values.put(Config.USER_FaceMailMessage.MAIL_REPLYTO, mail.getRePlyto());
        values.put(Config.USER_FaceMailMessage.MAIL_USER_NAME, mail.getMail_user_name());
        values.put(Config.USER_FaceMailMessage.MAIL_READ_STATUS, "0");
        values.put(Config.USER_FaceMailMessage.MAIL_RCV_TIME, SystemUtil.getDate(mail.getMail_rcv_time(),1));
        values.put(Config.USER_FaceMailMessage.MAIL_SEND_TIME, SystemUtil.getDate(mail.getMail_send_time(),1));
        values.put(Config.USER_FaceMailMessage.ISRECYCLEBIN, "0");
        values.put(Config.USER_FaceMailMessage.ISSTARMAIL, mail.getStarMail());
        values.put(Config.USER_FaceMailMessage.RECYCLEBINTIME, "");
        values.put(Config.USER_FaceMailMessage.MESSAGENUMBER, mail.getMessageNumber());

        newRowId = db.insert(Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME, null, values);
//        db.close();

        FaceAddressDao.getFaceAddressDao(mContext).saveAddress(mail.getFaceAddresses());
        SystemUtil.log("facemail0031,成功插入email" + mail.getMail_title());
        return newRowId;
    }

    @Override
    public void deleteMail(FaceMailMessage mail) {
        //标记为垃圾邮件
        if (mail == null) {
            return;
        }
        SystemUtil.log("facemail0031,开始将email:" + mail.getMail_title() + ", 标记为垃圾邮件");
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Config.USER_FaceMailMessage.ISRECYCLEBIN, "1");
        values.put(Config.USER_FaceMailMessage.RECYCLEBINTIME, SystemUtil.getDate(new Date(),1));
        db.update(Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME, values, Config.USER_FaceMailMessage._ID + " =? ", new String[]{String.valueOf(mail.get_id())});
//        db.close();
        SystemUtil.log("facemail0032,成功将email:" + mail.getMail_title() + ", 标记为垃圾邮件");

    }

    @Override
    public FaceMailMessage getMail(int emailid) {
        FaceMailMessage faceMailMessage = null;
        String sql = "select *  from " + Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME + " where " + Config.USER_FaceMailMessage._ID + " = " + emailid;
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToNext()) {
            faceMailMessage = new FaceMailMessage();
            faceMailMessage.set_id(c.getInt(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage._ID)));
            faceMailMessage.setMail_title(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_TITLE)));
            faceMailMessage.setMail_content(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_CONTENT)));
            faceMailMessage.setMail_from(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_FROM)));
            faceMailMessage.setMail_content_type(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_CONTENT_TYPE)));
            faceMailMessage.setMail_from_desc(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_FROM_DESC)));
            faceMailMessage.setMail_is_attach(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_IS_ATTACH)));
            faceMailMessage.setMail_attach_filename(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_ATTACH_FILENAME)));
            faceMailMessage.setTo(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_TO)));
            faceMailMessage.setTo_cc(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_TO_CC)));
            faceMailMessage.setTo_bcc(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_TO_BCC)));
            faceMailMessage.setRePlyto(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_REPLYTO)));
            faceMailMessage.setMail_user_name(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_USER_NAME)));
            faceMailMessage.setMail_READ_STATUS(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_READ_STATUS)));
            faceMailMessage.setMail_rcv_time(SystemUtil.getDate(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_SEND_TIME))));
            faceMailMessage.setMail_send_time(SystemUtil.getDate(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_SEND_TIME))));
            faceMailMessage.setRecycleBin(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.ISRECYCLEBIN)));
            faceMailMessage.setStarMail(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.ISSTARMAIL)));
            faceMailMessage.setRecycleBinTime(SystemUtil.getDate(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.RECYCLEBINTIME))));
            faceMailMessage.setMessageNumber(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MESSAGENUMBER)));
        }
        c.close();
//        db.close();
        return faceMailMessage;
    }

    @Override
    public List<FaceMailMessage> getMails(String mailAddress,int in_or_out) {
        //取该账户下所有的邮件列表
        if ("".equals(mailAddress)||mailAddress==null) {
            return null;
        }
        mailAddress=mailAddress.trim();
        if (mailAddress.equals("")) {
            return null;
        }
        List<FaceMailMessage> faceMailMessages = null;
        String sql = "select *  from " + Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME + " where " +
                Config.USER_FaceMailMessage.MAIL_USER_NAME + " ='" + mailAddress + "' and "+Config.USER_FaceMailMessage.ISRECYCLEBIN +"='0'  ORDER BY "+ Config.USER_FaceMailMessage._ID+" desc";
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);


        if (c != null && c.moveToNext()) {
            faceMailMessages = new ArrayList<FaceMailMessage>();
            do {
                FaceMailMessage faceMailMessage = new FaceMailMessage();

                faceMailMessage.set_id(c.getInt(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage._ID)));
                faceMailMessage.setMail_title(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_TITLE)));
                faceMailMessage.setMail_content(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_CONTENT)));
                faceMailMessage.setMail_from(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_FROM)));
                faceMailMessage.setMail_content_type(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_CONTENT_TYPE)));
                faceMailMessage.setMail_from_desc(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_FROM_DESC)));
                faceMailMessage.setMail_is_attach(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_IS_ATTACH)));
                faceMailMessage.setMail_attach_filename(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_ATTACH_FILENAME)));
                faceMailMessage.setTo(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_TO)));
                faceMailMessage.setTo_cc(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_TO_CC)));
                faceMailMessage.setTo_bcc(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_TO_BCC)));
                faceMailMessage.setRePlyto(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_REPLYTO)));
                faceMailMessage.setMail_user_name(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_USER_NAME)));
                faceMailMessage.setMail_READ_STATUS(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_READ_STATUS)));
                faceMailMessage.setMail_rcv_time(SystemUtil.getDate(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_SEND_TIME))));
                faceMailMessage.setMail_send_time(SystemUtil.getDate(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MAIL_SEND_TIME))));
                faceMailMessage.setRecycleBin(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.ISRECYCLEBIN)));
                faceMailMessage.setStarMail(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.ISSTARMAIL)));
                faceMailMessage.setRecycleBinTime(SystemUtil.getDate(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.RECYCLEBINTIME))));
                faceMailMessage.setMessageNumber(c.getString(c.getColumnIndexOrThrow(Config.USER_FaceMailMessage.MESSAGENUMBER)));

                faceMailMessages.add(faceMailMessage);
            } while (c.moveToNext());
            c.close();
//            db.close();
        }

        return faceMailMessages;
    }

    @Override
    public int getMailNumber(String mailAddress) {
        SystemUtil.log("facemail0038-getMailNumber,开始取email:" + mailAddress + ", 下所有的邮件总数");
        int size=0;
        if ("".equals(mailAddress)||mailAddress==null) {
            return 0;
        }
        mailAddress=mailAddress.trim();
        if (mailAddress.equals("")) {
            return 0;
        }
        String sql = "select "+Config.USER_FaceMailMessage._ID+"  from " + Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME + " where " + Config.USER_FaceMailMessage.MAIL_USER_NAME + " ='" + mailAddress + "'";
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToNext()) {
            do {
                size++;
            } while (c.moveToNext());
            c.close();
//            db.close();
        }
        SystemUtil.log("facemail0039-getMailNumber,成功取到email:" + mailAddress + ", 下所有的邮件总数:"+size);
        return size;
    }


    @Override
    public void update_star(FaceMailMessage mail) {
        if (mail == null) {
            return;
        }
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Config.USER_FaceMailMessage.ISSTARMAIL, "1");
        db.update(Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME, values, Config.USER_FaceMailMessage._ID + " =? ", new String[]{String.valueOf(mail.get_id())});
//        db.close();
    }

    @Override
    public void update_unStar(FaceMailMessage mail) {
        if (mail == null) {
            return;
        }
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Config.USER_FaceMailMessage.ISSTARMAIL, "0");
        db.update(Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME, values, Config.USER_FaceMailMessage._ID + " =? ", new String[]{String.valueOf(mail.get_id())});
//        db.close();
    }

    @Override
    public void update_undelete(FaceMailMessage mail) {
        if (mail == null) {
            return;
        }
        SystemUtil.log("facemail0035,开始将email:" + mail.getMail_title() + ", 恢复");
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Config.USER_FaceMailMessage.ISRECYCLEBIN, "0");
        values.put(Config.USER_FaceMailMessage.RECYCLEBINTIME, "");
        db.update(Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME, values, Config.USER_FaceMailMessage._ID + " =? ", new String[]{String.valueOf(mail.get_id())});
//        db.close();
        SystemUtil.log("facemail0036,成功将email:" + mail.getMail_title() + ", 恢复");
    }

    @Override
    public void update_real_delete(FaceMailMessage mail) {
        if (mail == null) {
            return;
        }
        SystemUtil.log("facemail0033,开始将email:" + mail.getMail_title() + ", delete");
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        db.delete(Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME, Config.USER_FaceMailMessage._ID + " =? ", new String[]{String.valueOf(mail.get_id())});
//        db.close();
        SystemUtil.log("facemail0034,成功将email:" + mail.getMail_title() + ", delete");

    }

    @Override
    public boolean isExsit(String messageNumber, String mailAddress) {
        boolean exsit = false;
        if ("".equals(messageNumber) || "".equals(mailAddress) || messageNumber == null || mailAddress == null) {
            return exsit;
        }
        String sql = "select " + Config.USER_FaceMailMessage._ID + " from " + Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME + " where " + Config.USER_FaceMailMessage.MAIL_USER_NAME
                + " ='" + mailAddress + "' and " + Config.USER_FaceMailMessage.MESSAGENUMBER + " ='" + messageNumber+"'";
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToNext()) {
            exsit = true;
        }
        c.close();
//        db.close();

        return exsit;
    }
}
