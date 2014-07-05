package com.facemail.mobile.android.test;

import javax.mail.PasswordAuthentication;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/28/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyAuthenticator {

    private String userName;
    private String passWord;

    public MyAuthenticator(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, passWord);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
