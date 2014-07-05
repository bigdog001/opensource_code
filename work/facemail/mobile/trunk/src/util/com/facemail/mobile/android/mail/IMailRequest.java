package com.facemail.mobile.android.mail;

import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/25/13
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IMailRequest {
    //一次作业，包括接收和发送,用 TASK_Type标志位来断定
    public int getTaskType();
    public void setTaskType(int type);
    public void setIMailResponse(IMailResponse iMailResponse);
    public IMailResponse getIMailResponse();
    public void setUserAccount(UserAcount userAccount);
    public UserAcount getUserAccount();
    public void setFaceMailMessage(FaceMailMessage faceMailMessage);
    public FaceMailMessage getFaceMailMessage();
    public void setTaskStatus(MailTaskStatus mailTaskStatus);

}
