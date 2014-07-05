package com.facemail.mobile.android.db.bean;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/29/13
 * Time: 6:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class FaceAddress {
    private  String emailAddress;
    private String emailDesc;
    private String address_source_email;        //此address是来自哪个邮箱账户的 ,即是被哪个帐号接收的邮件
    private String messagenumber;          //此帐号对应的邮件签名号，唯一的
    private String type="";            //cc ,bcc or to or replyto or from and so on
    private int _id;

    public FaceAddress(){

    }
    public FaceAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessagenumber() {
        return messagenumber;
    }

    public void setMessagenumber(String messagenumber) {
        this.messagenumber = messagenumber;
    }

    public String getAddress_source_email() {
        return address_source_email;
    }

    public void setAddress_source_email(String address_source_email) {
        this.address_source_email = address_source_email;
    }

    public String getEmailDesc() {
        return emailDesc;
    }

    public void setEmailDesc(String emailDesc) {
        this.emailDesc = emailDesc;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
