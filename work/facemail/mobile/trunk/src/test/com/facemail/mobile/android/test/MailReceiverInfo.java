package com.facemail.mobile.android.test;

import java.io.File;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/28/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailReceiverInfo { //邮件服务器IP和端口和协议
    private String mailServerHost;
    private String mailServerPost;
    private String protpcal = "pop3";
    //登录邮件服务器的用户名和密码
    private String userName;
    private String passWord;
    //保存邮件的路径
    private String attchmentDir = "";
    private String emailDir = "";
    private String emailFileSuffix = ".eml";
    //是否要确认身份
    private boolean vaildate = true;
    //获得邮件的属性初始化
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.pop3.host", mailServerHost);
        properties.put("mail.pop3.port", mailServerPost);
        properties.put("mail.pop3.auth", vaildate ? "true" : "false");
        return properties;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPost() {
        return mailServerPost;
    }

    public void setMailServerPost(String mailServerPost) {
        this.mailServerPost = mailServerPost;
    }

    public String getProtpcal() {
        return protpcal;
    }

    public void setProtpcal(String protpcal) {
        this.protpcal = protpcal;
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

    public String getAttchmentDir() {
        return attchmentDir;
    }

    public void setAttchmentDir(String attchmentDir) {
        if (!attchmentDir.endsWith(File.separator)) {
            attchmentDir = attchmentDir + File.separator;
        }
        this.attchmentDir = attchmentDir;
    }

    public String getEmailDir() {
        return emailDir;
    }

    public void setEmailDir(String emailDir) {
        if (!emailDir.endsWith(File.separator)) {
            emailDir = emailDir + File.separator;
        }
        this.emailDir = emailDir;
    }

    public String getEmailFileSuffix() {
        return emailFileSuffix;
    }

    public void setEmailFileSuffix(String emailFileSuffix) {
        if (!emailFileSuffix.endsWith(File.separator)) {
            emailFileSuffix = emailFileSuffix + File.separator;
        }
        this.emailFileSuffix = emailFileSuffix;
    }

    public boolean isVaildate() {
        return vaildate;
    }

    public void setVaildate(boolean vaildate) {
        this.vaildate = vaildate;
    }

}
