package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-22
 * Time: 13:49:57
 */
public class ShortMessage {
    private int mesageid;
    private int user_id;    //消息发送者的email
    private String ownertruename;
    private String messagecontent;
    private Timestamp messagetime;


    public String toStirng() {
        return "mesageid is:" + mesageid +
                "user_id is:" + user_id +
                "ownertruename is:" + ownertruename +
                "messagecontent is:" + messagecontent +
                "messagetime is:" + messagetime;
    }
   public  ShortMessage(){
       messagetime=new Timestamp(System.currentTimeMillis());
   }


    public int getMesageid() {
        return mesageid;
    }

    public void setMesageid(int mesageid) {
        this.mesageid = mesageid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOwnertruename() {
        return ownertruename;
    }

    public void setOwnertruename(String ownertruename) {
        this.ownertruename = ownertruename;
    }

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }

    public Timestamp getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(Timestamp messagetime) {
        this.messagetime = messagetime;
    }
}
