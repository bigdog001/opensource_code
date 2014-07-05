package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-23
 * Time: 0:06:13
 */
public class Comment {
    private int c_id;
    private String content;
    private int userid;
    private int user_set_onwer;
    private int setid;
    private Timestamp  createtime;
    public Comment(){
        createtime=new Timestamp(System.currentTimeMillis());
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUser_set_onwer() {
        return user_set_onwer;
    }

    public void setUser_set_onwer(int user_set_onwer) {
        this.user_set_onwer = user_set_onwer;
    }

    public int getSetid() {
        return setid;
    }

    public void setSetid(int setid) {
        this.setid = setid;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
