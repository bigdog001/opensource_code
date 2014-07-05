package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-28
 * Time: 11:52:35
 */
public class UserSee {
    private int see_id;
    private int user_id;//发起关注的人的user_id;   主动关注着
    private int be_seeed_user_id;//被关注的人的user_id； 被关注这
    private Timestamp see_time;//发起关注的时间
    public UserSee(){
       see_time=new Timestamp(System.currentTimeMillis()); 
    }

    public int getSee_id() {
        return see_id;
    }

    public void setSee_id(int see_id) {
        this.see_id = see_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBe_seeed_user_id() {
        return be_seeed_user_id;
    }

    public void setBe_seeed_user_id(int be_seeed_user_id) {
        this.be_seeed_user_id = be_seeed_user_id;
    }

    public Timestamp getSee_time() {
        return see_time;
    }

    public void setSee_time(Timestamp see_time) {
        this.see_time = see_time;
    }
}
