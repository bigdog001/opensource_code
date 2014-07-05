package com.facemail.mobile.android.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.facemail.mobile.android.db.FaceMainHelper;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.impl.UserAccountImpl;
import com.facemail.mobile.android.util.SystemUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/18/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserAccountDao implements UserAccountImpl{
    private static Context mContext;
    private static UserAccountDao uad;

    private UserAccountDao(Context context){
        this.mContext=context;
    }
    public static UserAccountDao getInstance(Context context) {
        if (uad==null) {
            uad=new UserAccountDao(context);
        }
        return  uad;
    }

    @Override
    public long insertUserAccount(UserAcount ua) {
        if(ua==null) return 0;
        SystemUtil.log("facemail009,开始插入用户"+ua.getUser_name());
        SQLiteDatabase db =FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        long newRowId=0;
        ContentValues values = new ContentValues();
        values.put(Config.USER_ACCOUNT.USER_NAME, ua.getUser_name());
        values.put(Config.USER_ACCOUNT.USER_PWD, ua.getUser_pwd());
        values.put(Config.USER_ACCOUNT.USER_SMTP, ua.getUser_smtp());
        values.put(Config.USER_ACCOUNT.USER_PORT_SMTP, ua.getUser_port_smtp());
        values.put(Config.USER_ACCOUNT.USER_POP3, ua.getUser_pop3());
        values.put(Config.USER_ACCOUNT.USER_PORT_POP3, ua.getUser_port_pop3());
        values.put(Config.USER_ACCOUNT.LASTRCV, SystemUtil.getDate(ua.getLastRcv(), 1));
        values.put(Config.USER_ACCOUNT.SCANFREQUENCY, ua.getScanfrequency());

        newRowId=db.insert(Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME,null,values);
//        db.close();
        SystemUtil.log("facemail0010,成功插入用户"+ua.getUser_name());
        return newRowId;
    }

    @Override
    public void deleteUserAccount(UserAcount ua) {
        if (ua==null) {
            SystemUtil.log("facemail0011,要删除的帐号为null");
            return;
        }
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        db.delete(Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME, Config.USER_ACCOUNT._ID + " =? ", new String[]{String.valueOf(ua.get_id())});
//        db.close();
        SystemUtil.log("facemail012,成功删除帐号"+ua.getUser_name());
    }

    @Override
    public UserAcount getUserAccount(String email) {
        if ("".equals(email)||email==null) {
            return null;
        }
        SystemUtil.log("facemail0013,开始query用户"+email);
        UserAcount ua = null;
        String sql = "select  account.*,count(0) as mailtotal  from " + Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME + " account , "+Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME+" message where account.username=message.username and" + Config.USER_ACCOUNT.USER_NAME + " = '" + ua.getUser_name() + "' group by account.username";
        SystemUtil.log("asdf---:"+sql);
        SQLiteDatabase db = FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c!=null&&c.moveToNext()) {
            ua =new UserAcount();
            ua.set_id(c.getInt(c.getColumnIndexOrThrow(Config.USER_ACCOUNT._ID)));
            ua.setUser_name(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_NAME)));
            ua.setUser_pwd(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_PWD)));
            ua.setUser_smtp(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_SMTP)));
            ua.setUser_port_smtp(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_PORT_SMTP)));
            ua.setUser_pop3(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_POP3)));
            ua.setUser_port_pop3(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_PORT_POP3)));
            ua.setLastRcv(SystemUtil.getDate(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.LASTRCV))));
            ua.setScanfrequency(c.getInt(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.SCANFREQUENCY)));
//            ua.setTotal_mail_number(FaceMailMessageDao.getInstance(mContext).getMailNumber(ua.getUser_name()));
            ua.setTotal_mail_number(c.getInt(c.getColumnIndexOrThrow("mailtotal")));
        }
        c.close();
//        db.close();
        SystemUtil.log("facemail014,成功query用户"+email);
        return ua;
    }

    @Override
    public List<UserAcount> getUserAccount() {
        List<UserAcount> uas=null;
        SystemUtil.log("facemail015,开始query所有用户");
        /**
         * select  account.*,count(0) as mailtotal  from useraccount account left join mailcontent message where account.username=message.username group by account.username
         */
//        String sql = "select  account.*,count(0) as mailtotal  from " + Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME + " account , "+Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME+" message where account.username=message.username" + " group by account.username";
//        String sql = "select  account.*,count(0) as mailtotal  from " + Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME + " account  left join select count(username) from "+Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME + " group by account.username ";
        String sql = "select  account.* from " + Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME + " account  ";
        SQLiteDatabase db =FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        Cursor c =db.rawQuery(sql,null);
        if (c!=null&&c.moveToNext()) {
            uas=new ArrayList<UserAcount>();
            do {
                UserAcount ua=new UserAcount();
                ua.set_id(c.getInt(c.getColumnIndexOrThrow(Config.USER_ACCOUNT._ID)));
                ua.setUser_name(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_NAME)));
                ua.setUser_pwd(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_PWD)));
                ua.setUser_smtp(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_SMTP)));
                ua.setUser_port_smtp(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_PORT_SMTP)));
                ua.setUser_pop3(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_POP3)));
                ua.setUser_port_pop3(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_PORT_POP3)));
                ua.setLastRcv(SystemUtil.getDate(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.LASTRCV))));
                ua.setScanfrequency(c.getInt(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.SCANFREQUENCY)));
                ua.setTotal_mail_number(FaceMailMessageDao.getInstance(mContext).getMailNumber(ua.getUser_name()));
                uas.add(ua);
            } while (c.moveToNext());
            c.close();
//            db.close();
        }
        SystemUtil.log("facemail016,成功query所有用户");
        return uas;
    }

    @Override
    public List<UserAcount> getUserAccountByScanFrequency() {
        List<UserAcount> uas=null;
        SystemUtil.log("facemail015,开始query所有用户");
        /**
         * select  account.*,count(0) as mailtotal  from useraccount account left join mailcontent message where account.username=message.username group by account.username
         */
//        String sql = "select  account.*,count(0) as mailtotal  from " + Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME + " account , "+Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME+" message where account.username=message.username" + " group by account.username";
//        String sql = "select  account.*,count(0) as mailtotal  from " + Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME + " account  left join select count(username) from "+Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME + " group by account.username ";
        String sql = "select  account.* from " + Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME + " account  where account."+Config.USER_ACCOUNT.SCANFREQUENCY+">0";
        SQLiteDatabase db =FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        Cursor c =db.rawQuery(sql,null);
        if (c!=null&&c.moveToNext()) {
            uas=new ArrayList<UserAcount>();
            do {
                UserAcount ua=new UserAcount();
                ua.set_id(c.getInt(c.getColumnIndexOrThrow(Config.USER_ACCOUNT._ID)));
                ua.setUser_name(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_NAME)));
                ua.setUser_pwd(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_PWD)));
                ua.setUser_smtp(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_SMTP)));
                ua.setUser_port_smtp(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_PORT_SMTP)));
                ua.setUser_pop3(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_POP3)));
                ua.setUser_port_pop3(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.USER_PORT_POP3)));
                ua.setLastRcv(SystemUtil.getDate(c.getString(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.LASTRCV))));
                ua.setScanfrequency(c.getInt(c.getColumnIndexOrThrow(Config.USER_ACCOUNT.SCANFREQUENCY)));
                ua.setTotal_mail_number(FaceMailMessageDao.getInstance(mContext).getMailNumber(ua.getUser_name()));
                uas.add(ua);
            } while (c.moveToNext());
            c.close();
//            db.close();
        }
        SystemUtil.log("facemail016,成功query所有用户");
        return uas;
    }

    @Override
    public void updateUserAccount(UserAcount ua) {
        if (ua==null) {
            return;
        }
        SystemUtil.log("facemail0017,开始update用户"+ua.getUser_name());
        ContentValues values = new ContentValues();
        values.put(Config.USER_ACCOUNT.USER_NAME,ua.getUser_name());
        values.put(Config.USER_ACCOUNT.USER_PWD,ua.getUser_pwd());
        values.put(Config.USER_ACCOUNT.USER_SMTP,ua.getUser_smtp());
        values.put(Config.USER_ACCOUNT.USER_PORT_SMTP,ua.getUser_port_smtp());
        values.put(Config.USER_ACCOUNT.USER_POP3,ua.getUser_pop3());
        values.put(Config.USER_ACCOUNT.USER_PORT_POP3,ua.getUser_port_pop3());
        values.put(Config.USER_ACCOUNT.LASTRCV, SystemUtil.getDate(ua.getLastRcv(),1));
        SQLiteDatabase db =FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        db.update(Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME,values,Config.USER_ACCOUNT._ID+" =? ",new String[]{String.valueOf(ua.get_id())});
//        db.close();
        SystemUtil.log("facemail018,成功update用户"+ua.getUser_name());
    }

    @Override
    public boolean isExsit(String email) {
        boolean  isExsit=false;
        if ("".equals(email)||email==null) {
            return isExsit;
        }
        String sql="select "+Config.USER_ACCOUNT._ID+" from "+Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME+ " where "+Config.USER_ACCOUNT.USER_NAME +" ='"+email+"'";
        SQLiteDatabase db =FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        Cursor c =db.rawQuery(sql,null);
        if (c!=null&&c.moveToNext()) {
            isExsit=true;
        }
        c.close();
//        db.close();
        return isExsit;
    }

    @Override
    public void updateRcvTime(Date date, UserAcount userAcount) {
        if (userAcount==null||date==null) {
            return;
        }
        SystemUtil.log("facemail00171,开始update用户的最后接收时间"+userAcount.getUser_name());
        ContentValues values = new ContentValues();
        values.put(Config.USER_ACCOUNT.LASTRCV, SystemUtil.getDate(date,1));
        SQLiteDatabase db =FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        db.update(Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME,values,Config.USER_ACCOUNT._ID+" =? ",new String[]{String.valueOf(userAcount.get_id())});
//        db.close();
        SystemUtil.log("facemail0181,成功update用户的最后接收时间"+SystemUtil.getDate(date,1));
    }

    @Override
    public void updateRcvFrequency(int minute, UserAcount userAcount) {
        if (userAcount==null) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(Config.USER_ACCOUNT.SCANFREQUENCY, userAcount.getScanfrequency());
        SQLiteDatabase db =FaceMainHelper.getfaceMainHelper(mContext).getWritableDatabase();
        db.update(Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME,values,Config.USER_ACCOUNT._ID+" =? ",new String[]{String.valueOf(userAcount.get_id())});
//        db.close();
        SystemUtil.log("updateRcvFrequency,开始update用户的接收频率"+userAcount.getUser_name()+",frequency is:"+userAcount.getScanfrequency());
    }

}
