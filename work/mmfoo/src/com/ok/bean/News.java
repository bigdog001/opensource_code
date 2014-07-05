package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-19
 * Time: 20:48:48
 */
public class News {
    private int newsid;
    private Timestamp createTime;
    private String title;
    private String content;
   // private int user_id;
    public News(){
     createTime=new Timestamp(System.currentTimeMillis());   
    }

    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
