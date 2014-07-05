package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-22
 * Time: 14:01:18
 */
public class UserHd {
    private int ids;
    private int user_id;
    private String is_ok;
    private int hdid;
    private Timestamp  jointime;
    private String toStirng(){
        return"ids is:"+ids+
                "user_id is:"+user_id+
                "hdid is:"+hdid+
                "jointime is:"+jointime;
    }
     public UserHd(){
        jointime=new Timestamp(System.currentTimeMillis());
         is_ok="N";
     }
    public int getIds() {
        return ids;
    }

    public String getIs_ok() {
        return is_ok;
    }

    public void setIs_ok(String is_ok) {
        this.is_ok = is_ok;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getHdid() {
        return hdid;
    }

    public void setHdid(int hdid) {
        this.hdid = hdid;
    }

    public Timestamp getJointime() {
        return jointime;
    }

    public void setJointime(Timestamp jointime) {
        this.jointime = jointime;
    }
}
