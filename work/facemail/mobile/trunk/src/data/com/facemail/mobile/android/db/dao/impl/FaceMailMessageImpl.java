package com.facemail.mobile.android.db.dao.impl;

import com.facemail.mobile.android.db.bean.FaceMailMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/26/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.

 */
public interface FaceMailMessageImpl {
    public long insertMail(FaceMailMessage mail);
    public void deleteMail(FaceMailMessage mail);  //goto trash
    public FaceMailMessage getMail(int emailid);
    public List<FaceMailMessage> getMails(String mailAddress,int in_or_out); //根据邮件帐号读出此帐号下所有的邮件
    public int getMailNumber(String mailAddress);
    public void update_star(FaceMailMessage mail);
    public void update_unStar(FaceMailMessage mail);
    public void update_undelete(FaceMailMessage mail);
    public void update_real_delete(FaceMailMessage mail);//delete
    public boolean isExsit(String messageNumber,String mailAddress);
}
