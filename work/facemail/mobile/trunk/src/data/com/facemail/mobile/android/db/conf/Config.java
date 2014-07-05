package com.facemail.mobile.android.db.conf;

import android.net.Uri;
import android.provider.BaseColumns;
import com.facemail.mobile.android.R;

import javax.mail.Message;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/17/13
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    public static final String dbName = "facemail.db";
    public static final int dbVersion = 1;
    public static final String faceProvider = "com.facemail.mobile.android.faceprovider";
    public static final String faceConfigFileSp = "faceconfig";
    public static final String accountSetup = "accountSetup";
    public static final String RCV_SERVICE_AUTORUN = "rcv_service_autorun";
    public static final int[] leftMenu = new int[]{
            R.id.tv_write_email_left_menu,
            R.id.tv_myemail_left_menu,
            R.id.tv_myemail_send_left_menu,
            R.id.tv_email_draft_left_menu,
            R.id.tv_email_important_star_left_menu,
            R.id.tv_email_recyclebin_left_menu,
            R.id.tv_email_setting_left_menu,
            R.id.v1_0_0_left_menu_addaccount_tv};
    public static final int [] MAIL_RCV_FREQUENCY_VALUE={0,5,10,30,60,180,300};
    public static final int MAIL_TASK_RCV=1;
    public static final int MAIL_TASK_SEND=0;
    public static final int MAIL_THREADS=5;
    public static final String MAIL_SEND_SUCESS="SUCESS";
    public static final String MAIL_RCV_ERROR_COLOSE_ERROR="ERROR001";
    public static final String MAIL_RCV_NOPROVIDER="ERROR002";
    public static final String MAIL_RCV_MESSAGE_ERROR="ERROR003";

    //回复邮件
    public static final String MAIL_RPL_SINGLE = "REPLY";
    public static final String MAIL_RPL_ALL = "REPLY_ALL";
    public static final String MAIL_TITLE_PREFIX = "Re: ";
    public static final String MAIL_CONTENT_PREFIX = "\n\n\n ";

    public static final String FORWARD_PARAM1 = "user";
    public static final String FORWARD_PARAM2 = "facemailmessage";
    public static final String FORWARD_PARAM3 = "reply_action";
    public static final String FRAGMENTFROM = "fragmentfrom";
    public static final String FROM1_COMPOSE = "COMPOSE";
    public static final String FROM2_DRAFTS = "DRAFTS";
    public static final String FROM3_REPLY = "REPLY";
//    public static final String FORWARD_PARAM4 = "";


//    public static final int MAIL_RCV_THREADS=4;
    public static class ListenerType{
        public static int SHOW_ACCOUNT_SETUP=1;
    }
    public static class FaceUri{
        public static final String CONTACTS="content://com.android.contacts/contacts";// 操作的数据是联系人信息Uri
        public static final String PHONES="content://com.android.contacts/data/phones";// 联系人电话Uri
        public static final String EMAILS="content://com.android.contacts/data/emails";// 联系人Email Uri
    }
    public static final class FaceBroadcastType{
        public static final String WATCHDOG="com.facemail.mobile.android.watchdog";
        public static final String RELOAD_ACCOUNT_FORSCAN="com.facemail.mobile.android.reloaduseraccount";
    }
    public static class QickActionID{
        public static int REFRESH=1;
        public static int EDIT=2;
        public static int DELETE=3;
//        public static int SETTING=4;
    }

    //定义Uri
    public static final Uri CONTENT_URI = Uri.parse("content://" + faceProvider);

    //邮件帐号表的表名
    public static final String dbTABLE_USER_ACCOUNT = "mailaccount";
    public static final String COMMA_SEP = ",";

    public static final class USER_ACCOUNT implements BaseColumns {

        //定义数据表列
        public static final String MAILACCOUNT_TABLE_NAME = "useraccount";
        public static final String USER_NAME = "username"; //必须是标准的email地址
        public static final String USER_PWD = "userpwd";
        public static final String USER_SMTP = "usersmtp";
        public static final String USER_POP3 = "userpop3";
        public static final String USER_PORT_SMTP = "portsmtp";
        public static final String USER_PORT_POP3 = "portpop3";
        public static final String LASTRCV="lastrcv";  //最近一次收邮件的时间
        public static final String SCANFREQUENCY="scanfrequency";  //最近一次收邮件的时间
//        public static final String USER_UNREAD_EMAILS = "USER_UNREAD_EMAILS";
    }
    public static final class ADRESS_TYPE{
        public static final String REPLY_TO="replyto";
        public static final String FROM="from";
        public static final String TO= Message.RecipientType.TO.toString();
        public static final String CC= Message.RecipientType.CC.toString();
        public static final String BCC= Message.RecipientType.BCC.toString();
    }


    public static final class USER_FaceMailMessage implements BaseColumns {
        //定义数据表列
        public static final String MAILMESSAGE_TABLE_NAME = "mailcontent";
        public static final String MAIL_TITLE = "mailtitle";
        public static final String MAIL_CONTENT = "mailcontent";
        public static final String MAIL_CONTENT_TYPE = "mailcontenttype";
        public static final String MAIL_FROM = "mailfrom";
        public static final String MAIL_FROM_DESC = "mailfromdesc";
        public static final String MAIL_IS_ATTACH = "mailisattach";
        public static final String MAIL_ATTACH_FILENAME = "mailattachfilename";
        public static final String MAIL_TO = "mailto";
        public static final String MAIL_TO_CC = "mailtocc";
        public static final String MAIL_TO_BCC = "mailtobcc";
        public static final String MAIL_REPLYTO = "replyto";
        public static final String MAIL_USER_NAME = "username";
        public static final String MAIL_READ_STATUS = "mailreadstatus";
        public static final String MAIL_RCV_TIME = "mailrcvtime";
        public static final String MAIL_SEND_TIME = "mail_send_time";
        public static final String ISRECYCLEBIN = "isrecyclebin";
        public static final String ISSTARMAIL = "isstarmail";
        public static final String RECYCLEBINTIME = "recyclebintime";
        public static final String MESSAGENUMBER="messagenumber";
    }


    public static final class USER_FACEADDRESS implements BaseColumns{
        public static final String EMAIL_ADDRESS_TABLE_NAME ="faceaddress";
        public static final String EMAIL_ADDRESS ="emailaddress";
        public static final String EMAIL_DESC ="emaildesc";
        public static final String EMAIL_ADRESS_SOURCE_EMAIL ="address_source_email";
        public static final String EMAIL_ADRESS_MESSAGE_NUMBER_EMAIL ="message_number";
        public static final String EMAIL_ADRESS_TYPE_EMAIL ="type";

    }
     private static final class BASIC_TYPE{
         public static final String INTEGER = " INTEGER ";
         public static final String TEXT = " TEXT ";
         public static final String DATE = " DATE ";

     }
    public static final class FIELD_TYPE{
        public static final String USER_NAME_FIELD_TYPE = BASIC_TYPE.TEXT; //必须是标准的email地址
        public static final String USER_PWD_FIELD_TYPE =  BASIC_TYPE.TEXT;
        public static final String USER_SMTP_FIELD_TYPE =  BASIC_TYPE.TEXT;
        public static final String USER_POP3_FIELD_TYPE =  BASIC_TYPE.TEXT;
        public static final String USER_PORT_SMTP_FIELD_TYPE =  BASIC_TYPE.TEXT;
        public static final String USER_PORT_POP3_FIELD_TYPE =  BASIC_TYPE.TEXT;
        public static final String LASTRCV_FIELD_TYPE =  BASIC_TYPE.DATE;
        public static final String SCANFREQUENCY_FIELD_TYPE =  BASIC_TYPE.INTEGER;

        public static final String MAIL_TITLE_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_CONTENT_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_CONTENT_TYPE_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_FROM_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_FROM_DESC_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_IS_ATTACH_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_ATTACH_FILENAME_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_TO_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_TO_CC_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_TO_BCC_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_REPLYTO_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_USER_NAME_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_READ_STATUS_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String MAIL_RCV_TIME_FIELD_TYPE = BASIC_TYPE.DATE;
        public static final String MAIL_SEND_TIME_FIELD_TYPE = BASIC_TYPE.DATE;
        public static final String ISRECYCLEBIN_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String ISSTARMAIL_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String RECYCLEBINTIME_FIELD_TYPE = BASIC_TYPE.DATE;
        public static final String MESSAGENUMBER_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String EMAIL_ADDRESS_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String EMAIL_DESC_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String EMAIL_ADRESS_SOURCE_EMAIL_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String EMAIL_ADRESS_MESSAGE_NUMBER_EMAIL_FIELD_TYPE = BASIC_TYPE.TEXT;
        public static final String EMAIL_ADRESS_TYPE_EMAIL_FIELD_TYPE = BASIC_TYPE.TEXT;

    }

}
