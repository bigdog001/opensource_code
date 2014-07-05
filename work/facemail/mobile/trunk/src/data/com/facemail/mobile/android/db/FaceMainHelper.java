package com.facemail.mobile.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.util.SystemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/17/13
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class FaceMainHelper extends SQLiteOpenHelper {

    private static final String SQL_MAILACCOUNT_TABLE_NAME_CREATE= "CREATE TABLE " + Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME + " (" +
            Config.USER_ACCOUNT._ID + " INTEGER PRIMARY KEY," +
            Config.USER_ACCOUNT.USER_NAME +  Config.FIELD_TYPE.USER_NAME_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_ACCOUNT.USER_PWD  +  Config.FIELD_TYPE.USER_PWD_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_ACCOUNT.USER_SMTP  +  Config.FIELD_TYPE.USER_SMTP_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_ACCOUNT.USER_PORT_SMTP  +  Config.FIELD_TYPE.USER_PORT_SMTP_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_ACCOUNT.USER_POP3  +  Config.FIELD_TYPE.USER_POP3_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_ACCOUNT.LASTRCV +  Config.FIELD_TYPE.LASTRCV_FIELD_TYPE + Config.COMMA_SEP+
            Config.USER_ACCOUNT.USER_PORT_POP3  +  Config.FIELD_TYPE.USER_PORT_POP3_FIELD_TYPE  +  Config.COMMA_SEP+
            Config.USER_ACCOUNT.SCANFREQUENCY  +  Config.FIELD_TYPE.SCANFREQUENCY_FIELD_TYPE  +
            ");";
    private static final String SQL_DELETE_MAILACCOUNT_TABLE_DELETE ="DROP TABLE IF EXISTS " + Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME;

    private static final String SQL_USER_FACEMAILMESSAGE_TABLE_CREATE= "CREATE TABLE " + Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME + " (" +
            Config.USER_FaceMailMessage._ID + " INTEGER PRIMARY KEY," +
            Config.USER_FaceMailMessage.MAIL_TITLE +  Config.FIELD_TYPE.MAIL_TITLE_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_CONTENT  +  Config.FIELD_TYPE.MAIL_CONTENT_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_CONTENT_TYPE  +  Config.FIELD_TYPE.MAIL_CONTENT_TYPE_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_FROM  +  Config.FIELD_TYPE.MAIL_FROM_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_FROM_DESC  +  Config.FIELD_TYPE.MAIL_FROM_DESC_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_IS_ATTACH  +  Config.FIELD_TYPE.MAIL_IS_ATTACH_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_ATTACH_FILENAME  +  Config.FIELD_TYPE.MAIL_ATTACH_FILENAME_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_TO  +  Config.FIELD_TYPE.MAIL_TO_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_TO_CC  +  Config.FIELD_TYPE.MAIL_TO_CC_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_TO_BCC  +  Config.FIELD_TYPE.MAIL_TO_BCC_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_REPLYTO  +  Config.FIELD_TYPE.MAIL_REPLYTO_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_USER_NAME  +  Config.FIELD_TYPE.MAIL_USER_NAME_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_READ_STATUS +  Config.FIELD_TYPE.MAIL_READ_STATUS_FIELD_TYPE + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_RCV_TIME  +  Config.FIELD_TYPE.MAIL_RCV_TIME_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MAIL_SEND_TIME  +  Config.FIELD_TYPE.MAIL_SEND_TIME_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FaceMailMessage.ISRECYCLEBIN  +  Config.FIELD_TYPE.ISRECYCLEBIN_FIELD_TYPE  +  Config.COMMA_SEP+
            Config.USER_FaceMailMessage.ISSTARMAIL  +  Config.FIELD_TYPE.ISSTARMAIL_FIELD_TYPE  +   Config.COMMA_SEP+
            Config.USER_FaceMailMessage.RECYCLEBINTIME  +  Config.FIELD_TYPE.RECYCLEBINTIME_FIELD_TYPE  +  Config.COMMA_SEP+
            Config.USER_FaceMailMessage.MESSAGENUMBER  +  Config.FIELD_TYPE.MESSAGENUMBER_FIELD_TYPE  +
            ");";
    private static final String SQL_DELETE_USER_FACEMAILMESSAGE_TABLE_DELETE ="DROP TABLE IF EXISTS " + Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME;


    private static final String SQL_USER_EMAIL_ADDRESS_TABLE_CREATE= "CREATE TABLE " + Config.USER_FACEADDRESS.EMAIL_ADDRESS_TABLE_NAME + " (" +
            Config.USER_FACEADDRESS._ID + " INTEGER PRIMARY KEY," +
            Config.USER_FACEADDRESS.EMAIL_ADDRESS +  Config.FIELD_TYPE.EMAIL_ADDRESS_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FACEADDRESS.EMAIL_ADRESS_MESSAGE_NUMBER_EMAIL  +  Config.FIELD_TYPE.EMAIL_ADRESS_MESSAGE_NUMBER_EMAIL_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FACEADDRESS.EMAIL_ADRESS_TYPE_EMAIL  +  Config.FIELD_TYPE.EMAIL_ADRESS_TYPE_EMAIL_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FACEADDRESS.EMAIL_DESC  +  Config.FIELD_TYPE.EMAIL_DESC_FIELD_TYPE  + Config.COMMA_SEP+
            Config.USER_FACEADDRESS.EMAIL_ADRESS_SOURCE_EMAIL  +  Config.FIELD_TYPE.EMAIL_ADRESS_SOURCE_EMAIL_FIELD_TYPE  +
            ");";
    private static final String SQL_USER_EMAIL_ADDRESS_TABLE_DELETE ="DROP TABLE IF EXISTS " + Config.USER_FACEADDRESS.EMAIL_ADDRESS_TABLE_NAME;

    private static FaceMainHelper faceMainHelper;
    public static FaceMainHelper getfaceMainHelper(Context context) {
        SystemUtil.log("facemail007,开始获取dbHelper....." );
        if (faceMainHelper==null) {
            faceMainHelper=new FaceMainHelper(context);
        }
        SystemUtil.log("facemail008,成功获取dbHelper.....");
        return faceMainHelper;
    }
    private FaceMainHelper(Context context) {
        super(context, Config.dbName, null, Config.dbVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建所需的数据库表结构
        db.execSQL(SQL_MAILACCOUNT_TABLE_NAME_CREATE);
        db.execSQL(SQL_USER_FACEMAILMESSAGE_TABLE_CREATE);
        db.execSQL(SQL_USER_EMAIL_ADDRESS_TABLE_CREATE);
        SystemUtil.log("facemail019,table "+Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME+
                " and "+Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME+
                " are created...,and sql is:"+SQL_MAILACCOUNT_TABLE_NAME_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MAILACCOUNT_TABLE_DELETE);
        db.execSQL(SQL_DELETE_USER_FACEMAILMESSAGE_TABLE_DELETE);
        db.execSQL(SQL_USER_EMAIL_ADDRESS_TABLE_DELETE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
