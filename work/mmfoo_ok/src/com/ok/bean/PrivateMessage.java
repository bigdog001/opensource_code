package com.ok.bean;

import java.sql.Timestamp;

/**
 * 私信类
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-9
 * Time: 20:31:04
 */
public class PrivateMessage {
    private int pmid;
    private Timestamp createtime;
    private int user_id_to;
    private int pm_return;
    private int user_id_from;
    private String is_read;
    private String pm_content;

    public PrivateMessage() {
        createtime = new Timestamp(System.currentTimeMillis());
        is_read = "N";
        pm_return = 0;
    }

    public int getPmid() {
        return pmid;
    }

    public void setPmid(int pmid) {
        this.pmid = pmid;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public int getUser_id_to() {
        return user_id_to;
    }

    public int getPm_return() {
        return pm_return;
    }

    public void setPm_return(int pm_return) {
        this.pm_return = pm_return;
    }

    public void setUser_id_to(int user_id_to) {
        this.user_id_to = user_id_to;
    }

    public int getUser_id_from() {
        return user_id_from;
    }

    public void setUser_id_from(int user_id_from) {
        this.user_id_from = user_id_from;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getPm_content() {
        return pm_content;
    }

    public void setPm_content(String pm_content) {
        this.pm_content = pm_content;
    }
}
