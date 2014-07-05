package com.facemail.mobile.android.db.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/25/13
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class FaceMailMessage extends BaseBean implements Comparable<FaceMailMessage>,Serializable {
    private int _id;
    private String mail_title;
    private String mail_content="";
    private String mail_content_type;
    private String mail_from="";    //如果邮件是被使用者在手机上发送出去的，此值为发件帐号的邮件地址
    private String mail_from_desc;
    private String mail_is_attach="0";//是否含有附件0 无 1 有
    private String mail_attach_filename="";//附件文件名,附件统一放在一个独立的目录下,各个邮箱建立自身独立的dir
    private String to="";
    private String to_cc="";
    private String to_bcc="";
    private String rePlyto="";
    private String mail_user_name;
    private String mail_READ_STATUS;
    private Date mail_rcv_time;
    private Date mail_send_time;
    private String isRecycleBin="0";
    private String isStarMail="0";
    private Date RecycleBinTime;
    private String messageNumber;
    private List<FaceAddress> faceAddresses;

    //和撰写以及发送邮件有关的属性=======================================
    private int in_or_out = 0;   //此邮件是接收到的邮件还是需要发送出去的邮件（回复的或新撰写的），0为收到的，1为发送出去的


    //=======================================和撰写以及发送邮件有关的属性

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Date getMail_send_time() {
        return mail_send_time;
    }

    public void setMail_send_time(Date mail_send_time) {
        this.mail_send_time = mail_send_time;
    }

    public List<FaceAddress> getFaceAddresses() {
        return faceAddresses;
    }

    public void setFaceAddresses(List<FaceAddress> faceAddresses) {
        this.faceAddresses = faceAddresses;
    }
    @Deprecated
    public String getMail_content_type() {
        return mail_content_type;
    }
    @Deprecated
    public void setMail_content_type(String mail_content_type) {
        this.mail_content_type = mail_content_type;
    }
    @Deprecated
    public String getMail_from_desc() {
        return mail_from_desc;
    }
    @Deprecated
    public void setMail_from_desc(String mail_from_desc) {
        this.mail_from_desc = mail_from_desc;
    }
    @Deprecated
    public String getMail_is_attach() {
        return mail_is_attach;
    }
    @Deprecated
    public void setMail_is_attach(String mail_is_attach) {
        this.mail_is_attach = mail_is_attach;
    }

    public String getMail_attach_filename() {
        return mail_attach_filename;
    }

    public void setMail_attach_filename(String mail_attach_filename) {
        this.mail_attach_filename = mail_attach_filename;
    }

    public String getRePlyto() {
        return rePlyto;
    }

    public void setRePlyto(String rePlyto) {
        this.rePlyto = rePlyto;
    }

    public String getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getTo_cc() {
        return to_cc;
    }

    public void setTo_cc(String to_cc) {
        this.to_cc = to_cc;
    }

    public String getTo_bcc() {
        return to_bcc;
    }

    public void setTo_bcc(String to_bcc) {
        this.to_bcc = to_bcc;
    }

    public String getRecycleBin() {
        return isRecycleBin;
    }

    public void setRecycleBin(String recycleBin) {
        isRecycleBin = recycleBin;
    }

    public String getStarMail() {
        return isStarMail;
    }

    public void setStarMail(String starMail) {
        isStarMail = starMail;
    }

    public Date getRecycleBinTime() {
        return RecycleBinTime;
    }

    public  void setRecycleBinTime(Date recycleBinTime) {
        RecycleBinTime = recycleBinTime;
    }

    public  Date getMail_rcv_time() {
        return mail_rcv_time;
    }

    public  void setMail_rcv_time(Date mail_rcv_time) {
        this.mail_rcv_time = mail_rcv_time;
    }

    public  String getMail_title() {
        return mail_title;
    }

    public  void setMail_title(String mail_title) {
        this.mail_title = mail_title;
    }

    public  String getMail_content() {
        return mail_content;
    }

    public void setMail_content(String mail_content) {
        this.mail_content = mail_content;
    }
    @Deprecated
    public String getMail_from() {
        return mail_from;
    }
    @Deprecated
    public  void setMail_from(String mail_from) {
        this.mail_from = mail_from;
    }

    public  String getTo() {
        return to;
    }

    public  void setTo(String to) {
        this.to = to;
    }

    public  String getMail_user_name() {
        return mail_user_name;
    }

    public  void setMail_user_name(String mail_user_name) {
        this.mail_user_name = mail_user_name;
    }


    public String getMail_READ_STATUS() {
        return mail_READ_STATUS;
    }

    public void setMail_READ_STATUS(String mail_READ_STATUS) {
        this.mail_READ_STATUS = mail_READ_STATUS;
    }

    @Override
    public int compareTo(FaceMailMessage another) {
        return this.getMail_title().compareTo(another.getMail_title());
    }

    @Override
    public String toString() {
        return "FaceMailMessage{" +
                "_id=" + _id +
                ", mail_title='" + mail_title + '\'' +
                ", mail_content='" + mail_content + '\'' +
                ", mail_content_type='" + mail_content_type + '\'' +
                ", mail_from='" + mail_from + '\'' +
                ", mail_from_desc='" + mail_from_desc + '\'' +
                ", mail_is_attach='" + mail_is_attach + '\'' +
                ", mail_attach_filename='" + mail_attach_filename + '\'' +
                ", to='" + to + '\'' +
                ", to_cc='" + to_cc + '\'' +
                ", to_bcc='" + to_bcc + '\'' +
                ", rePlyto='" + rePlyto + '\'' +
                ", mail_user_name='" + mail_user_name + '\'' +
                ", mail_READ_STATUS='" + mail_READ_STATUS + '\'' +
                ", mail_rcv_time=" + mail_rcv_time +
                ", mail_send_time=" + mail_send_time +
                ", isRecycleBin='" + isRecycleBin + '\'' +
                ", isStarMail='" + isStarMail + '\'' +
                ", RecycleBinTime=" + RecycleBinTime +
                ", messageNumber='" + messageNumber + '\'' +
                ", faceAddresses=" + faceAddresses +
                '}';
    }
}