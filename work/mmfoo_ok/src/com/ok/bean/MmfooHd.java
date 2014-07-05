package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-22
 * Time: 13:57:10
 */
public class MmfooHd {
    private int hdid;
    private int intrest_cnt;
    private int orders;
    private int visit_cnt;
    private int starter_user_id;
    private int discus_cnt;
    private String payfeed;
    private String hd_thumb;
    private String jiangxian;
    private String hdname;
    private String hdface;
    private String hdtitle;
    private String hdabstract;
    private String hdcontent;
    private Timestamp starttime;
    private Timestamp endtime;
    private Timestamp createtime;
    public String toString() {
        return "hdid is:" + hdid +
                "hdname is:" + hdname +
                "hdtitle is:" + hdtitle +
                "hdabstract is:" + hdabstract +
                "hdcontent is:" + hdcontent +
                "starttime is" + starttime +
                "endtime is:" + endtime;
    }
     public MmfooHd(){
         visit_cnt=0;
         discus_cnt=0;
        createtime=new Timestamp(System.currentTimeMillis());
    }

    public String getHd_thumb() {
        return hd_thumb;
    }

    public void setHd_thumb(String hd_thumb) {
        this.hd_thumb = hd_thumb;
    }

    public int getStarter_user_id() {
        return starter_user_id;
    }

    public void setStarter_user_id(int starter_user_id) {
        this.starter_user_id = starter_user_id;
    }

    public String getPayfeed() {
        return payfeed;
    }

    public void setPayfeed(String payfeed) {
        this.payfeed = payfeed;
    }

    public String getJiangxian() {
        return jiangxian;
    }

    public void setJiangxian(String jiangxian) {
        this.jiangxian = jiangxian;
    }

    public int getVisit_cnt() {
        return visit_cnt;
    }

    public void setVisit_cnt(int visit_cnt) {
        this.visit_cnt = visit_cnt;
    }

    public int getIntrest_cnt() {
        return intrest_cnt;
    }

    public void setIntrest_cnt(int intrest_cnt) {
        this.intrest_cnt = intrest_cnt;
    }

    public int getDiscus_cnt() {
        return discus_cnt;
    }

    public void setDiscus_cnt(int discus_cnt) {
        this.discus_cnt = discus_cnt;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public int getHdid() {
        return hdid;
    }

    public void setHdid(int hdid) {
        this.hdid = hdid;
    }

    public String getHdname() {
        return hdname;
    }

    public String getHdface() {
        return hdface;
    }

    public void setHdface(String hdface) {
        this.hdface = hdface;
    }

    public void setHdname(String hdname) {
        this.hdname = hdname;
    }

    public String getHdtitle() {
        return hdtitle;
    }

    public void setHdtitle(String hdtitle) {
        this.hdtitle = hdtitle;
    }

    public String getHdabstract() {
        return hdabstract;
    }

    public void setHdabstract(String hdabstract) {
        this.hdabstract = hdabstract;
    }

    public String getHdcontent() {
        return hdcontent;
    }

    public void setHdcontent(String hdcontent) {
        this.hdcontent = hdcontent;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }
}
