package com.facemail.mobile.android.mail;

import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/25/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailRequestWrapper implements IMailRequest {
    private int taskTYpe;
    private IMailResponse iMailResponse;
    private UserAcount ua;
    private FaceMailMessage faceMailMessage;
    private MailTaskStatus mailTaskStatus;

    @Override
    public int getTaskType() {
        return taskTYpe;
    }

    @Override
    public void setTaskType(int type) {
        this.taskTYpe=type;
    }

    @Override
    public void setIMailResponse(IMailResponse iMailResponse) {
        this.iMailResponse=iMailResponse;
    }

    @Override
    public IMailResponse getIMailResponse() {
        return  this.iMailResponse;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setUserAccount(UserAcount userAccount) {
       ua=userAccount;
    }

    @Override
    public UserAcount getUserAccount() {
        return ua;
    }

    @Override
    public void setFaceMailMessage(FaceMailMessage faceMailMessage) {
        this.faceMailMessage=faceMailMessage;
    }

    @Override
    public FaceMailMessage getFaceMailMessage() {
        return this.faceMailMessage;
    }

    @Override
    public void setTaskStatus(MailTaskStatus mailTaskStatus) {
        this.mailTaskStatus=mailTaskStatus;

    }

    public MailTaskStatus getMailTaskStatus() {
        return mailTaskStatus;
    }
}
