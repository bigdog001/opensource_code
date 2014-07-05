package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-22
 * Time: 22:24:46
 */
public class UserFriends {
    private int ufid;
    private int friendsid;     // 此用戶所具有的好友的id
    private int user_id;   //用戶的主字段
    private Timestamp friendstime; //成為好友的時間
    private String friends_flag;//是否同意此人作為自己的好友
    public  UserFriends(){
        friends_flag="N";
        friendstime=new  Timestamp(System.currentTimeMillis());
    }

    public int getUfid() {
        return ufid;
    }

    public void setUfid(int ufid) {
        this.ufid = ufid;
    }

    public int getFriendsid() {
        return friendsid;
    }

    public void setFriendsid(int friendsid) {
        this.friendsid = friendsid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getFriendstime() {
        return friendstime;
    }

    public void setFriendstime(Timestamp friendstime) {
        this.friendstime = friendstime;
    }

    public String getFriends_flag() {
        return friends_flag;
    }

    public void setFriends_flag(String friends_flag) {
        this.friends_flag = friends_flag;
    }
}
