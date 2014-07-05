package com.facemail.mobile.android.mail;

import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.util.json.JsonValue;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/25/13                         INetRequest req, JsonValue obj
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IMailResponse {
   public String EMAIL="email";
   public void onReceive(IMailRequest iMailRequest,JsonValue obj);
   public void onReceiveOne(IMailRequest iMailRequest,JsonValue obj);
   public void onReceiveFirst(IMailRequest iMailRequest,int total);
   public void onReceiveError(IMailRequest iMailRequest,String errorMessage);
}
