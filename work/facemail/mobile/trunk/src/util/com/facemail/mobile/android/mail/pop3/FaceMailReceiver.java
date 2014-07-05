package com.facemail.mobile.android.mail.pop3;

import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.UserAccountDao;
import com.facemail.mobile.android.mail.BaseReceiver;
import com.facemail.mobile.android.mail.IMailResponse;
import com.facemail.mobile.android.util.SystemUtil;
import com.facemail.mobile.android.util.json.JsonObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/24/13
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class FaceMailReceiver extends BaseReceiver {
    private ReceiveListener receiveListener;

    public UserAcount getUserAcount() {
        return userAcount;
    }

    public void setUserAcount(UserAcount userAcount) {
        this.userAcount = userAcount;
    }

    public ReceiveListener getReceiveListener() {
        return receiveListener;
    }

    public void setReceiveListener(ReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }

    public FaceMailReceiver(UserAcount userAcount) {
        this.userAcount = userAcount;
    }
    public void doReceive() {
        UserAccountDao.getInstance(FaceMailApplication.getFaceMailContext()).updateRcvTime(new Date(),userAcount);
        try {
//            session
            store = session.getStore("pop3");
//            store.connect("pop.gmail.com","jiunian.wang@gmail.com", "c1b22a333"); // 返回文件夹对象
            store.connect(userAcount.getUser_pop3(),userAcount.getUser_name(), userAcount.getUser_pwd()); // 返回文件夹对象
            folder = store.getFolder("INBOX"); // 设置仅读
            store.getPersonalNamespaces();
            folder.open(Folder.READ_ONLY); // 获取信息

            messages = folder.getMessages();
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.FLAGS);
            fp.add("X-Mailer");
            folder.fetch(messages, fp);
            if (receiveListener!=null) {
                receiveListener.doReceive(messages);
            }
        } catch (NoSuchProviderException e) {
            if (receiveListener!=null) {
            receiveListener.onError(new String[]{e.getMessage(), Config.MAIL_RCV_NOPROVIDER});
            }
            SystemUtil.log("faceMessage001："+e.getMessage());
        } catch (MessagingException e) {
            if (receiveListener!=null) {
                receiveListener.onError(new String[]{e.getMessage(), Config.MAIL_RCV_MESSAGE_ERROR});
            }
            SystemUtil.log("faceMessage002：" + e.getMessage());
        }
        if (folder!=null) {
            try {
                folder.close(false);
            } catch (MessagingException e) {
                if (receiveListener!=null) {
                    receiveListener.onError(new String[]{e.getMessage(), Config.MAIL_RCV_ERROR_COLOSE_ERROR});
                }

                SystemUtil.log("FaceMailReceiver.doReceive1 error："+e.getMessage());
            }
        }
        if (store!=null) {
            try {
                store.close();
            } catch (MessagingException e) {
                if (receiveListener!=null) {
                    receiveListener.onError(new String[]{e.getMessage(), Config.MAIL_RCV_ERROR_COLOSE_ERROR});
                }
                SystemUtil.log("FaceMailReceiver.doReceive2 error："+e.getMessage());
            }
        }
    }



    public interface ReceiveListener{
        public void doReceive(javax.mail.Message []messages);
        public void onError(String[] ojb);
    }
}
