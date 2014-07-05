package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-12
 * Time: 12:27:10
 */
public class Blacker {
    private int blackid;
    private int my_user_id;
    private int blacker_user_id;
    private Timestamp createtime;
    public Blacker(){
      createtime=new Timestamp(System.currentTimeMillis());  
    }
    public int getBlackid() {
        return blackid;
    }

    public void setBlackid(int blackid) {
        this.blackid = blackid;
    }

    public int getMy_user_id() {
        return my_user_id;
    }

    public void setMy_user_id(int my_user_id) {
        this.my_user_id = my_user_id;
    }

    public int getBlacker_user_id() {
        return blacker_user_id;
    }

    public void setBlacker_user_id(int blacker_user_id) {
        this.blacker_user_id = blacker_user_id;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
