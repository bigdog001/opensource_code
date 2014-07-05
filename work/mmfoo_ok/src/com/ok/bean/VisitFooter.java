package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-29
 * Time: 15:04:02
 */
public class VisitFooter {
    private int footerid;
    private int my_user_id;   //  我的空间
    private int friends_user_id;// 被这个人访问了
    private Timestamp visit_time;  //访问时间
    public VisitFooter(){
       visit_time=new Timestamp(System.currentTimeMillis()); 
    }
    public int getFooterid() {
        return footerid;
    }

    public void setFooterid(int footerid) {
        this.footerid = footerid;
    }

    public int getMy_user_id() {
        return my_user_id;
    }

    public void setMy_user_id(int my_user_id) {
        this.my_user_id = my_user_id;
    }

    public int getFriends_user_id() {
        return friends_user_id;
    }

    public void setFriends_user_id(int friends_user_id) {
        this.friends_user_id = friends_user_id;
    }

    public Timestamp getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(Timestamp visit_time) {
        this.visit_time = visit_time;
    }
}
