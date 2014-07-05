package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-22
 * Time: 13:06:26
 */
public class ImgSet {
    private int setid;
    private String setname;        
    private int user_id;
    private String setdiscribe;
    private String setface;
    private Timestamp createtime;

    public String toString(){
        return "setid is:"+setid+
                "setname is:"+setname+
                "user_id is:"+user_id+
                "createtime is:"+createtime+
                "setface is:"+setface+
                "setdiscribe is:"+setdiscribe;
    }
    public ImgSet(){
     createtime=new Timestamp(System.currentTimeMillis());   
    }

    public String getSetface() {
        return setface;
    }

    public void setSetface(String setface) {
        this.setface = setface;
    }

    public String getSetdiscribe() {
        return setdiscribe;
    }

    public void setSetdiscribe(String setdiscribe) {
        this.setdiscribe = setdiscribe;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public int getSetid() {
        return setid;
    }

    public void setSetid(int setid) {
        this.setid = setid;
    }

    public String getSetname() {
        return setname;
    }

    public void setSetname(String setname) {
        this.setname = setname;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
