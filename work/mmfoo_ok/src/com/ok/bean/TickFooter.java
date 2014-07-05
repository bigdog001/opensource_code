package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-28
 * Time: 12:47:13
 */
public class TickFooter {
    //投票足迹
    private int tickid;
    private int user_id;//这票是谁投的
    private int be_tick_user_id;//这票是投给谁的
    private Timestamp tick_time;//投票时间
    public TickFooter(){
     tick_time=new Timestamp(System.currentTimeMillis());   
    }

    public int getTickid() {
        return tickid;
    }

    public void setTickid(int tickid) {
        this.tickid = tickid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBe_tick_user_id() {
        return be_tick_user_id;
    }

    public void setBe_tick_user_id(int be_tick_user_id) {
        this.be_tick_user_id = be_tick_user_id;
    }

    public Timestamp getTick_time() {
        return tick_time;
    }

    public void setTick_time(Timestamp tick_time) {
        this.tick_time = tick_time;
    }
}
